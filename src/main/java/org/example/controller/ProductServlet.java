package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deserialize.ProductDeserialize;
import org.example.repository.ProductRepository;
import org.example.serialize.ProductSerialize;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdString = req.getParameter("productId");

        try {
            if (productIdString.isEmpty() || productIdString == null)
                throw new NumberFormatException();
            int userId = Integer.parseInt(productIdString);
            ProductSerialize productSerialize = productRepository.getProduct(userId);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(productSerialize);

            PrintWriter out = resp.getWriter();
            out.print(jsonResponse);
            out.flush();
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid userId format.");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDeserialize productDeserialize = objectMapper.readValue(req.getInputStream(), ProductDeserialize.class); // to read field of json

        if (productDeserialize.getTitle() == null || productDeserialize.getTitle().isEmpty() ||  productDeserialize.getPrice() == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Empty or incorrect json.");
        productRepository.saveNewProduct(productDeserialize);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String productIdString = req.getParameter("productId");

        try {
            if (productIdString.isEmpty() || productIdString == null)
                throw new NumberFormatException();
            int productId = Integer.parseInt(productIdString);
            productRepository.deleteProduct(productId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productId format.");
        }
    }
}
