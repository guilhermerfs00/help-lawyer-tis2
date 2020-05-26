package service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;
import model.Client;
import model.Lawyer;
import model.User;
import model.signatures.Free;
import model.signatures.Signature;

import org.json.JSONArray;
import spark.Request;
import spark.Response;

public class LawyerService {
    private UserDAO LawyerDAO;

    public LawyerService() {
        updateData();
    }

    private void updateData() {
        try {
            LawyerDAO = new UserDAO("lawyer.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object add(Request request, Response response) {
        updateData();
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String address = request.queryParams("address");
        String phone = request.queryParams("phone");
        LocalDate birthday = LocalDate.parse((request.queryParams("birthday")));
        String passwd = request.queryParams("passwd");
        String specialization = request.queryParams("specialization");
        String document = request.queryParams("document");
        float price = Float.parseFloat(request.queryParams("price"));
        String disponibility = request.queryParams("disponibility");
        Signature signature = new Free();
        LoginService aux = new LoginService();
        if (aux.emailExists(email)) {
            response.status(404);
            return -1;
        }
        int id = LawyerDAO.getMaxId() + 1;
        Lawyer lawyer = new Lawyer(id, name, email, address, phone, birthday, passwd, specialization, document, price,
                disponibility, signature);

        LawyerDAO.add(lawyer);

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");

            return lawyer.toJson();
        } else {
            response.status(404); // 404 Not found
            return "Advogado " + id + " nao encontrado.";
        }

    }

    public Object search(Request request, Response response) {
        updateData();
        String query = request.queryParams("query");
        List<Lawyer> lawyerList = new ArrayList<>();
        List<User> users = LawyerDAO.searchByQuery(query);
        for (User user : users) {
            lawyerList.add((Lawyer) user);
        }
        JSONArray jsonArray = new JSONArray();
        for (Lawyer lawyer : lawyerList) {
            jsonArray.put(lawyer.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");

        return jsonArray;
    }

    public Object update(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
            lawyer.setName(request.queryParams("name"));
            lawyer.setEmail(request.queryParams("email"));
            lawyer.setAddress(request.queryParams("address"));
            lawyer.setPhone(request.queryParams("phone"));
            lawyer.setBirthday(LocalDate.parse((request.queryParams("birthday"))));
            lawyer.setPasswd(request.queryParams("passwd"));
            lawyer.setDocument(request.queryParams("document"));
            lawyer.setSpecialization(request.queryParams("specialization"));
            lawyer.setPrice(Float.parseFloat(request.queryParams("price")));
            lawyer.setDisponibility(request.queryParams("disponibility"));

            LawyerDAO.update(lawyer);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Advogado nao encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {

            LawyerDAO.remove(lawyer);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Cliente encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        updateData();
        JSONArray jsonArray = new JSONArray();
        for (User user : LawyerDAO.getAll()) {
            Lawyer lawyer = (Lawyer) user;
            jsonArray.put(lawyer.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        return jsonArray;
    }

}
