package com.example.file_rouge.API.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@ToString
@Getter
public class Envie {



    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private Integer intensite;

    @ManyToOne
    @JoinColumn(name = "produit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;
}
