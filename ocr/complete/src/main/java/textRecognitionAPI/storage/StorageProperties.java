package textRecognitionAPI.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for the recognition service
 * 
 * @author kcho
 *
 */
@ConfigurationProperties("storage")
public class StorageProperties {

	// Folder location for storing files
	private String location = "/var/tmp/tessFiles";
	// Location of tess4j traineddata files and necessary DLL
	private String tess4jDir = "/usr/share/tesseract-ocr/";

	// ----------- OCR setting
	// Languages list based on
	// https://github.com/UCLALibrary/CC-ing/wiki/Books-and-languages-for-pilot
	private String ocrLanguage = "eng+jpn+mya+hin+ind+msa+lao+tgl+pan+tam+tha+amh+tir+san+vie+khm";
	// http://www.emgu.com/wiki/files/2.3.0/document/html/a4eee77d-90ad-4f30-6783-bc3ef71f8d49.htm
	// 0-3, look like 2 is the best, both OEM and cube, slowest though
	private int ocrEngineMode = 2;
	// 0-10, default page mode = 3, i.e., Fully automatic page segmentation, but
	// no OSD.
	private int ocrPageMode = 3;

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

	public String getTess4jDir() {
		return tess4jDir;
	}

	public void setTess4jDir(String tess4jDir) {
		this.tess4jDir = tess4jDir;
	}

	public String getOcrLanguage() {
		return ocrLanguage;
	}

	public void setOcrLanguage(String ocrLanguage) {
		this.ocrLanguage = ocrLanguage;
	}

	public int getOcrEngineMode() {
		return ocrEngineMode;
	}

	public void setOcrEngineMode(int ocrEngineMode) {
		this.ocrEngineMode = ocrEngineMode;
	}

	public int getOcrPageMode() {
		return ocrPageMode;
	}

	public void setOcrPageMode(int ocrPageMode) {
		this.ocrPageMode = ocrPageMode;
	}

}
