package pl.tware.cv.language;

import pl.tware.cv.ui.image.ImageName;

import com.google.common.base.CaseFormat;

public enum Language implements HasMessage {

	POLISH("pl"), //
	ENGLISH("en") //
	;

	private String suffix;

	private Language(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public ImageName getFlagImage() {
		return ImageName.valueOf(String.format("%s_FLAG", name()));
	}

	@Override
	public String getKey() {
		return DEFAULT_FORMAT.to(CaseFormat.LOWER_CAMEL, name());
	}

}
