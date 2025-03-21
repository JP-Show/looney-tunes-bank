package ltbank.control.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ltbank.model.personage.Personage;
import ltbank.model.personage.PersonageDTO;

@SessionScoped
@ManagedBean(name = "homeBean")
public class HomeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private PersonageBean personageBean;
	private List<PersonageDTO> personagesDTOList;
	private long idHidden;
	private String password;
	private PersonageDTO pDTO;
	private void loadPersonages() {
		personagesDTOList = personageBean.getAllPersonage();
	}
	@PostConstruct
	public void init() {
		loadPersonages();
	}
	public String login() {
		FacesContext faceContext = FacesContext.getCurrentInstance();
		try {
			if ("".equals(password)) {
				sendErrorMessage(faceContext, "type your password");
				System.out.println("".equals(password));
				return "";
			}
			if (password.length() > 0) {
				Personage p = personageBean.login(idHidden, password);
				pDTO = new PersonageDTO(p.getId(), p.getName());
				return "/transaction.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
            // Redireciona de volta para home
            return "/?faces-redirect=true";
		}
		return "";
	}

	private void sendErrorMessage(FacesContext fc, String msg) {
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PersonageDTO> getPersonagesDTOList() {
		return personagesDTOList;
	}
	public void setIdHidden(long idHidden) {
		this.idHidden = idHidden;
	}
	public PersonageDTO getPDTO() {
		return pDTO;
	}
}
