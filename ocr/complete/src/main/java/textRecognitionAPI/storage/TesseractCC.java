package textRecognitionAPI.storage;

import net.sourceforge.tess4j.Tesseract;

/**
 * Extension of Tess4j Tesseract api in CC-ing project
 * @author kcho
 *
 */
public class TesseractCC extends Tesseract{

	
	public TesseractCC(String datapath,String language, int ocrEngineMode,
			int pageMode){
		// set data path
		this.setDatapath(datapath);
		
		// set language
		this.setLanguage(language);
		
		// set engine mode 0-3		
		this.setOcrEngineMode(ocrEngineMode);
		
		// set segementation mode 0-10, default = 3
		this.setPageSegMode(pageMode);
	}
}
