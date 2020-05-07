package model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import model.signatures.Signature;

public class Lawyer extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int rate = 0;
	private String document;
	private String specialization;
	private float price;
	private String disponibility;
	private Signature signature;
	
	public Lawyer(int id, String name, String email, String address, String phone, String birthday,
			String specialization, String document, float price, String disponibility, Signature signature) {
		super(id, name, email, address, phone, birthday);
		setDocument(document);
		setSpecialization(specialization);
		setPrice(price);
		setDisponibility(disponibility);
		setSignature(signature);
	}
	
	public Lawyer(User user,String specialization, String document, float price, String disponibility, Signature signature) {
		super(user);
		setDocument(document);
		setSpecialization(specialization);
		setPrice(price);
		setDisponibility(disponibility);
		setSignature(signature);
	}
	
	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDisponibility() {
		return disponibility;
	}

	public void setDisponibility(String disponibility) {
		this.disponibility = disponibility;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = super.toJson();
		obj.put("rate", this.getRate());
		obj.put("document", this.getDocument());
		obj.put("specialization", this.getSpecialization());
		obj.put("price", this.getPrice());
		obj.put("disponibility", this.getDisponibility());
		obj.put("signature", this.getSignature());
		return obj;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n <Informacoes profissionais>\n Inscricao na OAB: " + document
				+ "\n Especialidade: " + specialization + "\n Nota: " + rate;
	}
	
}
