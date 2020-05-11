package model.signatures;

import java.io.Serializable;

public class Premium extends Signature implements Serializable{
	
	private static final long serialVersionUID = 1L;
	static final String description = "Plano que destaca e aumenta o alcance da visualizacao do profissional";
	static final String name = "Premium"; 
	
	@Override
	public void view() {

	}
	
	@Override
	public String toString() {
		return name;
	}

}
