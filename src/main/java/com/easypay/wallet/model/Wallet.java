package com.easypay.wallet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;

    @Transient
    BigDecimal balance = new BigDecimal(0.0D);

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date transactionDate;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;


    @PrePersist
    public void setTransactionDate() {
        this.transactionDate = new Date();
    }

    @PostLoad
    public void onLoad() {
        this.balance = new BigDecimal(0.0D);
        for (Transaction t : this.getTransactions()) {
            this.balance = this.balance.add(t.getAmount());
        }
    }

    public Wallet(String name) {
        this.name = name;
    }

    public Wallet() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getBalance() { return this.balance; }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
