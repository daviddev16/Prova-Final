package org.david.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.david.model.impl.IUnique;
import org.david.model.impl.SalableProduct;

public final class Sale implements IUnique, Comparable<Sale> {

	private LocalDate date;
	private final int uniqueId;
	private final List<SaleProduct> saleProducts;

	public Sale(int uniqueId, LocalDate date) {
		this.saleProducts = new LinkedList<>();
		this.uniqueId = uniqueId;
		this.date = date;
	}

	public double getSaleTotalPrice() {
		double totalPrice = 0;
		for(SaleProduct saleProduct : getSaleProducts()) {
			totalPrice += saleProduct.getAmount() * saleProduct.getProduct().getPrice();
		}
		return totalPrice;
	}

	public SaleProduct getSaleProduct(int productUniqueId) {
		return saleProducts.stream().filter(sp -> sp.getProduct().getUniqueId() == productUniqueId).findFirst()
				.orElse(null);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<SaleProduct> getSaleProducts() {
		return saleProducts;
	}

	@Override
	public int getUniqueId() {
		return uniqueId;
	}
	
	@Override
	public int compareTo(Sale o) {
		return Integer.compare(getUniqueId(), o.getUniqueId());
	}
	
	public static final class SaleProduct {

		private final SalableProduct product;
		private final int amount;

		public SaleProduct(SalableProduct product, int amount) {
			this.product = product;
			this.amount = amount;
		}

		public SalableProduct getProduct() {
			return product;
		}

		public int getAmount() {
			return amount;
		}

	}

}
