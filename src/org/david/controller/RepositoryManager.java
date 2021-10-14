package org.david.controller;

import java.util.Random;

import org.david.model.Sale;
import org.david.model.impl.Repository;
import org.david.model.impl.SalableProduct;

public final class RepositoryManager {

  private final Repository<SalableProduct> productRepository;
  private final Random random = new Random();

  private Repository<Sale> saleRepository;

  public RepositoryManager() {
    productRepository = new ProductRepository();
    saleRepository = new SaleRepository();
  }

  public SaleRepository getSaleRepository() {
    return (SaleRepository) saleRepository;
  }

  public ProductRepository getProductRepository() {
    return (ProductRepository) productRepository;
  }

  public int nextProductUniqueID() {
    return nextUniqueId(productRepository);
  }

  public int nextSaleUniqueID() {
    return nextUniqueId(saleRepository);
  }

  private int nextUniqueId(Repository<?> repository) {
    int randomValue = random.nextInt(999);
    if (containsId(repository, randomValue)) {
      randomValue = nextUniqueId(repository);
    }
    return randomValue;
  }

  private boolean containsId(Repository<?> repository, int id) {
    return repository.contains(id);
  }

}
