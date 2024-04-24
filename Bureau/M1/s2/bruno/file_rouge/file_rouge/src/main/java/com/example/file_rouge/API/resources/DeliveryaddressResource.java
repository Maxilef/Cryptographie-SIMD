package com.example.file_rouge.API.resources;

import com.example.file_rouge.API.DAO.ClientDAO;
import com.example.file_rouge.API.DAO.DeliveryaddressDAO;
import com.example.file_rouge.API.model.Client;
import com.example.file_rouge.API.model.Deliveryaddress;
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
public class DeliveryaddressResource {

    @Setter
    private static DeliveryaddressDAO deliveryaddressDAO; // Injecter votre ClientDAO ici

    public DeliveryaddressResource(DeliveryaddressDAO deliveryaddressDAO) {
        DeliveryaddressResource.deliveryaddressDAO = deliveryaddressDAO;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createDelivey(Deliveryaddress deliveryaddress) {
        deliveryaddressDAO.create(deliveryaddress);

        System.out.println(deliveryaddress);

        System.out.println(Response.status(Response.Status.CREATED).entity(deliveryaddress).build().toString());

        return Response.status(Response.Status.CREATED).entity(deliveryaddress).build().toString();
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDelivery(@PathParam("id") long id) {
        //System.out.println("++++++++++++++++++++++++++++++++++" + id);
        Deliveryaddress deliveryaddress = deliveryaddressDAO.findById(id);
        if (deliveryaddress != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();

                //System.out.println(client);
                return mapper.writeValueAsString(deliveryaddress);
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
    public String updateDelivery(@PathParam("id") long id, Deliveryaddress updatedDelivery) {
        Deliveryaddress existingDelivery = deliveryaddressDAO.findById(id);

        System.out.println(existingDelivery);

        if (existingDelivery != null) {
            try {
                // MAJ des champs du client existant avec les nouvelles valeurs
                existingDelivery.setNumero(updatedDelivery.getNumero());
                existingDelivery.setLibelleVoie(updatedDelivery.getLibelleVoie());
                existingDelivery.setCodePostal(updatedDelivery.getCodePostal());
                existingDelivery.setNomLocalité(updatedDelivery.getNomLocalité());

                // update de ClientDAO pour sauvegarder les modifications
                deliveryaddressDAO.update(existingDelivery);

                System.out.println(existingDelivery);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(existingDelivery);
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
    public String deleteDelivery(@PathParam("id") long id) {
        Deliveryaddress deliveryToDelete = deliveryaddressDAO.findById(id);
        if (deliveryToDelete != null) {
            deliveryaddressDAO.delete(deliveryToDelete);

            System.out.println(deliveryToDelete.toString());
            return Response.status(Response.Status.NO_CONTENT).build().toString();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build().toString();
        }
    }

}
