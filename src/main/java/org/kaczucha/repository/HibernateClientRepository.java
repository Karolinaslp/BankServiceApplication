package org.kaczucha.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.kaczucha.repository.entity.Client;

import java.sql.SQLException;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        client.getAccounts().forEach(session::save);
        session.save(client);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public Client findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Client> query = session.createQuery("FROM Client WHERE email=:email", Client.class);
        query.setParameter("email", email.toLowerCase());
        final Client client = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return client;
    }
}
