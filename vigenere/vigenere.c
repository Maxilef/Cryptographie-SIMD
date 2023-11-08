#include <immintrin.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <ctype.h>

#include "../global.c"

// redefinissiont de cesar et modification
__m128i NewCesarForVigenere(__m128i input, int key){
    /*
    Fonction pour crypter un message claire avec cesar a l'aide des instruction SIMD avec un clef en int
    */
    // Effectuer le chiffrement César en ajoutant la clef a chaque bit du vecteur input (x + b)
    __m128i encrypted = _mm_add_epi8(input, _mm_set1_epi8(key));

    // Créer un masque qui contient 1 où les éléments de 'encrypted' sont supérieurs à 25 (0x25)
    __m128i mask = _mm_cmpgt_epi8(encrypted, _mm_set1_epi8(25));

    // Appliquer le masque pour obtenir 26 (0x1A) où le masque est 1 et 0 sinon
    __m128i mod26 = _mm_and_si128(mask, _mm_set1_epi8(26));

    // Soustraire 26 (0x1A) des éléments de 'encrypted' où le masque est 1
    return _mm_sub_epi8(encrypted, mod26);
}

__m128i vigenere_encrypt(__m128i vector,const char* key){
    /*
    Fonction de chiffrement vigenere

    puisque on ne peut pas recuperer les valeur d'un vecteur simd dinamiquement
    avec _mm_extract_epi8
    voici une methode au chiffrement vigenere
    */

    __m128i cara ;
    int tab_cesar[16];
    int cesar_extract;

    char* plaintext = SIMDToString(vector);

    int len_pt = strlen(plaintext);
    int len_key = strlen(key);

    for (int i=0; i < len_pt ;i++){

        // recuperation de la clef i pour crypter
        char keyMod26 = (toupper(key[i%len_key]) - 'A') % 26;

        // création vecteur simd pour la lettre i
        cara = CharToSIMD(plaintext[i]);

        //chiffre le vecteur de la lettre i
        __m128i cesar = NewCesarForVigenere(cara,keyMod26);

        //recup du premier element du vect de la lettre i
        cesar_extract = _mm_extract_epi8(cesar, 1);

        //remise de l'int en ascci
        tab_cesar[i] = cesar_extract + 'A';

    }
    //création du char*
    char result[17];
    for (int i = 0; i < 16; i++) {
        result[i] = tab_cesar[i];
    }
    result[16] = '\0';

    //convertion en simd
    return StringToSIMD(result);
}

__m128i vigenere_decrypt(__m128i vector,const char* key){
    /*
    Fonction pour crypter un message claire avec cesar a l'aide des instruction SIMD
    */
    /*
    Fonction pour décrypter un message chiffré avec Vigenère à l'aide des instructions SIMD
    */
    int len_key = strlen(key);
    char keydecrypt[len_key];

    for (int i = 0; i < len_key; i++) {
        keydecrypt[i] = (26 - (toupper(key[i]) - 'A')) % 26 + 'A';
    }
    keydecrypt[len_key] = '\0';

    printf("Clé de déchiffrement : %s\n", keydecrypt);

    return vigenere_encrypt(vector, keydecrypt);

}

int main() {
    // CONVERSTION EN __m128i
    char* plaintext = "aacdefghijklmnoz";
    const char* cle = "abcde";

    __m128i data1 = StringToSIMD(plaintext);
    print_simd(data1,"plaintext :");

    __m128i result = vigenere_encrypt(data1,cle);
    print_simd(result,"vigenere chiffre");

    __m128i decrypt = vigenere_decrypt(result, cle);
    print_simd(result,"vigenere dechiffre");

    char* output1 = SIMDToString(result);
    printf("chiffrement: %s\n", output1);

    char* output2 = SIMDToString(decrypt);
    printf("dechiffrement: %s\n", output2);

    return 0;
}
