package org.kaczucha.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "FIRST_NAME")
    private String name;

    @Column(name = "MAIL")
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<Account> accounts;

    public Client(String name, String email, List<Account> accounts) {
        this.name = name;
        this.email = email;
        this.accounts = accounts;
    }

    public double getBalance() {
        if (!accounts.isEmpty()) {
            return accounts.get(0).getBalance();
        }
        return 0;
    }

    public void setBalance(double newBalance) {
        if (!accounts.isEmpty()) {
            accounts.get(0).setBalance(newBalance);
        }
    }
}