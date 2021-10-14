package org.david.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.david.controller.RepositoryManager;
import org.david.view.miscs.Utils;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.ComponentOrientation;

public class LogViewerDialog extends JDialog {

  private static final long serialVersionUID = 800968112957234975L;
  private final JPanel contentPanel = new JPanel();

  private LogViewerDialog(RepositoryManager repositoryManager) {
    setTitle("Registros");
    setType(Type.UTILITY);
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(345, 447));
    setBounds(100, 100, 498, 568);
    getContentPane().setLayout(new BorderLayout(0, 0));

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    getContentPane().add(scrollPane);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    scrollPane.setViewportView(textArea);
    textArea.setText(buildLogInfo(repositoryManager));
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    setModal(false);
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setVisible(true);
  }

  public static void viewLogInformations(RepositoryManager manager) {
    new LogViewerDialog(manager);
  }

  private static String buildLogInfo(RepositoryManager manager) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(String.format("+---------------------------------------------------------------------+%n"));
    buffer.append(String.format("| Registros Gerais                                                    |%n"));
    buffer.append(String.format("+---------------------------------------------------------------------+%n"));
    buffer.append(String.format("| 1# Produtos >>                                                      |%n"));
    buffer.append(String.format("+-------+------------------------+-----------------+------------------+%n"));
    buffer.append(String.format("| ID    | Nome                   | Preço           | Qtde em Estoque  |%n"));
    buffer.append(String.format("+-------+------------------------+-----------------+------------------+%n"));
    manager.getProductRepository().stream().forEach(product -> {

      buffer.append(String.format("| %-5s | %-22s | %-15s | %-16s |%n", product.getUniqueId(), product.getName(),
          product.getPrice(), product.getStock().getQuantityInStock()));

    });
    buffer.append(String.format("+---------------------------------------------------------------------+%n"));
    buffer.append(String.format("| 2# Vendas >>                                                        |%n"));
    buffer.append(String.format("+-------+------------------------+--------------+---------------------+%n"));
    buffer.append(String.format("| ID    | Preço Total            | Data         | Total de Produtos   |%n"));
    buffer.append(String.format("+-------+------------------------+--------------+---------------------+%n"));
    manager.getSaleRepository().stream().forEach(sale -> {

      buffer.append(String.format("| %-5s | %-22s | %-12s | %-19s |%n", sale.getUniqueId(), sale.getSaleTotalPrice(),
          sale.getDate().format(Utils.TIME_FORMAT), sale.getSaleProducts().size()));

    });
    buffer.append(String.format("+-------+------------------------+--------------+---------------------+%n"));
    return buffer.toString();
  }

}
