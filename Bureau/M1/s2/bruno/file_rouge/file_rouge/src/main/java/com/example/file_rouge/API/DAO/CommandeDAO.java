package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Commande;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CommandeDAO {

    private final EntityManager entityManager;

    public CommandeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Méthode pour créer une nouvelle commande dans la base de données
    public void create(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.persist(commande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour mettre à jour les informations d'une commande existante dans la base de données
    public void update(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.merge(commande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour supprimer une commande de la base de données
    public void delete(Commande commande) {
        entityManager.getTransaction().begin();
        entityManager.remove(commande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour trouver une commande par son identifiant dans la base de données
    public Commande findById(long id) {
        return entityManager.find(Commande.class, id);
    }

    // Méthode pour récupérer la liste de toutes les commandes présentes dans la base de données
    public List<Commande> findAll() {
        return entityManager.createQuery("SELECT c FROM Commande c", Commande.class).getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
