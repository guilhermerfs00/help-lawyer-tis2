package app;

import service.ClientService;

import static spark.Spark.*;

public class LawyerApplication {
	
	private static ClientService client = new ClientService();

	public static void main(String[] args) {		
		port(6789);

        staticFiles.location("/public");
        
        post("/client", (request, response) -> client.add(request, response));

        get("/client/:id", (request, response) -> client.get(request, response));

        put("/client/:id", (request, response) -> client.update(request, response));

        delete("/client/:id", (request, response) -> client.remove(request, response));

        get("/client", (request, response) -> client.getAll(request, response));
	}

}
