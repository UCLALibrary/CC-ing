package textRecognitionAPI.tesseractEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;

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
	
	/**
	 * Do OCR word by word and return word string with bounding box info.
	 * This function can be extended to return list of tess4j words
	 * @param imgFile
	 * @return
	 * @throws IOException 
	 */
	public String doOCRByWords(File imgFile) throws IOException{
		BufferedImage bImg = ImageIO.read(imgFile);
		ArrayList<Word> ocrWords = (ArrayList<Word>) this.getWords(bImg, ITessAPI.TessPageIteratorLevel.RIL_WORD);
		StringBuilder ocrDetailResult = new StringBuilder();
		for (Word w:ocrWords){
			int x = w.getBoundingBox().x;
			int y = w.getBoundingBox().y;
			int fx = x + w.getBoundingBox().width; // final x
			int fy = y + w.getBoundingBox().height; //final y
			
			String output = String.format("%s\t%s,%s,%s,%s\n", w.getText(),x,y,fx,fy);
			ocrDetailResult.append(output);
		}
		return ocrDetailResult.toString();
	}
}
