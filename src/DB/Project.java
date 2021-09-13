package DB;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Project {
    // Поля класса
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty region;
    private SimpleIntegerProperty price;


    // Конструкторы
    public Project(int id, String name, String region, int price) {
        this.id = new SimpleIntegerProperty(id);;
        this.name = new SimpleStringProperty(name);
        this.region = new SimpleStringProperty(region);
        this.price = new SimpleIntegerProperty(price);
    }


    public Project(String name, String region) {
        this.name = new SimpleStringProperty(name);
        this.region = new SimpleStringProperty(region);
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRegion() {
        return region.get();
    }

    public SimpleStringProperty regionProperty() {
        return region;
    }

    public void setRegion(String region) {
        this.region.set(region);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        return String.format("ID: %s | Название проекта: %s | Регион: %s",
                this.id, this.name, this.region);
    }
}
