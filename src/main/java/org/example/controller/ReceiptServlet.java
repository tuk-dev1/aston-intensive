package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deserialize.ReceiptDeserialize;
import org.example.repository.ReceiptRepository;
import org.example.serialize.ReceiptSerialize;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {
    ReceiptRepository receiptRepository = new ReceiptRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String receiptIdString = req.getParameter("receiptId");

        try {
            if (receiptIdString.isEmpty() || receiptIdString == null)
                throw new NumberFormatException();
            int userId = Integer.parseInt(receiptIdString);
            ReceiptSerialize receiptSerialize = receiptRepository.getReceipt(userId);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(receiptSerialize);

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
        ReceiptDeserialize receiptDeserialize = objectMapper.readValue(req.getInputStream(), ReceiptDeserialize.class); // to read field of json
        receiptRepository.saveNewReceipt(receiptDeserialize);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String receiptIdString = req.getParameter("receiptId");

        try {
            if (receiptIdString.isEmpty() || receiptIdString == null)
                throw new NumberFormatException();
            int receiptId = Integer.parseInt(receiptIdString);
            receiptRepository.deleteReceipt(receiptId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid receiptId format.");
        }
    }
}
