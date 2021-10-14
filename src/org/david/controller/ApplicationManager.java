package org.david.controller;

import org.david.model.Sale;
import org.david.model.impl.Repository;
import org.david.model.impl.SalableProduct;

public final class ApplicationManager {

	private Repository<SalableProduct> productRepository;
	private Repository<Sale> saleRepository;

	public ApplicationManager() {
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
		return getProductRepository().length() + 1;
	}

	public int nextSaleUniqueID() {
		return getSaleRepository().length() + 1;
	}


}
