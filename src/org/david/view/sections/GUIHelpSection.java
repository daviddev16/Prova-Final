package org.david.view.sections;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.david.controller.RepositoryManager;
import org.david.miscs.Section;
import org.david.model.impl.GUISections;
import org.david.view.TipHandler;

@Section(title = "Ajuda")
public class GUIHelpSection extends GUISection<GUISections> {

  private static final long serialVersionUID = -4750140186818332444L;

  private JButton btnAddons;

  public GUIHelpSection(RepositoryManager repositoryManager, GUISections root) {
    super(repositoryManager, root);

    btnAddons = new JButton("Adicionar amostras b\u00E1sicas\r\n");

    setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Ajuda", TitledBorder.CENTER, TitledBorder.TOP,
        null, new Color(192, 192, 192)));
    setBounds(10, 353, 487, 49);

    setLayout(null);

    btnAddons.setToolTipText("Adiciona uma lista de produtos aleat\u00F3rios como amostra.");
    btnAddons.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnAddons.setEnabled(false);
        ((GUIProductsSection) getRoot().getProductsSection()).addSamples();
      }
    });
    btnAddons.setBounds(6, 16, 147, 23);
    add(btnAddons);
    btnAddons.setFont(new Font("Tahoma", Font.PLAIN, 9));
    btnAddons.setOpaque(false);
    btnAddons.setFocusPainted(false);

  }

  @Override
  public void update() {

  }

  @Override
  public void handleTip(JLabel tipLabel) {
    btnAddons.addMouseListener(new TipHandler(tipLabel, "Adiciona amostras de produtos."));
  }

}
