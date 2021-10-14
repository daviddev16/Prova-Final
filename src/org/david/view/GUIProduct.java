package org.david.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.david.controller.RepositoryManager;
import org.david.model.Product;
import org.david.model.impl.SalableProduct;

public class GUIProduct extends JDialog {

  private static final long serialVersionUID = 2135962256855316310L;

  private SalableProduct product;

  private GUIProduct(boolean editMode, final RepositoryManager repositoryManager, final SalableProduct product) {

    JTextField tfName = new JTextField();

    JSpinner sPrice = new JSpinner();
    sPrice.setModel(new SpinnerNumberModel(new Double(1), new Double(1), null, new Double(1)));

    JSpinner sQuantity = new JSpinner();
    sQuantity.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));

    if (editMode) {
      this.product = product;
      tfName.setText(product.getName());
      sQuantity.setValue(product.getStock().getQuantityInStock());
      sPrice.setValue(product.getPrice());
    }

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setResizable(false);
    setBounds(100, 100, 331, 338);
    setLocationRelativeTo(null);
    setModal(true);
    getContentPane().setLayout(null);

    tfName.setColumns(10);
    tfName.setBounds(10, 121, 295, 20);
    getContentPane().add(tfName);

    JLabel lblName = new JLabel("Nome");
    lblName.setHorizontalAlignment(SwingConstants.CENTER);
    lblName.setBounds(10, 104, 295, 14);
    getContentPane().add(lblName);

    JLabel lblPrice = new JLabel("Pre\u00E7o");
    lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
    lblPrice.setBounds(10, 152, 295, 14);
    getContentPane().add(lblPrice);

    sPrice.setBounds(10, 169, 295, 20);
    getContentPane().add(sPrice);

    JLabel lblTitle = new JLabel(editMode ? "EDITAR PRODUTO" : "ADICIONAR UM NOVO PRODUTO");
    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
    lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
    lblTitle.setBounds(10, 11, 295, 27);
    getContentPane().add(lblTitle);

    JLabel lblQuantity = new JLabel("Quantidade em estoque");
    lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
    lblQuantity.setBounds(10, 201, 295, 14);
    getContentPane().add(lblQuantity);

    sQuantity.setBounds(10, 220, 295, 20);
    getContentPane().add(sQuantity);

    JLabel lblId = new JLabel("ID: " + ((editMode) ? product.getUniqueId() : repositoryManager.nextProductUniqueID()));
    lblId.setForeground(SystemColor.controlShadow);
    lblId.setHorizontalAlignment(SwingConstants.CENTER);
    lblId.setBounds(0, 65, 315, 14);
    getContentPane().add(lblId);

    JButton btnConcluir = new JButton(editMode ? "Editar" : "Adicionar");
    btnConcluir.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (tfName.getText().isEmpty()) {
          JOptionPane.showMessageDialog(null, "Nome inválido.");
          return;
        }

        if (editMode) {
          GUIProduct.this.product.setName(tfName.getText());
          GUIProduct.this.product.setPrice((Double) sPrice.getValue());
          GUIProduct.this.product.getStock().set((Integer) sQuantity.getValue());
          dispose();
          return;
        }

        GUIProduct.this.product = new Product(repositoryManager.nextProductUniqueID(), tfName.getText(),
            ((Double) sPrice.getValue()).doubleValue());

        GUIProduct.this.product.getStock().set(((Integer) sQuantity.getValue()).intValue());
        dispose();
      }
    });
    btnConcluir.setBounds(216, 269, 89, 23);
    getContentPane().add(btnConcluir);

    JButton btnCancelar = new JButton("Cancelar");
    btnCancelar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        dispose();
      }
    });
    btnCancelar.setBounds(116, 269, 89, 23);
    getContentPane().add(btnCancelar);

    getContentPane().add(separator(0, 47, 315, 7));
    getContentPane().add(separator(0, 93, 315, 7));
    getContentPane().add(separator(0, 257, 315, 7));

    setVisible(true);
  }

  public static void edit(SalableProduct product, RepositoryManager repositoryManager) {
    new GUIProduct(true, repositoryManager, product);
  }

  public static SalableProduct create(RepositoryManager repositoryManager) {
    return new GUIProduct(false, repositoryManager, null).getProduct();
  }

  private JSeparator separator(int x, int y, int width, int height) {
    JSeparator sep = new JSeparator();
    sep.setForeground(SystemColor.menu);
    sep.setBackground(Color.LIGHT_GRAY);
    sep.setBounds(x, y, width, height);
    return sep;
  }

  public SalableProduct getProduct() {
    return product;
  }

  public void setProduct(SalableProduct product) {
    this.product = product;
  }
}
