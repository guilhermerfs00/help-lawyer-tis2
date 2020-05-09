package service;

import java.io.IOException;

import dao.UserDAO;
import model.Lawyer;
import model.User;
import org.json.JSONArray;
import spark.Request;
import spark.Response;

public class LawyerService {

	private UserDAO LawyerDAO;

	public LawyerService() {
		try {
			LawyerDAO = new UserDAO("lawyer.dat");
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
		String passwd = request.queryParams("passwd");
		String specialization = request.queryParams("specialization");
		String document = request.queryParams("document");
		float price = Float.parseFloat(request.queryParams("price"));
		String disponibility = request.queryParams("disponibility");
		String signature = "Free";

		int id = LawyerDAO.getMaxId() + 1;
		Lawyer lawyer = new Lawyer(id, name, email, address, phone, birthday, passwd, specialization, document, price,
				disponibility, signature);

		LawyerDAO.add(lawyer);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
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

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        Lawyer lawyer = (Lawyer) LawyerDAO.get(id);

        if (lawyer != null) {
        	lawyer.setName(request.queryParams("name"));
        	lawyer.setEmail(request.queryParams("email"));
        	lawyer.setAddress(request.queryParams("address"));
        	lawyer.setPhone(request.queryParams("phone"));
        	lawyer.setBirthday(request.queryParams("birthday"));
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
		JSONArray jsonArray = new JSONArray();
		for (User user : LawyerDAO.getAll()) {
			Lawyer lawyer = (Lawyer) user;
			jsonArray.put(lawyer.toJson());
		}
		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		return jsonArray;
	}
	
	public Object login(Request request, Response response) {
		String email = request.params(":email");
		
		Lawyer lawyer = (Lawyer) LawyerDAO.getByEmail(email);
		
        if (lawyer != null) {
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");

            return lawyer.toJson();
        } else {
            response.status(404); // 404 Not found
            return "Usuario ou senha incorretos.";
        }
	}

}
