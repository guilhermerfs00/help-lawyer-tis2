package service;

import dao.UserDAO;
import model.Client;
import model.Lawyer;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class LoginService {

    private UserDAO ClientDAO;
    private UserDAO LawyerDAO;

    public LoginService() {
    	updateData();
    }
    
    public void updateData() {
    	try {
            LawyerDAO = new UserDAO("lawyer.dat");
            ClientDAO = new UserDAO("client.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean emailExists(String email) {
    	updateData();
        Lawyer lawyer = (Lawyer) LawyerDAO.getByEmail(email);
        Client client = (Client) ClientDAO.getByEmail(email);
        return lawyer != null || client != null;
    }

	public Object login(Request request, Response response) {
		updateData();
        String email = request.params(":email");
        String password = request.queryParams("passwd");

        Lawyer lawyer = (Lawyer) LawyerDAO.getByEmail(email);
        Client client = (Client) ClientDAO.getByEmail(email);

        if (lawyer != null && lawyer.getPasswd().equals(password)) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");

            return lawyer.toJson();
        } else if (client != null && client.getPasswd().equals(password)) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");
            return client.toJson();
        } else {
            response.status(404); // 404 Not found
            return "Usuario ou senha incorretos.";
        }
    }
}
