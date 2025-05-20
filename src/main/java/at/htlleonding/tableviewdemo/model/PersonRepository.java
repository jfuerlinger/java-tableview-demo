package at.htlleonding.tableviewdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository-Klasse für Person-Objekte.
 * Funktioniert als Model im MVC-Pattern und stellt die Daten bereit.
 */
public class PersonRepository {
    
    // Private Instanzvariable für das Singleton-Pattern
    private static PersonRepository instance;
    
    // Daten als normale ArrayList - keine UI-spezifischen Klassen im Model
    private final List<Person> personData;
    
    /**
     * Privater Konstruktor für Singleton-Pattern.
     * Initialisiert die Personen-Daten mit Beispielpersonen.
     */
    private PersonRepository() {
        // Beispieldaten erstellen mit normaler ArrayList
        personData = new ArrayList<>();
        
        // Ursprüngliche Beispieldaten
        personData.add(new Person("John", "Doe", 30, true));
        personData.add(new Person("Jane", "Doe", 25, false));
        personData.add(new Person("Mike", "Smith", 40, true));
        personData.add(new Person("Anna", "Smith", 35, false));
        
        // 100 weitere Beispieldatensätze
        addExampleData();
    }
    
    /**
     * Fügt 100 weitere Beispieldatensätze hinzu.
     */
    private void addExampleData() {
        // Vornamen für Beispieldaten
        String[] firstNames = {
            "Emma", "Noah", "Olivia", "Liam", "Ava", "William", "Sophia", "Mason", 
            "Isabella", "James", "Mia", "Benjamin", "Charlotte", "Jacob", "Amelia", 
            "Michael", "Harper", "Elijah", "Evelyn", "Ethan", "Abigail", "Alexander", 
            "Emily", "Daniel", "Elizabeth", "Matthew", "Sofia", "Aiden", "Ella", "Henry", 
            "Avery", "Joseph", "Scarlett", "Jackson", "Grace", "Samuel", "Chloe", "Sebastian", 
            "Victoria", "David", "Riley", "Carter", "Aria", "Wyatt", "Lily", "Jayden", 
            "Aubrey", "John", "Zoey", "Owen"
        };
        
        // Nachnamen für Beispieldaten
        String[] lastNames = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", 
            "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", 
            "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", 
            "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", 
            "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", 
            "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", 
            "Parker", "Evans", "Edwards", "Collins"
        };
        
        // Zufällige Auswahl für die Erstellung der Beispieldaten
        java.util.Random random = new java.util.Random(123); // Fester Seed für reproduzierbare Ergebnisse
        
        // 100 Beispieldatensätze erstellen
        for (int i = 0; i < 100; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            int age = 18 + random.nextInt(65); // Alter zwischen 18 und 82
            boolean active = random.nextBoolean();
            
            personData.add(new Person(firstName, lastName, age, active));
        }
    }
    
    /**
     * Gibt die einzige Instanz des Repositories zurück (Singleton-Pattern).
     * @return Die PersonRepository-Instanz
     */
    public static synchronized PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }
    
    /**
     * Gibt alle Personen zurück.
     * @return Liste aller Personen
     */
    public List<Person> getAllPersons() {
        return personData;
    }
    
    /**
     * Fügt eine neue Person hinzu.
     * @param person Die hinzuzufügende Person
     */
    public void addPerson(Person person) {
        personData.add(person);
    }
    
    /**
     * Entfernt eine Person.
     * @param person Die zu entfernende Person
     * @return true wenn erfolgreich, sonst false
     */
    public boolean removePerson(Person person) {
        return personData.remove(person);
    }
}
