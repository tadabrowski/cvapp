package pl.tware.cv.language;

import com.google.common.base.CaseFormat;

public enum MessageKey implements HasMessage {

	TITLE,
	BROWSER_WARNING,
	VERSION
	;

	@Override
	public String getKey() {
		return DEFAULT_FORMAT.to(CaseFormat.LOWER_CAMEL, name()) + "Label";
	}

}
