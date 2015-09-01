package pl.tware.cv.ui.component;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.Panel;

@StyleSheet("navigation-panel.css")
public class NavigationPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private static final String STYLE_NAME_ROOT = "navigationPanel";

	public NavigationPanel() {
		buildComponents();
	}

	private void buildComponents() {
		Panel content = new Panel();
		content.setStyleName(STYLE_NAME_ROOT);

		setContent(content);
	}

}
