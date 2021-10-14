package org.david.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.david.controller.ApplicationManager;
import org.david.model.Product;
import org.david.model.Sale;
import org.david.model.Sale.SaleProduct;
import org.david.model.impl.SalableProduct;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;

public class MainApp extends JFrame {

	private static final long serialVersionUID = -3843913650814570115L;

	private final ApplicationManager manager = new ApplicationManager();

	private final JPanel contentPane = new JPanel();
	private JPanel panelProdutos = new JPanel();
	private final JButton btnRemoverProduto = new JButton("Remover");
	private final JButton btnEditarProduto = new JButton("Editar");
	private final JButton btnAdicionarProduto = new JButton("Adicionar");
	private final JLabel lblProdutosInfo = new JLabel("...");
	private final GUIProductList listProdutos;
	private final JScrollPane scrollPaneProdutos = new JScrollPane();
	private final JButton btnVender = new JButton("Vender");
	private final JButton btnAddons = new JButton("Adicionar amostras b\u00E1sicas\r\n");
	private final JPanel panelAjuda = new JPanel();
	private final JLabel tip = new JLabel("");
	private GUISaleList listVendas;

	public MainApp() {

		setTitle("Prova Final (POO)");

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 478);
		setLocationRelativeTo(null);

		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panelProdutos.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Produtos", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(192, 192, 192)));

