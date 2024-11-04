package domain;

public class Item implements java.io.Serializable {
    private int id;
    private String name;
    private String category;
    private String type;
    private String picture;
    private String description;
    private double price;

    public Item() {

    }
    public Item(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item(int id, double price, String name, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Item(int id, String name, String category, String type, String picture, String description, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.picture = picture;
        this.description = description;
        this.price = price;
    }

    public int getId(int id) {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
