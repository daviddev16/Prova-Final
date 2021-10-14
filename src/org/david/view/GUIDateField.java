package org.david.view;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFormattedTextField;

public class GUIDateField extends JFormattedTextField {

  private static final long serialVersionUID = -4070878851012651987L;

  public GUIDateField(DateTimeFormatter dateFormatter) {
    super(dateFormatter.toFormat(LocalDate::from));
    setPreferredSize(new Dimension(100, 26));
  }

  @Override
  public LocalDate getValue() {
    return (LocalDate) super.getValue();
  }

}