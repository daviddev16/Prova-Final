package org.david.view;

import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import org.david.controller.ApplicationManager;
import org.david.model.Sale;

public class GUISaleList extends JList<Integer> {

	private static final long serialVersionUID = -4973167272491005453L;

	public GUISaleList(ApplicationManager manager, boolean load) {
		super();
		setOpaque(false);
		setModel(new DefaultListModel<Integer>());
		setCellRenderer(new ListCellRenderer<Integer>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (manager == null) {
					return new JLabel();
				}
				LocalDate data = manager.getSaleRepository().get(value, Sale::getDate);
				double totalPrice = manager.getSaleRepository().get(value, Sale::getSaleTotalPrice);

				JLabel label = new JLabel();
				label.setBorder(new EmptyBorder(0, 5, 0, 0));
				label.setOpaque(isSelected);
				label.setPreferredSize(new Dimension(100, 45));
				label.setFont(new FontUIResource("Segoe UI", Font.TRUETYPE_FONT, 12));
				label.setBackground(Color.GREEN.darker());
				label.setForeground(isSelected ? Color.WHITE : Color.BLACK);

				StringBuffer buffer = new StringBuffer();
				buffer.append("<html>");
				buffer.append("<b>Venda feita com sucesso!</b><br>");
				buffer.append("<font size=1>" + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - R$ " + totalPrice + "</font>");
				buffer.append("</html>");

				label.setText(buffer.toString().replace(".", ","));

				return label;
			}
		});
		if (load) {
			manager.getProductRepository().stream().forEach(product -> {
				((DefaultListModel<Integer>) getModel()).addElement(product.getUniqueId());
			});
		}
	}

}
