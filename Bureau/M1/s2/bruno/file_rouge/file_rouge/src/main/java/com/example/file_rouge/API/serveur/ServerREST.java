package com.example.file_rouge.API.serveur;

import com.example.file_rouge.API.DAO.ClientDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

//@SpringBootApplication
public class ServerREST {
    /*

    static private EntityManager entityManager;
    static private ClientDAO clientDAO;
    static private ItemDAO itemDAO;
    static private CommandDAO commandDAO; // Ajout de CommandDAO
    static private CommandLineDAO commandLineDAO; // Ajout de CommandLineDAO

    static void setUp() {
        entityManager = Persistence.createEntityManagerFactory("hellojpa-pu").createEntityManager();
        clientDAO = new ClientDAO(entityManager);
        itemDAO = new ItemDAO(entityManager);
        commandDAO = new CommandDAO(entityManager); // Initialisation de CommandDAO
        commandLineDAO = new CommandLineDAO(entityManager); // Initialisation de CommandLineDAO
    }



    static void tearDown() {
        entityManager.close();
    }

    public static void main(String[] args) {
        setUp();
        SpringApplication.run(ServerREST.class, args);
    }


    @RestController
    @RequestMapping("/clients")
    public class ClientController {

        @PostMapping
        public void createClient(@RequestBody Client client) {
            System.out.println(client);
            clientDAO.create(client);
        }

        @GetMapping("/{id}")
        public Client getClient(@PathVariable long id) {
            return clientDAO.findById(id);
        }

        @GetMapping
        public List<Client> getAllClients() {
            return clientDAO.findAll();
        }

        @PutMapping("/{id}")
        public void updateClient(@PathVariable long id, @RequestBody Client client) {
            client.setId(id); // Assurez-vous que l'ID du client correspond à l'ID dans le chemin
            clientDAO.update(client);
        }

        @DeleteMapping("/{id}")
        public void deleteClient(@PathVariable long id) {
            Client client = clientDAO.findById(id);
            if (client != null) {
                clientDAO.delete(client);
            }
        }
    }

     */
}
