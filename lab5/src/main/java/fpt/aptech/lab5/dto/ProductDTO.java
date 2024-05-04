package fpt.aptech.lab5.dto;

import fpt.aptech.lab5.entities.Categories;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {

    private int id;

    @NotEmpty(message = "Name is required...")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotNull(message = "price is required...")
    @Min(value = 1, message = "price must be higher than $0")
    private double price;

    @NotNull(message = "quantity is required...")
    @Min(value = 1, message = "quantity must be higher than 0")
    private int quantity;

    // validation on controller
    private MultipartFile photo;

    @NotNull(message = "Category is required...")
    private Categories categoryid;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, double price, int quantity, MultipartFile photo, Categories categoryid) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.photo = photo;
        this.categoryid = categoryid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Categories getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Categories categoryid) {
        this.categoryid = categoryid;
    }

}
