package org.david.model.impl;

import org.jetbrains.annotations.NotNull;

public abstract class SalableProduct implements ISalable, IUnique, Comparable<SalableProduct> {

  private final int uniqueId;
  private final ItemStock itemStock;
  private String name;
  private double price;

  public SalableProduct(int uniqueId, String name) {
    this.itemStock = new ItemStock(0);
    this.uniqueId = uniqueId;
    this.name = name;
  }

  public SalableProduct(int uniqueId, @NotNull String name, double price) {
    this(uniqueId, name);
    this.price = price;
  }

  @Override
  public abstract void sell(@NotNull ISellResult doneEvent);

  @Override
  public boolean canBeSold() {
    return getStock().isAvailable();
  }

  @Override
  public ItemStock getStock() {
    return itemStock;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public int getUniqueId() {
    return uniqueId;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public int compareTo(SalableProduct o) {
    return o.getName().compareTo(getName());
  }

}
