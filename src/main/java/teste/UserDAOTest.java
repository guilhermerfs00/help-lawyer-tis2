package teste;

import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import dao.UserDAO;
import model.User;

public class UserDAOTest {

	public static User user;

	@BeforeEach
	public void setUp() throws IOException {
		UserDAO userDAO = new UserDAO("arquivoTeste.txt");
	}

	@Test
	public void testUpdate() {

	}

}
