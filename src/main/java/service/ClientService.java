package service;

import dao.UserDAO;
import model.Client;
import model.Message;
import model.User;
import org.json.JSONArray;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private UserDAO ClientDAO;

    public ClientService() {
        updateData();
    }

    private void updateData() {
        try {
            ClientDAO = new UserDAO("client.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object add(Request request, Response response) {
        updateData();
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String state = request.queryParams("uf");
        String city = request.queryParams("municipio");
        String address = request.queryParams("address");
        String phone = request.queryParams("phone");
        LocalDate birthday = LocalDate.parse((request.queryParams("birthday")));
        String passwd = request.queryParams("passwd");
        LoginService aux = new LoginService();
        if (aux.emailExists(email)) {
            response.status(404);
            return -1;
        }
        int id = ClientDAO.getMaxId() + 1;
        Client client = new Client(id, name, email, state, city, address, phone, birthday, passwd);

        ClientDAO.add(client);

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Client client = (Client) ClientDAO.get(id);

        if (client != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");

            return client.toJson();
        } else {
            response.status(404); // 404 Not found
            return "Cliente " + id + " nao encontrado.";
        }

    }

    public Object search(Request request, Response response) {
        updateData();
        String query = request.queryParams("query");
        List<Client> clientList = new ArrayList<>();
        List<User> users = ClientDAO.searchByQuery(query);
        for (User user : users) {
            clientList.add((Client) user);
        }
        JSONArray jsonArray = new JSONArray();
        for (Client client : clientList) {
            jsonArray.put(client.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");

        return jsonArray;
    }

    public Object update(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Client client = (Client) ClientDAO.get(id);

        if (client != null) {
            client.setName(request.queryParams("name"));
            client.setEmail(request.queryParams("email"));
            client.setState(request.queryParams("uf"));
            client.setCity(request.queryParams("municipio"));
            client.setAddress(request.queryParams("address"));
            client.setPhone(request.queryParams("phone"));
            client.setBirthday(LocalDate.parse((request.queryParams("birthday"))));
            client.setPasswd(request.queryParams("passwd"));

            ClientDAO.update(client);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Cliente nao encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        updateData();
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

    public Object sendMessage(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Client client = (Client) ClientDAO.get(id);

        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String message = request.queryParams("message");
        int senderId = Integer.parseInt(request.queryParams("id"));
        if (client != null) {
            client.getMESSAGES().add(new Message(senderId, name, email, message));
            ClientDAO.update(client);
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Advogado nao encontrado.";
        }
    }

    public Object getAllMessages(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));
        Client client = (Client) ClientDAO.get(id);
        JSONArray jsonArray = new JSONArray();
        for (Message message : client.getMESSAGES()) {
            jsonArray.put(message.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        return jsonArray;
    }

    public Object getAll(Request request, Response response) {
        updateData();
        JSONArray jsonArray = new JSONArray();
        for (User user : ClientDAO.getAll()) {
            Client client = (Client) user;
            jsonArray.put(client.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        return jsonArray;

    }

}
