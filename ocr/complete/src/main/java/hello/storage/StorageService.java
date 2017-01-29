package hello.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String doOcr(MultipartFile file, String languages);

    String doOcr(String imageURL, String languages);

    void deleteAll();

}
