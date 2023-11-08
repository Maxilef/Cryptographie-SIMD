# MAXIME ROUSSEL
## Étudiant en M1 Informatique à l'Université de Toulon

## Cryptographie symétrique et Implémentation AES

Les processeurs x86-64 disposent d'extensions d'instructions telles que les instructions SIMD (Single Instruction Multiple Data), qui permettent de traiter simultanément plusieurs opérations sur des opérandes stockés dans des registres de grande taille (de 64 à 512 bits). Ces opérandes peuvent avoir différentes tailles, de 8 à 64 bits, en fonction de l'instruction choisie. Intel met à disposition une documentation en ligne qui permet d'accéder aux différents intrinsics, c'est-à-dire les instructions reconnues par les compilateurs C/C++, notamment gcc.

## Chiffrements réalisés à l'aide de ces instructions :

- Chiffrement César
- Chiffrement Affine
- Chiffrement Vigenère
- Chiffrement AES (Jouer)
- Chiffrement AES (Complet)

## Tâches réalisées avec succès :

- Chiffrement César OK
- Chiffrement Affine OK
- Chiffrement Vigenère OK

## Tâches réalisées partiellement :

- Chiffrement AES (Jouer) : Le chiffrement suit des étapes cohérentes, mais le déchiffrement ne fonctionne pas correctement.

- Chiffrement AES (Complet) : La génération des clés de ronde fonctionne correctement, mais le chiffrement avec les instructions SIMD prévues à cet effet ne produit pas les résultats attendus. Le déchiffrement AES ne fonctionne pas non plus correctement.

## Arborescence du projet :
'
.
├── aes
│   ├── AES.c
│   └── aesj.c
├── affine
│   └── affine.c
├── cesar
│   └── cesar.c
|
├── vigenere
|    └── vigenere.c
|
├── AVX2.c
├── global.c
├── makefile
└── README.md
'

## Compilation du projet :

Un fichier makefile est disponible pour compiler l'ensemble du projet. Pour ce faire, depuis la racine du projet, exécutez la commande : `make`.

Pour supprimer tous les exécutables, utilisez la commande : `make clean`.

## Exécution des différents chiffrements :

Pour exécuter les différents chiffrements, vous devez vous déplacer dans le répertoire correspondant au chiffrement souhaité. Voici la commande à utiliser pour chaque chiffrement :

- Pour AES : `cd aes/` puis `./AES`
- Pour AESJ : `cd aes/` puis `./aesj`
- Pour Affine : `cd affine/` puis `./affine`
- Pour César : `cd cesar/` puis `./cesar`
- Pour Vigenère : `cd vigenere/` puis `./vigenere`

## Remarques :

- Pour les chiffrements César, Affine, AESJ et Vigenère, utilisez des textes clairs de 16 octets en format __m128i epi8.

- Pour AES complet, utilisez également des textes clairs de 16 octets en format epi32, mais les valeurs doivent être lues à l'envers. Par exemple, pour le texte clair suivant :

    - exemple :

    pour le texte claire suivant :

     32 88 31 E0 <- depart    valeur a rentré : ..._epi32(0xe0318832, 0x37315a43, 0x079830f6, 0x34a28da8);
     43 5A 31 37
     F6 30 98 07
     A8 8D A2 34

- Dans l'implémentation AES complète, la fonction subbytes d'Alexandre Jaillant a été réutilisée.
