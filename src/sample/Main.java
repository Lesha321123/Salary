package sample;

import DB.DbConnector;
import DB.Project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        primaryStage.setTitle("Salary");
        primaryStage.setScene(new Scene(root, 880, 510));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        // Создаем экземпляр по работе с БД
        DbConnector dbConnector = DbConnector.getInstance();

        // Добавляем запись
        //dbConnector.addProject(new Project(19,"г. Кострома ", "kostroma"));
        //dbConnector.addProject(new Project(20,"арзамас", "arzamas"));


        launch(args);

        // Удаление записи с id = 19
        //dbConnector.deleteProject(19);
        //dbConnector.deleteProject(20);



    }
}
