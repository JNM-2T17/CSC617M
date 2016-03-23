# 1

k = (j + 13) / 27

while k <= 10:
	k = k + 1
	i = 3 * k - 1

# 2

k = 4

if k == 1 or k == 2:
	j = 2 * k - 1

if k == 3 or k == 5:
	j = 3 * k + 1

if k == 4:
	j = 4 * k - 1

if k == 6 or k == 7 or k == 8:
	j = k - 2

# 3

j = -3

i = 0
while i < 3 and j <= 0:
	value = j + 2

	if value == 3 or value == 2:
		j -= 1

	elif value == 0:
		j += 2

	else:
		j = 0

	if j <= 0:
		j = 3 - i

	i += 1

# 4

for i in range(0, n):	
	reject = False

	for j in range(0, n):	
		if x[i][j] != 0:
			reject = True
			break

	if(reject == False):
		print "First all-zero row is: " + str(i)
		break