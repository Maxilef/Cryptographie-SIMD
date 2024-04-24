package com.example.file_rouge.API.serveur;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.DAO.CommandeDAO;
import com.example.file_rouge.API.DAO.LigneDeCommandeDAO;
import com.example.file_rouge.API.DAO.ProduitDAO;
import com.example.file_rouge.API.model.*;
import com.example.file_rouge.API.resources.ClientResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.ws.rs.core.Response;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testressource {

    private ClientResource clientResource;
    private ClientDAO clientDAO;
    private CommandeDAO commandeDAO;
    private LigneDeCommandeDAO ligneDeCommandeDAO ;
    private ProduitDAO produitDAO ;

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;


    @BeforeAll
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        clientDAO = new ClientDAO(entityManager);
        commandeDAO = new CommandeDAO(entityManager);


        // Initialisez clientResource avec le ClientDAO créé
        clientResource = new ClientResource(clientDAO);

    }

    @AfterAll
    public void tearDown() {
        // Récupérer l'EntityManager et arrêter la transaction
        EntityManager entityManager = clientDAO.getEntityManager();
        entityManager.getTransaction().rollback();
    }

    @Test
    public void testCreateClient() {
        // Créer un objet Client pour la requête
        Client client = new Client();
        client.setEmail("john@example.com");
        client.setNom("Doe");
        client.setPrenom("John");

        // Appeler la méthode createClient de ClientResource
        String response = clientResource.createClient(client);

        // Vérifier que la réponse est bien 201 (Created)
        assertEquals(Response.Status.CREATED.getStatusCode(), response.toString());
    }

    @Test
    public void testGetClient() {
        // ID du client à récupérer
        long clientId = 1;

        // Appeler la méthode getClient de ClientResource
        String response = clientResource.getClient(clientId);

        // Vérifier que la réponse est bien 200 (OK)
        assertEquals(Response.Status.OK.getStatusCode(), response.toString());
    }

    @Test
    public void testUpdateClient() {
        // ID du client à mettre à jour
        long clientId = 1;

        // Créer un objet Client pour la requête
        Client updatedClient = new Client();
        updatedClient.setEmail("updated@example.com");
        updatedClient.setNom("Updated");
        updatedClient.setPrenom("Client");

        // Appeler la méthode updateClient de ClientResource
        String response = clientResource.updateClient(clientId, updatedClient);

        // Vérifier que la réponse est bien 200 (OK)
        assertEquals(Response.Status.OK.getStatusCode(), response.toString());
    }

    @Test
    public void testDeleteClient() {
        // ID du client à supprimer
        long clientId = 1;

        // Créer un client factice pour simuler la suppression
        Client clientToDelete = new Client();
        clientToDelete.setId(clientId); // Associer l'ID au client fictif


        // Appeler la méthode deleteClient de ClientResource
        String response = clientResource.deleteClient(clientId);

        // Vérifier que la réponse est bien 200 (OK)
        assertEquals(Response.Status.OK.getStatusCode(), response.toString());
    }
}

