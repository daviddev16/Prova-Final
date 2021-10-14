package org.david.view.sections;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.david.controller.RepositoryManager;
import org.david.model.Sale;
import org.david.view.GUISections;
import org.david.view.components.DateField;
import org.david.view.components.SaleList;
import org.david.view.dialogs.SaleBuilderDialog;
import org.david.view.dialogs.SaleViewerDialog;
import org.david.view.miscs.Section;
import org.david.view.miscs.TipHandler;
import org.david.view.miscs.Utils;

@Section(title = "Vendas")
public class GUISalesSection extends GUISection<GUISections> {

  private static final long serialVersionUID = 1619303410598747538L;

  public static final int ALL_TIME_CONST = -921;

  private SaleList listVendas;
  private JPanel panelFiltro;
  private JButton btnVender;
  private JButton btnFiltrar;
  private JScrollPane scrollPaneVendas;
  private DateField dfPara;
  private DateField dfDe;
  private JLabel lblDe;
  private JLabel lblPara;
  private JButton btnExcluir;
  private JButton btnAnalisar;
  private JButton btnRegistro;

  public GUISalesSection(RepositoryManager repositoryManager, GUISections root) {
    super(repositoryManager, root);

    setBounds(313, 11, 184, 338);

    panelFiltro = new JPanel();
    scrollPaneVendas = new JScrollPane();

    listVendas = new SaleList(getRepositoryManager(), false);

    btnExcluir = new JButton("<html>Excluir<br>Venda</html>");
    btnVender = new JButton("Vender");
    btnFiltrar = new JButton("Filtrar");
    btnAnalisar = new JButton("Ver");
    btnRegistro = new JButton("Relatório");

    lblDe = new JLabel("De:");
    lblPara = new JLabel("Para:");

    dfPara = new DateField(Utils.TIME_FORMAT);
    dfDe = new DateField(Utils.TIME_FORMAT);

    listVendas.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent arg0) {
        boolean canInteract = listVendas.getSelectedValue() != null && listVendas.getSelectedValue() >= 0;
        btnExcluir.setEnabled(canInteract);
        btnAnalisar.setEnabled(canInteract);
      }
    });

    btnFiltrar.addActionListener((event) -> {

      if (btnFiltrar.getText().equals("Resetar")) {
        btnFiltrar.setText("Filtrar");
        listVendas.loadAll(repositoryManager);
        ((DefaultListModel<Integer>) listVendas.getModel()).add(0, ALL_TIME_CONST);
        return;
      }

      if (dfDe.getValue() != null && dfPara.getValue() != null) {

        LocalDate from = dfDe.getValue();
        LocalDate to = dfPara.getValue();

        if (!from.isBefore(to)) {
          return;
        }

        ((DefaultListModel<Integer>) listVendas.getModel()).removeAllElements();
        ((DefaultListModel<Integer>) listVendas.getModel()).add(0, (int) -from.toEpochDay());

        List<Sale> salesBetweenDates = repositoryManager.getSaleRepository().stream().filter(new Predicate<Sale>() {
          @Override
          public boolean test(Sale t) {
            return t.getDate().isAfter(from.plusDays(1)) && t.getDate().isBefore(to);
          }
        }).collect(Collectors.toList());

        btnFiltrar.setText("Resetar");

        salesBetweenDates.forEach(e -> {
          ((DefaultListModel<Integer>) listVendas.getModel()).addElement(e.getUniqueId());
        });

        ((DefaultListModel<Integer>) listVendas.getModel())
            .add(((DefaultListModel<Integer>) listVendas.getModel()).getSize(), (int) -to.toEpochDay());
      }

    });

    btnExcluir.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if (listVendas.getSelectedValue() != null) {
          removeSale(repositoryManager.getSaleRepository().getElement(listVendas.getSelectedValue()));
        }
      }
    });

    btnVender.addActionListener((event) -> {
      Sale newSale = SaleBuilderDialog.createASale(getRepositoryManager(), getRoot());

      if (newSale == null)
        return;

      addSale(newSale);

    });
    btnVender.setOpaque(false);
    btnVender.setFocusPainted(false);

    btnAnalisar.addActionListener((event) -> {
      if (listVendas.getSelectedValue() != null) {
        SaleViewerDialog
            .viewSaleInformations(repositoryManager.getSaleRepository().getElement(listVendas.getSelectedValue()));
      }
    });

    scrollPaneVendas.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));
    scrollPaneVendas.setViewportView(listVendas);

    panelFiltro.setOpaque(false);
    panelFiltro.setBorder(new LineBorder(Color.LIGHT_GRAY));

    dfPara.setValue(LocalDate.of(2021, 12, 30));
    dfDe.setValue(LocalDate.of(2021, 01, 01));
    btnFiltrar.setOpaque(false);
    btnFiltrar.setFocusPainted(false);

    btnExcluir.setEnabled(false);
    btnExcluir.setOpaque(false);
    btnExcluir.setFocusPainted(false);

    btnAnalisar.setEnabled(false);
    btnAnalisar.setOpaque(false);
    btnAnalisar.setFocusPainted(false);

    btnRegistro.setOpaque(false);
    btnRegistro.setFocusPainted(false);

    ((DefaultListModel<Integer>) listVendas.getModel()).add(0, ALL_TIME_CONST);

    lblDe.setHorizontalAlignment(SwingConstants.CENTER);
    lblPara.setHorizontalAlignment(SwingConstants.CENTER);

    GroupLayout groupLayout = new GroupLayout(this);

    groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
        groupLayout.createSequentialGroup().addGap(5)
            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addComponent(scrollPaneVendas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addComponent(btnRegistro, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addGroup(Alignment.LEADING,
                    groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addComponent(btnAnalisar, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVender)
                            .addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(panelFiltro, GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE)))
            .addGap(5)));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(btnVender).addGap(11)
                    .addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGap(11)
                    .addComponent(btnAnalisar))
                .addGroup(groupLayout.createSequentialGroup().addGap(7).addComponent(panelFiltro,
                    GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(scrollPaneVendas, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE).addGap(10)
            .addComponent(btnRegistro).addGap(2)));
    GroupLayout gl_panelFiltro = new GroupLayout(panelFiltro);
    gl_panelFiltro.setHorizontalGroup(gl_panelFiltro.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelFiltro.createSequentialGroup().addGap(7)
            .addGroup(gl_panelFiltro.createParallelGroup(Alignment.LEADING)
                .addComponent(lblDe, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(dfDe, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(lblPara, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(dfPara, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE).addComponent(btnFiltrar,
                    Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
            .addGap(9)));
    gl_panelFiltro.setVerticalGroup(gl_panelFiltro.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelFiltro.createSequentialGroup().addGap(5).addComponent(lblDe)
            .addComponent(dfDe, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addGap(5)
            .addComponent(lblPara).addComponent(dfPara, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE).addComponent(btnFiltrar).addGap(6)));
    panelFiltro.setLayout(gl_panelFiltro);
    setLayout(groupLayout);

  }

  @Override
  public void update() {
  }

  @Override
  public void handleTip(JLabel tipLabel) {
    btnVender.addMouseListener(new TipHandler(tipLabel, "Iniciar um processo de venda."));
  }

  public void addSale(Sale sale) {
    getRepositoryManager().getSaleRepository().add(sale);
    ((DefaultListModel<Integer>) listVendas.getModel()).addElement(sale.getUniqueId());
  }

  public void removeSale(Sale sale) {
    getRepositoryManager().getSaleRepository().remove(sale);
    ((DefaultListModel<Integer>) listVendas.getModel()).removeElement(sale.getUniqueId());
  }

}
