package org.david.controller;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.david.model.impl.Repository;
import org.david.model.impl.SalableProduct;

public final class ProductRepository extends Repository<SalableProduct> {

	public double getMinimumPrice() {
		AtomicReference<Double> currentValue = new AtomicReference<>(Double.MAX_VALUE);
		stream().forEach(product -> {
			if(product.getPrice() < currentValue.get()) {
				currentValue.set(product.getPrice());
			}
		});
		return currentValue.get();
	}

	public double getMaximumPrice() {
		AtomicReference<Double> currentValue = new AtomicReference<>(Double.MIN_VALUE);
		stream().forEach(product -> {
			if(product.getPrice() > currentValue.get()) {
				currentValue.set(product.getPrice());
			}
		});
		return currentValue.get();
	}

	public int getTotalStockAmount() {
		AtomicInteger count = new AtomicInteger(0);
		stream().forEach(product -> count.addAndGet(product.getStock().getQuantityInStock()));
		return count.get();
	}
}
