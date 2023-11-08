#include <immintrin.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>

#include "../global.c"

// Fonction de permutation (rotation à droite des octets)
__m128i permutationInverse(__m128i input) {
    // Inverser le décalage supplémentaire pour chaque octet
    __m128i shifted = _mm_or_si128(
        _mm_slli_epi32(input, 0 * 8),
        _mm_srli_epi32(input, (4 - 0) * 8)
    );

    // Inverser la permutation en utilisant le masque inverse
    __m128i maskInverse = _mm_setr_epi8(
        0x00, 0x01, 0x02, 0x03,
        0x07, 0x04, 0x05, 0x06,
        0x0A, 0x0B, 0x08, 0x09,
        0x0D, 0x0E, 0x0F, 0x0C
    );

    __m128i unshuffled = _mm_shuffle_epi8(shifted, maskInverse);

    return unshuffled;
}

//permutation
__m128i permutation(__m128i input) {
    __m128i mask = _mm_setr_epi8(
        0x00, 0x01, 0x02, 0x03,
        0x05, 0x06, 0x07, 0x04,
        0x0A, 0x0B, 0x08, 0x09,
        0x0F, 0x0C, 0x0D, 0x0E
    );

    __m128i shuffled = _mm_shuffle_epi8(input, mask);

    // Un décalage supplémentaire pour chaque octet
    __m128i shifted = _mm_or_si128(
        _mm_slli_epi32(shuffled, 0 * 8),
        _mm_srli_epi32(shuffled, (4 - 0) * 8)
    );

    return shifted;
}


// Boîte S
__m128i boiteS(__m128i input) {


    int mod = 256;

    // Appliquer la fonction affine : y = (41 * x + 163) mod 256
    __m128i multiplication = multiplication_mod26(input, 41,mod);
    __m128i addition = add_mod(multiplication, 163, mod);

    __m128i resultat = modulo26(addition,mod); //modulo 255 pas 26

    return resultat;
}

// Boîte S
__m128i boiteS_inverse(__m128i input) {


    int mod = 256;

    // Appliquer la fonction affine : y = (41 * x + 163) mod 256
    __m128i multiplication = multiplication_mod26(input, modInverse(41,mod), mod);
    __m128i addition = add_mod(multiplication, 163, mod);

    __m128i resultat = modulo26(addition,mod); //modulo 255 pas 26

    return resultat;
}



// Fonction pour effectuer le mix_columns sur un __m128i
__m128i mix_columns(__m128i input, __m128i matrice_rinjindael) {
    __m128i result;
    __m128i temp[4];

    // Multiplier chaque colonne de l'entrée avec la matrice de Rijndael
    for (int i = 0; i < 4; i++) {
        temp[i] = _mm_setzero_si128();
        temp[i] = _mm_xor_si128(temp[i], _mm_mullo_epi16(input, matrice_rinjindael));
        input = _mm_shuffle_epi32(input, 0x39);
    }

    // Additionner les résultats intermédiaires
    result = _mm_xor_si128(temp[0], temp[1]);
    result = _mm_xor_si128(result, temp[2]);
    result = _mm_xor_si128(result, temp[3]);

    return result;
}




__m128i AESJ_encrypt(__m128i in, __m128i key){

    printf("\n\nCRYPT\n");

    __m128i key_now;
    key_now = key ;

    __m128i roundKey ;
    // Initial Round Key Addition
    roundKey = _mm_xor_si128(in, key_now);
    print_simd(roundKey,"permutation");


    // on fait 14 tours
    for (int i = 0 ; i<14; i++){

        printf("TOUR %d \n",i );

        // Boîte S
        roundKey = boiteS(roundKey);
        print_simd(roundKey,"boiteS");

        // Permutation
        roundKey = permutation(roundKey);
        print_simd(roundKey,"permutation");

        //permutaion de la clef
        key_now = permutation(key_now);
        print_simd(key_now,"key_now    :");

        // Initial Round Key Addition
        roundKey = _mm_xor_si128(roundKey, key_now);
        print_simd(roundKey,"XOR");

        printf("\n");
    }
    // Boîte S
    roundKey = boiteS(roundKey);
    print_simd(roundKey,"boiteS");

    //permutaion de la clef
    key_now = permutation(key_now);
    print_simd(key_now,"key_now    :");

    // Initial Round Key Addition
    roundKey = _mm_xor_si128(roundKey, key_now);
    print_simd(roundKey,"XOR");

    return roundKey;
}



__m128i AESJ_decrypt(__m128i in, __m128i key){

    printf("\n\nDECRYPT\n");

    __m128i key_now;
    key_now = key ;

    // last turn

    __m128i roundKey;
    // Initial Round Key Addition
    roundKey = _mm_xor_si128(in, permutation(key_now));
    print_simd(roundKey,"XOR");

    roundKey = boiteS_inverse(roundKey);
    print_simd(roundKey,"boiteS");


    for (int i = 14 ; i >= 0; i--){

        printf("TOUR %d \n",i );

        // Initial Round Key Addition
        roundKey = _mm_xor_si128(roundKey, key_now);
        print_simd(roundKey,"permutation");

        roundKey = permutationInverse(roundKey);
        print_simd(roundKey,"permutatioI");

        roundKey = boiteS_inverse(roundKey);
        print_simd(roundKey,"boiteS");

        //permutaion de la clef
        key_now = permutation(key_now);
        print_simd(key_now,"key_now    :");

        printf("\n");
    }

    // Initial Round Key Addition
    roundKey = _mm_xor_si128(roundKey, key_now);
    print_simd(roundKey,"permutation");

    return roundKey;
}


int main() {



    char* plaintext = "abcdefghijklmnop";
    const char* cle = "amudjtyehcnwyzir";


    __m128i data1 = StringToSIMD(plaintext);


    __m128i cle_c = StringToSIMD(cle);

    __m128i res = AESJ_encrypt(data1,cle_c);

    __m128i resdecrypt = AESJ_decrypt(res, cle_c);

    printf("\n");

    printf("%s\n",plaintext );

    printf("\n");

    print_simd(res,"aes :");
    char* res_char = SIMDToString(res);
    printf("%s\n",res_char );

    printf("\n");

    print_simd(resdecrypt,"aes decrypt:");
    char* resdecrypt_char = SIMDToString(resdecrypt);
    printf("%s\n",resdecrypt_char );



    return 0;
}
