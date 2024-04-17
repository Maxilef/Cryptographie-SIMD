def square_and_multiply(base, exponent, modulus=None):
    result = 1
    base = base % modulus if modulus else base
    while exponent > 0:
        if exponent % 2 == 1:
            result = (result * base) % modulus if modulus else result * base
        base = (base * base) % modulus if modulus else base * base
        exponent //= 2
    return result

# Exemple d'utilisation
base = 3
exponent = 13
modulus = 1000000007  # Modulo pour rester dans des valeurs gérables
resultat = square_and_multiply(base, exponent, modulus)
print("Le résultat de", base, "élévé à la puissance", exponent, "modulo", modulus, "est", resultat)
