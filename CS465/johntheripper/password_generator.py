#!/usr/bin/env python
import crypt
import sys
import os
# from passlib.hash import md5_crypt
import string
from random import choice
allchars = string.letters + string.digits + string.punctuation

def coolName(x):
	if x == 1: return "Alice Anderson,,,"
	elif x == 2: return "Bob Barker,,,"
	elif x == 3: return "Carl Cooper,,,"
	elif x == 4: return "David Darling,,,"
	elif x == 5: return "Elaine Erikson,,,"
	elif x == 6: return "Fred Farnsworth,,,"
	elif x == 7: return "George Gunderson,,,"
	elif x == 8: return "Harry Humphry,,,"
	elif x == 9: return "Isaac Irving,,,"
	elif x == 10: return "James Johnson,,,"
	else: return "Cool Guy,,,"

def passwordMaker(strength, passtype):
	length = 0
	if strength == "WEAK":
		length = 5
	elif strength == "MEDIUM":
		length = 8
	elif strength == "STRONG":
		length = 15
	elif strength == "BEST":
		length = 15

	password = ""
	if passtype == "LOWERS":
		password = ''.join(choice(string.ascii_lowercase) for _ in xrange(length))
	elif passtype == "LOWERSANDUPPERS":
		password = ''.join(choice(string.ascii_lowercase+string.ascii_uppercase) for _ in xrange(length))
	elif passtype == "ALL":
		password = ''.join(choice(allchars) for _ in xrange(length))

	print "PASSWORD is " + password

	# return md5_crypt.encrypt(password)
	salt = "$1$ABCDEFGH$"
	return crypt.crypt(password, salt)

def genPasswd(numAccounts):
	f = open('passwd','w')
	for x in xrange(1, numAccounts+1):
		userCompleteName = coolName(x)
		userName = (userCompleteName.split(' ', 1)[0]).lower()
		userID = 1000 + x
		groupID = 1000
		directory = "/home/%s:/usr/bin/sh" % (userName)
		complete = "%s:x:%d:%d:%s:%s" % (userName, userID, groupID, userCompleteName, directory)
		f.write(complete + '\n')
	f.close()

def genShadow(numAccounts, strength, passtype):
	f = open('shadow','w')
	for x in xrange(1, numAccounts+1):
		userName = (coolName(x).split(' ', 1)[0]).lower()
		passwordHash = passwordMaker(strength, passtype)
		lastChange = 12345
		minDays = 0
		maxDays = 99999
		warnDays = 7
		complete = "%s:%s:%d:%d:%d:%d:::" % (userName, passwordHash, lastChange, minDays, maxDays, warnDays)
		f.write(complete + '\n')
	f.close()

def main():
	numAccounts = 1
	if len(sys.argv) < 2:
		print "Must supply a level of strength! (WEAK, MEDIUM, STRONG, or BEST)"
		return
	if len(sys.argv) < 3:
		print "Must supply a password type! (LOWERS, LOWERSANDUPPERS, or ALL)"
		return
	strength = sys.argv[1]
	passtype = sys.argv[2]
	genPasswd(numAccounts)
	genShadow(numAccounts, strength, passtype)

if __name__ == '__main__':
   main()
