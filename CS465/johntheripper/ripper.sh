#!/bin/sh
set -x #echo on

rm ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 1 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 1 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 1 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 2 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 2 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 2 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 4 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 4 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 4 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 6 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 6 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 6 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 8 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 8 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 8 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 10 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 10 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 10 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 12 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 12 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 12 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 14 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 14 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 14 -type a))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 16 -type l))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 16 -type u))" >> ~/Fall2013/CS465/password.txt
echo "user1:$(openssl passwd -1 -salt salty $(./random_password.py -length 16 -type a))" >> ~/Fall2013/CS465/password.txt
~/Downloads/john-1.8.0/run/john ~/Fall2013/CS465/password.txt &

echo "DONE"