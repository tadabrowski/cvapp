package pl.tware.cv.template;

import pl.tware.cv.language.HasMessage;
import pl.tware.cv.ui.image.HasImage;
import pl.tware.cv.ui.image.ImageName;

import com.google.common.base.CaseFormat;

public enum TemplateName  implements HasMessage, HasImage {

	BASIC_INFO,
	EDUCATION,
	EXPERIENCE,
	TECHNOLOGIES,
	CERTIFICATES,
	DOWNLOAD_PDF,
	;

	public String getFileName() {
		return name().replace('_', '-').toLowerCase() + ".ftl";
	}

	@Override
	public String getKey() {
		return DEFAULT_FORMAT.to(CaseFormat.LOWER_CAMEL, name()) + "Label";
	}
	
	@Override
	public ImageName getImageName() {
		return ImageName.valueOf(name());
	}

}
