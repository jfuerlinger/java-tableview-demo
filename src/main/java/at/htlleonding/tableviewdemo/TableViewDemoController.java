package at.htlleonding.tableviewdemo;

import at.htlleonding.tableviewdemo.model.Person;
import at.htlleonding.tableviewdemo.model.PersonRepository;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewDemoController {

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, Integer> ageColumn;

    @FXML
    private TableColumn<Person, Boolean> activeColumn;
    
    @FXML
    private TextField filterField;
    
    // ObservableList als UI-Datenquelle (Controller-Verantwortung)
    private final ObservableList<Person> masterData;
    
    /**
     * Konstruktor: Konvertiert die normalen Daten aus dem Model in eine UI-freundliche ObservableList
     */
    public TableViewDemoController() {

        // Konvertieren der normalen ArrayList<Person> in eine ObservableList für JavaFX
        masterData = FXCollections.observableArrayList(
                PersonRepository.getInstance().getAllPersons());
    }
    
    /**
     * Initialisierungsmethode, die von JavaFX aufgerufen wird, nachdem alle FXML-Elemente injiziert wurden.
     * Hier konfigurieren wir die TableView, ihre Spalten und die Filterlogik.
     */
    public void initialize() {
        // =========== SCHRITT 1: Konfiguration der Tabellenspalten ===========
        
        // PropertyValueFactory ist eine einfache Implementierung für CellValueFactory,
        // die den Wert basierend auf dem angegebenen Property-Namen des Objekts ermittelt.
        // Wichtig: Die Namen müssen genau mit den Getter-Methoden in der Person-Klasse übereinstimmen.
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName")); // verwendet getFirstName()
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));   // verwendet getLastName()
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));             // verwendet getAge()
        
        // =========== SCHRITT 2: Spezielle Konfiguration für die Checkbox-Spalte ===========
        
        // Für Checkboxen brauchen wir eine spezielle Konfiguration:
        // 1. Der CellValueFactory gibt eine Observable-Property zurück (nicht direkt einen Wert)
        // 2. Diese Property wird automatisch mit dem UI-Element (CheckBox) verbunden
        activeColumn.setCellValueFactory(cellData -> 
            // Erstellt für jede Zeile eine neue BooleanProperty, die den active-Wert der Person enthält
            new SimpleBooleanProperty(cellData.getValue().isActive()));
        
        // CheckBoxTableCell wird als Cell-Factory verwendet, um Checkboxen in der Spalte darzustellen
        // Das activeColumn-Argument wird benötigt, um die Checkboxen mit den Datenwerten zu verbinden
        activeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(activeColumn));
        
        // =========== SCHRITT 3: Filterung implementieren ===========
        
        // FilteredList ist ein Wrapper für unsere Originaldaten, der Elemente basierend auf einem Prädikat anzeigt
        // Das anfängliche Prädikat (p -> true) zeigt alle Elemente an
        FilteredList<Person> filteredData = new FilteredList<>(masterData, p -> true);
    
        // Listener für Änderungen im Filterfeld
        // Jedes Mal, wenn der Text im Textfeld geändert wird, wird das Prädikat neu gesetzt
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // Wenn das Filterfeld leer ist, zeigen wir alle Personen an
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
            
                // Text aus dem Filterfeld in Kleinbuchstaben umwandeln für case-insensitive Suche
                String lowerCaseFilter = newValue.toLowerCase();
            
                // Nur Personen anzeigen, deren Nachname den Filtertext enthält
                // contains() prüft, ob der Filtertext irgendwo im Nachnamen vorkommt
                return person.getLastName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // =========== SCHRITT 4: Sortierung implementieren ===========
        
        // SortedList ist ein Wrapper für unsere gefilterte Liste, der Elemente sortiert anzeigt
        SortedList<Person> sortedData = new SortedList<>(filteredData);
        
        // Verbindet den Comparator der SortedList mit dem der TableView
        // Dies ermöglicht das Sortieren durch Klicken auf die Spaltenüberschriften
        sortedData.comparatorProperty().bind(personTable.comparatorProperty());
        
        // Fügt die sortierte (und gefilterte) Liste als Datenquelle zur TableView hinzu
        personTable.setItems(sortedData);



        // Escape-Taste zum Zurücksetzen des Filters
        filterField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                filterField.clear(); // Löscht den Inhalt des Filterfelds
            }
        });
    }

    /**
     * Behandelt das Klicken des Schließen-Buttons
     */
    @FXML
    private void handleCloseButtonAction() {
        javafx.application.Platform.exit();
    }
}