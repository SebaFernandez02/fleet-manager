package ar.edu.ungs.fleet_manager.products.application.update;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.search.ProviderByProductSearcher;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;


@Component
public class ProductProviderUpdater {

    private final ProductRepository repository;
    private final ProductFinder productFinder;
    private final ProviderFinder providerFinder;
    private final ProviderByProductSearcher searcher;

    public ProductProviderUpdater(ProductRepository repository, ProductFinder productFinder, ProviderFinder providerFinder, ProviderByProductSearcher searcher) {
        this.repository = repository;
        this.productFinder = productFinder;
        this.providerFinder = providerFinder;
        this.searcher = searcher;
    }


    public void execute(String productId, String providerId){
        Product product = this.productFinder.execute(new ProductId(productId));
        Provider provider = this.providerFinder.execute(new ProviderId(providerId));


        if (this.searcher.execute(product.id().value())
                .stream()
                .map(ProviderResponse::id)
                .anyMatch(provider_id -> provider_id.equals(provider.id().value()))) {
            throw new InvalidParameterException(String.format(
                    "The product with ID '%s' already has this provider associated.",
                    product.id().value()));}

        this.repository.addProvider(product.id(),provider.id());
    }

    public void delete(String productId, String providerId){
        Product product = this.productFinder.execute(new ProductId(productId));
        Provider provider = this.providerFinder.execute(new ProviderId(providerId));

        if (this.searcher.execute(product.id().value())
                .stream()
                .map(ProviderResponse::id)
                .noneMatch(provider_id -> provider_id.equals(provider.id().value()))) {
            throw new InvalidParameterException(String.format(
                    "The product '%s' is not provided by the provider '%s'.",
                    product.id().value(),provider.id().value()));}

        this.repository.deleteProvider(product.id(),provider.id());
    }

}
