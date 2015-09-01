package pl.tware.cv.ui.component;

import pl.tware.cv.template.TemplateName;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

@StyleSheet({ "html-container.css" })
public class HtmlContainer extends Label {

	private static final long serialVersionUID = 1L;
	private TemplateName templateName;

	public HtmlContainer(TemplateName templateName, String html) {
		super(html, ContentMode.HTML);
		this.templateName = templateName;
		setStyleName("html-container");
	}

	public TemplateName getTemplateName() {
		return templateName;
	}

	public void setHtml(String html) {
		setValue(html);
	}

}
