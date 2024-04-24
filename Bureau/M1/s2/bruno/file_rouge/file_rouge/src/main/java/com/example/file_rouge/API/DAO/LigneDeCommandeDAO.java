package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.LigneDeCommande;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LigneDeCommandeDAO {

    private static EntityManager entityManager;

    public LigneDeCommandeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Méthode pour créer une nouvelle ligne de commande dans la base de données
    public static void create(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.persist(lignedecommande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour mettre à jour les informations d'une ligne de commande existante dans la base de données
    public static void update(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.merge(lignedecommande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour supprimer une ligne de commande de la base de données
    public void delete(LigneDeCommande lignedecommande) {
        entityManager.getTransaction().begin();
        entityManager.remove(lignedecommande);
        entityManager.getTransaction().commit();
    }

    // Méthode pour trouver une ligne de commande par son identifiant dans la base de données
    public static LigneDeCommande findById(long id) {
        return entityManager.find(LigneDeCommande.class, id);
    }

    // Méthode pour récupérer la liste de toutes les lignes de commande présentes dans la base de données
    public static List<LigneDeCommande> findAll() {
        return entityManager.createQuery("SELECT l FROM LigneDeCommande l", LigneDeCommande.class).getResultList();
    }
}
