package service;

import java.io.IOException;

import dao.UserDAO;
import model.Lawyer;
import model.User;
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
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");
    	    
            return "<lawyer>\n" + 
            		"\t<id> " + lawyer.getId() + "</id>\n" +
            		"\t<name> " + lawyer.getName() + "</name>\n" +
            		"\t<email> " + lawyer.getEmail() + "</email>\n" +
            		"\t<address> " + lawyer.getAddress() + "</address>\n" +
            		"\t<phone> " + lawyer.getPhone() + "</phone>\n" +
            		"\t<birthday> " + lawyer.getBirthday() + "</birthday>\n" +
            		"\t<document> " + lawyer.getDocument() + "</document>\n" +
            		"\t<specialization> " + lawyer.getSpecialization() + "</specialization>\n" +
            		"\t<price> " + lawyer.getPrice() + "</price>\n" +
            		"\t<disponibility> " + lawyer.getDisponibility() + "</disponibility>\n" +
            		"\t<signature> " + lawyer.getSignature() + "</signature>\n" +
            		"</lawyer>\n";
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
		StringBuffer returnValue = new StringBuffer("<lawyer type=\"array\">");
		for (User user : LawyerDAO.getAll()) {
			Lawyer lawyer = (Lawyer) user;
			returnValue.append("<lawyer>\n" + 
            		"\t<id> " + lawyer.getId() + "</id>\n" +
            		"\t<name> " + lawyer.getName() + "</name>\n" +
            		"\t<email> " + lawyer.getEmail() + "</email>\n" +
            		"\t<address> " + lawyer.getAddress() + "</address>\n" +
            		"\t<phone> " + lawyer.getPhone() + "</phone>\n" +
            		"\t<birthday> " + lawyer.getBirthday() + "</birthday>\n" +
            		"\t<document> " + lawyer.getDocument() + "</document>\n" +
            		"\t<specialization> " + lawyer.getSpecialization() + "</specialization>\n" +
            		"\t<price> " + lawyer.getPrice() + "</price>\n" +
            		"\t<disponibility> " + lawyer.getDisponibility() + "</disponibility>\n" +
            		"\t<signature> " + lawyer.getSignature() + "</signature>\n" +
            		"</lawyer>\n");
		}
		returnValue.append("</lawyer>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();

	}
	
	public Object login(Request request, Response response) {
		String email = request.params(":email");
		
		Lawyer lawyer = (Lawyer) LawyerDAO.getByEmail(email);
		
        if (lawyer != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<lawyer>\n" +
            		"\t<id> " + lawyer.getId() + "</id>\n" +
            		"\t<name> " + lawyer.getName() + "</name>\n" +
            		"\t<passwd> " + lawyer.getPasswd() + "</passwd>\n" +
            		"\t<name> " + lawyer.getName() + "</name>\n" +
            		"\t<email> " + lawyer.getEmail() + "</email>\n" +
            		"\t<address> " + lawyer.getAddress() + "</address>\n" +
            		"\t<phone> " + lawyer.getPhone() + "</phone>\n" +
            		"\t<birthday> " + lawyer.getBirthday() + "</birthday>\n" +
            		"\t<document> " + lawyer.getDocument() + "</document>\n" +
            		"\t<specialization> " + lawyer.getSpecialization() + "</specialization>\n" +
            		"\t<price> " + lawyer.getPrice() + "</price>\n" +
            		"\t<disponibility> " + lawyer.getDisponibility() + "</disponibility>\n" +
            		"\t<signature> " + lawyer.getSignature() + "</signature>\n" +
            		"</lawyer>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuario ou senha incorretos.";
        }
	}

}