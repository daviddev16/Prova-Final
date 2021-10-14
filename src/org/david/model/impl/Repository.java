package org.david.model.impl;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Repository<E extends Comparable<E> & IUnique> {

  private Set<E> elements;

  public Repository() {
    elements = new TreeSet<>();
  }

  public void add(@NotNull E product) {
    elements.add(product);
  }

  public void remove(@NotNull E product) {
    elements.remove(product);
  }

  public Stream<E> stream() {
    return elements.stream();
  }

  public int length() {
    return elements.size();
  }

  public boolean contains(int id) {
    return stream().anyMatch(e -> e.getUniqueId() == id);
  }

  public E getElement(int uniqueId) {
    return stream().filter(e -> e.getUniqueId() == uniqueId).findFirst().orElseGet(null);
  }

  @Nullable
  public <R> R get(int uniqueId, @NotNull Function<E, R> func) {

    Optional<E> optProduct = elements.stream().filter(e -> e.getUniqueId() == uniqueId).findAny();

    if (optProduct.isPresent())
      return func.apply(optProduct.get());

    return null;
  }

}
