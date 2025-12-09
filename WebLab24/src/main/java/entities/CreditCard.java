package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class CreditCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @Column(name = "card_number", nullable = false, unique = true, length = 20)
    private String cardNumber;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // Конструкторы
    public CreditCard() {
        this.isActive = true;
    }
    
    public CreditCard(Account account, String cardNumber, LocalDate expiryDate) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.isActive = true;
    }
    
    // Геттеры и сеттеры
    public Long getCardId() {
        return cardId;
    }
    
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public Boolean getActive() {
        return isActive;
    }
    
    public void setActive(Boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return "CreditCard{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryDate=" + expiryDate +
                ", isActive=" + isActive +
                '}';
    }
}