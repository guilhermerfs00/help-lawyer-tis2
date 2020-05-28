package model.signatures;

import java.io.Serializable;

public class Free extends Signature implements Serializable{

	private static final long serialVersionUID = 1L;
	static final String description = "Plano básico";
	static final String name = "Gratuito"; 
	
	@Override
	public String toString() {
		return name + ": " + description;
	}

}
