package pl.tware.cv.template.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.tware.cv.language.Language;
import pl.tware.cv.language.service.LanguageService;
import pl.tware.cv.rest.service.RestService;
import pl.tware.cv.template.TemplateName;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class TemplateService {

	@Autowired
	private LanguageService languageService;

	private Configuration configuration;

	@PostConstruct
	public void init() {
		configuration = new Configuration(Configuration.VERSION_2_3_22);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(TemplateName.class, "");
		configuration.setEncoding(Locale.forLanguageTag("pl"), "utf-8");
	}

	public String processTemplate(TemplateName name, Language language) {
		StringWriter writer = new StringWriter();
		try {
			Template template = configuration.getTemplate(name.getFileName());
			Map<?, ?> model = getDataModel(language);
			template.process(model, writer);
		} catch (IOException | TemplateException e) {
			throw Throwables.propagate(e);
		}
		return writer.toString();
	}

	private Map<?, ?> getDataModel(Language language) {
		HashMap<Object, Object> model = Maps.newHashMap(languageService.getMessagesAsModel(language));
		model.put("image", new ImageTag());
		model.put("currentLanguage", language);
		model.put("languages", languageService.getLanguagesTranslations(language));
		return model;
	}

	public static class ImageTag {
		public String get(String name, String styleName) {
			return String.format("<img class=%s src='%s?%s=%s'/>", styleName, RestService.GET_IMAGE_PATH, RestService.GET_IMAGE_PARAM, name);
		}

		public String get(String name) {
			return String.format("<img src='%s?%s=%s'/>", RestService.GET_IMAGE_PATH, RestService.GET_IMAGE_PARAM, name);
		}
	}

}
