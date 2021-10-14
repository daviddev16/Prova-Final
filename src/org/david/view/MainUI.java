package org.david.view;

import java.awt.Color;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.david.controller.RepositoryManager;
import org.david.model.impl.GUISections;
import org.david.view.sections.GUIHelpSection;
import org.david.view.sections.GUIProductsSection;
import org.david.view.sections.GUISalesSection;
import org.david.view.sections.GUISection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class MainUI extends JFrame implements GUISections {

  private static final long serialVersionUID = -3843913650814570115L;

  private final RepositoryManager repositoryManager = new RepositoryManager();

  private final JPanel contentPane = new JPanel(true);

  private JLabel tip;
  GUISection<GUISections> helpSection;
  GUISection<GUISections> salesSection;
  GUISection<GUISections> productsSection;

  public MainUI() {

    setMinimumSize(new Dimension(554, 500));
    setBounds(100, 100, 554, 500);
    setTitle("Prova Final (POO)");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    tip = new JLabel();

    helpSection = new GUIHelpSection(repositoryManager, this);
    helpSection.handleTip(tip);
    salesSection = new GUISalesSection(repositoryManager, this);
    salesSection.handleTip(tip);
    productsSection = new GUIProductsSection(repositoryManager, this);
    productsSection.handleTip(tip);

    contentPane.setBorder(null);
    setContentPane(contentPane);

    tip.setText("É possível aumentar o tamanho do programa");
    tip.setHorizontalAlignment(SwingConstants.CENTER);
    tip.setForeground(Color.GRAY);

    GroupLayout gl_contentPane = new GroupLayout(contentPane);

    gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_contentPane.createSequentialGroup().addGap(10)
            .addComponent(productsSection, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE).addGap(10)
            .addComponent(salesSection, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE).addGap(7))
        .addGroup(gl_contentPane.createSequentialGroup().addGap(10)
            .addComponent(helpSection, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE).addGap(7))
        .addComponent(tip, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE));
    gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_contentPane.createSequentialGroup().addGap(11)
            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addComponent(productsSection, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(salesSection, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE).addGap(2)))
            .addGap(2).addComponent(helpSection, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE).addGap(2)
            .addComponent(tip, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)));

    contentPane.setLayout(gl_contentPane);

    addMouseListener(new TipHandler(tip, "É possível aumentar o tamanho do programa."));
    setVisible(true);

  }

  @Override
  public GUISection<GUISections> getHelpSection() {
    return helpSection;
  }

  @Override
  public GUISection<GUISections> getProductsSection() {
    return productsSection;
  }

  @Override
  public GUISection<GUISections> getSalesSection() {
    return salesSection;
  }

}
