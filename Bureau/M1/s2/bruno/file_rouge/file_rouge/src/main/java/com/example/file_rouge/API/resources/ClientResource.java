package com.example.file_rouge.API.resources;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.core.MediaType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;


@Path("clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Log
public class ClientResource {

    @Setter
    private static ClientDAO clientDAO; // Injecter votre ClientDAO ici

    public ClientResource(ClientDAO clientDAO) {
        ClientResource.clientDAO = clientDAO;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createClient(Client client) {
        clientDAO.create(client);

        System.out.println(client);

        System.out.println(Response.status(Response.Status.CREATED).entity(client).build().toString());

        return Response.status(Response.Status.CREATED).entity(client).build().toString();
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getClient(@PathParam("id") long id) {
        //System.out.println("++++++++++++++++++++++++++++++++++" + id);
        Client client = clientDAO.findById(id);
        if (client != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();

                //System.out.println(client);
                return mapper.writeValueAsString(client);
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else {
            return "ERROR";
        }
    }


    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateClient(@PathParam("id") long id, Client updatedClient) {
        Client existingClient = clientDAO.findById(id);

        System.out.println(existingClient);

        if (existingClient != null) {
            try {
                // MAJ des champs du client existant avec les nouvelles valeurs
                existingClient.setEmail(updatedClient.getEmail());
                existingClient.setNom(updatedClient.getNom());
                existingClient.setPrenom(updatedClient.getPrenom());

                // update de ClientDAO pour sauvegarder les modifications
                clientDAO.update(existingClient);

                System.out.println(existingClient);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(existingClient);
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else {
            return "ERROR";
        }

    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteClient(@PathParam("id") long id) {
        Client clientToDelete = clientDAO.findById(id);
        if (clientToDelete != null) {
            clientDAO.delete(clientToDelete);

            System.out.println(clientToDelete.toString());
            return Response.status(Response.Status.NO_CONTENT).build().toString();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build().toString();
        }
    }

}
