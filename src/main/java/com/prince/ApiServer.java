package com.prince;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;

public class ApiServer {

    private final Javalin server;

    // Static files directory and URL root folder:
    private final String PAGE_DIR = "/public";

    private ArrayList<Note> notes = new ArrayList<>();

    public ApiServer(){
        server = Javalin.create(config -> {
            config.staticFiles.add(PAGE_DIR, Location.CLASSPATH);

            config.routes.get("/notes/{id}", ctx -> {});
            config.routes.post("/notes", ctx -> {});
        });
    }

    public static void main(String[] args) {
        ApiServer server = new ApiServer();
        server.start(5000);
    }

    public void start(int port){
        this.server.start(port);
    }
}
