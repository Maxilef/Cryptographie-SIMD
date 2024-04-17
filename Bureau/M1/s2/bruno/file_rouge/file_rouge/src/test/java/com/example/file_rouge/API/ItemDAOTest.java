package com.example.file_rouge.API;

import com.example.file_rouge.API.DAO.ItemDAO;
import com.example.file_rouge.API.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static ItemDAO commandeDAO;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("hellojpa-pu");
        entityManager = emf.createEntityManager();
        commandeDAO = new ItemDAO(entityManager);
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        emf.close();
    }

    @Test
    void create() {
        // Créez une instance de Commande avec des valeurs appropriées
        Item item =  Item.of(123,"item_test",1);

        assertDoesNotThrow(() -> {
            ItemDAO.create(item);
        });

        assertNotNull(item.getId());
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void update() {
        Item item = Item.of(456,"item_update",2); // Créez une instance de Commande avec des valeurs appropriées
        ItemDAO.create(item);

        item.setLibelle("item_updated_now");
        ItemDAO.update(item);

        Item itemupdated = ItemDAO.findById(item.getId());
        assertEquals("item_updated_now", itemupdated.getLibelle());
    }

    @Test
    void delete() {
        Item item = Item.of(789,"item_delete",3); // Créez une instance de Commande avec des valeurs appropriées
        commandeDAO.create(item);

        commandeDAO.delete(item);

        Item deletedCommande = commandeDAO.findById(item.getId());
        assertNull(deletedCommande);
    }

    @Test
    void findById() {
        Item item = Item.of(666,"item_find_by_id",4); // Créez une instance de Commande avec des valeurs appropriées
        ItemDAO.create(item);

        Item retrievedCommande = ItemDAO.findById(item.getId());
        assertNotNull(retrievedCommande);
        // Assurez-vous de vérifier d'autres attributs si nécessaire
    }

    @Test
    void findAll() {
        List<Item> allCommandes = ItemDAO.findAll();
        int initialSize = allCommandes.size();


        Item item = Item.of(777,"item_1",7); // Créez une instance de Commande avec des valeurs appropriées
        ItemDAO.create(item);

        Item item2 = Item.of(888,"item_2",8); // Créez une instance de Commande avec des valeurs appropriées
        ItemDAO.create(item2);

        allCommandes = ItemDAO.findAll();
        assertEquals(initialSize + 2, allCommandes.size());
    }
}