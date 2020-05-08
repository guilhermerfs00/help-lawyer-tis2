package model;
import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

public class Client extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Client(int id, String name, String email, String address, String phone, String birthday,
			String passwd) {
		super(id, name, email, address, phone, birthday, passwd);
	}

	public Lawyer[] search(String area) {
		return null;
		
	}
	
	public void rate(int rate) {
		
	}
	
	public void sendMessage(String message) {
		
	}

	@Override
	public JSONObject toJson() {
		return super.toJson();
	}

}
