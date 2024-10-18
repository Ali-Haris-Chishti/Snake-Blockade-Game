module org.example.snakegame {
    requires javafx.controls;
    requires javafx.media;


    opens org.example.snakegame to javafx.fxml;
    exports org.example.snakegame;
}