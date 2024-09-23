package ar.edu.ungs.fleet_manager.products.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Product {


    private final ProductId id;
    private final ProductName name;
    private final ProductBrand brand;
    private final ProductCategory category;

    private final LocalDateTime purchaseDate;


    public Product(ProductId id,
                   ProductName name,
                   ProductBrand brand,
                   ProductCategory category,
                   LocalDateTime purchaseDate) {

        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.purchaseDate = purchaseDate;
    }

    public static Product create(String id,
                                 String name,
                                 String brand,
                                 String category,
                                 LocalDateTime purchaseDate) {

        return new Product(new ProductId(id),
                new ProductName(name),
                new ProductBrand(brand),
                new ProductCategory(category),
                LocalDateTime.now());
    }

    public ProductId id() { return id;}

    public ProductName name() { return name;}

    public ProductBrand brand() { return brand;}

    public ProductCategory category() { return category;}



    public LocalDateTime purchaseDate() { return purchaseDate;}

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, category, purchaseDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name,product.name) && Objects.equals(brand,product.brand) && Objects.equals(category,product.category) && Objects.equals(purchaseDate, product.purchaseDate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name+
                ", brand=" + brand +
                ", category=" + category +
                ", purchaseDate" + purchaseDate +
                "}";
    }
}













