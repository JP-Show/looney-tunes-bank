package ltbank.model.personage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class PersonageDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "mypu")
	private EntityManager em;

	//os métodos precisam ser públicos
	public void create(Personage personage) {
		em.persist(personage);
	}

	public void updateName(String newName, long id, ZonedDateTime zdt) {
		String sql = "UPDATE Personage SET name = :name, lastUpdate = :lastUpdate WHERE id = :id";
		em.createQuery(sql).setParameter("name", newName).setParameter("lastUpdate", zdt)
				.setParameter("id", id).executeUpdate();
	}
	public void updateMoney(long id, BigDecimal money, ZonedDateTime zdt) {
		String sql = "UPDATE Personage SET money = :money, lastUpdate = :lastUpdate WHERE id = :id";
		em.createQuery(sql).setParameter("money", money).setParameter("lastUpdate", zdt)
				.setParameter("id", id).executeUpdate();
	}
	public void addMoney(long id, BigDecimal money, ZonedDateTime zdt) {
		String sql = "UPDATE Personage SET money = money + :money, lastUpdate = :lastUpdate WHERE id = :id";
		em.createQuery(sql).setParameter("money", money).setParameter("lastUpdate", zdt)
				.setParameter("id", id).executeUpdate();
	}
	public void deleteById(Personage personage) {
		em.remove(personage);
	}

	public Personage findById(long id) {
		Personage p = em.createQuery("SELECT p FROM Personage p WHERE p.id = " + id, Personage.class).getSingleResult();
		return p;
	}

	public Personage findByName(String name) {
		Personage p = em.createQuery("SELECT p FROM Personage p where p.name like '%" + name + "%'", Personage.class)
				.getSingleResult();
		return p;
	}
	
	public List<Personage> findAll(){
		List<Personage> pList = em.createQuery("SELECT p FROM Personage p", Personage.class).getResultList();
		return pList;
	}
}
