package org.david.model;

import org.david.model.impl.ISellResult;
import org.david.model.impl.SalableProduct;
import org.jetbrains.annotations.NotNull;

public class Product extends SalableProduct {

  public Product(int uniqueId, @NotNull String name, double price) {
    super(uniqueId, name, price);
  }

  @Override
  public void sell(@NotNull ISellResult event) {
    getStock().remove(1);
  }

  public static Product create(int uniqueId, String name, double price, int inStock) {
    Product product = new Product(uniqueId, name, price);
    product.getStock().set(inStock);
    return product;
  }

}
