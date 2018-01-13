package be.tomcools.javaboost.http;

import be.tomcools.javaboost.Config;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import static be.tomcools.javaboost.EventBusConstants.VERNIE;

public class HttpVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(HttpVerticle.class);

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.exceptionHandler(throwable -> LOG.error(throwable.getCause() + ":" + throwable.getMessage()));
        router.route(HttpMethod.GET, "/vernie/:command").handler((h) -> {
            vertx.eventBus().send(VERNIE, h.pathParam("command"), handler -> {
                if (handler.succeeded()) {
                    h.response().end();
                } else {
                    h.response().setStatusCode(500).end("Command could not execute.");
                }
            });
        });
        configHandlingRoutes(router);

        //fallback route
        router.route("/*").handler(r -> {
            r.request().response().end("I'm Alive!");
        });
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

    private void configHandlingRoutes(Router router) {
        router.route(HttpMethod.GET, "/config").handler((h) -> {
            h.response().end(Json.encode(Config.getConfig()));
        });
        router.route(HttpMethod.PUT, "/config").handler((h) -> {
            Config config = Json.decodeValue(h.getBody(), Config.class);
            Config.setCONFIG(config);
            h.response().end(Json.encode(Config.getConfig()));
        });
    }
}

