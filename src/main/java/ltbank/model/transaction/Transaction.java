package ltbank.model.transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ltbank.model.personage.Personage;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "money", nullable = false, updatable = false)
	private BigDecimal money;
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private ZonedDateTime date;
	@OneToOne
	@JoinColumn(name = "sender_id", nullable = true, updatable = false)
	private Personage sender;
	@OneToOne
	@JoinColumn(name = "receiver_id", nullable = true, updatable = false)
	private Personage receiver;
	@Column(nullable = false)
	private String status;
	
	public Transaction() {}

	public Transaction(BigDecimal money, ZonedDateTime date, Personage sender, Personage receiver, String status) {
		this.money = money;
		this.date = date;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	public long getId() {
		return id;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public ZonedDateTime getDate() {
		return date;
	}
	public Personage getReceiver() {
		return receiver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Personage getSender() {
		return sender;
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
		Transaction other = (Transaction) obj;
		return id == other.id;
	}
}
