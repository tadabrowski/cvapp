package pl.tware.cv.language;

import com.google.common.base.CaseFormat;

public interface HasMessage {

	public static final CaseFormat DEFAULT_FORMAT = CaseFormat.UPPER_UNDERSCORE;

	public String getKey();

}
