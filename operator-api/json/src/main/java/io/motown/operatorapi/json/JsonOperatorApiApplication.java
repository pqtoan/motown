package io.motown.operatorapi.json;

import io.motown.operatorapi.json.commands.JsonCommandService;
import io.motown.operatorapi.json.queries.OperatorApiService;
import io.motown.operatorapi.json.spark.JsonTransformerRoute;
import io.motown.operatorapi.viewmodel.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static spark.Spark.get;
import static spark.Spark.post;

public class JsonOperatorApiApplication implements SparkApplication {

    private static final Logger log = LoggerFactory.getLogger(JsonOperatorApiApplication.class);

    private OperatorApiService service;

    private JsonCommandService commandService;

    public JsonOperatorApiApplication() throws Exception {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        this.service = (OperatorApiService) context.getBean("operatorApiService");
        this.commandService = (JsonCommandService) context.getBean("jsonCommandService");
    }

    @Override
    public void init() {
        post(new Route("/charging-stations/:chargingStationId/commands") {
            @Override
            public Object handle(Request request, Response response) {
                String chargingStationId = request.params(":chargingStationId");

                commandService.handleCommand(chargingStationId, request.body());

                response.status(202);
                return "";
            }
        });
        get(new JsonTransformerRoute("/charging-stations") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("application/json");
                return service.findAllChargingStations();
            }
        });
    }
}