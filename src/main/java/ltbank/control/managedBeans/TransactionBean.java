package ltbank.control.managedBeans;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ltbank.model.personage.Personage;
import ltbank.model.personage.PersonageDAO;
import ltbank.model.personage.PersonageDTO;
import ltbank.model.transaction.Transaction;
import ltbank.model.transaction.TransactionDAO;
import ltbank.model.transaction.TransactionException;

@Stateful
public class TransactionBean {
	
	@EJB
	private TransactionDAO tDAO;
	@EJB
	private PersonageDAO pDAO;
	private List<Transaction> ListT;
	public List<Transaction> getListTransaction(PersonageDTO pDTO) {
		try {
			if(pDTO == null) throw new TransactionException("Personage not logged");
			Personage personage = pDAO.findById(pDTO.getId());
			ListT = tDAO.findByPersonage(personage);
			return ListT;
		}catch (Exception e) {
			throw new RuntimeException( "Error while fetching transaction: " + e.getMessage());
		}
	}
	//deixar que o EJB gerencie essa transação em caso que algo dê problema, fazer rollback;
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendMoney(PersonageDTO currentP, long whoToSend, BigDecimal amountMoney) {
		try {
			Personage sender = returnIfExist(currentP.getId());
			if(sender == null || !sender.getName().equals(currentP.getName()))
				throw new TransactionException("User not found");
			if(amountMoney.doubleValue() < 0.0)
				throw new TransactionException("You trying to send money less than 0");
			Personage receiver = returnIfExist(whoToSend);
			if(receiver == null)
				throw new TransactionException("Who you want to send, doesn't exist");
			BigDecimal sub = sender.getMoney().subtract(amountMoney);
			if(sub.doubleValue() < 0.0)
				throw new TransactionException("Insufficient balance");
			
			Transaction t1 = new Transaction(amountMoney, ZonedDateTime.now(), sender,
					receiver, "sendded");
			tDAO.create(t1);
			pDAO.updateMoney(sender.getId(), sub, ZonedDateTime.now());
			pDAO.addMoney(whoToSend, amountMoney, ZonedDateTime.now());										
		}catch (Exception e) {
			throw new RuntimeException("Error while send money: " + e );
		}
	}
	
	public void addMoney(PersonageDTO currentP, BigDecimal amountMoney) {
		try {
			Personage sender = returnIfExist(currentP.getId());
			if(sender == null || !sender.getName().equals(currentP.getName()))
				throw new TransactionException("Personage with error - Current Personage don't exist or you not logged");
			if(amountMoney.doubleValue() < 0.0)
				throw new TransactionException("You trying to add money less than 0");
			Transaction t = new Transaction(amountMoney, ZonedDateTime.now(), sender, sender, "Receive");
			pDAO.addMoney(sender.getId(), amountMoney, ZonedDateTime.now());
			tDAO.create(t);
		}catch (Exception e) {
			throw new TransactionException("Error while send money:" + e );
		}
	}
	private Personage returnIfExist(long id) {
		try {
			Personage p = pDAO.findById(id);
			return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Transaction> getListT() {
		return ListT;
	}
	
	public void setListT(List<Transaction> listT) {
		ListT = listT;
	}

}
