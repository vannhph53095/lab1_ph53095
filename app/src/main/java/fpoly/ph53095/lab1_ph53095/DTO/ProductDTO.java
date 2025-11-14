package fpoly.ph53095.lab1_ph53095.DTO;

public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private int id_cat;

    // Constructor
    public ProductDTO() {}

    public ProductDTO(String name, double price, int id_cat) {
        this.name = name;
        this.price = price;
        this.id_cat = id_cat;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getId_cat() { return id_cat; }
    public void setId_cat(int id_cat) { this.id_cat = id_cat; }
}