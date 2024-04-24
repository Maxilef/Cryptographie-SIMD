package com.example.file_rouge.API;

import com.example.file_rouge.API.DAO.ProduitDAO;
import com.example.file_rouge.API.model.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProduitDAOTest {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static ProduitDAO commandeDAO;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        commandeDAO = new ProduitDAO(entityManager);
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        emf.close();
    }

    @Test
    void create() {
        // Créez une instance de Commande avec des valeurs appropriées
        Produit produit =  Produit.of(123,"produit_test",1);

        assertDoesNotThrow(() -> {
            ProduitDAO.create(produit);
        });

        assertNotNull(produit.getId());
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void update() {
        Produit produit = Produit.of(456,"produit_update",2); // Créez une instance de Commande avec des valeurs appropriées
        ProduitDAO.create(produit);

        produit.setLibelle("produit_updated_now");
        ProduitDAO.update(produit);

        Produit produitUpdated = ProduitDAO.findById(produit.getId());
        assertEquals("produit_updated_now", produitUpdated.getLibelle());
    }

    @Test
    void delete() {
        Produit produit = Produit.of(789,"item_delete",3); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(produit);

        commandeDAO.delete(produit);

        Produit deletedCommande = commandeDAO.findById(produit.getId());
        assertNull(deletedCommande);
    }

    @Test
    void findById() {
        Produit produit = Produit.of(666,"item_find_by_id",4); // Créez une instance de Commande avec des valeurs appropriées
        ProduitDAO.create(produit);

        Produit retrievedCommande = ProduitDAO.findById(produit.getId());
        assertNotNull(retrievedCommande);
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void findAll() {
        List<Produit> allCommandes = ProduitDAO.findAll();
        int initialSize = allCommandes.size();


        Produit produit = Produit.of(777,"item_1",7); // Créez une instance de Commande avec des valeurs appropriées
        ProduitDAO.create(produit);

        Produit produit2 = Produit.of(888,"item_2",8); // Créez une instance de Commande avec des valeurs appropriées
        ProduitDAO.create(produit2);

        allCommandes = ProduitDAO.findAll();
        assertEquals(initialSize + 2, allCommandes.size());
    }
}