#include <wmmintrin.h>
#include <stdio.h>

#include "../global.c"

typedef unsigned char uchar;

char RCON_CHAR[10] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36};
uchar const MASK_4L = 0x0F;
uchar const SHIFT_4 = 4;
uchar const _16 = 16;


const unsigned char SBOX[256] = {
    0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
    0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
    0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
    0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
    0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
    0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
    0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
    0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
    0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
    0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
    0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
    0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
    0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
    0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
    0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
    0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
};


__m128i SubBytes(__m128i matter) {
    /*
    --SubBytes de Alexandre Jaillant--

    Permet d'appliquer le subytes a un élément en hexa
    */
    uchar *p_matter = (uchar*)&matter;
    return _mm_set_epi8(
        SBOX[ (p_matter[15] >> SHIFT_4)*_16 + (p_matter[15] & MASK_4L)],
        SBOX[ (p_matter[14] >> SHIFT_4)*_16 + (p_matter[14] & MASK_4L)],
        SBOX[ (p_matter[13] >> SHIFT_4)*_16 + (p_matter[13] & MASK_4L)],
        SBOX[ (p_matter[12] >> SHIFT_4)*_16 + (p_matter[12] & MASK_4L)],

        SBOX[ (p_matter[11] >> SHIFT_4)*_16 + (p_matter[11] & MASK_4L)],
        SBOX[ (p_matter[10] >> SHIFT_4)*_16 + (p_matter[10] & MASK_4L)],
        SBOX[ (p_matter[9] >> SHIFT_4)*_16 + (p_matter[9] & MASK_4L)],
        SBOX[ (p_matter[8] >> SHIFT_4)*_16 + (p_matter[8] & MASK_4L)],

        SBOX[ (p_matter[7] >> SHIFT_4)*_16 + (p_matter[7] & MASK_4L)],
        SBOX[ (p_matter[6] >> SHIFT_4)*_16 + (p_matter[6] & MASK_4L)],
        SBOX[ (p_matter[5] >> SHIFT_4)*_16 + (p_matter[5] & MASK_4L)],
        SBOX[ (p_matter[4] >> SHIFT_4)*_16 + (p_matter[4] & MASK_4L)],

        SBOX[ (p_matter[3] >> SHIFT_4)*_16 + (p_matter[3] & MASK_4L)],
        SBOX[ (p_matter[2] >> SHIFT_4)*_16 + (p_matter[2] & MASK_4L)],
        SBOX[ (p_matter[1] >> SHIFT_4)*_16 + (p_matter[1] & MASK_4L)],
        SBOX[ (p_matter[0] >> SHIFT_4)*_16 + (p_matter[0] & MASK_4L)]
    );
}




__m128i shift_row(__m128i input) {
    /*
    Fonction permetant d'effectuer le shift_row pour le chiffrement AES
    */

    //On creer un masque representant nos 16 octets en choisissant l'ordre (permutation a droite)
    __m128i masque = _mm_setr_epi8(
        0x00, 0x01, 0x02, 0x03,
        0x05, 0x06, 0x07, 0x04,
        0x0A, 0x0B, 0x08, 0x09,
        0x0F, 0x0C, 0x0D, 0x0E
    );

    // on melange les octets grace au masque
    __m128i shuflle_key = _mm_shuffle_epi8(input, masque);

    // décalage supplémentaire pour chaque octet
    __m128i resultat = _mm_or_si128(
        _mm_slli_epi32(shuflle_key, 0 * 8),
        _mm_srli_epi32(shuflle_key, (4 - 0) * 8)
    );

    return resultat;
}


