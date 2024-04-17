package com.example.file_rouge.API.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.logging.Logger;


@Entity
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@ToString
@Getter
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

    @Version
    Long version;

}