package app;

import service.*;

import static spark.Spark.*;

public class LawyerApplication {

    private static ClientService client = new ClientService();
    private static LawyerService lawyer = new LawyerService();
    private static LoginService loginService = new LoginService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        get("/", (request, response) -> "Server on");

        post("/client", (request, response) -> client.add(request, response));

        get("/client/:id", (request, response) -> client.get(request, response));

        put("/client/:id", (request, response) -> client.update(request, response));

        delete("/client/:id", (request, response) -> client.remove(request, response));

        get("/client", (request, response) -> client.getAll(request, response));

        post("/lawyer", (request, response) -> lawyer.add(request, response));

        get("/lawyer/:id", (request, response) -> lawyer.get(request, response));

        put("/lawyer/:id", (request, response) -> lawyer.update(request, response));

        delete("/lawyer/:id", (request, response) -> lawyer.remove(request, response));

        get("/lawyer", (request, response) -> lawyer.getAll(request, response));

        get("/login/:email", (request, response) -> loginService.login(request, response));
    }

}
