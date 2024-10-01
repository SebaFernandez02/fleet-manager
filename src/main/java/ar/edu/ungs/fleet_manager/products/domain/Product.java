package ar.edu.ungs.fleet_manager.products.domain;

import java.util.Objects;
import java.util.UUID;

public final class Product {
    private final ProductId id;
    private final ProductName name;
    private final ProductBrand brand;
    private final ProductDescription description;
    private final ProductCategory category;
    private ProductQuantity quantity;

    public Product(ProductId id,
                   ProductName name,
                   ProductBrand brand,
                   ProductDescription description,
                   ProductCategory category,
                   ProductQuantity quantity) {

        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description= description;
        this.quantity = quantity;
    }

    public static Product create(String name,
                                 String brand,
                                 String description,
                                 String category,
                                 Integer quantity) {
        return new Product(
                new ProductId(UUID.randomUUID().toString()),
                new ProductName(name),
                new ProductBrand(brand),
                new ProductDescription(description),
                new ProductCategory(category),
                new ProductQuantity(quantity));
    }

    public static Product build(String id,
                                 String name,
                                 String brand,
                                 String description,
                                 String category,
                                 Integer quantity) {
        return new Product(
                new ProductId(id),
                new ProductName(name),
                new ProductBrand(brand),
                new ProductDescription(description),
                new ProductCategory(category),
                new ProductQuantity(quantity));
    }

    public ProductId id() { return id;}

    public ProductName name() { return name;}

    public ProductBrand brand() { return brand;}

    public ProductCategory category() { return category;}

    public ProductDescription description() { return description;}

    public ProductQuantity quantity(){return quantity;}

    public void add(Integer quantity) {
        this.quantity = new ProductQuantity(this.quantity.value() * quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, description ,category, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name,product.name) && Objects.equals(brand,product.brand) && Objects.equals(description,product.description) && Objects.equals(category,product.category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name+
                ", brand=" + brand +
                ", description=" + description +
                ", category=" + category +
                ", quantity=" + quantity +
                "}";
    }
}













