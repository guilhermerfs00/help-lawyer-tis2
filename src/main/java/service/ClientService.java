package service;

import java.io.IOException;

import dao.UserDAO;
import model.Client;
import model.User;
import spark.Request;
import spark.Response;

public class ClientService {

	private UserDAO ClientDAO;

	public ClientService() {
		try {
			ClientDAO = new UserDAO("client.dat");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		String name = request.queryParams("name");
		String email = request.queryParams("email");
		String address = request.queryParams("address");
		String phone = request.queryParams("phone");
		String birthday = request.queryParams("birthday");

		int id = ClientDAO.getMaxId() + 1;
		Client client = new Client(id, name, email, address, phone, birthday);

		ClientDAO.add(client);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Client client = (Client) ClientDAO.get(id);
		
        if (client != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<client>\n" + 
            		"\t<id> " + client.getId() + "</id>\n" +
            		"\t<name> " + client.getName() + "</name>\n" +
            		"\t<email> " + client.getEmail() + "</email>\n" +
            		"\t<address> " + client.getAddress() + "</address>\n" +
            		"\t<phone> " + client.getPhone() + "</phone>\n" +
            		"\t<birthday> " + client.getBirthday() + "</birthday>\n" +
            		"</client>\n";
        } else {
            response.status(404); // 404 Not found
            return "Cliente " + id + " nao encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        Client client = (Client) ClientDAO.get(id);

        if (client != null) {
        	client.setName(request.queryParams("name"));
        	client.setEmail(request.queryParams("email"));
        	client.setAddress(request.queryParams("address"));
        	client.setPhone(request.queryParams("phone"));
        	client.setBirthday(request.queryParams("birthday"));

        	ClientDAO.update(client);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Cliente nao encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Client client = (Client) ClientDAO.get(id);

        if (client != null) {

        	ClientDAO.remove(client);

        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Cliente encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<client type=\"array\">");
		for (User user : ClientDAO.getAll()) {
			Client client = (Client) user;
			returnValue.append("<client>\n" + 
            		"\t<id> " + client.getId() + "</id>\n" +
            		"\t<name> " + client.getName() + "</name>\n" +
            		"\t<email> " + client.getEmail() + "</email>\n" +
            		"\t<address> " + client.getAddress() + "</address>\n" +
            		"\t<phone> " + client.getPhone() + "</phone>\n" +
            		"\t<birthday> " + client.getBirthday() + "</birthday>\n" +
            		"</client>\n");
		}
		returnValue.append("</client>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();

	}

}
