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
public class Item {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private Integer code;

    @NonNull
    private String libelle;

    @NonNull
    private Integer prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @OneToMany(mappedBy = "item")
    private List<LigneDeCommande> lignesDeCommande;

    private static final Logger LOGGER = Logger.getLogger(Item.class.getName());

    @Version
    Long version;
}
