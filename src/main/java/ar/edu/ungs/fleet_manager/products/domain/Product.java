package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Product {
    private final ProductId id;
    private ProductName name;
    private ProductBrand brand;
    private ProductDescription description;
    private ProductCategory category;
    private Quantity quantity;
    private ProductMeasurement measurement;
    private ProductPrice price;
    private ProviderId prefProvider;
    private ProductMinStock minStock;

    public Product(ProductId id,
                   ProductName name,
                   ProductBrand brand,
                   ProductDescription description,
                   ProductCategory category,
                   Quantity quantity,
                   ProductMeasurement measurement,
                   ProductPrice price,
                   ProductMinStock minStock) {

        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description= description;
        this.quantity = quantity;
        this.measurement = measurement;
        this.price = price;
        this.minStock = minStock;
    }

    public static Product create(String name,
                                 String brand,
                                 String description,
                                 String category,
                                 Integer quantity,
                                 String measurement,
                                 BigDecimal price,
                                 Integer minStock) {


        return new Product(
                new ProductId(UUID.randomUUID().toString()),
                new ProductName(name),
                new ProductBrand(brand),
                new ProductDescription(description),
                new ProductCategory(category),
                new Quantity(quantity),
                ProductMeasurement.parse(measurement),
                new ProductPrice(price),
                new ProductMinStock(minStock));
    }

    public static Product build(String id,
                                 String name,
                                 String brand,
                                 String description,
                                 String category,
                                 Integer quantity,
                                 String measurement,
                                 BigDecimal price,
                                 String prefProvider,
                                 Integer minStock) {


        Product product = new Product(
                new ProductId(id),
                new ProductName(name),
                new ProductBrand(brand),
                new ProductDescription(description),
                new ProductCategory(category),
                new Quantity(quantity),
                ProductMeasurement.parse(measurement),
                new ProductPrice(price),
                new ProductMinStock(minStock));


        if(prefProvider != null){
            product.updatePrefProvider(prefProvider);

        }


        return product;
    }

    public ProductId id() { return id;}

    public ProductName name() { return name;}

    public void updateName(String value) {
        this.name = new ProductName(value);
    }

    public ProductBrand brand() { return brand;}

    public ProductCategory category() { return category;}

    public void updateBrand(String value) {
        this.brand = new ProductBrand(value);
    }

    public void updateCategory(String value) {
        this.category = new ProductCategory(value);
    }

    public ProductDescription description() { return description;}

    public void updateDescription(String value) {
        this.description = new ProductDescription(value);
    }

    public Quantity quantity(){return quantity;}

    public void add(Quantity quantity) {
        this.quantity = new Quantity(this.quantity.value() + quantity.value());
    }

    public void substract(Quantity quantity) {
        this.quantity = new Quantity(this.quantity.value() - quantity.value());
    }

    public ProductMeasurement measurement() {
        return measurement;
    }

    public void updateMeasurement(String value){
        this.measurement = ProductMeasurement.parse(value);
    }

    public ProductPrice price() {
        return price;
    }

    public void updatePrice(BigDecimal value){
        this.price = new ProductPrice(value);
    }

    public ProductMinStock minStock(){return minStock;}

    public void setMinStock(Integer value){this.minStock = new ProductMinStock(value);}

    public Optional<ProviderId> prefProvider(){return Optional.ofNullable(this.prefProvider);}

    public void updatePrefProvider(String providerId){this.prefProvider = new ProviderId(providerId);}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(quantity, product.quantity) && measurement == product.measurement && Objects.equals(price, product.price) && Objects.equals(prefProvider,product.prefProvider) && Objects.equals(minStock, product.minStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, description, category, quantity, measurement, price, prefProvider, minStock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", brand=" + brand +
                ", description=" + description +
                ", category=" + category +
                ", quantity=" + quantity +
                ", minStock=" + minStock +
                ", measurement=" + measurement +
                ", price=" + price +
                ", prefProvider=" + prefProvider +
                '}';
    }
}













