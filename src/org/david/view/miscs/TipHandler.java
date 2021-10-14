package org.david.view.miscs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class TipHandler extends MouseAdapter {

  private volatile JLabel tipLabel;
  private volatile String text;

  public TipHandler(JLabel tipLabel, String text) {
    this.tipLabel = tipLabel;
    this.text = text;
  }

  public JLabel getTipLabel() {
    return tipLabel;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (tipLabel != null) {
      tipLabel.setText(text);
    }
  }

}
