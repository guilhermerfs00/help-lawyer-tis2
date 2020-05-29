package app;

import service.ClientService;
import service.LawyerService;
import service.LoginService;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.get;
import static spark.Spark.delete;
import static spark.Spark.options;
import static spark.Spark.before;

public class LawyerApplication {

    private static ClientService client = new ClientService();
    private static LawyerService lawyer = new LawyerService();
    private static LoginService loginService = new LoginService();

    public static void main(String[] args) {
        port(6789);
        
        enableCORS("*", "*", "*");

        get("/", (request, response) -> "Server on");

        post("/client", (request, response) -> client.add(request, response));

        get("/client/:id", (request, response) -> client.get(request, response));

        put("/client/:id", (request, response) -> client.update(request, response));

        delete("/client/:id", (request, response) -> client.remove(request, response));

//        get("/client", (request, response) -> client.getAll(request, response));

        get("/client", (request, response) -> client.search(request, response));

        post("/lawyer", (request, response) -> lawyer.add(request, response));

        get("/lawyer/:id", (request, response) -> lawyer.get(request, response));

        put("/lawyer/:id", (request, response) -> lawyer.update(request, response));

        delete("/lawyer/:id", (request, response) -> lawyer.remove(request, response));

//        get("/lawyer", (request, response) -> lawyer.getAll(request, response));

        get("/lawyer", (request, response) -> lawyer.search(request, response));
        
        put("/lawyer/:id", (request, response) -> lawyer.upgrade(request, response));
        
        put("/lawyer/:id", (request, response) -> lawyer.downgrade(request, response));
        
        put("/lawyer/:id", (request, response) -> lawyer.rate(request, response));

        get("/login/:email", (request, response) -> loginService.login(request, response));
    }
    
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }

}
