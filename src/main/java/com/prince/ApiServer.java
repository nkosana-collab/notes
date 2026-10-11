package com.prince;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ApiServer {

    private final Javalin server;

    // Static files directory and URL root folder:
    private final String PAGE_DIR = "/public";

    private ArrayList<Note> notes = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public ApiServer(){
        server = Javalin.create(config -> {
            config.staticFiles.add(PAGE_DIR, Location.CLASSPATH);

            config.routes.get("/notes/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));

                for(Note note : notes){
                    if(note.getId() == id){
                        ctx.status(200);
                        ctx.json(note);
                    }
                }
            });
            config.routes.post("/notes", ctx -> {
                CreateNoteRequest request = ctx.bodyAsClass(CreateNoteRequest.class);

                int id = nextId.getAndIncrement();
                Note note = new Note(request.getMessage(),id);
                notes.add(note);

                ctx.status(201);
                ctx.header("Location", "/notes/" + note.getId());
                ctx.json(note);
            });
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
