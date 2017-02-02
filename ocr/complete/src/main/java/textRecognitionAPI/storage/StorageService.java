package textRecognitionAPI.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.HashMap;

public interface StorageService {

    void init();

    HashMap<String, String> doOcr(MultipartFile file, String languages);

    HashMap<String, String> doOcr(String imageURL, String languages);

    void deleteAll();

}
