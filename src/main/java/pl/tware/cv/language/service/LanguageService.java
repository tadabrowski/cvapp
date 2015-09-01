package pl.tware.cv.language.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import pl.tware.cv.language.HasMessage;
import pl.tware.cv.language.Language;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Maps;

@Component
public class LanguageService {

	@SuppressWarnings("rawtypes")
	private LoadingCache<Language, Map> languagesPropertiesCache = CacheBuilder.newBuilder() //
			.expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<Language, Map>() {

				@Override
				public Map load(Language key) throws JsonParseException, JsonMappingException, IOException {
					return loadMessagesFile(String.format("messages_%s.json", key.getSuffix()));
				}
			});

	@PostConstruct
	public void init() {
	}

	public String getMessage(Language language, HasMessage hasMessage) {
		return Objects.toString(languagesPropertiesCache.getUnchecked(language).get(hasMessage.getKey()));
	}

	public Map<?, ?> getMessagesAsModel(Language language) {
		return languagesPropertiesCache.getUnchecked(language);
	}

	public Map<Language, String> getLanguagesTranslations(final Language language) {
		return Maps.asMap(ImmutableSortedSet.copyOf(Language.values()), new Function<Language, String>() {

			@Override
			public String apply(Language input) {
				return Objects.toString(languagesPropertiesCache.getUnchecked(language).get(input.getKey()));
			}
		});
	}

	public Map<Language, String> getMessageTranslations(final HasMessage hasMessage) {
		return Maps.asMap(ImmutableSortedSet.copyOf(Language.values()), new Function<Language, String>() {

			@Override
			public String apply(Language input) {
				return Objects.toString(languagesPropertiesCache.getUnchecked(input).get(hasMessage.getKey()));
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private Map loadMessagesFile(String filename) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = LanguageService.class.getClassLoader().getResourceAsStream(filename);
		try {
			return mapper.readValue(stream, HashMap.class);
		} finally {
			stream.close();
		}
	}

}