		panelProdutos.setBounds(10, 11, 293, 340);
		contentPane.add(panelProdutos);
		panelProdutos.setLayout(null);
		btnAdicionarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SalableProduct newProduct = GUIProduct.create(manager);
				if (newProduct != null) {
					addProduct((Product) newProduct);
				}
			}
		});

		btnAdicionarProduto.setFocusPainted(false);
		btnAdicionarProduto.setOpaque(false);
		btnAdicionarProduto.setBounds(199, 249, 82, 23);
		panelProdutos.add(btnAdicionarProduto);

		btnRemoverProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (listProdutos.getSelectedValue() != null) {
					removeProduct((Product) manager.getProductRepository().getElement(listProdutos.getSelectedValue()));
				}
			}
		});
		btnRemoverProduto.setOpaque(false);
		btnRemoverProduto.setFocusPainted(false);
		btnRemoverProduto.setBounds(199, 306, 82, 23);
		panelProdutos.add(btnRemoverProduto);

		btnEditarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listProdutos.getSelectedValue() != null) {
					edit((Product) manager.getProductRepository().getElement(listProdutos.getSelectedValue()));
				}
			}
		});
		btnEditarProduto.setEnabled(false);
		btnEditarProduto.setOpaque(false);
		btnEditarProduto.setFocusPainted(false);
		btnEditarProduto.setBounds(199, 278, 82, 23);
		panelProdutos.add(btnEditarProduto);
		scrollPaneProdutos.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));
		scrollPaneProdutos.setBounds(10, 21, 179, 308);

		panelProdutos.add(scrollPaneProdutos);

		listProdutos = new GUIProductList(manager, false);
		scrollPaneProdutos.setViewportView(listProdutos);

		listProdutos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selectionEvent) {
				btnEditarProduto.setEnabled(listProdutos.getSelectedIndex() != -1);
				buildInfo();
			}
		});
		
		lblProdutosInfo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblProdutosInfo.setVerticalAlignment(SwingConstants.TOP);
		lblProdutosInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		lblProdutosInfo.setHorizontalAlignment(SwingConstants.LEFT);
		lblProdutosInfo.setBounds(199, 22, 118, 216);

		panelProdutos.add(lblProdutosInfo);

		JPanel panelVendas = new JPanel();
		panelVendas.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Vendas", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(192, 192, 192)));
		panelVendas.setBounds(313, 11, 184, 338);
		contentPane.add(panelVendas);
		btnVender.setBounds(10, 22, 66, 22);
		btnVender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUISale.createASale(manager);
			}
		});
		panelVendas.setLayout(null);
		btnVender.setOpaque(false);
		btnVender.setFocusPainted(false);

		panelVendas.add(btnVender);

		JScrollPane scrollPaneVendas = new JScrollPane();
		scrollPaneVendas.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));
		scrollPaneVendas.setBounds(10, 119, 164, 208);
		panelVendas.add(scrollPaneVendas);

		listVendas = new GUISaleList(manager, false);

		scrollPaneVendas.setViewportView(listVendas);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Filtrar");
		tglbtnNewToggleButton.setBounds(88, 22, 86, 23);
		panelVendas.add(tglbtnNewToggleButton);
		
		GUIDateField dateField = new GUIDateField(GUIUtils.TIME_FORMAT);
		dateField.setBounds(108, 90, 66, 18);
		panelVendas.add(dateField);
		
		GUIDateField dateField_1 = new GUIDateField(GUIUtils.TIME_FORMAT);
		dateField_1.setBounds(98, 56, 74, 18);
		panelVendas.add(dateField_1);
		panelAjuda.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Ajuda", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(192, 192, 192)));
		panelAjuda.setBounds(10, 353, 487, 49);

		contentPane.add(panelAjuda);
		panelAjuda.setLayout(null);

		btnAddons.setToolTipText("Adiciona uma lista de produtos aleat\u00F3rios como amostra.");
		btnAddons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSamples();
			}
		});
		btnAddons.setBounds(6, 16, 147, 23);
		panelAjuda.add(btnAddons);
		btnAddons.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAddons.setOpaque(false);
		btnAddons.setFocusPainted(false);
		contentPane.addMouseListener(new TipHandler(tip, "-"));

		tip.setHorizontalAlignment(SwingConstants.CENTER);
		tip.setBounds(0, 414, 518, 25);
		contentPane.add(tip);
		tip.setForeground(Color.GRAY);

		btnAddons.addMouseListener(new TipHandler(tip, "Adiciona amostras de produtos."));
		btnAdicionarProduto.addMouseListener(new TipHandler(tip, "Adicionar um novo produto na lista."));
		btnRemoverProduto.addMouseListener(new TipHandler(tip, "Remover um produto da lista."));
		btnEditarProduto.addMouseListener(new TipHandler(tip, "Editar o produto selecionado."));
		btnVender.addMouseListener(new TipHandler(tip, "Iniciar um processo de venda."));
		listProdutos.addMouseListener(new TipHandler(tip, "Lista de produtos cadastrados."));
		lblProdutosInfo.addMouseListener(new TipHandler(tip, "Informações de estoque e preços."));

		setVisible(true);

	}

	public void addSale(Sale sale) {

		manager.getSaleRepository().add(sale);
		((DefaultListModel<Integer>) listVendas.getModel()).addElement(sale.getUniqueId());
		updateInfos();
	}

	public void addProduct(Product product) {
		manager.getProductRepository().add(product);
		((DefaultListModel<Integer>) listProdutos.getModel()).addElement(product.getUniqueId());
		updateInfos();
	}

	public void removeProduct(Product product) {
		manager.getProductRepository().remove(product);
		((DefaultListModel<Integer>) listProdutos.getModel()).removeElement(product.getUniqueId());
		updateInfos();
	}

	public void edit(Product product) {
		GUIProduct.edit(manager.getProductRepository().getElement(listProdutos.getSelectedValue()), manager);
		updateInfos();
	}

	public void updateInfos() {
		listProdutos.repaint();
		buildInfo();
	}

	public void addSamples() {
		addProduct(Product.create(manager.nextProductUniqueID(), "Maçã", 3.5, 50));
		addProduct(Product.create(manager.nextProductUniqueID(), "Desinfetante", 7.5, 125));
		addProduct(Product.create(manager.nextProductUniqueID(), "Refrigerante 2L", 9.25, 25));
		addProduct(Product.create(manager.nextProductUniqueID(), "Cenoura", 2.5, 145));
		addProduct(Product.create(manager.nextProductUniqueID(), "Biscoito 200g", 5.53, 102));
		addProduct(Product.create(manager.nextProductUniqueID(), "Arroz 1kg", 10.52, 55));
		addProduct(Product.create(manager.nextProductUniqueID(), "Café 500g", 2.5, 145));
		addProduct(Product.create(manager.nextProductUniqueID(), "Sabonete", 5.99, 100));
		addProduct(Product.create(manager.nextProductUniqueID(), "Sal 400g", 2.5, 175));
		addProduct(Product.create(manager.nextProductUniqueID(), "Manteiga 250g", 10.5, 134));
		addProduct(Product.create(manager.nextProductUniqueID(), "Faca", 5.5, 32));
		addProduct(Product.create(manager.nextProductUniqueID(), "Iogurte 1L", 8.5, 64));
		addProduct(Product.create(manager.nextProductUniqueID(), "Televisão X", 549.99, 20));
		addProduct(Product.create(manager.nextProductUniqueID(), "Televisão Z", 899.99, 10));
		addProduct(Product.create(manager.nextProductUniqueID(), "Smartphone Y", 2099.99, 39));
		addProduct(Product.create(manager.nextProductUniqueID(), "Vassoura", 5.99, 34));

		Sale sale = new Sale(0, LocalDate.now());
		sale.getSaleProducts().add(new SaleProduct(manager.getProductRepository().getElement(1), 10));
		addSale(sale);

		btnAddons.setEnabled(false);
		updateInfos();
	}

	public void buildInfo() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<html>");
		buffer.append("Preço mínimo: <br>R$ " + manager.getProductRepository().getMinimumPrice()).append("<br><br>");
		buffer.append("Preço máximo: <br>R$ " + manager.getProductRepository().getMaximumPrice()).append("<br><br>");
		buffer.append("Total em estoque: <br>" + manager.getProductRepository().getTotalStockAmount() + " Produtos")
				.append("<br><br>");

		buffer.append("<i>---------------------------</i><br><br>");

		Integer selectedId = listProdutos.getSelectedValue();
		if (selectedId != null) {
			SalableProduct product = manager.getProductRepository().getElement(selectedId);
			buffer.append("Nome: <br>" + product.getName()).append("<br><br>");
			buffer.append("Preço: <br>R$ " + (product.getPrice() + "").replace(".", ",")).append("<br><br>");
			buffer.append("Qtd. em estoque: <br>" + product.getStock().getQuantityInStock()).append("<br><br>");
		}

		buffer.append("</html>");

		lblProdutosInfo.setText(buffer.toString().replace(".", ","));
	}
}
