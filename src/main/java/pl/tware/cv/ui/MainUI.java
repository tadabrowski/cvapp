package pl.tware.cv.ui;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.viritin.util.BrowserCookie;

import pl.tware.cv.cookie.CookiesNames;
import pl.tware.cv.language.Language;
import pl.tware.cv.language.MessageKey;
import pl.tware.cv.language.service.LanguageService;
import pl.tware.cv.template.TemplateName;
import pl.tware.cv.template.service.TemplateService;
import pl.tware.cv.ui.component.DashboardPanel;
import pl.tware.cv.ui.component.HtmlContainer;
import pl.tware.cv.ui.component.StickerButton;
import pl.tware.cv.ui.component.TopPanel;
import pl.tware.cv.ui.image.ImageName;
import pl.tware.cv.ui.image.util.ImageResourceUtils;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SpringUI
@Theme("valo")
@StyleSheet({ "main-ui.css" })
@PreserveOnRefresh
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;

	@Value("${app.version}")
	private String version;

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private TemplateService templateService;

	private Language currentLanguage;

	@Override
	protected void init(VaadinRequest request) {
		currentLanguage = readCurrentLanguage();
		buildComponents();
		showBrowserWarning();
	}

	@Override
	protected void refresh(VaadinRequest request) {
		currentLanguage = readCurrentLanguage();
		buildComponents();
		refreshWindows();
	}

	private void showBrowserWarning() {
		Notification notification = new Notification(languageService.getMessage(currentLanguage, MessageKey.BROWSER_WARNING), Type.HUMANIZED_MESSAGE);
		notification.setDelayMsec(5 * 1000);
		notification.show(Page.getCurrent());
	}

	private Language readCurrentLanguage() {
		return Language.valueOf(getOrCreateCookieByName(CookiesNames.LANGUAGE, Language.POLISH.name()).getValue());
	}

	private void updateCurrentLanguage(Language language) {
		BrowserCookie.setCookie(CookiesNames.LANGUAGE, language.name());
		currentLanguage = language;
	}

	private void buildComponents() {
		setSizeFull();

		TopPanel topPanel = buildTopPanel();
		DashboardPanel dashboardPanel = buildDashboardPanel();
		Image quoteImage = buildQuoteImage();

		setContent(new VerticalLayout(topPanel, dashboardPanel, quoteImage));
	}

	private Image buildQuoteImage() {
		Image quoteImage = new Image();
		quoteImage.setSource(ImageResourceUtils.getImage(ImageName.QUOTE));
		quoteImage.addStyleName("quote-image");
		return quoteImage;
	}

	private DashboardPanel buildDashboardPanel() {
		DashboardPanel dashboardPanel = new DashboardPanel();
		HorizontalLayout layout = new HorizontalLayout();
		for (TemplateName i : ImmutableList.of( //
				TemplateName.BASIC_INFO, //
				TemplateName.EDUCATION, //
				TemplateName.EXPERIENCE, //
				TemplateName.TECHNOLOGIES, //
				TemplateName.CERTIFICATES, //
				TemplateName.DOWNLOAD_PDF //
				)) {
			final TemplateName templateName = i;
			StickerButton button = new StickerButton( //
					ImageResourceUtils.getImage(i.getImageName(), currentLanguage), //
					languageService.getMessage(currentLanguage, i), //
					new Supplier<Component>() {

						@Override
						public Component get() {
							return new HtmlContainer(templateName, templateService.processTemplate(templateName, currentLanguage));
						}
					});
			layout.addComponent(button);
		}
		dashboardPanel.addComponent(layout);

		dashboardPanel.setSizeFull();
		return dashboardPanel;
	}

	private TopPanel buildTopPanel() {
		TopPanel topPanel = new TopPanel(//
				languageService.getLanguagesTranslations(currentLanguage), //
				languageService.getMessageTranslations(MessageKey.TITLE), //
				languageService.getMessageTranslations(MessageKey.VERSION) //
		);
		topPanel.setVersion(version);
		topPanel.setCurrentLanguage(currentLanguage);
		topPanel.setHeight(50, Unit.PIXELS);
		topPanel.setLanguageChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateCurrentLanguage((Language) event.getProperty().getValue());
				Page.getCurrent().reload();
			}
		});
		return topPanel;
	}

	private void refreshWindows() {
		for (Window i : MainUI.this.getWindows()) {
			if (i.getContent() instanceof HtmlContainer) {
				HtmlContainer container = (HtmlContainer) i.getContent();
				i.setCaption(languageService.getMessage(currentLanguage, container.getTemplateName()));
				container.setHtml(templateService.processTemplate(container.getTemplateName(), currentLanguage));
			}
		}
	}

	private Cookie getOrCreateCookieByName(final String name, String defaultValue) {
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		ImmutableList<Cookie> cookiesList = ImmutableList.<Cookie> copyOf(cookies != null ? cookies : new Cookie[0]);
		Optional<Cookie> cookie = Iterables.tryFind(cookiesList, new Predicate<Cookie>() {

			@Override
			public boolean apply(Cookie input) {
				return StringUtils.equals(name, input.getName());
			}
		});
		if (!cookie.isPresent() || StringUtils.isEmpty(cookie.get().getValue())) {
			Cookie newCookie = new Cookie(name, defaultValue);
			newCookie.setMaxAge(60 * 60 * 24 * 30);
			return newCookie;
		} else {
			return cookie.get();
		}
	}

}
