package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.*;

public class DbConnector {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:src\\DB\\project.db";

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbConnector instance = null;

    public static synchronized DbConnector getInstance() throws SQLException {
        if (instance == null)
            instance = new DbConnector();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    public DbConnector() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public ObservableList<Project> getAllProjects() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            ObservableList<Project> projects = FXCollections.observableArrayList();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("select project.id, project.name, project.region, price.price from project, price where project.region = price.region");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                projects.add(new Project(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("region"),
                        resultSet.getInt("price")));
            }
            // Возвращаем наш список
            return projects;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return null;
        }
    }

    public ObservableList<Price> getAllTypeProjects()  throws SQLException {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            ObservableList<Price> prices = FXCollections.observableArrayList();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT * FROM price");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                prices.add(new Price(resultSet.getInt("id"),
                        resultSet.getString("region"),
                        resultSet.getInt("price")));
            }
            // Возвращаем наш список
            return prices;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return null;
        }
    }

    public String getSalary() {
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {

            String salary = " ";
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            statement.executeUpdate("drop table salary");
            statement.executeUpdate("create table salary as select price.price from project join price on project.region = price.region;");
            ResultSet resultSet = statement.executeQuery("select sum(price) from salary;");
            // заносим данные в salary
            salary = resultSet.getString(1);
            // Возвращаем значение
            return salary;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Добавление продукта в БД
    public void addProject(Project project) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Project(`name`, `region`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, project.getName());
            statement.setObject(2, project.getRegion());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteProject(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Project WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPrice(Price price) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Price(`region`, `price`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, price.getRegion());
            statement.setObject(2, price.getPrice());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList addTypeProjects() {
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            ObservableList typeProject = FXCollections.observableArrayList();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT region FROM price");
            // Проходимся по нашему resultSet и заносим данные в typeProject
            while (resultSet.next()) {
                typeProject.add(resultSet.getString("region"));
            }
            // Возвращаем наш список
            return typeProject;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return null;
        }
    }

    public void editPrice(Price price) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE Price SET `region` = ?, `price` = ?  WHERE 'id' = ?")) {
            statement.setObject(1, price.getRegion());
            statement.setObject(2, price.getPrice());
            statement.setObject(3, price.getId());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
