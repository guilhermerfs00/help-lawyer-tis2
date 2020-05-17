package teste;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import dao.UserDAO;
import model.Client;
import model.User;

public class UserDAOTest {

	public static User user;
	

	@BeforeEach
	public void setUp() throws IOException {
		UserDAO userDAO = new UserDAO("arquivoTeste.txt");
	}

	@Test
	public void testAdd() throws IOException {
		UserDAO userDAO = new UserDAO("arquivoTeste.txt");
		LocalDate data = LocalDate.now();
		Client client = new Client(1, "Nome", "e@mail.com", "address", "31994875788", data, "senha");
		userDAO.add(client);
	}

}
