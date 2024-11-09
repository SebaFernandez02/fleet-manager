package ar.edu.ungs.fleet_manager.products.application.find;

import ar.edu.ungs.fleet_manager.products.application.ProductProvidersResponse;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.application.update.ProductProviderUpdater;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.search.ProviderByProductSearcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductByIdFinder {
    private final ProductFinder productFinder;
    private final ProviderByProductSearcher providerByProductSearcher;

    public ProductByIdFinder(ProductFinder productFinder, ProviderByProductSearcher providerByProductSearcher) {
        this.productFinder = productFinder;
        this.providerByProductSearcher = providerByProductSearcher;
    }

    public ProductResponse execute(String id) {
        Product product = this.productFinder.execute(new ProductId(id));

        return ProductResponse.map(product);
    }

    public ProductProvidersResponse executeProviders(String id){
        Product product = this.productFinder.execute(new ProductId(id));
        List<ProviderResponse> providers = this.providerByProductSearcher.execute(product.id().value());

        return ProductProvidersResponse.map(product, providers);
    }

}
