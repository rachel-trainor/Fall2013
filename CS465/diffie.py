# from Crypto.PublicKey import RSA

def modexp(g, e, p):
    total = 1
    for char in bin(e)[2:][::-1]:
        if int(char) == 1:
            total = (total * g) % p
        g = (g * g) % p
    return total

def main():
    # rsaKey = RSA.generate(1024)
    # p = getattr(rsaKey.key, 'p')
    # e = getattr(rsaKey.key, 'e')
    
    g = 5
    e = 65537
    p = 9605217549440987338858821671094188739653568426146524085248720988329461554834721297429464363885633634917335276735149676295826049592177376524055266006120321
    gtp = 5007232365422371944455610819629230704164208197374726183553768322751087906372047163418842134070948904449222034037161090467005763099668293261017007657676133

    print "base  : " + str(g)
    print "prime : " + str(p)
    print "secret: " + str(e)
    print "result: " + str(modexp(g, e, p))
    print "g^t%p : " + str(gtp)
    print "key   : " + str(modexp(gtp, e, p))

if __name__ == "__main__":
    main()