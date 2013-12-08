#!/usr/bin/env python
import string
from random import choice
allchars = string.letters + string.digits + string.punctuation
import argparse

parser = argparse.ArgumentParser(description='Process password length and type')
parser.add_argument('-length', type=int, default=1, help='number of characters to have in the password (default: 1)')
parser.add_argument('-type', default='lowers', help='character set to user for the password (default: lowers)')
args = parser.parse_args()

def passwordMaker(length, passtype):
	passtype = passtype.lower()

	password = ""
	if passtype == "lowers" or passtype == "l":
		password = ''.join(choice(string.ascii_lowercase) for _ in xrange(length))
	elif passtype == "lowersanduppers" or passtype == "both" or passtype == "withuppers" or passtype == "uppers" or passtype == "u":
		password = ''.join(choice(string.ascii_lowercase+string.ascii_uppercase) for _ in xrange(length))
	elif passtype == "all" or passtype == "a":
		password = ''.join(choice(allchars) for _ in xrange(length))

	return password

def main():
	print passwordMaker(args.length, args.type)

if __name__ == '__main__':
   main()