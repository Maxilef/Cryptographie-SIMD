package com.example.file_rouge.API;

import com.example.file_rouge.API.DAO.CommandeDAO;
import com.example.file_rouge.API.model.Commande;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

class CommandeDAOTest {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static CommandeDAO commandeDAO;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        commandeDAO = new CommandeDAO(entityManager);
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        emf.close();
    }

    @Test
    void create() {
        // Créez une instance de Commande avec des valeurs appropriées
        Date date = new Date(01,01,01);
        Integer numero = 4 ;
        Commande commande =  Commande.of(numero, date);

        assertDoesNotThrow(() -> {
            commandeDAO.create(commande);
        });

        assertNotNull(commande.getId());
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void update() {
        Date date = new Date(02,02,02);
        Integer numero = 20;
        Commande commande = Commande.of(numero,date); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(commande);


        Integer newnum = 21 ;
        commande.setNumero(newnum);
        commandeDAO.update(commande);

        Commande updatedCommande = commandeDAO.findById(commande.getId());
        assertEquals(21, updatedCommande.getNumero());
    }

    @Test
    void delete() {
        Date date = new Date(03,03,03);
        Integer numero = 3;
        Commande commande = Commande.of(numero,date); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(commande);

        commandeDAO.delete(commande);

        Commande deletedCommande = commandeDAO.findById(commande.getId());
        assertNull(deletedCommande);
    }

    @Test
    void findById() {
        Date date = new Date(04,04,04);
        Integer numero = 5;
        Commande commande = Commande.of(numero,date); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(commande);

        Commande retrievedCommande = commandeDAO.findById(commande.getId());
        assertNotNull(retrievedCommande);
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void findAll() {
        List<Commande> allCommandes = commandeDAO.findAll();
        int initialSize = allCommandes.size();

        Date date = new Date(10,01,01);
        Integer numero = 10;
        Commande commande = Commande.of(numero,date); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(commande);

        Date date2 = new Date(10,02,02);
        Integer numero2 = 11;
        Commande commande2 = Commande.of(numero2,date2); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(commande2);

        allCommandes = commandeDAO.findAll();
        assertEquals(initialSize + 2, allCommandes.size());
    }
}

