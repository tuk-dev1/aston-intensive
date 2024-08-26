package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.deserialize.ClientDeserialize;
import org.example.repository.ClientRepository;
import org.example.serialize.ClientSerialize;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    ClientRepository clientRepository = new ClientRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientIdString = req.getParameter("clientId");

        try {
            if (clientIdString.isEmpty() || clientIdString == null)
                throw new NumberFormatException();
            int userId = Integer.parseInt(clientIdString);
            ClientSerialize clientSerialize = clientRepository.getClient(userId);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(clientSerialize);

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
        ClientDeserialize clientDeserialize = objectMapper.readValue(req.getInputStream(), ClientDeserialize.class); // to read field of json
        clientRepository.saveClient(clientDeserialize);

        resp.setContentType("text/html"); //TODO: delete
        PrintWriter out = resp.getWriter();
        out.printf("client Name : %s", clientDeserialize.getName());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String clientIdString = req.getParameter("clientId");

        try {
            if (clientIdString.isEmpty() || clientIdString == null)
                throw new NumberFormatException();
            int userId = Integer.parseInt(clientIdString);
            clientRepository.deleteClient(userId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid userId format.");
        }
    }
}
