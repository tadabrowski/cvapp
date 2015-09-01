package pl.tware.cv.ui.image.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import pl.tware.cv.language.Language;
import pl.tware.cv.ui.image.HasImage;
import pl.tware.cv.ui.image.ImageName;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.vaadin.server.ClassResource;
import com.vaadin.server.Resource;

public class ImageResourceUtils {

	public static Resource getImage(HasImage image, Language language) {
		Preconditions.checkArgument(image.getImageName().isLocalized());
		return new ClassResource(createLocalizedFileName(image.getImageName(), language));
	}

	public static Resource getImage(HasImage image) {
		return new ClassResource(createFileName(image.getImageName()));

	}

	public static InputStream getImageAsStream(HasImage image) {
		Preconditions.checkArgument(!image.getImageName().isLocalized());
		return ImageResourceUtils.class.getClassLoader().getResourceAsStream("pl/tware/cv/ui/" + createFileName(image.getImageName()));
	}

	public static byte[] getImageAsBytes(ImageName image) {
		InputStream stream = getImageAsStream(image);
		try {
			return IOUtils.toByteArray(stream);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}

	}

	private static String createFileName(ImageName image) {
		return String.format("%s/%s.%s", ImageName.ROOT_FOLDER, image.getFileName(), ImageName.IMAGE_EXTENSION);
	}

	private static String createLocalizedFileName(ImageName image, Language language) {
		return String.format("%s/%s-%s.%s", ImageName.ROOT_FOLDER, image.getFileName(), language.getSuffix(), ImageName.IMAGE_EXTENSION);
	}

}
