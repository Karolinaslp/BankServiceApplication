package org.kaczucha.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.kaczucha.domain.Client;

import java.sql.SQLException;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Client findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Client> query = session.createQuery("FROM Client WHERE email=:email", Client.class);
        query.setParameter("email", email);
        final Client client = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return client;
    }
}
