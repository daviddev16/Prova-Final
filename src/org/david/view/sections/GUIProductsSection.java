package org.david.view.sections;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import org.david.controller.RepositoryManager;
import org.david.miscs.Section;
import org.david.model.Product;
import org.david.model.impl.GUISections;
import org.david.model.impl.SalableProduct;
import org.david.view.GUIProduct;
import org.david.view.GUIProductList;
import org.david.view.TipHandler;

@Section(title = "Produtos")
public class GUIProductsSection extends GUISection<GUISections> {

  private static final long serialVersionUID = 2757529971362687605L;

  private JButton btnRemoverProduto;
  private JButton btnEditarProduto;
  private JButton btnAdicionarProduto;

  private JLabel lblProdutosInfo;
  private GUIProductList listProdutos;
  private JScrollPane scrollPaneProdutos;

  public GUIProductsSection(RepositoryManager repositoryManager, GUISections root) {
    super(repositoryManager, root);

    setBounds(10, 11, 293, 340);

    scrollPaneProdutos = new JScrollPane();

    btnRemoverProduto = new JButton("Remover");
    btnEditarProduto = new JButton("Editar");
    btnAdicionarProduto = new JButton("Adicionar");

    lblProdutosInfo = new JLabel("...");
    listProdutos = new GUIProductList(getRepositoryManager(), false);

    btnAdicionarProduto.addActionListener((event) -> {
      SalableProduct newProduct = GUIProduct.create(getRepositoryManager());
      if (newProduct != null) {
        addProduct((Product) newProduct);
      }
    });

    btnAdicionarProduto.setFocusPainted(false);
    btnAdicionarProduto.setOpaque(false);
    btnRemoverProduto.setEnabled(false);

    btnRemoverProduto.addActionListener((event) -> {
      if (listProdutos.getSelectedValue() != null) {
        removeProduct(
            (Product) getRepositoryManager().getProductRepository().getElement(listProdutos.getSelectedValue()));
      }
    });
    btnRemoverProduto.setOpaque(false);
    btnRemoverProduto.setFocusPainted(false);

    btnEditarProduto.addActionListener((event) -> {
      if (listProdutos.getSelectedValue() != null) {
        editProduct(
            (Product) getRepositoryManager().getProductRepository().getElement(listProdutos.getSelectedValue()));
      }
    });
    btnEditarProduto.setEnabled(false);
    btnEditarProduto.setOpaque(false);
    btnEditarProduto.setFocusPainted(false);
    scrollPaneProdutos.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));

    scrollPaneProdutos.setViewportView(listProdutos);

    listProdutos.addListSelectionListener((event) -> {
      btnEditarProduto.setEnabled(listProdutos.getSelectedIndex() != -1);
      btnRemoverProduto.setEnabled(listProdutos.getSelectedIndex() != -1);
      createSideInfo();
    });

    lblProdutosInfo.setFont(new Font("Tahoma", Font.PLAIN, 9));
    lblProdutosInfo.setVerticalAlignment(SwingConstants.TOP);
    lblProdutosInfo.setHorizontalTextPosition(SwingConstants.LEFT);
    lblProdutosInfo.setHorizontalAlignment(SwingConstants.LEFT);

    GroupLayout groupLayout = new GroupLayout(this);

    groupLayout
    .setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addGap(10)
            .addComponent(scrollPaneProdutos, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE).addGap(10)
            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(lblProdutosInfo, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addGroup(Alignment.TRAILING,
                    groupLayout.createSequentialGroup()
                    .addComponent(btnAdicionarProduto, GroupLayout.PREFERRED_SIZE, 82,
                        GroupLayout.PREFERRED_SIZE)
                    .addGap(2))
                .addGroup(Alignment.TRAILING,
                    groupLayout.createSequentialGroup()
                    .addComponent(btnEditarProduto, GroupLayout.PREFERRED_SIZE, 82,
                        GroupLayout.PREFERRED_SIZE)
                    .addGap(2))
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addComponent(btnRemoverProduto, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                    .addGap(2)))
            .addGap(10)));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addGap(21)
            .addComponent(scrollPaneProdutos, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE).addGap(11))
        .addGroup(groupLayout.createSequentialGroup().addGap(22)
            .addComponent(lblProdutosInfo, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE).addGap(11)
            .addComponent(btnAdicionarProduto, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addGap(6)
            .addComponent(btnEditarProduto, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addGap(5)
            .addComponent(btnRemoverProduto, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addGap(11)));

    setLayout(groupLayout);
  }

  @Override
  public void update() {
    listProdutos.repaint();
    createSideInfo();
  }

  @Override
  public void handleTip(JLabel tipLabel) {
    btnAdicionarProduto.addMouseListener(new TipHandler(tipLabel, "Adicionar um novo produto na lista."));
    btnRemoverProduto.addMouseListener(new TipHandler(tipLabel, "Remover um produto da lista."));
    btnEditarProduto.addMouseListener(new TipHandler(tipLabel, "Editar o produto selecionado."));
    listProdutos.addMouseListener(new TipHandler(tipLabel, "Lista de produtos cadastrados."));
    lblProdutosInfo.addMouseListener(new TipHandler(tipLabel, "Informações de estoque e preços."));
  }

  public void addProduct(Product product) {
    getRepositoryManager().getProductRepository().add(product);
    ((DefaultListModel<Integer>) listProdutos.getModel()).addElement(product.getUniqueId());
    update();
  }

  public void removeProduct(Product product) {
    getRepositoryManager().getProductRepository().remove(product);
    ((DefaultListModel<Integer>) listProdutos.getModel()).removeElement(product.getUniqueId());
    update();
  }

  private void createSideInfo() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("<html>");
    buffer.append("Preço mínimo: <br>R$ " + getRepositoryManager().getProductRepository().getMinimumPrice())
    .append("<br><br>");
    buffer.append("Preço máximo: <br>R$ " + getRepositoryManager().getProductRepository().getMaximumPrice())
    .append("<br><br>");
    buffer.append(
        "Total em estoque: <br>" + getRepositoryManager().getProductRepository().getTotalStockAmount() + " Produtos")
    .append("<br><br>");

    buffer.append("<i>---------------------------</i><br><br>");

    Integer selectedId = listProdutos.getSelectedValue();
    if (selectedId != null) {
      SalableProduct product = getRepositoryManager().getProductRepository().getElement(selectedId);
      buffer.append("Nome: <br>" + product.getName()).append("<br><br>");
      buffer.append("Preço: <br>R$ " + (product.getPrice() + "").replace(".", ",")).append("<br><br>");
      buffer.append("Qtd. em estoque: <br>" + product.getStock().getQuantityInStock()).append("<br><br>");
    }

    buffer.append("</html>");

    lblProdutosInfo.setText(buffer.toString().replace(".", ","));

  }

  public void addSamples() {
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Maçã", 3.5, 50));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Desinfetante", 7.5, 125));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Refrigerante 2L", 9.25, 25));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Cenoura", 2.5, 145));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Biscoito 200g", 5.53, 102));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Arroz 1kg", 10.52, 55));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Café 500g", 2.5, 145));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Sabonete", 5.99, 100));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Sal 400g", 2.5, 175));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Manteiga 250g", 10.5, 134));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Faca", 5.5, 32));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Iogurte 1L", 8.5, 64));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Televisão X", 549.99, 20));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Televisão Z", 899.99, 10));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Smartphone Y", 2099.99, 39));
    addProduct(Product.create(getRepositoryManager().nextProductUniqueID(), "Vassoura", 5.99, 34));
  }

  public void editProduct(Product product) {
    GUIProduct.edit(getRepositoryManager().getProductRepository().getElement(listProdutos.getSelectedValue()),
        getRepositoryManager());
    update();
  }

}
