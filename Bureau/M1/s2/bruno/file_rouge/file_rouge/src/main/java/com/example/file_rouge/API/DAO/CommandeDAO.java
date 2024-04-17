package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Commande;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CommandeDAO {

    private final EntityManager entityManager;

    public CommandeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.persist(commande);
        entityManager.getTransaction().commit();
    }

    public void update(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.merge(commande);
        entityManager.getTransaction().commit();
    }

    public void delete(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.remove(commande);
        entityManager.getTransaction().commit();
    }

    public Commande findById(long id) {
        return entityManager.find(Commande.class, id);
    }

    public List<Commande> findAll() {
        return entityManager.createQuery("SELECT c FROM Commande c", Commande.class).getResultList();
    }
}
