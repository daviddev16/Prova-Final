package org.david.view.dialogs;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.david.model.Sale;
import org.david.model.Sale.SaleProduct;
import org.david.view.miscs.Utils;

public class SaleViewerDialog extends JDialog {

  private static final long serialVersionUID = 800968112957234975L;
  private final JPanel contentPanel = new JPanel();

  private SaleViewerDialog(Sale sale) {
    setTitle("Registro de venda");
    setType(Type.UTILITY);
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(345, 447));
    setBounds(100, 100, 353, 499);
    getContentPane().setLayout(new BorderLayout(0, 0));

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    getContentPane().add(scrollPane);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    scrollPane.setViewportView(textArea);
    textArea.setText(buildSaleInfo(sale));
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    setModal(false);
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setVisible(true);
  }

  public static void viewSaleInformations(Sale sale) {
    new SaleViewerDialog(sale);
  }

  private String buildSaleInfo(Sale sale) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(String.format("+-----------------+-----------------------+%n"));
    buffer.append(String.format("| Registro de venda                       |%n"));
    buffer.append(String.format("+-----------------+-----------------------+%n"));
    buffer.append(String.format("| Identificação   | Data da venda         |%n"));
    buffer.append(String.format("+-----------------+-----------------------+%n"));
    buffer.append(String.format("| %-15s | %-21s |%n", sale.getUniqueId(), sale.getDate().format(Utils.TIME_FORMAT)));
    buffer.append(String.format("+-----------------+-----------------------+%n"));
    buffer.append(String.format("+-----------------+--------------+--------+%n"));
    buffer.append(String.format("| Nome            | Valor        | Qtde   |%n"));
    buffer.append(String.format("+---------------------+----------+--------+%n"));
    for (SaleProduct saleProduct : sale.getSaleProducts()) {
      buffer.append(String.format("| %-15s | %-12s | %-6s |%n", saleProduct.getProduct().getName(),
          Utils.toBRL(saleProduct.getProduct().getPrice()), saleProduct.getAmount()));
    }
    buffer.append(String.format("+-----------------+--------------+--------+%n"));
    buffer.append(String.format("| Preço total: %-26s |%n", Utils.toBRL(sale.getSaleTotalPrice())));
    buffer.append(String.format("+-----------------------------------------+%n"));
    return buffer.toString();
  }

}
