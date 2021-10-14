package org.david.view.dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.StringJoiner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.david.controller.RepositoryManager;
import org.david.model.Sale;
import org.david.model.impl.SalableProduct;
import org.david.view.GUISections;
import org.david.view.components.DateField;
import org.david.view.components.ProductList;
import org.david.view.miscs.Utils;

public class SaleBuilderDialog extends JDialog {

  private static final long serialVersionUID = 2064249842195650567L;

  private Sale sale;

  private DateField tfData;
  private ProductList listProdutos;
  private JLabel textProdutos;
  private JScrollPane scrollPane;
  private JButton btnAdicionarProduto;
  private JButton btnRemoverProduto;
  private JButton btnFinalizarVenda;
  private JLabel lblDataDeVenda;
  private JLabel hint;

  private SaleBuilderDialog(RepositoryManager repositoryManager, GUISections sections) {

    scrollPane = new JScrollPane();

    btnAdicionarProduto = new JButton("Adicionar na venda ->");
    btnRemoverProduto = new JButton("<- Remover da venda");
    btnFinalizarVenda = new JButton("Finalizar venda");

    lblDataDeVenda = new JLabel("Data da venda");
    hint = new JLabel("dd/MM/yyyy");
    textProdutos = new JLabel();

    sale = new Sale(repositoryManager.nextSaleUniqueID(), null);

    setTitle("Vender produtos");
    setModal(true);
    setResizable(false);
    setBounds(100, 100, 528, 301);
    getContentPane().setLayout(null);
    setLocationRelativeTo(null);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent arg0) {
        sale = null;
      }
    });

    scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));
    scrollPane.setBounds(10, 11, 148, 239);
    getContentPane().add(scrollPane);
    listProdutos = new ProductList(repositoryManager, true);
    scrollPane.setViewportView(listProdutos);

    btnAdicionarProduto.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if (listProdutos.getSelectedValue() != null) {
          SalableProduct product = repositoryManager.getProductRepository().getElement(listProdutos.getSelectedValue());

          String amountStringValue = JOptionPane.showInputDialog(null,
              "Digite a quantidade para venda: (Dispon�vel " + product.getStock().getQuantityInStock() + ")",
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

    textProdutos.setVerticalAlignment(SwingConstants.TOP);
    textProdutos.setHorizontalAlignment(SwingConstants.LEFT);
    textProdutos.setBorder(new LineBorder(Color.LIGHT_GRAY));
    textProdutos.setBounds(317, 11, 185, 239);
    getContentPane().add(textProdutos);

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

    btnFinalizarVenda.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (sale.getSaleProducts().isEmpty()) {
          if (JOptionPane.showConfirmDialog(null, "Nenhum item foi adicionado, Deseja cancelar a venda?", "Venda vazia",
              JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            sale = null;
            dispose();
          }
          return;
        }

        if (tfData.getValue() == null) {
          JOptionPane.showMessageDialog(null, "Data inválida.");
          return;
        }

        sale.setDate(tfData.getValue());
        sale.getSaleProducts().forEach(product -> {
          product.getProduct().getStock().remove(product.getAmount());
        });

        sections.getProductsSection().update();
        dispose();
      }
    });
    btnFinalizarVenda.setBounds(168, 227, 139, 23);
    getContentPane().add(btnFinalizarVenda);

    tfData = new DateField(Utils.TIME_FORMAT);
    tfData.setValue(LocalDate.now());
    tfData.setBounds(168, 126, 139, 20);
    getContentPane().add(tfData);
    tfData.setColumns(10);

    lblDataDeVenda.setHorizontalAlignment(SwingConstants.CENTER);
    lblDataDeVenda.setBounds(168, 104, 139, 14);
    getContentPane().add(lblDataDeVenda);

    hint.setFont(new Font("Tahoma", Font.PLAIN, 7));
    hint.setHorizontalAlignment(SwingConstants.CENTER);
    hint.setBounds(168, 145, 139, 14);
    getContentPane().add(hint);

    getContentPane().add(Utils.createSeparator(168, 86, 139, 11));
    getContentPane().add(Utils.createSeparator(168, 161, 139, 11));

    setVisible(true);

  }

  public void updateInfos() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<html>");
    StringJoiner joiner = new StringJoiner("-=-=-=-=-=-=-");
    buffer.append("-=-=-=-=-=-=-");
    sale.getSaleProducts().forEach(saleProduct -> {

      String productString = new StringBuffer()
          .append("<br>Produto: " + saleProduct.getProduct().getName() + "<br> Quantidade: " + saleProduct.getAmount()
              + " de " + saleProduct.getProduct().getStock().getQuantityInStock())
          .append("<br>").toString();

      joiner.add(productString);

    });
    buffer.append(joiner.toString());
    buffer.append("-=-=-=-=-=-=-");
    buffer.append("</html>");
    buffer.trimToSize();
    textProdutos.setText(buffer.toString());
  }

  public static Sale createASale(RepositoryManager repositoryManager, GUISections sections) {
    return new SaleBuilderDialog(repositoryManager, sections).getSale();
  }

  private Sale getSale() {
    return sale;
  }

}
