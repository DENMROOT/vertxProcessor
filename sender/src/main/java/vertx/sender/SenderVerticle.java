package vertx.sender;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static vertx.common.Constants.ADDRESS;
import static vertx.common.Constants.DEFAULT_HTTP_PORT;
import static vertx.common.Constants.HTML_PRODUCE;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenderVerticle.class);

    private static final String PATH_PARAM_TO_RECEIVE_MESSAGE = "all";

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = createRouter(vertx, "Hello from clustered messenger example!");

        router
            .post("/sendForAll/:" + PATH_PARAM_TO_RECEIVE_MESSAGE)
            .handler(this::sendMessageForAllReceivers);

        createAnHttpServer(vertx, router, config(), startPromise);
    }

    private void sendMessageForAllReceivers(RoutingContext routingContext) {
        EventBus eventBus = vertx.eventBus();
        String message = routingContext.request().getParam(PATH_PARAM_TO_RECEIVE_MESSAGE);
        eventBus.publish(ADDRESS, message);
        LOGGER.info("Current Thread Id {} Is Clustered {}, message: {} ",
            Thread.currentThread().getId(), vertx.isClustered(), message);
        routingContext.response().end(message);
    }

    public static Router createRouter(Vertx vertx, String welcomeMessage) {
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader(CONTENT_TYPE, HTML_PRODUCE).end("<h1>" + welcomeMessage + "</h1>");
        });
        return router;
    }

    public static void createAnHttpServer(Vertx vertx, Router router,
                                          JsonObject config, Promise<Void> promise) {
        createAnHttpServer(
            vertx,
            router,
            config,
            config.getInteger("http.port", DEFAULT_HTTP_PORT),
            promise);
    }

    public static void createAnHttpServer(Vertx vertx, Router router,
                                          JsonObject config,
                                          int port, Promise<Void> promise) {
        vertx.createHttpServer().requestHandler(router)
            .listen(config.getInteger("http.port", port), result -> {
                if (result.succeeded()) {
                    LOGGER.info("HTTP server running on port {}", port);
                    promise.complete();
                } else {
                    LOGGER.error("Could not start a HTTP server ", result.cause());
                    promise.fail(result.cause());
                }
            });
    }
}
