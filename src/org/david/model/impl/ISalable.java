package org.david.model.impl;

import org.jetbrains.annotations.NotNull;

public interface ISalable {

  void sell(@NotNull ISellResult doneEvent);

  @NotNull ItemStock getStock();

  boolean canBeSold();

}


