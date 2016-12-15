package hello.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import net.sourceforge.tess4j.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final String TESS4J_FOLDER_PATH;
    private final Tesseract OCR; 

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
    {
            File convFile = new File( multipart.getOriginalFilename());
            multipart.transferTo(convFile);
            return convFile;
    }

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.TESS4J_FOLDER_PATH = properties.getTessDir();
        Tesseract OCR = new Tesseract();
        OCR.setDatapath(TESS4J_FOLDER_PATH);
        // TODO: Base on user input
        OCR.setLanguage("eng");
        this.OCR = OCR;
    }

    @Override
    public String doOcr(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            // Mark each uploaded file with UNIX epoch timestamp
            long unixTime = System.currentTimeMillis() / 1000L;
            String fileTimeStamped = this.rootLocation.resolve(Long.toString(unixTime) + "-" + file.getOriginalFilename()).toString();

            // Store locally for OCR
            Files.copy(file.getInputStream(), Paths.get(fileTimeStamped));
            File imageFile = new File(fileTimeStamped);

            String text = this.OCR.doOCR(imageFile);
            return text;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        } catch (TesseractException e) {
            throw new StorageException("Failed to ocr file " + file.getOriginalFilename(), e);
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
