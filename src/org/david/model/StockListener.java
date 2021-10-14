package org.david.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class StockListener {
  
  private List<OldNewValue> history;
  
  public StockListener() {
    history = new LinkedList<>();
  }
  
  public void registerStockChange(int oldValue, int newValue) {
    history.add(new OldNewValue(oldValue, newValue));
  }

  public Stream<OldNewValue> stream(){
    return history.stream();
  }
  
  public static final class OldNewValue {
    
    private final int oldValue;
    private final int newValue;
    
    public OldNewValue(int oldValue, int newValue) {
      this.oldValue = oldValue;
      this.newValue = newValue;
    }
    public int getOldValue() {
      return oldValue;
    }
    public int getNewValue() {
      return newValue;
    }
    
  }
  
}
