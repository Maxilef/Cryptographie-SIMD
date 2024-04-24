package com.example.file_rouge.API.model;

import jakarta.persistence.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.*;

import java.util.List;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

@Entity
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@ToString
@Getter
@Path("Ressource/Client")
public class Client {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String email;

    @NonNull
    private String nom;

    @NonNull
    private String prenom;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commands;

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());


    //JPA utilise la valeur de la propriété version pour vérifier si une entité a été modifiée par une autre transaction avant de la mettre à jour.
    @Version
    Long version;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}