__m128i mix_columns(__m128i input) {
    /*
    Fonction pour effectuer le mix_columns sur un __m128i NE FONCTIONNE PAS
    */

    __m128i matrice_rinjindael = _mm_setr_epi32(0x01010302, 0x01030201, 0x03020101, 0x02010103);
    aes_afficher_simd(matrice_rinjindael);

    __m128i masque_col4 = _mm_setr_epi8(
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF
    );

    __m128i masque_col3 = _mm_setr_epi8(
        0, 0, 0xFF,0,
        0, 0, 0xFF,0,
        0, 0, 0xFF,0,
        0, 0, 0xFF,0
    );

    __m128i masque_col2 = _mm_setr_epi8(
        0, 0xFF,0,0,
        0, 0xFF,0,0,
        0, 0xFF,0,0,
        0, 0xFF,0,0
      );

    __m128i masque_col1 = _mm_setr_epi8(
        0xFF, 0,0,0,
        0xFF, 0,0,0,
        0xFF, 0,0,0,
        0xFF, 0,0,0
      );


    // Extraire les colonnes
    __m128i c1 = _mm_and_si128(input, masque_col1);
    __m128i c2 = _mm_and_si128(input, masque_col2);
    __m128i c3 = _mm_and_si128(input, masque_col3);
    __m128i c4 = _mm_and_si128(input, masque_col4);

    aes_afficher_simd(c1);

    return c1;
}



__m128i* generation_roundkey(__m128i start_key){
    /*
    Fonction pour generer toutes les clefs de rondes en partant d'un clef donné
    */


    // genérations des masque pour les différentes colonnes
    __m128i masque_rcon = _mm_setr_epi8(
        0xFF, 0xFF, 0xFF, 0xFF,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    );

    __m128i masque_col4 = _mm_setr_epi8(
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF,
        0, 0, 0, 0xFF
    );

    __m128i masque_col3 = _mm_setr_epi8(
        0, 0, 0xFF,0,
        0, 0, 0xFF,0,
        0, 0, 0xFF,0,
        0, 0, 0xFF,0
    );

    __m128i masque_col2 = _mm_setr_epi8(
        0, 0xFF,0,0,
        0, 0xFF,0,0,
        0, 0xFF,0,0,
        0, 0xFF,0,0
    );

    __m128i masque_col1 = _mm_setr_epi8(
        0xFF, 0,0,0,
        0xFF, 0,0,0,
        0xFF, 0,0,0,
        0xFF, 0,0,0
    );

    __m128i* roundkey = malloc(sizeof(__m128i)*11);

    roundkey[0] = start_key;

    for(int i=0; i<10; i++) {

        // on prend la derniere colonne est on met son premier element a la fin
        __m128i shuflle_key = _mm_shuffle_epi8(roundkey[i], _mm_setr_epi8(
            0x00, 0x01, 0x02, 0x07,
            0x04, 0x05, 0x06, 0x0B,
            0x08, 0x09, 0x0A, 0x0F,
            0x0C, 0x0D, 0x0E, 0x03
        ));


        // on recupere seulement la derniere colonne grace au masque
        __m128i col4 = _mm_and_si128(shuflle_key,masque_col4);
        //aes_afficher_simd(col4);

        // on etend la derniere colonne sur toute sa matrice
        col4 = _mm_shuffle_epi8(col4,_mm_setr_epi8( 3,3,3,3,
                                                    7,7,7,7,
                                                    11,11,11,11,
                                                    15,15,15,15
        ));
        //aes_afficher_simd(col4);

        // etape 2 : subyte
        // on subyte note colone avec la s-box
        col4 = SubBytes(col4);
        //aes_afficher_simd(col4);

        // etape 3 : xor avec RCON
        // on recupere a colonne de la s-box qui nous interesse
        __m128i rcon = _mm_and_si128(_mm_set1_epi8(RCON_CHAR[i]), masque_rcon);

        // on applique le xor avec notre derniere colonne sur la rcon
        col4= _mm_xor_si128(col4, rcon);

        // etape 4 : xor avec la premier colonne
        // on recupere uniquement la premier colonne avec le masque
        __m128i col1_key =  _mm_and_si128(shuflle_key,masque_col1);

        // on apllique le xor
        col1_key = _mm_xor_si128(col4, col1_key);

        // on etend le resulat sur toute la matrice
        col1_key =  _mm_shuffle_epi8(col1_key,_mm_setr_epi8( 0,0,0,0,
                                                            4,4,4,4,
                                                            8,8,8,8,
                                                            12,12,12,12
        ));
        //aes_afficher_simd(col1_key);

        // on recupere uniquement la deuxieme colonne avec un mask
        __m128i col2 = _mm_and_si128(shuflle_key,masque_col2);
        //aes_afficher_simd(col2);

        // on le xor avec roundKey colonne 1
        col2 = _mm_xor_si128(col1_key, col2);
        col2 = _mm_shuffle_epi8(col2,_mm_setr_epi8( 0,1,1,1,
                                                    4,5,5,5,
                                                    8,9,9,9,
                                                    12,13,13,13
        ));
        //aes_afficher_simd(col2);

        // on recupere uniquement la troisieme colonne avec un mask
        __m128i col3 = _mm_and_si128(shuflle_key,masque_col3);
        //aes_afficher_simd(col3);

        // on le xor avec roundKey colonne 2
        col3 = _mm_xor_si128(col2, col3);
        col3 = _mm_shuffle_epi8(col3,_mm_setr_epi8( 0,1,2,2,
                                                    4,5,6,6,
                                                    8,9,10,10,
                                                    12,13,14,14
        ));
        //aes_afficher_simd(col3);

        // on recupere uniquement la quatrime colonne avec un mask
        __m128i col4_key = _mm_and_si128(roundkey[i],masque_col4);

        // on le xor avec roundKey colonne 3
        col4_key = _mm_xor_si128(col3, col4_key);
        //aes_afficher_simd(col4_key);

        // store la clef trouver dans tab de toutes les clefs
        roundkey[i+1] = col4_key;

    }
    return roundkey;

}




