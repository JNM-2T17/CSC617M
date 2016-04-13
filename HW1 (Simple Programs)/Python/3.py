number  = input()
isPrime = True

for i in range(2,number):
	if number % i == 0:
		isPrime = False
		break

if(isPrime == True):
	print "Prime"

else:
	print "Not Prime"
