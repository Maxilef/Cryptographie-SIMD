#include <immintrin.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>


// affichage simd
void print_simd(__m128i simd_vector,char* message) {
    /*
    Fonction pour print un vecteur SIMD
    */

    uint8_t data[16];
    _mm_store_si128((__m128i*)data, simd_vector);

    printf("%s :",message);
    for (int i = 0; i < 16 ; i++) {
        printf("%02X ", data[i]);
    }
    printf("\n");
}

void aes_afficher_simd(__m128i simd) {

    uint8_t data_array[16];

    _mm_storeu_si128((__m128i*)data_array, simd);

    printf("\n");

    for (int i = 0; i < 16 ; i = i + 4) {
        printf("  %02X %02X %02X %02X \n", data_array[i],data_array[i+1],data_array[i+2],data_array[i+3]);

    }

    printf("\n");
}

// convertor
__m128i StringToSIMD(const char* input) {
    /*
    Fonction pour convertir une chaîne de caractères en entiers modulo 26
    */

    // chargez la chaîne de caractères dans un registre SIMD 128 bits
    __m128i str = _mm_loadu_si128((__m128i*)input);

    // soustrayez 'A' pour ramener les lettres à l'intervalle [0, 25]
    __m128i charToInt = _mm_sub_epi8(str, _mm_set1_epi8('A'));

    // effectuez la conversion modulo 26
    __m128i mod26 = _mm_and_si128(charToInt, _mm_set1_epi8(0x1F));  // 0x1F = 31 (en binaire)

    return mod26;
}
__m128i CharToSIMD(char character) {
    /*
    Fonction qui convertit uniquement un caractère en SIMD
    */

    // Convertir le caractère en un entier modulo 26
    __m128i charInt = _mm_set1_epi8(character);
    __m128i charToInt = _mm_sub_epi8(charInt, _mm_set1_epi8('A'));

    // Effectuer la conversion modulo 26
    __m128i mod26 = _mm_and_si128(charToInt, _mm_set1_epi8(0x1F)); // 0x1F = 31 (en binaire)

    return mod26;
}
char* SIMDToString(__m128i mod26) {
    /*
    Fonction pour convertir un registre SIMD 128 bits en une chaîne de caractères
    */

    // tab vide de 16 +1 pour \0
    char result[17];

    // masque SIMD contenant les valeurs ASCII de A
    __m128i asciiA = _mm_set1_epi8('A');

    // conversion en ajoutant A pour ramener les valeurs dans l'interval [A, Z]
    __m128i mod26ToChar = _mm_add_epi8(mod26, asciiA);

    // copie des valeurs du registre SIMD dans le tableau de caractères
    _mm_storeu_si128((__m128i*)result, mod26ToChar);

    // ajout d'un caractère nul de fin de chaîne pour indiquer la fin de la chaine
    result[16] = '\0';

    // allocation de la mémoire pour stocker la chaîne de caractères
    char* output = (char*)malloc(17);
    strcpy(output, result);

    return output;
}

// opérations
__m128i modulo26(__m128i input,int mod){

  // masque qui contient 1 où les éléments de vecteur input sont supérieurs à 25
  __m128i mask = _mm_cmpgt_epi8(input, _mm_set1_epi8(mod-1));

  // appliquer le masque pour obtenir 26 (0x1A) où le masque est 1 et 0 sinon
  __m128i mod26 = _mm_and_si128(mask, _mm_set1_epi8(mod));

  // soustraire 26 (0x1A) des éléments de input où le masque est 1
  return _mm_sub_epi8(input, mod26);
}
__m128i mod_negatif26 (__m128i result,int mod){

    __m128i mask = _mm_cmpgt_epi8(_mm_set1_epi8(0), result);
    __m128i mod_adjust = _mm_and_si128(mask, _mm_set1_epi8(mod));
    result = _mm_add_epi8(result, mod_adjust);

    return result ;
}

__m128i add_mod(__m128i input, char key, int mod){
    /*
    Fonction pour addtioner a chaque valeur du vecteurA une valeur entieree (key)
    ( vectA + VectB(key) ) % x
    */

    // creer le vect pour la clef key
    __m128i keyVector = _mm_set1_epi8(key);

    // addition entre le vecteur d'entree et le vecteur de la clef
    __m128i result = _mm_add_epi8(input, keyVector);

    // on retourne le resultat en appliquant le modulo26
    return modulo26(result,mod);
}
__m128i sub_mod(__m128i input, char key,int mod) {
    /*
    Fonction pour soustraire à chaque valeur du vecteurA une valeur entière (key) mod mod
    (vectA - key) % mod
    */

    // Créer le vecteur pour la clé key
    __m128i keyVector = _mm_set1_epi8(key);

    // Soustraction entre le vecteur d'entrée et le vecteur de la clé
    __m128i result = _mm_sub_epi8(input, keyVector);

    result = mod_negatif26(result,mod);

    // On retourne le résultat en appliquant le modulo mod
    result = modulo26(result,mod);

    return result;
}

__m128i multiplication_mod26(__m128i vector, char mult, int mod) {
    /*
    Fonction pour multiplier a chaque valeur du vecteurA une valeur entieree mult
    */

    // init du vecteurs SIMD
    __m128i res = _mm_set1_epi8(0);

    // tant que mult n'est pas nul
    while (mult) {
        // si le bit de poids faible de mult est 1
        if (mult & 1) {
          // ajoute le vecteur à res
          res = _mm_add_epi8(res, vector);

          // reduction modulo
          res = modulo26(res,mod);
        }

        // decalage du vecteur de 1 bit vers la gauche (vect*2)
        vector = _mm_add_epi8(vector, vector);
        vector = modulo26(vector,mod);

        // decalage de mult d'un bit vers la droite
        mult >>= 1;
    }
    return res;
}

// inversible mod
int eclide_etendu(int a, int b, int *x, int *y) {
    if (a == 0) {
        *x = 0;
        *y = 1;
        return b;
    }

    int x1, y1;
    int gcd = eclide_etendu(b % a, a, &x1, &y1);

    *x = y1 - (b / a) * x1;
    *y = x1;

    return gcd;
}
int modInverse(int x,int mod) {
    int xInverse, y;
    int gcd = eclide_etendu(x, mod, &xInverse, &y);

    if (gcd == 1) {
        xInverse = (xInverse % mod + mod) % mod;
        return xInverse;
    } else {
        // inverse existe pas si le eclide pas égal à 1
        return -1;
    }
}
