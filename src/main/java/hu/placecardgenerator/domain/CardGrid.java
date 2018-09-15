package hu.placecardgenerator.domain;

import java.io.IOException;
import java.util.Queue;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class CardGrid {
	
	private final int numberOfCol;
	private final int numberOfRow;
	private final float startPoint;
	private final Queue<String> guests;
	private final float widthPart;
	private final float heightPart;
	private final PlaceCardDocument document;
	private final PDFont font;
	private final PDImageXObject bgImage;
	private final int fontSize;
	
	public CardGrid(
			int numberOfCol,
			int numberOfRow,
			PlaceCardDocument document,
			float startPoint,
			float width, float height, Queue<String> guests, int fontSize, PDImageXObject bgImage, PDFont font) {
		this.numberOfCol = numberOfCol;
		this.numberOfRow = numberOfRow;
		this.document = document;
		this.startPoint = startPoint;
		this.widthPart = width / numberOfCol;
		this.heightPart = height / numberOfRow;
		this.guests = guests;
		this.fontSize = fontSize;
		this.bgImage = bgImage;
		this.font = font;
	}

	public PDPage draw() throws IOException {
		PDPage page = new PDPage(PDRectangle.A4);
		
		PDPageContentStream cos = new PDPageContentStream(document, page);

		drawGrid(cos);
		
		cos.close();
		return page;
	}

	private void drawGrid(PDPageContentStream cos) throws IOException {
		for (int i = 0; i < numberOfCol; i++) {
			drawColumn(cos, i);
		}
	}

	private void drawColumn(PDPageContentStream cos, int i) throws IOException {
		for (int j = 0; j < numberOfRow; j++) {
			drawCard(cos, i, j);
		}
	}

	private void drawCard(PDPageContentStream cos, int i, int j) throws IOException {
		if (!guests.isEmpty()) {
			float posX = startPoint + i * widthPart;
			float posY = startPoint + j * heightPart;
			Card card = new Card(font, bgImage, guests.poll(), posX, posY, cos, widthPart, heightPart, fontSize);
			card.draw();
		}
	}
}
