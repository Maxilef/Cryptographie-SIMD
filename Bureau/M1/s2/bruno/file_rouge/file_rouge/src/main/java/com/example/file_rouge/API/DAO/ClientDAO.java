package com.example.file_rouge.API.DAO;

import com.example.file_rouge.API.model.Client;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientDAO {

    private final EntityManager entityManager;

    public ClientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    public void update(Client client) {
        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
    }

    public void delete(Client client) {
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }

    public Client findById(long id) {
        return entityManager.find(Client.class, id);
    }

    public List<Client> findAll() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }
}
