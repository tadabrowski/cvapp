package pl.tware.cv.rest.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.tware.cv.language.Language;
import pl.tware.cv.ui.image.ImageName;
import pl.tware.cv.ui.image.util.ImageResourceUtils;

import com.google.common.base.Throwables;
import com.google.common.io.Files;

@RestController
public class RestService {

	public static final String GET_IMAGE_PATH = "/get-image";
	public static final String GET_IMAGE_PARAM = "name";

	public static final String GET_PDF_FILE_PATH = "/get-pdf-file";
	public static final String GET_PDF_FILE_PARAM = "language";

	@Value("${cv.pdf.directory}")
	private String cvPdfDirectory;

	@Value("${cv.pdf.filename.prefix}")
	private String cvPdfFilenamePrefix;

	@Value("${cv.pdf.filename.extension}")
	private String cvPdfFilenameExtension;

	@RequestMapping(GET_IMAGE_PATH)
	public @ResponseBody ResponseEntity<byte[]> getImage(@RequestParam(GET_IMAGE_PARAM) String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		ResponseEntity<byte[]> response = new ResponseEntity<>( //
				ImageResourceUtils.getImageAsBytes(ImageName.valueOf(StringUtils.upperCase(name))), headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping(GET_PDF_FILE_PATH)
	public @ResponseBody ResponseEntity<byte[]> getPdfFile(@RequestParam(GET_PDF_FILE_PARAM) String stringLanguage) {
		Language language = Language.valueOf(stringLanguage);
		File file = new File(cvPdfDirectory);
		for (File i : file.listFiles()) {
			if (i.getName().matches(String.format("%s.%s", language.getSuffix(), cvPdfFilenameExtension))) {
				try {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.set("Content-Disposition",
							String.format("attachment; filename=\"%s%s.%s\"", cvPdfFilenamePrefix, language.getSuffix(), cvPdfFilenameExtension));
					ResponseEntity<byte[]> response = new ResponseEntity<>(Files.toByteArray(i), headers, HttpStatus.OK);
					return response;
				} catch (IOException e) {
					throw Throwables.propagate(e);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
