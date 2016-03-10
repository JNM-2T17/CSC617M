#include <iostream>
#include <cmath>
using namespace std;

int main()
{
	int i;
	int j = 17;
	int k = (j + 13) / 27;
	
	//Number 1
	while(k <= 10)
	{
		k = k + 1;
		i = 3 * k - 1;
	}
	
	cout << "Number 1: I is " << i << ", J is " << j << ", K is " << k << "\n";
	
	//Number 2
	switch(k)
	{
		case 1:
		case 2:
			j = 2 * k - 1;
			break;
		case 3: case 5:
			j = 3 * k + 1;
			break;
		case 4:
			j = 4 * k - 1;
			break;
		case 6: case 7: case 8:
			j = k - 2;
			break;
	}
	
	cout << "Number 2: J is " << j << ", K is " << k << "\n";
	
	//Number 3
	j = -3;
	bool done = false;
	for(i = 0; !done && i < 3; i++)
	{
		int temp = j + 2;
		if(temp == 3 || temp == 2)
			j--;
		else if(temp == 0)
			j += 2;
		else
			j = 0;
		
		if(j > 0)
		{
			done = true;
			i--;
		}
		else
			j = 3 - i;
	}
	
	cout << "Number 3: J is " << j << ", I is " << i << "\n";
	
	//Number 4
	bool reject;
	int n = 10;
	int x[n][n];
	
	for(i = 0; i < 10; i++)
		x[5][i] = 0;
	
	for(i = 0; i < n; i++)
	{
		for(j = 0, reject = false; j < n; j++, reject = false)
			if(x[i][j] != 0)
			{
				reject = true;
				break;
			}
		if(!reject)
		{
			cout << "First all-zero row is " << i;
			break;
		}
	}
	return 0;
}
