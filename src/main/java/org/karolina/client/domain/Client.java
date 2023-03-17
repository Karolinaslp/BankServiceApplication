package org.karolina.client.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.karolina.account.domain.Account;

import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "FIRST_NAME")
    private String name;

    @Column(unique = true, name = "MAIL")
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Set<Account> accounts;

    public void removeAccounts() {
        accounts.clear();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}