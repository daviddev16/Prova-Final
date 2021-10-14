package org.david.model.impl;

public final class ItemStock {

	private int quantityInStock;

	public ItemStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public void add(int quantity) {
		quantityInStock += quantity;
	}

	public void remove(int quantity) {
		quantityInStock -= quantity;
	}

	public void set(int quantity) {
		this.quantityInStock = quantity;
	}

	public boolean isAvailable() {
		return quantityInStock > 0;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

}

