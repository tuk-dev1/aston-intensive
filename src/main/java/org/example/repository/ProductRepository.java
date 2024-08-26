package org.example.repository;

import org.example.deserialize.ProductDeserialize;
import org.example.serialize.ProductSerialize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {
    public ProductSerialize getProduct(Integer productId) throws IllegalArgumentException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ProductSerialize productSerialize = new ProductSerialize();
                productSerialize.setId(resultSet.getInt("id"));
                productSerialize.setTitle(resultSet.getString("title"));
                productSerialize.setPrice(resultSet.getInt("price"));
                return productSerialize;
            }
            else
                throw new IllegalArgumentException("product with id = " + productId + " not found");
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveNewProduct(ProductDeserialize productDeserialize) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products (title, price) " +
                    "VALUES (?, ?)");
            statement.setString(1, productDeserialize.getTitle());
            statement.setInt(2, productDeserialize.getPrice());
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Integer productId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
