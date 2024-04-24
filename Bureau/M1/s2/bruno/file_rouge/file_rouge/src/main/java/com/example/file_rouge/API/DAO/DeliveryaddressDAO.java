package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Client;
import com.example.file_rouge.API.model.Deliveryaddress;
import jakarta.persistence.EntityManager;

import java.util.List;
import com.example.file_rouge.API.model.Client;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DeliveryaddressDAO {
    private final EntityManager entityManager;

    public DeliveryaddressDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Méthode pour créer un nouveau client dans la base de données
    public void create(Deliveryaddress deliveryaddress) {
        entityManager.getTransaction().begin();
        entityManager.persist(deliveryaddress);
        entityManager.getTransaction().commit();
    }

    // Méthode pour mettre à jour les informations d'un client existant dans la base de données
    public void update(Deliveryaddress deliveryaddress) {
        entityManager.getTransaction().begin();
        entityManager.merge(deliveryaddress);
        entityManager.getTransaction().commit();
    }

    // Méthode pour supprimer un client de la base de données
    public void delete(Deliveryaddress deliveryaddress) {
        entityManager.getTransaction().begin();
        entityManager.remove(deliveryaddress);
        entityManager.getTransaction().commit();
    }

    // Méthode pour trouver un client par son identifiant dans la base de données
    public Deliveryaddress findById(long id) {
        return entityManager.find(Deliveryaddress.class, id);
    }

    // Méthode pour récupérer la liste de tous les clients présents dans la base de données
    public List<Deliveryaddress> findAll() {
        return entityManager.createQuery("SELECT d FROM Deliveryaddress d", Deliveryaddress.class).getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
