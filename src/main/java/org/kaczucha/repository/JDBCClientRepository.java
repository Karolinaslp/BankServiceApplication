package org.kaczucha.repository;

import org.kaczucha.domain.Client;

import java.sql.*;

public class JDBCClientRepository implements ClientRepository {
    public static final String USER = "postgres";
    public static final String PASSWORD = "qwerty";
    public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/test";

    @Override
    public void save(Client client) {
        String name = client.getName();
        String email = client.getEmail();
        try (final Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS(FIRST_NAME, MAIL) VALUES (?,?)");
            statement.setString(1, name);
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client findByEmail(String email) {
        try (final Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT first_name, mail FROM USERS WHERE mail=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                final String name = resultSet.getString("first_name");
                final String mail = resultSet.getString("mail");
                return new Client(name, mail, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
