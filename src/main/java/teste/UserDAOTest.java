package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;

import dao.UserDAO;
import model.Client;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

	@Test
	public void testAdd() throws IOException { //Teste p/ adicionar cliente
		UserDAO userDAO = new UserDAO("arquivoTeste.txt");
		LocalDate data = LocalDate.now();
		Client client = new Client(1, "Nome", "e@mail.com", "estado", "cidade", "address", "31994875788", data, "senha");
		userDAO.add(client);
		assertTrue(client.equals(userDAO.get(1)));
		userDAO.remove(client);
	}
	
	@Test
	public void testUpdate() throws IOException { //Teste de atualizacao de cliente
		UserDAO userDAO = new UserDAO("arquivoTeste.txt");
		LocalDate data = LocalDate.now(); 
		Client client = new Client(2, "Roberto", "e@mail.com", "estado", "cidade", "address", "31994875788", data, "senha");
		userDAO.add(client);
		assertTrue(client.equals(userDAO.get(2)));
		data = LocalDate.ofYearDay(1996, 300);
		client = new Client(2, "Fernanda", "e@mail.com", "estado", "cidade", "address", "31994875788", data, "senha");
		userDAO.update(client);
		assertEquals("Fernanda", userDAO.get(2).getName());
		userDAO.remove(client);
	}

}
