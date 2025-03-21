package ltbank.model.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import ltbank.model.transaction.Transaction;

public class PDF {

	public String GeneratePDF(List<Transaction> list) throws IOException {
		String pdfPath = "D:\\Dev-tools\\wildfly-24.0.1.Final\\standalone\\deployments\\ltbank-0.0.1-SNAPSHOT.war\\assets\\transactions.pdf";
		// Criando o documento DOCX com tabela
		XWPFDocument document = createDocxWithTable(list.toArray(new Transaction[0]));
		
		// Cria um arquivo PDF
		PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document pdfDocument = new Document(pdfDoc);

		// Convertendo a tabela do DOCX para o PDF
		for (XWPFTable table : document.getTables()) {
			int numRows = table.getNumberOfRows();
			int numCols = table.getRow(0).getTableCells().size();

			// Criando uma tabela no PDF com o mesmo número de colunas
			Table pdfTable = new Table(numCols);

			for (int i = 0; i < numRows; i++) {
				XWPFTableRow row = table.getRow(i);
				for (XWPFTableCell cell : row.getTableCells()) {
					pdfTable.addCell(new Cell().add(new com.itextpdf.layout.element.Paragraph(cell.getText())));
				}
			}
			pdfDocument.add(pdfTable);
		}

		pdfDocument.close();
		document.close();
		return pdfPath;
	}

	private static XWPFDocument createDocxWithTable(Transaction[] data) {

		XWPFDocument document = new XWPFDocument();
		XWPFTable table = document.createTable(data.length + 1, 5);
		XWPFTableRow rowHeader = table.getRow(0);
		rowHeader.getCell(0).setText("ID");
		rowHeader.getCell(1).setText("DATA");
		rowHeader.getCell(2).setText("QUANTIDADE");
		rowHeader.getCell(3).setText("BENEFICIÁRIO");
		rowHeader.getCell(4).setText("REMETENTE");
		for (int i = 0; i < data.length; i++) {
			XWPFTableRow row = table.getRow(i + 1);
			row.getCell(0).setText(String.valueOf(data[i].getId()));
			row.getCell(1).setText(String.valueOf(data[i].getDate().format(DateTimeFormatter.ISO_INSTANT)));
			row.getCell(2).setText(String.valueOf(data[i].getMoney()));
			row.getCell(3).setText(String.valueOf(data[i].getReceiver().getName()));
			row.getCell(4).setText(String.valueOf(data[i].getSender().getName()));
		}

		return document;
	}
}
