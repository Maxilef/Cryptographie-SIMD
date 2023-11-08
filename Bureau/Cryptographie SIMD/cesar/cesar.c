#include <immintrin.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <ctype.h>

#include "../global.c"

__m128i EncryptCesar128(__m128i input, char key){
    /*
    Fonction pour crypter un message claire avec cesar a l'aide des instruction SIMD
    */

    //clé avec  un décalage modulo 26 en soustrayant A
    int keyMod26 = (toupper(key) - 'A') % 26;

    // chiffrement César en ajoutant la clef a chaque bit du vecteur input (x + b)
    __m128i encrypted = _mm_add_epi8(input, _mm_set1_epi8(keyMod26));

    // masque qui contient 1 où les éléments du chiffré sont supérieurs à 25 (0x25)
    __m128i mask = _mm_cmpgt_epi8(encrypted, _mm_set1_epi8(25));

    // applique le masque pour obtenir 26 (0x1A) où le masque est 1 et 0 sinon
    __m128i mod26 = _mm_and_si128(mask, _mm_set1_epi8(26));

    // soustraire 26 des éléments du chiffrer  où le masque est 1
    return _mm_sub_epi8(encrypted, mod26);
}


__m128i DecryptedCesar128(__m128i input,char key){
    /*
    Fonction pour crypter un message claire avec cesar a l'aide des instruction SIMD
    */

    int keyMod26 = 26 - (toupper(key) - 'A') % 26;

    return EncryptCesar128(input,keyMod26 + 'A') ;
}


int main() {

  const char* plaintext = "ABCDEFGHZZZZZZZZ";
  char key = 'B';  // Clé unique pour le chiffrement César

  __m128i data128 = StringToSIMD(plaintext);

  __m128i encryptedData = EncryptCesar128(data128, key);

  __m128i decryptedData = DecryptedCesar128(encryptedData , key);


  // print des données

  print_simd (data128,"convertion en vector");

  print_simd (encryptedData,"chiffrement");

  char* decesar = SIMDToString(encryptedData);
  printf("Output: %s\n", decesar);


  print_simd (decryptedData,"dechiffrement");

  char* output = SIMDToString(decryptedData);
  printf("Output: %s\n", output);

  free(decesar);
  free(output);

  return 0;
}
