package hu.placecardgenerator;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class Card {

	private static final int ROTATION_180_DEGREE = 180;

	private static PDColor black = new PDColor(new float[] { 1.0f, 1.0f, 1.0f }, PDDeviceRGB.INSTANCE);

	private PDFont font;
	private PDImageXObject bgImage;
	private String name;
	private float x;
	private float y;
	private PDPageContentStream cos;
	private float width;
	private float height;
	
	private float centerLowerX;
	private float centerLowerY;
	private float textWidth;
	private int fontSize;

	public Card(PDFont font, PDImageXObject bgImage, String name, float x, float y, PDPageContentStream cos,
			float width, float height, int fontSize) throws IOException {
		super();
		this.font = font;
		this.bgImage = bgImage;
		this.name = name;
		this.x = x;
		this.y = y;
		this.cos = cos;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		
		this.centerLowerX = width / 2;
		this.centerLowerY = height / 3;
		this.textWidth = font.getStringWidth(name) / 1000 * fontSize;
	}

	public void draw() throws IOException {
		drawImage();
		drawText();
		drawRotatedText();
		drawLines();
	}

	private void drawText() throws IOException {		
		cos.beginText();
		cos.setFont(font, fontSize);
		cos.setNonStrokingColor(black);
		cos.newLineAtOffset(x + centerLowerX - textWidth / 2, y + centerLowerY);
		cos.showText(name);
		cos.endText();
	}

	private void drawRotatedText() throws IOException {
		cos.beginText();
		cos.setFont(font, fontSize);
		cos.setNonStrokingColor(black);
		float posX = x + centerLowerX + textWidth / 2;
		float posY = y + centerLowerY;
		cos.newLineAtOffset(posX, posY);
		Matrix m = Matrix.getRotateInstance(Math.toRadians(ROTATION_180_DEGREE), posX, posY);
		cos.setTextMatrix(m);
		cos.showText(name);
		cos.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(-ROTATION_180_DEGREE), x - textWidth / 2, x));
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
