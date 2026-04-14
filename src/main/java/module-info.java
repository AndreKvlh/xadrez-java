module xadrez.java { // Use pontos ou underscores, nunca hífens aqui
    requires javafx.controls;
    requires javafx.fxml;
    opens com.xadrez.project.xadrez_java to javafx.graphics, javafx.fxml;
    exports com.xadrez.project.xadrez_java;
}