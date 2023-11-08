#include <immintrin.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>

#include "../global.c"


__m128i affine_encrypt(__m128i vector,char mult,char key,int mod){
    /*
    Fonction de chiffrement affine
    */

    __m128i res_mult = multiplication_mod26(vector,mult,mod);
    __m128i res_add = add_mod(res_mult,key,mod);

    return res_add ;

}
__m128i affine_decrypt(__m128i vector, char mult, char key,int mod) {

    /*
    Fonction de déchiffrement affine
    */

    char key_inversed = modInverse(mult,mod);

    if (key_inversed != -1) {
        // vec[i] - b
        __m128i dec_add = sub_mod(vector, key,mod);

        // vec[i] * mult^-1%26
        __m128i res_mult = multiplication_mod26(dec_add,key_inversed,mod);
        __m128i res_dec = modulo26(res_mult,mod);

        return res_dec ;

   } else {
        printf("L'inverse n'existe pas pour %d mod 26.\n", mult);
        exit(0);
   }
}


int main() {


    // CONVERSTION EN __m128i
    char* plaintext = "zzzzzzzazzzzzzzz";
    __m128i data1 = StringToSIMD(plaintext);
    print_simd(data1,"plaintext :");

    // a doit etre premier
    char a = 23;
    char b = 1;
    int mod = 26 ;
    __m128i res_crypt = affine_encrypt(data1,a,b,mod);
    print_simd(res_crypt,"msg crypté SIMD :");

    __m128i res_decrypt = affine_decrypt(res_crypt,a,b,mod);
    print_simd(res_decrypt,"msg decrypté SIMD :");



    // msg string crypted
    char* output = SIMDToString(res_crypt);
    printf("Output: %s\n", output);
    free(output);

    // msg string decrypted
    char* output2 = SIMDToString(res_decrypt);
    printf("Output: %s\n", output2);
    free(output2);




    return 0;
}
