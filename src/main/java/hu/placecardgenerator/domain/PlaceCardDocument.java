package hu.placecardgenerator.domain;

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

public class PlaceCardDocument extends PDDocument {

	private static float realMargin = 0;

	// private static final Color c = new Color(228, 218, 217);
	private static final Color c = new Color(44, 84, 45);

	private static float width = PDRectangle.A4.getWidth();
	private static float height = PDRectangle.A4.getHeight();

	private static final int numberOfCol = 2;
	private static final int numberOfRow = 3;

	private static final int fontSize = 40;
	private static final float offsetLeft = 100;
	private static final float offsetRight = 40;

	private static final float verticalOffset = 30;

	private List<String> nameList;

	private String imageName = "src/main/resources/test.png";
	private String fontFileName = "ArchitectsDaughter.ttf";

	public PlaceCardDocument(List<String> names) {
		this.nameList = names;
	}

	public PDDocument getDoc() throws Exception {

		float margin = realMargin;
		float startPoint = margin;

		float widthPart = width / numberOfCol;
		float heightPart = height / numberOfRow;

		File fontFile = new File(fontFileName);
		PDFont font = PDType1Font.HELVETICA;

		PDImageXObject ximageBase = PDImageXObject.createFromFile(imageName, this);

		float div = nameList.size() / new Float(numberOfCol * numberOfRow);
		double ceil = Math.ceil(div);
		int pageNumber = ((Double) ceil).intValue();
		int counter = 0;

		for (int p = 0; p < pageNumber; p++) {
			PDPage page = new PDPage(PDRectangle.A4);

			PDPageContentStream cos = new PDPageContentStream(this, page);

			for (int i = 0; i < numberOfCol; i++) {

				for (int j = 0; j < numberOfRow; j++) {
					if (counter < nameList.size()) {
						float posX = startPoint + i * widthPart;
						float posY = startPoint + j * heightPart;
						Card card = new Card(font, ximageBase, nameList.get(counter), posX, posY, cos, widthPart, heightPart, fontSize);
						card.draw();
						counter++;
					}
				}

			}
			cos.close();
			this.addPage(page);
		}
		return this;
	}

}
