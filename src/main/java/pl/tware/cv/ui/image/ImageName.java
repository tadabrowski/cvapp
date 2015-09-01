package pl.tware.cv.ui.image;


public enum ImageName implements HasImage {

	TECHNOLOGIES(true), //
	EDUCATION(true), //
	EXPERIENCE(true), //
	CERTIFICATES(true), //
	BASIC_INFO(true), //
	DOWNLOAD_PDF(true),

	ENGLISH_FLAG, //
	POLISH_FLAG, //
	
	WEITI,
	
	JAVA,
	SPRING,
	VAADIN,
	MOCKITO,
	HIBERNATE,
	CAMEL,
	ACTIVITI,
	GROOVY,
	GWT,
	SMARTGWT,
	FREEMARKER,
	LIFERAY,
	DROOLS,
	JBPM,
	MAVEN,	
	SCALA,
	
	ORACLE,
	PRINCE2,
	ITIL,
	
	QUOTE,
	TITLE(true)
	
	;

	public static final String ROOT_FOLDER = "image";
	public static final String IMAGE_EXTENSION = "png";

	private boolean localized;

	private ImageName() {
		this(false);
	}

	private ImageName(boolean localized) {
		this.localized = localized;
	}

	public String getFileName() {
		return name().toLowerCase().replace('_', '-');
	}

	public boolean isLocalized() {
		return localized;
	}
	
	@Override
	public ImageName getImageName() {
		return this;
	}

}
