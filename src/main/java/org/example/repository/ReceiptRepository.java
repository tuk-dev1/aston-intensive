package org.example.repository;

import org.example.deserialize.AddProductToReceiptDeserialize;
import org.example.deserialize.ReceiptDeserialize;
import org.example.serialize.ProductInReceiptSerialize;
import org.example.serialize.ProductSerialize;
import org.example.serialize.ReceiptSerialize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository {
    private final static int INITIAL_ZERO_SUM = 0;

    public ReceiptSerialize getReceipt(Integer receiptId) {
        List<ProductInReceiptSerialize> products = new ArrayList<>();
        String query = "SELECT p.id, p.title, p.price, rp.quantity, rp.receipt_id " +
                "FROM products p " +
                "JOIN receipt_product rp ON p.id = rp.product_id " +
                "WHERE rp.receipt_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ProductSerialize product = new ProductSerialize();
                    product.setId(rs.getInt("id"));
                    product.setTitle(rs.getString("title"));
                    product.setPrice(rs.getInt("price"));
                    ProductInReceiptSerialize productInReceipt = new ProductInReceiptSerialize();
                    productInReceipt.setProduct(product);
                    productInReceipt.setCount(rs.getInt("quantity"));
                    products.add(productInReceipt);
                }
            }
            return new ReceiptSerialize(receiptId, products);
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveNewReceipt(ReceiptDeserialize addReceiptDeserialize) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO receipts (client_id, total_price) " +
                    "VALUES (?, ?)");
            statement.setInt(1, addReceiptDeserialize.getClientId());
            statement.setInt(2, INITIAL_ZERO_SUM);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addProductToReceipt(AddProductToReceiptDeserialize addProductToReceiptDeserialize) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement("INSERT INTO receipt_product (receipt_id, product_id, quantity) VALUES (?, ?, ?)");
            statement.setInt(1, addProductToReceiptDeserialize.getReceiptId());
            statement.setInt(2, addProductToReceiptDeserialize.getProductId());
            statement.setInt(3, addProductToReceiptDeserialize.getQuantity());
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteReceipt(Integer receiptId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM receipts WHERE id = ?");
            statement.setInt(1, receiptId);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
