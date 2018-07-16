package hu.placecardgenerator;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class MyDocument {

	private static float realMargin = 5;
	private static final float w = 210;

	// private static final Color c = new Color(228, 218, 217);
	private static final Color c = new Color(44, 84, 45);
	private static final Color bordo = new Color(108, 5, 25);

	private static float width = PDRectangle.A4.getWidth();

	private static final int numberOfCol = 2;
	private static final int numberOfRow = 3;

	private static final float fontSize = 30;
	private static final float offsetLeft = 100;
	private static final float offsetRight = 40;

	private static final float verticalOffset = 30;

	private float wScale = width / w;

	private PDDocument doc = new PDDocument();
	private List<String> nameList;

	private String imageName = "src/main/resources/test.png";
	private String fontFileName = "ArchitectsDaughter.ttf";

	public MyDocument(List<String> names) {
		this.nameList = names;
	}

	public PDDocument getDoc() throws Exception {

		float margin = realMargin * wScale;

		float width = 2 * margin;
		float height = 2 * margin;
		float startPoint = margin;

		float widthPart = width / numberOfCol;
		float heightPart = height / numberOfRow;

		File fontFile = new File(fontFileName);
		PDFont font = PDType1Font.HELVETICA;

		PDImageXObject ximageBase = PDImageXObject.createFromFile(imageName, doc);
		PDImageXObject ximageRotated = PDImageXObject.createFromFile(imageName, doc);

		float div = nameList.size() / 6.0f;
		double ceil = Math.ceil(div);
		int pageNumber = ((Double) ceil).intValue();
		int counter = 0;
		float half = heightPart / 2f;

		for (int p = 0; p < pageNumber; p++) {
			PDPage page = new PDPage(PDRectangle.A4);

			PDPageContentStream cos = new PDPageContentStream(doc, page);

			for (int i = 0; i < numberOfCol; i++) {

				for (int j = 0; j < numberOfRow; j++) {
					float posX = startPoint + i * widthPart;
					float posY = startPoint + j * heightPart;

					paintImage(posX, posY, ximageBase, cos, widthPart, half);

					paintImage(posX, posY + half, ximageRotated, cos, widthPart, half);

					PDRectangle rect = new PDRectangle(widthPart, half);
					rect.setLowerLeftX(posX + offsetLeft);
					rect.setLowerLeftY(posY + 2 * verticalOffset);
					rect.setUpperRightX(posX + widthPart - offsetRight);
					rect.setUpperRightY(posY + half - verticalOffset);

					if (counter < nameList.size()) {

						writeText(font, fontSize, nameList.get(counter), cos, rect, false);

						rect.setLowerLeftY(rect.getLowerLeftY() + half - 2 * verticalOffset);
						rect.setUpperRightY(rect.getUpperRightY() + half - verticalOffset / 2);

						writeText(font, fontSize, nameList.get(counter), cos, rect, true);
					}
					counter++;
					cos.setStrokingColor(bordo);
					cos.setNonStrokingColor(bordo);
					cos.moveTo(posX, posY);
					cos.lineTo(posX + 20, posY);
					cos.moveTo(posX + widthPart - 20, posY);
					cos.lineTo(posX + widthPart, posY);

					// vizszintes vonal
					cos.setLineDashPattern(new float[] { 3 }, 0);
					cos.moveTo(posX, posY + half);
					cos.lineTo(posX + widthPart, posY + half);
					cos.setLineDashPattern(new float[] { 0 }, 0);
				}

			}

			cos.setStrokingColor(c);
			cos.setNonStrokingColor(c);
			// fuggoleges vonal

			cos.moveTo(width / 2, 0);
			cos.lineTo(width / 2, 20);

			cos.moveTo(width / 2, height - 20);
			cos.lineTo(width / 2, height);

			cos.close();
			doc.addPage(page);
		}
		return doc;
	}

	private static void writeText(PDFont font, float fontSize, String text, PDPageContentStream cos, PDRectangle rect,
			boolean rotated) throws IOException {
		drawRectangle(rect, cos);

		text = text.replaceAll("õ", "o");
		text = text.replaceAll("Õ", "O");
		text = text.replaceAll("û", "u");
		text = text.replaceAll("Û", "U");
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;

		while (textWidth > rect.getWidth()) {
			fontSize -= 1f;
			textWidth = font.getStringWidth(text) / 1000 * fontSize;
		}

		float textHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

		float centerX = rect.getLowerLeftX() + (rect.getWidth() / 2f);
		float centerY = rect.getLowerLeftY() + (rect.getHeight() / 2f);

		float x = centerX - (textWidth / 2f);
		float y = centerY - (textHeight / 2f);

		cos.beginText();
		cos.setFont(font, fontSize);
		cos.setNonStrokingColor(bordo);

		if (rotated) {
			cos.moveTextPositionByAmount(x, y);
			cos.setTextRotation(Math.PI, centerX + textWidth / 2, centerY + textHeight / 2);
		} else {
			cos.moveTextPositionByAmount(x, y);
		}
		cos.showText(text);
		cos.endText();
	}

	private static void paintImage(float x, float y, PDImageXObject ximage, PDPageContentStream cos, float width,
			float height) throws IOException {

		cos.drawImage(ximage, x, y, width, height);
	}

	private static void drawRectangle(PDRectangle rect, PDPageContentStream cos) throws IOException {

		cos.setStrokingColor(bordo);

		cos.moveTo(rect.getLowerLeftX(), rect.getLowerLeftY());
		cos.lineTo(rect.getUpperRightX(), rect.getUpperRightY());

		cos.moveTo(rect.getLowerLeftX(), rect.getLowerLeftY() + rect.getHeight());
		cos.lineTo(rect.getUpperRightX(), rect.getUpperRightY() - rect.getHeight());
	}

}
