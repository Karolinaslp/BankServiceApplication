package org.kaczucha.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "USERS")
public class Client {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
   @SequenceGenerator(name = "user_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
   @Column(name = "USER_ID")
   private long id;

   @Column(name = "FIRST_NAME")
   private String name;

   @Column(name = "MAIL")
   private String email;

   @Transient
   private double balance;

   public Client() {
   }

   public Client(String name, String email, double balance) {
      this.name = name;
      this.email = email;
      this.balance = balance;
   }

   public String getName() {
      return name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public double getBalance() {
      return balance;
   }

   public void setBalance(double balance) {
      this.balance = balance;
   }

   @Override
   public String toString() {
      return "Client{" +
              "name='" + name + '\'' +
              ", email='" + email + '\'' +
              ", balance=" + balance +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Client client = (Client) o;
      return Double.compare(client.balance, balance) == 0 && Objects.equals(name, client.name) && Objects.equals(email, client.email);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, email, balance);
   }
}
