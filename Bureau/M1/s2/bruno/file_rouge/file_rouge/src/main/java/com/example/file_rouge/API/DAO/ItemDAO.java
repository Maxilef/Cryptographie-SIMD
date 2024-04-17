package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Item;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ItemDAO {

    private static EntityManager entityManager;

    public ItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static void create(Item item) {
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    public static void update(Item item) {
        entityManager.getTransaction().begin();
        entityManager.merge(item);
        entityManager.getTransaction().commit();
    }

    public void delete(Item item) {
        entityManager.getTransaction().begin();
        entityManager.remove(item);
        entityManager.getTransaction().commit();
    }

    public static Item findById(long id) {
        return entityManager.find(Item.class, id);
    }

    public static List<Item> findAll() {
        return entityManager.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }
}
