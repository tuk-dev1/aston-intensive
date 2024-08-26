package org.example.repository;

import org.example.deserialize.ClientDeserialize;
import org.example.serialize.ClientSerialize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository {
    public ClientSerialize getClient(Integer clientId) throws IllegalArgumentException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients WHERE id = ?");
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ClientSerialize clientSerialize = new ClientSerialize();
                clientSerialize.setId(resultSet.getInt("id"));
                clientSerialize.setName(resultSet.getString("name"));
                return clientSerialize;
            }
            else
                throw new IllegalArgumentException("Client with id = " + clientId + " not found");
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveClient(ClientDeserialize clientDeserialize) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO clients (name) VALUES (?)");
            statement.setString(1, clientDeserialize.getName());
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(Integer clientId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM clients WHERE id = ?");
            statement.setInt(1, clientId);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
