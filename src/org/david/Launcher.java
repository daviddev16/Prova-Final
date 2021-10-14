package org.david;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.david.view.MainUI;

public class Launcher {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      EventQueue.invokeLater(() -> new MainUI());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
