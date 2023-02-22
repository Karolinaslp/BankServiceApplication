package org.rejowska.domain;

import java.util.Objects;

public class Client {
   private final String name;
   private String email;
   private double balance;

   public Client(String name, String email, double balance) {
      this.name = name;
      this.email = email;
      this.balance = balance;
   }

   public String getName() {
      return name;
   }

   public double getBalance() {
      return balance;
   }

   public void setBalance(double balance) {
      this.balance = balance;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
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
