def pgcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a

# Exemple d'utilisation
num1 = 48
num2 = 18
resultat = pgcd(num1, num2)
print("Le PGCD de", num1, "et", num2, "est", resultat)
