package org.david.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.david.controller.RepositoryManager;
import org.david.model.Sale;
import org.david.model.impl.SalableProduct;

public class GUISale extends JDialog {

  private static final long serialVersionUID = 2064249842195650567L;
  private GUIDateField tfData;
  private Sale sale;
  private GUIProductList listProdutos;
  private JLabel textProdutos;

  private GUISale(RepositoryManager repositoryManager) {

    sale = new Sale(repositoryManager.nextSaleUniqueID(), null);

    setModal(true);
    setResizable(false);
    setBounds(100, 100, 528, 301);
    getContentPane().setLayout(null);
    setLocationRelativeTo(null);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));
    scrollPane.setBounds(10, 11, 148, 239);
    getContentPane().add(scrollPane);
    listProdutos = new GUIProductList(repositoryManager, true);
    scrollPane.setViewportView(listProdutos);

    JButton btnAdicionarProduto = new JButton("Adicionar na venda ->");
    btnAdicionarProduto.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if (listProdutos.getSelectedValue() != null) {
          SalableProduct product = repositoryManager.getProductRepository().getElement(listProdutos.getSelectedValue());

          String amountStringValue = JOptionPane.showInputDialog(null,
              "Digite a quantidade para venda: (Disponível " + product.getStock().getQuantityInStock() + ")",
              Integer.valueOf(0));
          try {

            int amount = Integer.parseInt(amountStringValue);

            if (amount > product.getStock().getQuantityInStock() || amount < 0) {
              throw new NumberFormatException();
            }

            sale.getSaleProducts().add(new Sale.SaleProduct(product, amount));
            updateInfos();

          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.");
          }
        }
      }
    });
    btnAdicionarProduto.setBounds(168, 9, 139, 23);
    getContentPane().add(btnAdicionarProduto);

    textProdutos = new JLabel();
    textProdutos.setVerticalAlignment(SwingConstants.TOP);
    textProdutos.setHorizontalAlignment(SwingConstants.LEFT);
    textProdutos.setBorder(new LineBorder(Color.LIGHT_GRAY));
    textProdutos.setBounds(317, 11, 185, 239);
    getContentPane().add(textProdutos);

    JButton btnRemoverProduto = new JButton("<- Remover da venda");
    btnRemoverProduto.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (listProdutos.getSelectedValue() != null) {
          sale.getSaleProducts().remove(sale.getSaleProduct(listProdutos.getSelectedValue()));
          updateInfos();
        }
      }
    });
    btnRemoverProduto.setBounds(168, 43, 139, 23);
    getContentPane().add(btnRemoverProduto);

    JButton btnFinalizarVenda = new JButton("Finalizar venda");
    btnFinalizarVenda.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (tfData.getValue() == null) {
          JOptionPane.showMessageDialog(null, "Data inválida.");
          return;
        }

        sale.setDate(tfData.getValue());
        sale.getSaleProducts().forEach(product -> {
          product.getProduct().getStock().remove(product.getAmount());
        });
        dispose();
      }
    });
    btnFinalizarVenda.setBounds(168, 227, 139, 23);
    getContentPane().add(btnFinalizarVenda);

    tfData = new GUIDateField(GUIUtils.TIME_FORMAT);
    tfData.setValue(LocalDate.now());
    tfData.setBounds(168, 126, 139, 20);
    getContentPane().add(tfData);
    tfData.setColumns(10);

    JLabel lblDataDeVenda = new JLabel("Data da venda");
    lblDataDeVenda.setHorizontalAlignment(SwingConstants.CENTER);
    lblDataDeVenda.setBounds(168, 104, 139, 14);
    getContentPane().add(lblDataDeVenda);

    getContentPane().add(separator(168, 86, 139, 11));
    getContentPane().add(separator(168, 161, 139, 11));

    JLabel hint = new JLabel("dd/MM/yyyy");
    hint.setFont(new Font("Tahoma", Font.PLAIN, 7));
    hint.setHorizontalAlignment(SwingConstants.CENTER);
    hint.setBounds(168, 145, 139, 14);
    getContentPane().add(hint);
    setVisible(true);

  }

  public void updateInfos() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<html>");
    sale.getSaleProducts().forEach(saleProduct -> {
      buffer.append("Produto= " + saleProduct.getProduct().getName() + ", Qtd= " + saleProduct.getAmount() + " de "
          + saleProduct.getProduct().getStock().getQuantityInStock()).append("<br>");
    });
    buffer.append("</html>");
    buffer.trimToSize();
    textProdutos.setText(buffer.toString());
  }

  public static Sale createASale(RepositoryManager repositoryManager) {
    return new GUISale(repositoryManager).getSale();
  }

  private Sale getSale() {
    return sale;
  }

  private JSeparator separator(int x, int y, int width, int height) {
    JSeparator sep = new JSeparator();
    sep.setForeground(SystemColor.menu);
    sep.setBackground(Color.LIGHT_GRAY);
    sep.setBounds(x, y, width, height);
    return sep;
  }

}
