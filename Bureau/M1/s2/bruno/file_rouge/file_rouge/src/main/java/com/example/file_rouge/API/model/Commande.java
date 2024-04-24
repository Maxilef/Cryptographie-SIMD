package com.example.file_rouge.API.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Entity
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@ToString
@Getter
public class Commande {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private Integer numero;

    @NonNull
    private Date date;

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    //Cette annotation indique qu'une commande peut être associée à un seul client, mais qu'un client peut avoir
    // plusieurs commandes. C'est une relation many-to-one, où plusieurs commandes peuvent appartenir à un seul client.
    @ManyToOne

    //@JoinColumn(name = "client_id") : Cette annotation spécifie la colonne de la table Commande qui contient la clé
    // étrangère faisant référence à l'identifiant du client dans la table Client. Dans cet exemple, la colonne client_id
    // de la table Commande contiendra les identifiants des clients associés à chaque commande.
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "commande")
    private List<Produit> produits;

    @OneToMany(mappedBy = "commande")
    private List<LigneDeCommande> lignesDeCommande;

    @ManyToOne
    //@JoinColumn(name = "deliveryadresse_id")
    private Deliveryaddress deliveryaddress;



    private static final Logger LOGGER = Logger.getLogger(Commande.class.getName());

    @Version
    Long version;
}