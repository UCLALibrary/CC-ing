package textRecognitionAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import textRecognitionAPI.storage.FileSystemStorageService;
import textRecognitionAPI.storage.StorageFileNotFoundException;
import textRecognitionAPI.storage.StorageService;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.HashMap;


/**
 * This follow closely with fileupload tutorial: https://spring.io/guides/gs/uploading-files/
 * @author kcho
 *
 */
@Controller
public class FileUploadController {

    private final StorageService storageService;

    /*
     * Wire the interface, but the implementation. 
     * A good practice: http://stackoverflow.com/questions/12899372/spring-why-do-we-autowire-the-interface-and-not-the-implemented-class
     */
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }

    // Handle web page uploads
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(value="languages", required=false) String languages,
                                   RedirectAttributes redirectAttributes) {
    	
        HashMap<String, String> ocrResult = storageService.doOcr(file, languages);
        
        redirectAttributes.addFlashAttribute("message",
                "OCR of " + file.getOriginalFilename() + " successful! Result is");
        redirectAttributes.addFlashAttribute("ocrResult",
                ocrResult.get("ocrResult"));

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
