package ltbank.control.managedBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ltbank.model.personage.Personage;
import ltbank.model.personage.PersonageDTO;
import ltbank.model.transaction.Transaction;

@RequestScoped
@ManagedBean(name = "reviewBean")
public class ReviewBean {

	@EJB
	private TransactionBean tBean;
	@EJB
	private PersonageBean pBean;
	private List<Transaction> listT = new ArrayList<>();
	private Transaction t;
	private BigDecimal money = BigDecimal.valueOf(0);
	private String password;
	private long whoSendId;
	private BigDecimal currentMoney;
	
	@PostConstruct
	public void init() {
		getPersonageMoney();
		loadTable();
	}
	public PersonageDTO getLoggedUser() {
		try {
			FacesContext faceContext = FacesContext.getCurrentInstance();
			HomeBean homeBean = (HomeBean) faceContext.getExternalContext().getSessionMap().get("homeBean");			
			PersonageDTO userLogged = homeBean.getPDTO();
			return userLogged;
		}catch (Exception e) {
			throw new RuntimeException("Erro while get the current logged personage:" + e);  
		}
	}
	public void loadTable () {
		try {
			PersonageDTO userLogged = getLoggedUser();
			listT = tBean.getListTransaction(userLogged);
		} catch (Exception e) {
			System.err.println("Error reviewBean - ");
			e.printStackTrace();
		}
	}
	public String deposit() {
		try {
			PersonageDTO userLogged = getLoggedUser();
			tBean.addMoney(userLogged, money);
			return "/transaction.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public String transfer() {
		try {
			PersonageDTO userLogged = getLoggedUser();
			tBean.sendMoney(userLogged, whoSendId, money);
			return "/transaction.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public void getPersonageMoney() {
		try {
			PersonageDTO pDTO = getLoggedUser();
			Personage p = pBean.findPersoge(pDTO);
			currentMoney = p.getMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public BigDecimal getCurrentMoney() {
		return currentMoney;
	}
	public long getWhoSendId() {
		return whoSendId;
	}
	public void setWhoSendId(long whoSendId) {
		this.whoSendId = whoSendId;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Transaction> getListT() {
		return listT;
	}
	public void setListT(List<Transaction> listT) {
		this.listT = listT;
	}
	public Transaction getT() {
		return t;
	}
	public void setT(Transaction t) {
		this.t = t;
	}
	
}
