#include <iostream>
#include <string>

using namespace std;

int main()
{
	string input = "";
	do
	{
		cout << "NOTE: Should you want to exit, type EXIT\n";
		cout << "Enter input of any length: ";
		cin >> input;
		cout << "Your input was: " << input << "\n\n";
		if(input.compare("EXIT") != 0)
		{
			system("pause");
			system("cls");
		}
	} while(input.compare("EXIT") != 0);
	return 0;
}
