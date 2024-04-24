package com.example.file_rouge.API.serveur;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.model.Deliveryaddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testrest {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static ClientDAO clientDAO;

    private static Process serverProcess;

    private static HttpServer server;

    @BeforeAll
    public static void startServer() throws IOException {
        // Start the server in a separate thread
        new Thread(() -> {
            try {
                server = App.startServer();
                System.out.println(String.format("Server started and running at %s%n", App.BASE_URI));
                Thread.currentThread().join(); // Wait indefinitely until interrupted
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Wait for the server to start
        try {
            Thread.sleep(2000); // Adjust delay if needed to allow the server to start completely
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void stopServer() {
        // Stop the server
        if (server != null) {
            System.out.println("Stopping server...");
            server.shutdownNow();
        }
    }

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        clientDAO = new ClientDAO(entityManager);
    }


    @Test
    public void testClientRequests() throws IOException {

        System.out.println("debut test client");
        // Requêtes curl pour les clients
        String createClient = "{\"email\": \"john@example.com\",\"nom\": \"Doe\",\"prenom\": \"John\"}";
        assertTrue(executeCurl("POST", "http://localhost:7777/clients", createClient));

        String getClient = executeCurl("GET", "http://localhost:7777/clients/1");
        assertTrue(statusOK(getClient));

        String getAllClients = executeCurl("GET", "http://localhost:7777/clients");
        assertTrue(statusOK(getAllClients));

        String updateClient = "{\"email\": \"john@example.com\",\"nom\": \"Doe\",\"prenom\": \"John\"}";
        assertTrue(executeCurl("PUT", "http://localhost:7777/clients/1", updateClient));
    }

    private String execcheckAdresse(String url) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "curl --output - "+ url);
        System.out.println(processBuilder.command("bash", "-c", "curl --output - "+ url));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        }

        process.destroy();
        return output.toString().trim();
    }
    private Deliveryaddress deliveryaddress = new Deliveryaddress();



    @Test
    public void checkAdresse() throws IOException {
        deliveryaddress.setNumero(185);
        deliveryaddress.setLibelleVoie("Place de la Liberte");
        deliveryaddress.setCodePostal(83300);
        deliveryaddress.setNomLocalité("Toulon");


        String res = execcheckAdresse("https://api-adresse.data.gouv.fr/search/?q="+deliveryaddress.getNumero()+"+"+deliveryaddress.getLibelleVoie()+"+"+deliveryaddress.getCodePostal()+"+"+deliveryaddress.getNomLocalité());
        System.out.println(res);
    }

    private boolean statusOK(String retour) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Lire la chaîne JSON en tant qu'arbre JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(retour);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return false;
        }
        // Extraire la valeur du champ "status"
        int status = jsonNode.get("status").asInt();
        System.out.println(status);
        return status == 200;
    }

    private String executeCurl(String method, String url) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "curl -X --output -" + method + " " + url);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        process.destroy();
        return output.toString().trim();
    }

    private Boolean executeCurl(String method, String url, String body) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "curl -X " + method + " " + url + " -H \"Content-Type: application/json\" -d '" + body + "'");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        process.destroy();
        return statusOK(output.toString().trim());
    }


}
