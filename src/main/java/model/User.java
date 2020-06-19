package model;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable, JsonFormatter {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
	private String state;
    private String city;
    private String address;
    private String phone;
    private LocalDate birthday;
    private String passwd;
    private final List<Message> MESSAGES = new ArrayList<>();

    public User(int id, String name, String email, String state, String city, String address, 
    		String phone, LocalDate birthday, String passwd) {
        setName(name);
        setEmail(email);
        setState(state);
        setCity(city);
        setAddress(address);
        setPhone(phone);
        setBirthday(birthday);
        setId(id);
        setPasswd(passwd);
    }

    public User(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setState(user.getState());
        setCity(user.getCity());
        setAddress(user.getAddress());
        setPhone(user.getPhone());
        setBirthday(user.getBirthday());
        setId(user.getId());
        setPasswd(user.getPasswd());
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
        if (!email.isEmpty())
            this.email = email;
    }

    public List<Message> getMESSAGES() {
        return MESSAGES;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.isEmpty())
            this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.isEmpty())
            this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!phone.isEmpty())
            this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (!birthday.toString().isEmpty())
            this.birthday = birthday;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        if (!passwd.isEmpty())
            this.passwd = passwd;
    }
    
    public String getState() {
		return state;
	}

	public void setState(String uf) {
		this.state = uf;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
        obj.put("uf", this.getState());
        obj.put("municipio", this.getCity());
        obj.put("address", this.getAddress());
        obj.put("phone", this.getPhone());
        obj.put("birthday", this.getBirthday());
        obj.put("passwd", this.getPasswd());
        return obj;
    }
}



