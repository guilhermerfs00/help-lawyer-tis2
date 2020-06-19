package model;

import org.json.JSONObject;

import java.io.Serializable;

public class Message implements Serializable, JsonFormatter {
    private String email;
    private String nome;
    private int id;
    private String message;

    public Message(int id, String nome, String email, String message) {
        this.message = message;
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getNome());
        obj.put("email", this.getEmail());
        obj.put("message", this.getMessage());
        return obj;
    }
}
