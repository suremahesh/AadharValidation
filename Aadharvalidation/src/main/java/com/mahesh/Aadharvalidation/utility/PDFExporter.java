package com.mahesh.Aadharvalidation.utility;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mahesh.Aadharvalidation.entities.AadharData;
import com.mahesh.Aadharvalidation.entities.ImageData;

import antlr.collections.List;

public class PDFExporter {

	ImageData imageData;

	static String path = "C:\\Users\\003J20744\\IMAGES\\";

	public PDFExporter(ImageData imageData) {
		this.imageData = imageData;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

//		System.out.println(imageData);
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
		font1.setColor(Color.WHITE);

		Image jpg = Image.getInstance(imageData.getImagePath());
		table.addCell(jpg);

		if (imageData.getStatus().equalsIgnoreCase("matched")) {

			writeTableHeaderacknoledged(table);

		} else if (imageData.getStatus().equalsIgnoreCase("not matched")) {

			writeTableHeaderrejected(table);

		}
		document.add(table);

		document.close();

	}

	private void writeTableHeaderacknoledged(PdfPTable table)
			throws BadElementException, MalformedURLException, IOException {

//		System.out.println(imageData);

		Image jpg1 = Image.getInstance(path + "acknowledge.jpg");
		table.addCell(jpg1);

	}

	private void writeTableHeaderrejected(PdfPTable table)
			throws BadElementException, MalformedURLException, IOException {

//		System.out.println(imageData);

		Image jpg1 = Image.getInstance(path + "rejected.jpg");
		table.addCell(jpg1);

	}

	/*
	 * cell.setPhrase(new Phrase("User ID", font));
	 * 
	 * table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("E-mail", font)); table.addCell(cell);
	 * 
	 * cell.setPhrase(new Phrase("Full Name", font)); table.addCell(cell);
	 */

}
