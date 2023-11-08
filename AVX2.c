/*****************************************************************

				EXEMPLE AVX2

*****************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <time.h>

#include <immintrin.h>

#define WORD 64



void affiche256Elt(__m256i *_m_A, char *var, int size)
{
	int i;
	unsigned long A[size];

	printf("%s := ",var);

	for(i=0;i<size;i++)
		A[i]= ((unsigned long int *) _m_A )[i];
	for(i=0;i<size;i++)
		printf("%16.16lX ",A[size-1-i]);

	printf(" ;\n");
}

void affiche128Elt(__m128i *_m_A, char *var, int size)
{
	int i;
	unsigned long A[size];

	printf("%s := ",var);

	for(i=0;i<size;i++)
		A[i]= ((unsigned long int *) _m_A )[i];
	for(i=0;i<size;i++)
		printf("%16.16lX ",A[size-1-i]);

	printf(" ;\n");
}

void afficheVect(unsigned long int *A, char *var, int size)
{
	int i;
	unsigned long int tmp;
	printf("%s := ",var);

	for(i=0;i<size;i++){
		tmp=0;
		for(int j=0;j<WORD;j++) tmp^= ((A[size-1-i]>>j)&1UL)<<(WORD-1-j);
		printf("%16.16lX ",tmp);
	}
	printf("\n");
}

/********************************************************************************
*
* MAIN
*
*********************************************************************************/



int main(){

	__m128i in[1], key[1], in2[1],out[1];

	uint64_t * in64 = (uint64_t *) in;

	uint64_t * key64 = (uint64_t *) key;

	uint64_t * out64 = (uint64_t *) out;

	uint64_t * in2_64 = (uint64_t *) in;

	srand(time(NULL));

	for(int j=0; j<2;j++){
		in64[j] = ( ((uint64_t)((rand()&0xffff|rand()<<16))<<32) )| ((uint64_t)((rand()&0xffff|rand()<<16))&0xffffffffUL);

		key64[j] = ( ((uint64_t)((rand()&0xffff|rand()<<16))<<32) )| ((uint64_t)((rand()&0xffff|rand()<<16))&0xffffffffUL);
	}

	affiche128Elt(in,"in ",2);

	affiche128Elt(key,"key",2);

	printf("VERNAM 128 bits\n");

	affiche128Elt(in,"in ",2);

	affiche128Elt(key,"key",2);

	//out[0] = *in^*key;
	out[0] = _mm_xor_si128(in[0],key[0]);

	affiche128Elt(out,"out",2);

	printf("VERNAM 256 bits\n");

	__m256i in256[1], key256[1], out256[1];
	in64 = (uint64_t *) in256;

	key64 = (uint64_t *) key256;

	out64 = (uint64_t *) out256;

	//uint64_t * in2_64 = (uint64_t *) in;

	for(int j=0; j<4;j++){
		in64[j] = ( ((uint64_t)((rand()&0xffff|rand()<<16))<<32) )| ((uint64_t)((rand()&0xffff|rand()<<16))&0xffffffffUL);

		key64[j] = ( ((uint64_t)((rand()&0xffff|rand()<<16))<<32) )| ((uint64_t)((rand()&0xffff|rand()<<16))&0xffffffffUL);
	}
	affiche256Elt(in256,"in256 ",4);

	affiche256Elt(key256,"key256",4);

	//out256[0] = *in256^*key256;//
	out256[0] = _mm256_xor_si256(in256[0],key256[0]);

	affiche256Elt(out256,"out256",4);


	return 0;

}
