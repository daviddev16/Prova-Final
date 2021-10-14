package org.david.view.sections;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import org.david.controller.RepositoryManager;
import org.david.miscs.Section;
import org.david.model.Sale;
import org.david.model.impl.GUISections;
import org.david.view.GUIDateField;
import org.david.view.GUISale;
import org.david.view.GUISaleList;
import org.david.view.GUIUtils;
import org.david.view.TipHandler;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@Section(title = "Vendas")
public class GUISalesSection extends GUISection<GUISections> {

  private static final long serialVersionUID = 1619303410598747538L;

  private GUISaleList listVendas;
  private JPanel panelFiltro;
  private JButton btnVender;
  private JButton btnFiltrar;
  private JScrollPane scrollPaneVendas;
  private GUIDateField dfPara;
  private GUIDateField dfDe;
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

    btnExcluir = new JButton("<html>Excluir<br>Venda</html>");
    btnVender = new JButton("Vender");
    btnFiltrar = new JButton("Filtrar");
    btnAnalisar = new JButton("Ver");
    btnRegistro = new JButton("Registro Geral");

    lblDe = new JLabel("De:");
    lblPara = new JLabel("Para:");
    btnVender.addActionListener((event) -> GUISale.createASale(getRepositoryManager()));
    btnVender.setOpaque(false);
    btnVender.setFocusPainted(false);

    scrollPaneVendas.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.decode("#dedede")));

    listVendas = new GUISaleList(getRepositoryManager(), false);

    scrollPaneVendas.setViewportView(listVendas);
    panelFiltro.setOpaque(false);
    panelFiltro.setBorder(new LineBorder(Color.LIGHT_GRAY));

    dfPara = new GUIDateField(GUIUtils.TIME_FORMAT);

    dfDe = new GUIDateField(GUIUtils.TIME_FORMAT);
    btnFiltrar.setOpaque(false);
    btnFiltrar.setFocusPainted(false);

    lblDe.setHorizontalAlignment(SwingConstants.CENTER);

    lblPara.setHorizontalAlignment(SwingConstants.CENTER);

    btnExcluir.setEnabled(false);
    btnExcluir.setOpaque(false);
    btnExcluir.setFocusPainted(false);

    btnAnalisar.setEnabled(false);
    btnAnalisar.setOpaque(false);
    btnAnalisar.setFocusPainted(false);

    btnRegistro.setOpaque(false);
    btnRegistro.setFocusPainted(false);

    GroupLayout groupLayout = new GroupLayout(this);

    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
            .addGap(5)
            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addComponent(scrollPaneVendas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addComponent(btnRegistro, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnAnalisar, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVender)
                        .addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelFiltro, GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE)))
            .addGap(5))
        );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(6)
                    .addComponent(btnVender)
                    .addGap(11)
                    .addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addGap(11)
                    .addComponent(btnAnalisar))
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(7)
                    .addComponent(panelFiltro, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(scrollPaneVendas, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
            .addGap(10)
            .addComponent(btnRegistro)
            .addGap(2))
        );
    GroupLayout gl_panelFiltro = new GroupLayout(panelFiltro);
    gl_panelFiltro.setHorizontalGroup(
        gl_panelFiltro.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelFiltro.createSequentialGroup()
            .addGap(7)
            .addGroup(gl_panelFiltro.createParallelGroup(Alignment.LEADING)
                .addComponent(lblDe, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(dfDe, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(lblPara, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(dfPara, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addComponent(btnFiltrar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
            .addGap(9))
        );
    gl_panelFiltro.setVerticalGroup(
        gl_panelFiltro.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panelFiltro.createSequentialGroup()
            .addGap(5)
            .addComponent(lblDe)
            .addComponent(dfDe, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
            .addGap(5)
            .addComponent(lblPara)
            .addComponent(dfPara, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
            .addComponent(btnFiltrar)
            .addGap(6))
        );
    panelFiltro.setLayout(gl_panelFiltro);
    setLayout(groupLayout);

  }

  @Override
  public void update() {}

  @Override
  public void handleTip(JLabel tipLabel) {
    btnVender.addMouseListener(new TipHandler(tipLabel, "Iniciar um processo de venda."));
  }

  public void addSale(Sale sale) {
    getRepositoryManager().getSaleRepository().add(sale);
    ((DefaultListModel<Integer>) listVendas.getModel()).addElement(sale.getUniqueId());
  }

}
