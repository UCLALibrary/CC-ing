package hello.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/var/tmp/tessFiles";
    // Location of tess4j traineddata files and necessary DLL
    private String tess4jDir = "/usr/local/Cellar/tesseract/3.04.01_2/share";

    public String getTessDir() {
        return tess4jDir;
    }

    public void setTess(String tess4jDir) {
        this.tess4jDir = tess4jDir;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
