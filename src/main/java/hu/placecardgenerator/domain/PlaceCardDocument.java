package hu.placecardgenerator.domain;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PlaceCardDocument extends PDDocument {

	private static float realMargin = 0;

	private static float width = PDRectangle.A4.getWidth();
	private static float height = PDRectangle.A4.getHeight();

	private static final int numberOfCol = 2;
	private static final int numberOfRow = 3;

	private static final int fontSize = 40;
	private static final float offsetLeft = 100;
	private static final float offsetRight = 40;

	private static final float verticalOffset = 30;

	private GuestHandler guestHandler;

	private String imageName = "src/main/resources/test.png";
	private String fontFileName = "ArchitectsDaughter.ttf";

	public PlaceCardDocument(GuestHandler guestHandler) {
		this.guestHandler = guestHandler;
	}

	public PDDocument getDoc() throws Exception {

		float margin = realMargin;
		float startPoint = margin;

		File fontFile = new File(fontFileName);
		PDFont font = PDType1Font.HELVETICA;

		PDImageXObject ximageBase = PDImageXObject.createFromFile(imageName, this);

		float div = guestHandler.size() / (float)(numberOfCol * numberOfRow);
		double ceil = Math.ceil(div);
		int pageNumber = ((Double) ceil).intValue();

		for (int p = 0; p < pageNumber; p++) {
			CardGrid grid = new CardGrid(numberOfCol, numberOfRow, this, startPoint, width, height, guestHandler.getNext(numberOfCol * numberOfRow), fontSize,
					ximageBase, font);
			PDPage page = grid.draw();

			this.addPage(page);
		}
		return this;
	}

}
