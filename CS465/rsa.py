from Crypto.PublicKey import RSA

def modexp(g, e, p):
    total = 1
    for char in bin(e)[2:][::-1]:
        if int(char) == 1:
            total = (total * g) % p
        g = (g * g) % p
    return total

def gcd(a, b):
    """ the euclidean algorithm """
    while a:
        a, b = b%a, a
    return b

def extended_gcd(a, b):
    if b == 0:
        return (1, 0)
    else:
        q = a/b
        r = a%b
        s, t = extended_gcd(b, r)
        return (t, s - q * t)

def main():
    rsaKey = RSA.generate(1024)
    p = getattr(rsaKey.key, 'p')
    q = getattr(rsaKey.key, 'q')
    e = getattr(rsaKey.key, 'e')
    
    phiN = (p-1)*(q-1)
    n = p*q

    while gcd(phiN, e) != 1:
        print "(p-1)(q-1) and e not co-prime!"
        rsaKey = RSA.generate(2048)
        p = getattr(rsaKey.key, 'p')
        q = getattr(rsaKey.key, 'q')

    d, other = extended_gcd(e, phiN)

    print "p: " + str(p)
    print "q: " + str(q)
    print "n: " + str(n)
    print "e: " + str(e)
    print "d: " + str(d)
    
    m = long(raw_input("Encrypt: "))
    print "Encrypted: ", str(modexp(m, e, n))
    m = long(raw_input("Decrypt: "))
    print "Decrypted: ", str(modexp(m, d, n))

if __name__ == "__main__":
    main()