package org.david.view.sections;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.david.controller.RepositoryManager;
import org.david.miscs.Section;

public abstract class GUISection<R> extends JPanel {

	private static final long serialVersionUID = 3604377381051905696L;

	private final RepositoryManager repositoryManager;
	private final R root;
	
	public GUISection(final RepositoryManager repositoryManager, R root) {
		this.repositoryManager = repositoryManager;
		this.root = root;
		
		Section sectionAnn = getClass().getAnnotation(Section.class);
		String title = (sectionAnn != null) ? sectionAnn.title() : "-";

		setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), title, TitledBorder.CENTER, TitledBorder.TOP,
				null, new Color(192, 192, 192)));
	}

	public abstract void update();

	public abstract void handleTip(JLabel tipLabel);

	public R getRoot() {
		return root;
	}
	
	public RepositoryManager getRepositoryManager() {
		return this.repositoryManager;
	}

}
