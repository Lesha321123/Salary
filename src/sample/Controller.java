package sample;

import DB.DbConnector;
import DB.Price;
import DB.Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;


public class Controller {

    @FXML
    private Button buttonAddProject;
    @FXML
    private Button buttonDeleteProject;
    @FXML
    private Button buttonCleanProject;
    @FXML
    private Button buttonAddPrice;

    @FXML
    private Button ButtonDeletePrice;
    @FXML
    private Button buttonCleanPrice;
    @FXML
    private Button buttonCalcSalary;

    @FXML
    private Label labelTextSalary;

    @FXML
    private TableView<Project> tableProject;
    @FXML
    private TableColumn<Project, Integer> idProjectColumn;
    @FXML
    private TableColumn<Project, String> nameProjectColumn;
    @FXML
    private TableColumn<Project, String> typeProjectColumn;
    @FXML
    private TableColumn<Project, Integer> priceProjectColumn;

    @FXML
    private TextField addNameProject;
    @FXML
    private TextField addRegion;
    @FXML
    private TextField addPriceProject;

    @FXML
    private TableView<Price> tablePrice;
    @FXML
    private TableColumn<Price, Integer> idPriceColumn;
    @FXML
    private TableColumn<Price, String> typePriceColumn;
    @FXML
    private TableColumn<Price, Integer> priceColumn;

    @FXML
    private ComboBox addTypeProject  = new ComboBox();


    DbConnector connector = DbConnector.getInstance();

    // инициализируем форму данными
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        addTypeProject.setItems(connector.addTypeProjects());

        tablePrice.setItems(connector.getAllTypeProjects());
        // устанавливаем тип и значение которое должно хранится в колонке
        idPriceColumn.setCellValueFactory(new PropertyValueFactory<Price, Integer>("id"));

        typePriceColumn.setCellValueFactory(new PropertyValueFactory<Price, String>("region"));

        //Методы для выделенной строки
        TableView.TableViewSelectionModel<Price> selectionModel = tablePrice.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Price>(){
            @Override
            public void changed(ObservableValue<? extends Price> observable, Price oldValue, Price newValue) {
                System.out.println("newValue " + newValue);

            }


        });


        //методы для редактирования строки
        typePriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typePriceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Price, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Price, String> t) {
                        ((Price) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRegion(t.getNewValue());
                        System.out.println(t.getTablePosition().getRow());
                    }
                }
        );


        priceColumn.setCellValueFactory(new PropertyValueFactory<Price, Integer>("price"));


        // устанавливаем тип и значение которое должно хранится в колонке
        tableProject.setItems(connector.getAllProjects());
        idProjectColumn.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        nameProjectColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
        typeProjectColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("region"));
        priceProjectColumn.setCellValueFactory(new PropertyValueFactory<Project, Integer>("price"));
        //устанавливается общая зарплата
        labelTextSalary.setText(String.valueOf(connector.getSalary()));
    }

    public Controller() throws SQLException {
    }


    public void addPrice(ActionEvent actionEvent) throws SQLException {
            connector.addPrice(new Price(
                    addRegion.getText(),
                    Integer.parseInt(addPriceProject.getText())
            ));
            addRegion.clear();
            addPriceProject.clear();
            tablePrice.setItems(connector.getAllTypeProjects());
            addTypeProject.setItems(connector.addTypeProjects());
    }

    public void addProject(ActionEvent actionEvent) {
        connector.addProject(new Project(
                addNameProject.getText(),
                (String) addTypeProject.getValue()
        ));
        addNameProject.clear();
        tableProject.setItems(connector.getAllProjects());
        labelTextSalary.setText(String.valueOf(connector.getSalary()));
    }

    public void deletePrice(ActionEvent actionEvent) {
        int rowIndex = tablePrice.getSelectionModel().getSelectedIndex();
        ObservableList rowList = (ObservableList) tablePrice.getItems().get(rowIndex);
        int columnIndex = 0;
        int value = Integer.parseInt(rowList.get(columnIndex).toString());
        System.out.println(value);
    }
}
