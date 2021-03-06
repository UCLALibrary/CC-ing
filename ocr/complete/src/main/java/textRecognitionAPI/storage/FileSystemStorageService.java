package textRecognitionAPI.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import net.sourceforge.tess4j.*;
import textRecognitionAPI.tesseractEngine.TesseractCC;
import textRecognitionAPI.tesseractEngine.WordCC;

import java.util.stream.Stream;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class implement the service interface that provides recognition service
 * 
 * @author kcho
 *
 */

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;
	private final String TESS4J_FOLDER_PATH;
	private final String OCR_LANGUAGE;
	private final int OCR_ENGINE_MODE;
	private final int OCR_PAGE_MODE;
	private final TesseractCC OCRcc;

	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		// properties values
		this.rootLocation = Paths.get(properties.getLocation());
		this.TESS4J_FOLDER_PATH = properties.getTessDir();
		this.OCR_LANGUAGE = properties.getOcrLanguage();
		this.OCR_ENGINE_MODE = properties.getOcrEngineMode();
		this.OCR_PAGE_MODE = properties.getOcrPageMode();

		// create the tesseract instance
		TesseractCC OCRcc = new TesseractCC(TESS4J_FOLDER_PATH, OCR_LANGUAGE, OCR_ENGINE_MODE, OCR_PAGE_MODE);
		this.OCRcc = OCRcc;
	}

	/**
	 * Do ocr based on uploaded images
	 */
	@Override
	public HashMap<String, String> doOcr(MultipartFile file, String languages) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			// Mark each uploaded file with UNIX epoch timestamp
			long unixTime = System.currentTimeMillis() / 1000L;
			String fileTimeStamped = this.rootLocation
					.resolve(Long.toString(unixTime) + "-" + file.getOriginalFilename()).toString();

			// Store locally for OCR
			Files.copy(file.getInputStream(), Paths.get(fileTimeStamped));
			File imageFile = new File(fileTimeStamped);

			// Set language
			String languagesUsed = languages;			
			if (languages != null && !languages.isEmpty()) {
				OCRcc.setLanguage(languages);
			} else {
				OCRcc.setLanguage(OCR_LANGUAGE);
				languagesUsed = OCR_LANGUAGE;
			}
			
			// Perform normal OCR and get string
			String ocrResult = OCRcc.doOCR(imageFile); // get all the string
			
			// Perform OCR and get word bounding boxes.
			// 02/02/17 For now, return the bounding box string, you can always return the bounding box information as well
			String ocrDetailResult = OCRcc.doOCRByWords(imageFile);
									
			// Store the result into hashmap
			HashMap results = new HashMap();
			results.put("ocrResult", ocrResult);
			results.put("languagesUsed", languagesUsed);
			results.put("Epoch Time", unixTime);
			results.put("ocrDetailResult", ocrDetailResult);
			
			return results;
			
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		} catch (TesseractException e) {
			throw new StorageException("Failed to ocr file " + file.getOriginalFilename(), e);
		}
	}

	/**
	 * Donwload the given image
	 * @param imageURL
	 * @param destinationFile
	 * @return
	 * @throws IOException
	 */
	public File downloadImageURL(String imageURL, String destinationFile) throws IOException {
		try {
			String extension = imageURL.substring(imageURL.lastIndexOf(".") + 1);
			if (!extension.equals("jpg") && !extension.equals("png")) {
				Exception e = new Exception("Invalid file type, must be jpg or png URL");
				throw e;
			}
			URL url = new URL(imageURL);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			return new File(destinationFile);
		} catch (IndexOutOfBoundsException e) {
			throw new IOException("URL is invalid: " + imageURL, e);
		} catch (IOException e) {
			throw new IOException("Failed to retrieve or store file " + imageURL, e);
		} catch (Exception e) {
			throw new IOException("Failed to retrieve or store file " + imageURL, e);
		}
	}

	/**
	 * Overload OCR with url instead of the file
	 */
	@Override
	public HashMap<String, String> doOcr(String imageURL, String languages) {
		try {
			if (imageURL.isEmpty()) {
				throw new StorageException("Failed to retrieve URL" + imageURL);
			}

			// Mark each uploaded file with UNIX epoch timestamp
			long unixTime = System.currentTimeMillis() / 1000L;
			String fileTimeStamped = this.rootLocation.resolve(Long.toString(unixTime) + "-"
					+ imageURL.substring(imageURL.lastIndexOf('/') + 1, imageURL.length())).toString();
			File file = downloadImageURL(imageURL, fileTimeStamped);

			String languagesUsed = languages;
			// Set language
			if (languages != null && !languages.isEmpty()) {
				OCRcc.setLanguage(languages);
			} else {
				OCRcc.setLanguage(OCR_LANGUAGE);
				languagesUsed = OCR_LANGUAGE;
			}
			
			// do ocrs
			String ocrResult = OCRcc.doOCR(file);
			String ocrDetailResult = OCRcc.doOCRByWords(file);
			
			// store the result
			HashMap results = new HashMap();
			results.put("ocrResult", ocrResult);
			results.put("languagesUsed", languagesUsed);
			results.put("Epoch Time", unixTime);
			results.put("ocrDetailResult", ocrDetailResult);
			
			return results;
		} catch (IOException e) {
			throw new StorageException("Failed to retrieve or store file " + imageURL, e);
		} catch (TesseractException e) {
			throw new StorageException("Failed to ocr file " + imageURL, e);
		} catch (IndexOutOfBoundsException e) {
			throw new StorageException("URL is invalid " + imageURL, e);
		}

	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
