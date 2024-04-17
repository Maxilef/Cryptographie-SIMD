def euclide_etendu(a, b):
    # Initialisation des valeurs de départ
    x0, y0, x1, y1 = 1, 0, 0, 1
    while b != 0:
        # Division euclidienne de a par b
        q, r = divmod(a, b)
        a, b = b, r
        # Mise à jour des coefficients de Bézout
        x0, x1 = x1, x0 - q * x1
        y0, y1 = y1, y0 - q * y1
    # Retourne le PGCD et les coefficients de Bézout
    return a, x0, y0

# Exemple d'utilisation
num1 = 48
num2 = 18
pgcd, coef_x, coef_y = euclide_etendu(num1, num2)
print("Le PGCD de", num1, "et", num2, "est", pgcd)
print("Coefficients de Bézout:", coef_x, coef_y)
