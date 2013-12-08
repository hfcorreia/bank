package pt.ulisboa.tecnico.bank.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 2:47 AM
 */

@Entity
@Table(name="account")
public class Account extends DomainObject {

	@ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "ownerAccount", referencedColumnName="id")
    private User owner;

    @Column(name = "number")
    private String number;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    @Column(name = "blocked")
    private Boolean isBlocked;


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }
}
