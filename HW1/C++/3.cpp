#include <iostream>
using namespace std;

bool checkPrime(int integer)
{
	for(int i = 2; i < integer; i++)
		if(integer%i == 0)
			return false;
	return true;
}

int main()
{
	int integer = 1;
	do
	{
		cout << "NOTE: Should you want to exit, type any integer less than 2\n";
		cout << "Enter a positive integer: ";
		cin >> integer;
		if(integer > 1)
		{
			if(checkPrime(integer))
				cout << integer << " is A PRIME integer.\n\n";
			else
				cout << integer << " is NOT A PRIME integer.\n\n";
			system("pause");
			system("cls");
		}
	} while(integer > 1);
	return 0;
}
