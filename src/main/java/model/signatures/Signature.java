package model.signatures;

import java.io.Serializable;

public abstract class Signature implements Serializable{

	private static final long serialVersionUID = 1L;
	public String description;
	public String name;

	public boolean view() {
		return false;
	}
}