__m128i aes_encrypt(__m128i a, __m128i cle) {
    /*
    Fonction qui applique le chiffrement de l'aes en utilisant des instruction
    Intrinsics d'Intel
    */

    __m128i texte = a ;
    __m128i* roundkey = generation_roundkey(cle);


    printf("ROUND %d :\n\n",0);
    printf("CLE  RONDE :\n");

    texte = _mm_xor_si128(texte,roundkey[0]);
    aes_afficher_simd(roundkey[0]);

    printf("TEXTE chiffre :\n");
    aes_afficher_simd(texte);

    for (int i = 1; i<10;i++){
        printf("ROUND %d :\n\n",i);
        printf("CLE  RONDE :\n");
        aes_afficher_simd(roundkey[i]);

        printf("TEXTE chiffre :\n");
        texte = _mm_aesenc_si128(texte, roundkey[i]);
        aes_afficher_simd(texte);

    }
    printf("ROUND %d :\n\n",10);
    printf("CLE  RONDE :\n");
    aes_afficher_simd(roundkey[10]);
    texte = _mm_aesenclast_si128(texte, roundkey[10]);

    return texte;
}

__m128i aes_decrypt(__m128i a, __m128i* roundKey) {
    /*
    Fonction qui applique le dechiffrement de l'aes en utilisant des instruction
    Intrinsics d'Intel

    non fonctionel
    */

    __m128i texte = a ;

    for (int i = 9 ; i>=1;i--){
        texte = _mm_aesdec_si128(texte,roundKey[i]);
    }
    texte = _mm_aesdeclast_si128(texte, roundKey[0]);
    return texte ;
}


int main() {

    __m128i plaintext = _mm_setr_epi32(0xe0318832, 0x37315a43, 0x079830f6, 0x34a28da8);
    printf("TEXTE CLAIRE\n");
    aes_afficher_simd(plaintext);

    __m128i cle = _mm_setr_epi32(0x09AB282B, 0xCFF7AE7E, 0x4F15D215, 0x3C88A616);
    printf("Cle Initial\n");
    aes_afficher_simd(cle);

    __m128i texte_chiffre = aes_encrypt(plaintext,cle);
    printf("TEXTE CHIFFRE\n");
    aes_afficher_simd(texte_chiffre);

    //printf("TEXTE DECHIFFRE");
    //__m128i texte_dechiffre = aes_decrypt(texte_chiffre,cle);
    //aes_afficher_simd(texte_dechiffre);

    return 0;
}
