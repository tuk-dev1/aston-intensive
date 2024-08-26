package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deserialize.AddProductToReceiptDeserialize;
import org.example.repository.ReceiptRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-product-to-receipt")
public class AddProductToReceiptServlet extends HttpServlet {
    ReceiptRepository receiptRepository = new ReceiptRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        AddProductToReceiptDeserialize addProductToReceiptDeserialize = objectMapper.readValue(req.getInputStream(), AddProductToReceiptDeserialize.class); // to read field of json
        receiptRepository.addProductToReceipt(addProductToReceiptDeserialize);
    }
}
