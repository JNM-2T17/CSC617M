using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC617M {
	class HW1_4 {
		static void Main(string[] args) {
			Console.Write("Enter a value: ");
			double x = double.Parse(Console.ReadLine());
			double ans = x*x + (Math.Log(x) - Math.Sin(x))/Math.Sqrt(x);
			Console.WriteLine("Answer is " + ans + "!\n");
		}
	}
}