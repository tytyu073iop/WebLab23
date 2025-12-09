package entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards = new ArrayList<>();
    
    @OneToMany(mappedBy = "fromAccount")
    private List<Payment> outgoingPayments = new ArrayList<>();
    
    @OneToMany(mappedBy = "toAccount")
    private List<Payment> incomingPayments = new ArrayList<>();
    
    // Конструкторы
    public Account() {
        this.createdAt = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
        this.isActive = true;
    }
    
    public Account(Client client, BigDecimal balance) {
        this.client = client;
        this.balance = balance;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }
    
    // Геттеры и сеттеры
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public Boolean getActive() {
        return isActive;
    }
    
    public void setActive(Boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }
    
    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
    
    public List<Payment> getOutgoingPayments() {
        return outgoingPayments;
    }
    
    public void setOutgoingPayments(List<Payment> outgoingPayments) {
        this.outgoingPayments = outgoingPayments;
    }
    
    public List<Payment> getIncomingPayments() {
        return incomingPayments;
    }
    
    public void setIncomingPayments(List<Payment> incomingPayments) {
        this.incomingPayments = incomingPayments;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
