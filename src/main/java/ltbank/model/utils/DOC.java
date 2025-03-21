package ltbank.model.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import ltbank.model.transaction.Transaction;

public class DOC {
	public String generateDOCX(Transaction[] data) {
		String path = "D:\\Dev-tools\\wildfly-24.0.1.Final\\standalone\\deployments\\ltbank-0.0.1-SNAPSHOT.war\\assets\\transactions.pdf";
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
		// o que está acontecendo aqui: Criamos uma tabela ali em cima
		// aqui o document vai gerar um saida, e o FileOutputStream gera um arquivo
		try (FileOutputStream out = new FileOutputStream("transactions.docx")) {
			document.write(out);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}