package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Client;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClientDAO {

    private final EntityManager entityManager;

    public ClientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Méthode pour créer un nouveau client dans la base de données
    public void create(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    // Méthode pour mettre à jour les informations d'un client existant dans la base de données
    public void update(Client client) {
        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
    }

    // Méthode pour supprimer un client de la base de données
    public void delete(Client client) {
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }

    // Méthode pour trouver un client par son identifiant dans la base de données
    public Client findById(long id) {
        return entityManager.find(Client.class, id);
    }

    // Méthode pour récupérer la liste de tous les clients présents dans la base de données
    public List<Client> findAll() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
