using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC617M {
	class HW1_3 {
		static void Main(string[] args) {
			Console.Write("Enter a positive integer: ");
			int n = Int32.Parse(Console.ReadLine());
			bool[] sieve = new bool[n+1];
			for (int i = 2; i <= n; i++) sieve[i] = true;
			for (int i = 2; i <= n; i++) {
				if (sieve[i]) {
					for (int j = i * 2; j <= n; j += i) {
					sieve[j] = false;
					}
				}
			}
			if (sieve[n]) Console.WriteLine("Prime!\n");
			else Console.WriteLine("Not Prime!\n");
		}
	}
}