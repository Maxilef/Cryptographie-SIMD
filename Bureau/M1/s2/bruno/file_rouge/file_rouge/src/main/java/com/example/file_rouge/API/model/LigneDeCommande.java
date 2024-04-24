package com.example.file_rouge.API.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.logging.Logger;

@Entity
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@ToString
@Getter
public class LigneDeCommande {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    private static final Logger LOGGER = Logger.getLogger(LigneDeCommande.class.getName());

    @Version
    Long version;

}
