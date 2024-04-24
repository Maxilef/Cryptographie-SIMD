package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Produit;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProduitDAO {

    private static EntityManager entityManager;

    public ProduitDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Méthode pour créer un nouvel élément dans la base de données
    public static void create(Produit produit) {
        entityManager.getTransaction().begin();
        entityManager.persist(produit);
        entityManager.getTransaction().commit();
    }

    // Méthode pour mettre à jour les informations d'un élément existant dans la base de données
    public static void update(Produit produit) {
        entityManager.getTransaction().begin();
        entityManager.merge(produit);
        entityManager.getTransaction().commit();
    }

    // Méthode pour supprimer un élément de la base de données
    public void delete(Produit produit) {
        entityManager.getTransaction().begin();
        entityManager.remove(produit);
        entityManager.getTransaction().commit();
    }

    // Méthode pour trouver un élément par son identifiant dans la base de données
    public static Produit findById(long id) {
        return entityManager.find(Produit.class, id);
    }

    // Méthode pour récupérer la liste de tous les éléments présents dans la base de données
    public static List<Produit> findAll() {
        return entityManager.createQuery("SELECT i FROM Produit i", Produit.class).getResultList();
    }
}
