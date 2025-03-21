package ltbank.model.personage;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ltbank.model.transaction.Transaction;

@Entity
public class Personage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100) UNIQUE" )
	private String name;
	@Column(length = 142, nullable = false)
	private String password;
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private ZonedDateTime createAt;
	@Column(name = "last_update",  columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private ZonedDateTime lastUpdate;
	@Column(name = "money", nullable = false, columnDefinition = "DECIMAL")
	private BigDecimal money;
    //relação como sender
    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions;
    //relação como receiver
    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions;

	public Personage() {}
	public Personage(String name, String password, ZonedDateTime createAt, ZonedDateTime lastUpdate, BigDecimal money) {
		this.name = name;
		this.password = password;
		this.createAt = createAt;
		this.lastUpdate = lastUpdate;
		this.money = money;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ZonedDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(ZonedDateTime createAt) {
		this.createAt = createAt;
	}
	public ZonedDateTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(ZonedDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public List<Transaction> getSentTransactions() {
		return sentTransactions;
	}
	public List<Transaction> getReceivedTransactions() {
		return receivedTransactions;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personage other = (Personage) obj;
		return id == other.id;
	}

}
