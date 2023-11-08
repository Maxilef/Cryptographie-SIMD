CC = gcc
CFLAGS = -maes -mavx2 -O3

all: cesar_m affine_m aesj_m vigenere_m aes_m

cesar_m: cesar/cesar.c
	$(CC) $(CFLAGS) cesar/cesar.c -o cesar/cesar

affine_m: affine/affine.c
	$(CC) $(CFLAGS) affine/affine.c -o affine/affine

aesj_m: aes/aesj.c
	$(CC) $(CFLAGS) aes/aesj.c -o aes/aesj

aes_m: aes/AES.c
	$(CC) $(CFLAGS) aes/AES.c -o aes/AES

vigenere_m: vigenere/vigenere.c
	$(CC) $(CFLAGS) vigenere/vigenere.c -o vigenere/vigenere

clean:
	rm -f cesar/cesar affine/affine aes/aesj aes/AES vigenere/vigenere
