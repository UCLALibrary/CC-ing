package textRecognitionAPI.controller;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import textRecognitionAPI.storage.StorageFileNotFoundException;
import textRecognitionAPI.storage.StorageService;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/fileAPI")
public class FileAPIController {     

    private final StorageService storageService;
    private Gson gson;

    @Autowired
    public FileAPIController(StorageService storageService) {
        this.storageService = storageService;
	this.gson = new Gson();
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getAll() {
	return "No GET supported";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam(value="file", required=true) MultipartFile file, @RequestParam(value="languages", required=false) String languages) {
        HashMap<String, String> ocrResult = storageService.doOcr(file, languages);
	return gson.toJson(ocrResult);
    }

    @RequestMapping(method=RequestMethod.POST, value="/imageURL")
    public String handleFileURL(@RequestParam(value="imageURL", required=true) String imageURL, @RequestParam(value="languages", required=false) String languages) {
        HashMap<String, String> ocrResult = storageService.doOcr(imageURL, languages);
	return gson.toJson(ocrResult);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable String id) {
  
    }

}
