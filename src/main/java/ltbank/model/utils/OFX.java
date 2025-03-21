package ltbank.model.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import ltbank.model.transaction.Transaction;

public class OFX {
	public String generateOFX(Transaction[] data) throws IOException {
		 StringBuilder sb = new StringBuilder();
		 String path = "D:\\Dev-tools\\wildfly-24.0.1.Final\\standalone\\deployments\\ltbank-0.0.1-SNAPSHOT.war\\assets\\transactions.ofx";
 	 
	        sb.append("OFXHEADER:100\n")
	          .append("DATA:OFXSGML\n")
	          .append("VERSION:102\n")
	          .append("SECURITY:NONE\n")
	          .append("ENCODING:UTF-8\n")
	          .append("CHARSET:NONE\n")
	          .append("COMPRESSION:NONE\n")
	          .append("OLDFILEUID:NONE\n")
	          .append("NEWFILEUID:NONE\n\n")
	          
	          .append("<OFX>\n")
	          .append("<LANGUAGE>POR\n")
	          .append("<CREDITCARDMSGSRSV1><CCSTMTTRNRS><CCSTMTRS>\n")
	          .append("<CURDEF>BRL\n")
	          .append("<BANKACCTFROM><ACCTID>example</BANKACCTFROM>\n")
	          .append("<BANKTRANLIST>\n");

	        for (Transaction info : data) {
	            sb.append("<STMTTRN>\n")
	              .append("<TRNTYPE>").append("DEBIT").append("</TRNTYPE>\n")
	              .append("<DTPOSTED>").append(info.getDate().format(DateTimeFormatter.ISO_INSTANT)).append("</DTPOSTED>\n")
	              .append("<TRNAMT>").append(info.getMoney()).append("</TRNAMT>\n")
	              .append("<FITID>").append(info.getId()).append("</FITID>\n")
	              .append("<NAME>").append(info.getReceiver().getName()).append("</NAME>\n")
	              .append("</STMTTRN>\n");
	        }

	        sb.append("</BANKTRANLIST>\n")
	        .append("</CCSTMTRS>\n")
	        .append("</CCSTMTTRNRS>\n")
	        .append("</CREDITCARDMSGSRSV1>\n")
	        .append("</OFX>\n");

	        try (FileWriter writer = new FileWriter(path)) {
	            writer.write(sb.toString());
	            writer.close();
	        }
		return path;
	}

	public static void saveToFile(String content, String filePath) throws IOException {

	}
}
