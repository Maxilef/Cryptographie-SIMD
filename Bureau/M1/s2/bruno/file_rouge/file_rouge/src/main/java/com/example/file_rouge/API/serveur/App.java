package com.example.file_rouge.API.serveur;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.resources.ClientResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

public class App {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:7777/";

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Server started and running at %s%nHit Ctrl-C to stop it...", BASE_URI));
        waitForShutdown(server);
    }

    public static void waitForShutdown(HttpServer server) {
        try {
            // Wait indefinitely until a shutdown signal is received
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Shutting down server...");
            server.shutdown();
        }
    }

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static ClientDAO clientDAO;

    private ClientResource clientResource;

    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        clientDAO = new ClientDAO(entityManager);

        // Initialisez clientResource avec le ClientDAO créé
        ClientResource.setClientDAO(clientDAO);
    }

    public static HttpServer startServer() {
        setUp();
        // configuration de ressource pour JAX-RS
        final ResourceConfig rc = new ResourceConfig().packages("com.example.file_rouge.API.resources");

        // Récupérer les classes de ressources à partir de la configuration
        Set<Class<?>> resourceClasses = rc.getClasses();

        // Afficher les classes de ressources
        for (Class<?> resourceClass : resourceClasses) {
            System.out.println("Classe de ressource: " + resourceClass.getName());
        }

        System.out.println(rc.getClassLoader().getName());
        // Création et start d'un nouveau serveur Grizzly HTTP exposant l'application Jersey à BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}
