package be.tomcools.javaboost;

import be.tomcools.javaboost.http.HttpVerticle;
import be.tomcools.javaboost.vernie.VernieVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MainVerticle());
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(new HttpVerticle());
        vertx.deployVerticle(new VernieVerticle());
    }
}
