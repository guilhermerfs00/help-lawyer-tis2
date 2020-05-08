package app;

import service.ClientService;
import service.LawyerService;

import static spark.Spark.*;

public class LawyerApplication {
	
	private static ClientService client = new ClientService();
	private static LawyerService lawyer = new LawyerService();

	public static void main(String[] args) {		
		port(6789);

        staticFiles.location("/public");
        
        get("/", (request, response) -> "Abra por um dos arquivos html em src/main/resources/public");
        
        post("/client", (request, response) -> client.add(request, response));

        get("/client/:id", (request, response) -> client.get(request, response));

        put("/client/:id", (request, response) -> client.update(request, response));

        delete("/client/:id", (request, response) -> client.remove(request, response));

        get("/client", (request, response) -> client.getAll(request, response));
        
        get("/client/:id", (request, response) -> client.login(request, response));
        
        post("/lawyer", (request, response) -> lawyer.add(request, response));

        get("/lawyer/:id", (request, response) -> lawyer.get(request, response));

        put("/lawyer/:id", (request, response) -> lawyer.update(request, response));

        delete("/lawyer/:id", (request, response) -> lawyer.remove(request, response));

        get("/lawyer", (request, response) -> lawyer.getAll(request, response));
        
        get("/lawyer/:id", (request, response) -> lawyer.login(request, response));
	}

}
