package com.example.file_rouge.API;

import com.example.file_rouge.API.DAO.LigneDeCommandeDAO;
import com.example.file_rouge.API.model.LigneDeCommande;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LigneDeCommandeDAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static LigneDeCommandeDAO commandeDAO;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        commandeDAO = new LigneDeCommandeDAO(entityManager);
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        emf.close();
    }

    @Test
    void create() {
        // Créez une instance de Commande avec des valeurs appropriées
        LigneDeCommande lignedecommande =  LigneDeCommande.of(1);

        assertDoesNotThrow(() -> {
            LigneDeCommandeDAO.create(lignedecommande);
        });

        assertNotNull(lignedecommande.getId());
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void update() {
        LigneDeCommande lignedecommande = LigneDeCommande.of(2); // Créez une instance de Commande avec des valeurs appropriées
        LigneDeCommandeDAO.create(lignedecommande);

        lignedecommande.setQuantite(0);
        LigneDeCommandeDAO.update(lignedecommande);

        LigneDeCommande lignedecommandeupdated = LigneDeCommandeDAO.findById(lignedecommande.getId());
        assertEquals(0, lignedecommandeupdated.getQuantite());
    }

    @Test
    void delete() {
        LigneDeCommande lignedecommande = LigneDeCommande.of(10); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(lignedecommande);

        commandeDAO.delete(lignedecommande);

        LigneDeCommande deletedCommande = commandeDAO.findById(lignedecommande.getId());
        assertNull(deletedCommande);
    }

    @Test
    void findById() {
        LigneDeCommande lignedecommande = LigneDeCommande.of(666); // Créez une instance de Commande avec des valeurs appropriées
        LigneDeCommandeDAO.create(lignedecommande);

        LigneDeCommande retrievedCommande = LigneDeCommandeDAO.findById(lignedecommande.getId());
        assertNotNull(retrievedCommande);
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void findAll() {
        List<LigneDeCommande> allCommandes = LigneDeCommandeDAO.findAll();
        int initialSize = allCommandes.size();


        LigneDeCommande lignedecommande = LigneDeCommande.of(777); // Créez une instance de Commande avec des valeurs appropriées
        LigneDeCommandeDAO.create(lignedecommande);

        LigneDeCommande lignedecommande2 = LigneDeCommande.of(888); // Créez une instance de Commande avec des valeurs appropriées
        LigneDeCommandeDAO.create(lignedecommande2);

        allCommandes = LigneDeCommandeDAO.findAll();
        assertEquals(initialSize + 2, allCommandes.size());
    }
}
