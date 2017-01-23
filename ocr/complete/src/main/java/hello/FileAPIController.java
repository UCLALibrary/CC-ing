package hello;

import hello.storage.StorageFileNotFoundException;
import hello.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/fileAPI")
public class FileAPIController {     

    private final StorageService storageService;

    @Autowired
    public FileAPIController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getAll() {
	return "No GET supported";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam(value="file", required=true) MultipartFile file) {
        return storageService.doOcr(file);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable String id) {
  
    }

}
