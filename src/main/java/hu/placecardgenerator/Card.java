package hu.placecardgenerator;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Card {

	private static final int SIZE = 30;
	private static PDColor black = new PDColor(new float[] { 0.0f, 0.0f, 0.0f }, PDDeviceRGB.INSTANCE);

	private PDFont font;
	private PDImageXObject bgImage;
	private String name;
	private float x;
	private float y;
	private PDPageContentStream cos;
	private float width;
	private float height;

	public Card(PDFont font, PDImageXObject bgImage, String name, float x, float y, PDPageContentStream cos,
			float width, float height) {
		super();
		this.font = font;
		this.bgImage = bgImage;
		this.name = name;
		this.x = x;
		this.y = y;
		this.cos = cos;
		this.width = width;
		this.height = height;
	}

	public void draw() throws IOException {
		drawText();
		drawImage();
		drawLines();
	}

	private void drawText() throws IOException {
		cos.beginText();
		cos.setFont(font, SIZE);
		cos.setNonStrokingColor(black);
		cos.newLineAtOffset(x, y);
//		if (rotated) {
//			cos.setTextRotation(Math.PI, centerX + textWidth / 2, centerY + textHeight / 2);
//		}
		cos.showText(name);
		cos.endText();
	}

	private void drawImage() throws IOException {
		cos.drawImage(bgImage, x, y, width, height);
	}

	private void drawLines() throws IOException {
		cos.setStrokingColor(black);
		cos.setNonStrokingColor(black);

		// vizszintes vonal
		cos.setLineDashPattern(new float[] { 3 }, 0);
		cos.moveTo(x, y);
		cos.lineTo(x + width, y + height);
		cos.setLineDashPattern(new float[] { 0 }, 0);
	}

}
