package ar.edu.ungs.fleet_manager.products.application.update;

import ar.edu.ungs.fleet_manager.products.application.ProductProviderRequest;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import org.springframework.stereotype.Component;


@Component
public class ProductProviderUpdater {

    private final ProductRepository repository;
    private final ProductFinder productFinder;
    private final ProviderFinder providerFinder;

    public ProductProviderUpdater(ProductRepository repository, ProductFinder productFinder, ProviderFinder providerFinder) {
        this.repository = repository;
        this.productFinder = productFinder;
        this.providerFinder = providerFinder;
    }


    public void execute(String id, ProductProviderRequest request){
        Product product = this.productFinder.execute(new ProductId(id));
        Provider provider = this.providerFinder.execute(new ProviderId(request.providerId()));


        this.repository.addProvider(product.id(),provider.id());
    }


}