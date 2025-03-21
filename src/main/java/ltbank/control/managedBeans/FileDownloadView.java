package ltbank.control.managedBeans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ltbank.model.personage.PersonageDTO;
import ltbank.model.transaction.Transaction;
import ltbank.model.utils.DOC;
import ltbank.model.utils.OFX;
import ltbank.model.utils.PDF;


@RequestScoped
@ManagedBean(name = "fileDownloadView")
public class FileDownloadView {
	private StreamedContent file;
	
	@EJB
	TransactionBean tBean;
	
	public void prepareDownload(String typeArchive) {
		try {
			System.out.println(typeArchive);
			FacesContext faceContext = FacesContext.getCurrentInstance();
			HomeBean homeBean = (HomeBean) faceContext.getExternalContext().getSessionMap().get("homeBean");			
			PersonageDTO userLogged = homeBean.getPDTO();
			List<Transaction>listT = tBean.getListTransaction(userLogged);
			String path = getPathArchive(typeArchive, listT);
			file = DefaultStreamedContent.builder()
			        .name("extrato." + typeArchive)
			        .contentType("application/" + typeArchive)
			        .stream(() -> {
			            try {
			                return new FileInputStream(path);
			            } catch (FileNotFoundException e) {
			                throw new RuntimeException("Arquivo não encontrado!", e);
			            }
			        })
			        .build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String getPathArchive(String typeArchive, List<Transaction> list) {
		
		String path = "";
		if(typeArchive.equals("pdf")) {
			try {
				path = new PDF().GeneratePDF(list);
			} catch (IOException e) {
				throw new RuntimeException("Error while generate PDF:" + e);
			}
		}
		if(typeArchive.equals("docx")) {
			try {
				path = new DOC().generateDOCX(list.toArray(new Transaction[0]));
			} catch (Exception e) {
				throw new RuntimeException("Error while generate DOCX:" + e);
			}
		}
		if(typeArchive.equals("ofx")) {
			try {
				path = new OFX().generateOFX(list.toArray(new Transaction[0]));
			} catch (Exception e) {
				throw new RuntimeException("Error while generate OFX:" + e);
			}
		}
		return path;
	
	}
	
    public StreamedContent getFile() {
        return file;
    }
	
}
