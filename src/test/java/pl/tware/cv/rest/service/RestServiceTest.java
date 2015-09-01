package pl.tware.cv.rest.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import pl.tware.cv.language.Language;
import pl.tware.cv.ui.image.ImageName;

import com.google.common.base.Throwables;
import com.google.common.io.Files;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceTest {

	private static final String PDF_EXTENSION = "pdf";
	private static final String PDF_FILE_PREFIX = "JohnSmith";

	@InjectMocks
	private RestService restService = new RestService();

	private File pdfFilesDirectory;

	@Before
	public void before() throws IllegalAccessException {
		pdfFilesDirectory = Files.createTempDir();
		FieldUtils.writeField(restService, "cvPdfDirectory", pdfFilesDirectory.getAbsolutePath(), true);
		FieldUtils.writeField(restService, "cvPdfFilenameExtension", PDF_EXTENSION, true);
		FieldUtils.writeField(restService, "cvPdfFilenamePrefix", PDF_FILE_PREFIX, true);
	}

	@Test
	public void getImageTest() {
		ResponseEntity<byte[]> response = restService.getImage(ImageName.JAVA.name().toLowerCase());
		commonResponseVerification(response);
		Assert.assertArrayEquals(response.getBody(), loadJavaImage());
	}

	@Test
	public void getPdfFileTest() throws IOException {
		File pdfFile = createEnglishPdfFile(pdfFilesDirectory);

		ResponseEntity<byte[]> response = restService.getPdfFile(Language.ENGLISH.name());
		commonResponseVerification(response);
		Assert.assertArrayEquals(response.getBody(), FileUtils.readFileToByteArray(pdfFile));
	}

	private void commonResponseVerification(ResponseEntity<?> response) {
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_OCTET_STREAM);
		Assert.assertNotNull(response.getBody());
	}

	private File createEnglishPdfFile(File parent) {
		File pdfFile = new File(parent, Language.ENGLISH.getSuffix() + "." + PDF_EXTENSION);
		byte[] fakeData = new byte[1024];
		Arrays.fill(fakeData, Byte.MAX_VALUE);
		try {
			Files.write(fakeData, pdfFile);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return pdfFile;
	}

	private byte[] loadJavaImage() {
		try {
			return IOUtils.toByteArray(this.getClass().getResourceAsStream("/pl/tware/cv/ui/image/java.png"));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

}
