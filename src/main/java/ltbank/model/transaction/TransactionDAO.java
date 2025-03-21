package ltbank.model.transaction;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ltbank.model.personage.Personage;

/*
 * If a stateful session bean instantiates a stateful session bean(executing in the same EJB container instance)
 * which also has such an extended persistence context with the same synchronization type,the extended persistence 
 * context of the first stateful session bean is inherited by the second stateful session bean and bound to it,and 
 * this rule recursively applies independently of whether transactions are active or not at the point of the creation 
 * of the stateful session beans.If the stateful session beans differ in declared synchronization type,the EJBException 
 * is thrown by the container.If the persistence context has been inherited by any stateful session beans,the container 
 * does not close the persistence context until all such stateful session beans have been removed or otherwise destroyed. 
 */


@Stateless
public class TransactionDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "mypu")
	private EntityManager em;

	//os métodos precisam ser públicos
	public void create(Transaction transaction) {
		em.persist(transaction);
	}
	public void deleteById(Transaction transaction) {
		em.remove(transaction);
	}
	
	public Transaction findById(long id) {
		Transaction t = em.createQuery("SELECT t FROM Transactio t WHERE t.id = " + id, Transaction.class).getSingleResult();
		return t;
	}

	public Transaction findByMoney(String money) {
		Transaction t = em.createQuery("SELECT t FROM Transaction t where t.money like '%" + money + "%'", Transaction.class)
				.getSingleResult();
		return t;
	}
	
	public List<Transaction> findByPersonage(Personage p) {
		//desta forma evitar sql injection, fazer nas demais depois
		String jpql = "SELECT t FROM Transaction t WHERE t.receiver = :id OR t.sender = :id";
		TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
		query.setParameter("id", p);
		List<Transaction> transactions = query.getResultList();
		return transactions;
	}
	
	public List<Transaction> findAll(){
		List<Transaction> pList = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
		return pList;
	}
}
