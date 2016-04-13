#include <iostream>
#include <cmath>
using namespace std;

int main()
{
	double value = 0;
	do
	{
		cout << "NOTE: Should you want to exit, type any value\n";
		cout << " less than or equal to 0\n";
		cout << "Enter a positive value: ";
		cin >> value;
		if(value > 0)
		{
			double answer = value * value + (log10(value)-sin(value))/sqrt(value);
			cout << "The result is " << answer << "\n\n";
			system("pause");
			system("cls");
		}
	} while(value > 0);
	return 0;
}
