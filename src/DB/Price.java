package DB;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Price {
    // Поля класса
    private SimpleIntegerProperty id;

    private SimpleStringProperty region;

    private SimpleIntegerProperty price;

    // Конструктор


    public Price(int id, String region, int price) {
        this.id = new SimpleIntegerProperty(id);
        this.region = new SimpleStringProperty(region);
        this.price = new SimpleIntegerProperty(price);
    }

    public Price(String region, int price) {
        this.region = new SimpleStringProperty(region);
        this.price = new SimpleIntegerProperty(price);
    }

    public Price () {

    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getRegion() {
        return region.get();
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
        return String.format("ID: %s | Регион: %s | Цена: %s",
                this.id, this.region, this.price);
    }
}
