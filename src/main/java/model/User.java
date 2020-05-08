package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.json.JSONObject;

public abstract class User implements Serializable, JsonFormatter  {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String email;
	private String address;
	private String phone;
	private String birthday;
	private String passwd;

	public User(int id, String name, String email, String address, String phone, String birthday,
			String passwd) {
		setName(name);
		setEmail(email);
		setAddress(address);
		setPhone(phone);
		setBirthday(birthday);
		setId(id);
		setPasswd(passwd);
	}
	
	public User(User user) {
		setName(user.getName());
		setEmail(user.getEmail());
		setAddress(user.getAddress());
		setPhone(user.getPhone());
		setBirthday(user.getBirthday());
		setId(user.getId());
		setPasswd(user.getPasswd());
	}

	public void singUp() {

	}

	public void change() {

	}

	public void logIn() {

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Override
	public String toString() {
		return " ID: " + id + "\n Usuario: " + name + "\n Email: " + email + "\n Telefone: " + phone
				+ "\n Data de nascimento: " + birthday;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((User) obj).getId());
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.getId());
		obj.put("name", this.getName());
		obj.put("email", this.getEmail());
		obj.put("address", this.getAddress());
		obj.put("phone", this.getPhone());
		obj.put("birthday", this.getBirthday());
		obj.put("passwd", this.getBirthday());
		return obj;
	}
}



