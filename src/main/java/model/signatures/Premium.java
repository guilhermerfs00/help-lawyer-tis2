package model.signatures;

import java.io.Serializable;

public class Premium extends Signature implements Serializable{
	
	private static final long serialVersionUID = 1L;
	final String description = "Plano que destaca e aumenta o alcance da visualizacao do profissional";
	final String name = "Premium";
	
	@Override
	public boolean view() {
		return true;
	}
	
	@Override
	public String toString() {
		return name + ": " + description;
	}

}
