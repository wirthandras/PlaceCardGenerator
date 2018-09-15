package hu.placecardgenerator.domain;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class Card {

	private static final int FONT_SIZE_MULTIPLIER = 1000;
	private static final int ROTATION_180_DEGREE = 180;

	private static PDColor black = new PDColor(new float[] { 1.0f, 1.0f, 1.0f }, PDDeviceRGB.INSTANCE);
	private static PDColor red = new PDColor(new float[] { 0.0f, 1.0f, 1.0f }, PDDeviceRGB.INSTANCE);

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
	private float fontHeight;

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

		this.centerLowerX = getHalf(width);
		this.centerLowerY = getHalf(getHalf(height));
		this.textWidth = font.getStringWidth(this.name) / FONT_SIZE_MULTIPLIER * fontSize;
		this.fontHeight = font.getFontDescriptor().getCapHeight() / FONT_SIZE_MULTIPLIER * fontSize;
	}

	public void draw() throws IOException {
		drawImage();
		drawText(centerLowerX, centerLowerY, false);
		drawText(centerLowerX, 3 * centerLowerY, true);
		drawLines();
	}
	
	private float getHalf(float value) {
		return value / 2;
	}

	private void drawText(float centerX, float centerY, boolean isRotated) throws IOException {

		float posX = x + centerX - getHalf(textWidth);
		float posY = y + centerY - getHalf(fontHeight);

		cos.beginText();
		if (isRotated) {
			posX = x + centerX + getHalf(textWidth);
			posY = y + centerY + getHalf(fontHeight);
		}
		cos.newLineAtOffset(posX, posY);
		if (isRotated) {
			Matrix m = Matrix.getRotateInstance(Math.toRadians(ROTATION_180_DEGREE), posX, posY);
			cos.setTextMatrix(m);
		}
		cos.setFont(font, fontSize);
		cos.setNonStrokingColor(black);

		cos.showText(name);
		cos.endText();
	}

	private void drawImage() throws IOException {
		cos.drawImage(bgImage, x, y, width, height);
	}

	private void drawLines() throws IOException {
		cos.setStrokingColor(red);
		cos.setNonStrokingColor(red);

		// vizszintes vonal
		cos.setLineDashPattern(new float[] { 3 }, 0);
		cos.moveTo(x, y + centerLowerY);
		cos.lineTo(x + width, y + centerLowerY);
		cos.setLineDashPattern(new float[] { 0 }, 0);
	}

}
