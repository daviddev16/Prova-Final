package org.david.view.miscs;

import java.awt.Color;
import java.awt.SystemColor;
import java.time.format.DateTimeFormatter;

import javax.swing.JSeparator;

public class Utils {

  public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static final String toBRL(double value) {
    return "R$ " + String.format("%.2f", value).replace('.', ',');
  }

  public static JSeparator createSeparator(int x, int y, int width, int height) {
    JSeparator sep = new JSeparator();
    sep.setForeground(SystemColor.menu);
    sep.setBackground(Color.LIGHT_GRAY);
    sep.setBounds(x, y, width, height);
    return sep;
  }

}
