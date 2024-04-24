package com.example.file_rouge.API;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDAOTest {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static ClientDAO clientDAO;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        clientDAO = new ClientDAO(entityManager);
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        emf.close();
    }

    @Test
    void create() {
        Client client = Client.of("testmail", "Test", "User");

        assertDoesNotThrow(() -> {
            clientDAO.create(client);
        });

        assertNotNull(client.getId());
        assertEquals("testmail", client.getEmail());
        assertEquals("Test", client.getNom());
        assertEquals("User", client.getPrenom());
    }

    @Test
    void update() {
        Client client = Client.of("updatemail", "Update", "User");
        clientDAO.create(client);

        client.setPrenom("Updated");
        clientDAO.update(client);

        Client updatedClient = clientDAO.findById(client.getId());
        assertEquals("Updated", updatedClient.getPrenom());
    }

    @Test
    void delete() {
        Client client = Client.of("deletemail", "Delete", "User");
        clientDAO.create(client);

        clientDAO.delete(client);

        Client deletedClient = clientDAO.findById(client.getId());
        assertNull(deletedClient);
    }

    @Test
    void findById() {
        Client client = Client.of("findbyidmail", "FindById", "User");
        clientDAO.create(client);

        Client retrievedClient = clientDAO.findById(client.getId());
        assertNotNull(retrievedClient);
        assertEquals(client.getEmail(), retrievedClient.getEmail());
    }

    @Test
    void findAll() {
        List<Client> allClients = clientDAO.findAll();
        int initialSize = allClients.size();

        Client client1 = Client.of("client1mail", "Client1", "User");
        clientDAO.create(client1);

        Client client2 = Client.of("client2mail", "Client2", "User");
        clientDAO.create(client2);

        allClients = clientDAO.findAll();
        assertEquals(initialSize + 2, allClients.size());
    }
}