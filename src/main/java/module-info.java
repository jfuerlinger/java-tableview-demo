module at.htlleonding.observerdemo.tableviewdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htlleonding.tableviewdemo to javafx.fxml;
    exports at.htlleonding.tableviewdemo;
    exports at.htlleonding.tableviewdemo.model;
    opens at.htlleonding.tableviewdemo.model to javafx.fxml;
}