package com.example.file_rouge.API.resources;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.DAO.CommandeDAO;
import com.example.file_rouge.API.model.Client;
import com.example.file_rouge.API.model.Commande;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Setter;

@Path("commandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandeResource {

    @Setter
    private static CommandeDAO commandeDAO; // Assurez-vous d'injecter votre CommandeDAO correctement
    // Injecter votre ClientDAO ici

    public CommandeResource(CommandeDAO commandeDAO) {
        CommandeResource.commandeDAO = commandeDAO;
    }

    // Méthode pour créer une nouvelle commande
    @POST
    public Response createCommande(Commande commande) {
        commandeDAO.create(commande);
        return Response.status(Response.Status.CREATED).entity(commande).build();
    }

    // Méthode pour récupérer une commande par son ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommande(@PathParam("id") long id) {
        //System.out.println("++++++++++++++++++++++++++++++++++" + id);
        Commande commande = commandeDAO.findById(id);
        if (commande != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(commande);
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else {
            return "ERROR";
        }
    }

    // Méthode pour mettre à jour une commande existante
    @PUT
    @Path("{id}")
    public Response updateCommande(@PathParam("id") long id, Commande updatedCommande) {
        Commande existingCommande = commandeDAO.findById(id);
        if (existingCommande != null) {
            existingCommande.setNumero(updatedCommande.getNumero());
            existingCommande.setDate(updatedCommande.getDate());
            existingCommande.setClient(updatedCommande.getClient());
            commandeDAO.update(existingCommande);
            return Response.ok(existingCommande).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Méthode pour supprimer une commande par son ID
    @DELETE
    @Path("{id}")
    public Response deleteCommande(@PathParam("id") long id) {
        Commande commandeToDelete = commandeDAO.findById(id);
        if (commandeToDelete != null) {
            commandeDAO.delete(commandeToDelete);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
