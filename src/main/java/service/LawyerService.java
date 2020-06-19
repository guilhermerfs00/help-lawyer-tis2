package service;

import dao.UserDAO;
import model.Client;
import model.Lawyer;
import model.Message;
import model.User;
import model.signatures.Free;
import model.signatures.Premium;
import model.signatures.Signature;
import org.json.JSONArray;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        String state = request.queryParams("uf");
        String city = request.queryParams("municipio");
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
        Lawyer lawyer = new Lawyer(id, name, email, state, city, address, phone, birthday, passwd,
                specialization, document, price, disponibility, signature);

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
        for (User user : LawyerDAO.getAll()) {
            Lawyer lawyer = (Lawyer) user;
            if (lawyer.getEmail().toLowerCase().contains(query.toLowerCase()) || lawyer.getName().toLowerCase().contains(query.toLowerCase())
                    || lawyer.getAddress().toLowerCase().contains(query.toLowerCase()) || lawyer.getSpecialization().toLowerCase().contains(query)) {
                lawyerList.add(lawyer);
            }
        }

        Collections.sort(lawyerList, new Comparator<Lawyer>() {
            @Override
            public int compare(Lawyer o1, Lawyer o2) {
                if (o1.getSignature() != null && o2.getSignature() != null) {
                    if (o1.getSignature().equals(o2.getSignature()))
                        return 0;
                    if (o1.getSignature() instanceof Free && o2.getSignature() instanceof Premium) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            }
        });
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
            lawyer.setState(request.queryParams("uf"));
            lawyer.setCity(request.queryParams("municipio"));
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

    //Mudar plano para Premium
    public Object upgrade(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
            lawyer.setSignature(new Premium());

            LawyerDAO.update(lawyer);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Advogado nao encontrado.";
        }

    }

    //Voltar pro plano gratuito
    public Object downgrade(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
            lawyer.setSignature(new Free());

            LawyerDAO.update(lawyer);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Advogado nao encontrado.";
        }

    }

    //Ainda nao coloquei pra limitar pra um voto por cliente, se funcionar coloco dpois
    public Object rate(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
            lawyer.setRate(Integer.parseInt(request.queryParams("rate")));

            LawyerDAO.update(lawyer);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Advogado nao encontrado.";
        }

    }

    public Object getAllMessages(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));
        Lawyer client = (Lawyer) LawyerDAO.get(id);
        JSONArray jsonArray = new JSONArray();
        for (Message message : client.getMESSAGES()) {
            jsonArray.put(message.toJson());
        }
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        return jsonArray;
    }

    public Object sendMessage(Request request, Response response) {
        updateData();
        int id = Integer.parseInt(request.params(":id"));

        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String message = request.queryParams("message");
        int senderId = Integer.parseInt(request.queryParams("id"));
        if (lawyer != null) {
            lawyer.getMESSAGES().add(new Message(senderId, name, email, message));
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
