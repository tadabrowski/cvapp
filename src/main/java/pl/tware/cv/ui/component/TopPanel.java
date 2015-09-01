package pl.tware.cv.ui.component;

import java.util.Map;
import java.util.Map.Entry;

import pl.tware.cv.language.Language;
import pl.tware.cv.ui.image.util.ImageResourceUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@StyleSheet("top-panel.css")
public class TopPanel extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private static final String CAPTION_PROPERTY_ID = "languageCaption";

	private ComboBox languageComboBox;
	private ValueChangeListener listener;
	private Label titleLabel;
	private Map<Language, String> translatedLanguages;
	private Map<Language, String> translatedTitles;
	private Map<Language, String> translatedVersions;
	private Label versionLabel;
	private Label versionValue;

	public TopPanel(Map<Language, String> translatedLanguages, Map<Language, String> translatedTitles, Map<Language, String> translatedVersions) {
		Preconditions.checkArgument(translatedLanguages.keySet().containsAll(ImmutableSet.copyOf(Language.values())));
		Preconditions.checkArgument(translatedTitles.keySet().containsAll(ImmutableSet.copyOf(Language.values())));
		Preconditions.checkArgument(translatedVersions.keySet().containsAll(ImmutableSet.copyOf(Language.values())));
		this.translatedLanguages = translatedLanguages;
		this.translatedTitles = translatedTitles;
		this.translatedVersions = translatedVersions;
		addStyleName("top-panel");
		setSizeFull();
		buildComponents();
	}

	public void setLanguageChangeListener(ValueChangeListener listener) {
		if (this.listener != null) {
			languageComboBox.removeValueChangeListener(listener);
		}
		this.listener = listener;
		languageComboBox.addValueChangeListener(listener);
	}

	@SuppressWarnings("unchecked")
	public void setCurrentLanguage(Language currentLanguage) {
		titleLabel.setValue(translatedTitles.get(currentLanguage));
		versionLabel.setValue(translatedVersions.get(currentLanguage));
		Container container = languageComboBox.getContainerDataSource();
		for (Entry<Language, String> e : translatedLanguages.entrySet()) {
			container.getItem(e.getKey()).getItemProperty(CAPTION_PROPERTY_ID).setValue(e.getValue());
		}
		languageComboBox.setValue(currentLanguage);
	}

	public void setVersion(String version) {
		versionValue.setValue(version);
	}

	private void buildComponents() {
		buildTitleLabel();
		buildVersionLabel();
		buildVersionValue();
		buildLanguageComboBox();

		addComponent(new HorizontalLayout(versionLabel, versionValue));
		addComponent(languageComboBox);
		setComponentAlignment(languageComboBox, Alignment.MIDDLE_RIGHT);
	}

	private void buildLanguageComboBox() {
		languageComboBox = new ComboBox();
		languageComboBox.setWidth(200, Unit.PIXELS);
		languageComboBox.setNullSelectionAllowed(false);
		languageComboBox.setItemCaptionPropertyId(CAPTION_PROPERTY_ID);

		BeanItemContainer<Language> languageContainer = new BeanItemContainer<>(Language.class);
		languageComboBox.setContainerDataSource(languageContainer);
		for (Language i : Language.values()) {
			languageContainer.addItem(i).addItemProperty(CAPTION_PROPERTY_ID, new ObjectProperty<String>(""));
			languageComboBox.setItemIcon(i, ImageResourceUtils.getImage(i.getFlagImage()));
		}
	}

	private void buildVersionLabel() {
		versionLabel = new Label();
		versionLabel.addStyleName("top-version-label");
	}

	private void buildVersionValue() {
		versionValue = new Label();
		versionValue.addStyleName("top-version-value");
	}

	private void buildTitleLabel() {
		titleLabel = new Label();
		titleLabel.addStyleName("top-title");
		addComponent(titleLabel);
	}

}
