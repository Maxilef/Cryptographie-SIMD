package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.LigneDeCommande;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LigneDeCommandeDAO {

    private static EntityManager entityManager;

    public LigneDeCommandeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static void create(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.persist(lignedecommande);
        entityManager.getTransaction().commit();
    }

    public static void update(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.merge(lignedecommande);
        entityManager.getTransaction().commit();
    }

    public void delete(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.remove(lignedecommande);
        entityManager.getTransaction().commit();
    }

    public static LigneDeCommande findById(long id) {
        return entityManager.find(LigneDeCommande.class, id);
    }

    public static List<LigneDeCommande> findAll() {
        return entityManager.createQuery("SELECT l FROM LigneDeCommande l", LigneDeCommande.class).getResultList();
    }
}