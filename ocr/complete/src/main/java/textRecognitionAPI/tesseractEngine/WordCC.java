package textRecognitionAPI.tesseractEngine;

import java.awt.Rectangle;

import net.sourceforge.tess4j.Word;

/**
 * Extension of tess4j Word
 * @author kcho
 *
 */
public class WordCC extends Word{

	public WordCC(String text, float confidence, Rectangle boundingBox) {
		super(text, confidence, boundingBox);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "WordCC [getText()=" + getText() + ", getConfidence()=" + getConfidence() + ", getBoundingBox()="
				+ getBoundingBox() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}

}
