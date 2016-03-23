using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC617M
{
    class CSharpExer
    {
        static void Main(String[] args)
        {
            f1(0);
            f2(1);
            f2(2);
            Console.Read();
        }

        static void f1(int j){
            int i;
            for (int k = (j + 13) / 27; k <= 10; k++)
            {
                i = 3 * k - 1;
            }
        }

        static void f2(int k)
        {
            int j = 0;
            /*switch (k)
            {
                case 1: j = 2 * k - 1; break;
                case 2: j = 2 * k - 1; break;
                case 3: j = 3 * k + 1; break;
                case 4: j = 4 * k - 1; break;
                case 5: j = 3 * k + 1; break;
                case 6: j = k - 2; break;
                case 7: j = k - 2; break;
                case 8: j = k - 2; break;
            }*/
            switch (k)
            {
                case 1: 
                case 2: j = 2 * k - 1; break;
                case 3: 
                case 5: j = 3 * k + 1; break;
                case 4: j = 4 * k - 1; break;
                case 6:
                case 7:
                case 8: j = k - 2; break;
            }
            Console.WriteLine(j);
        }

        static void f3()
        {
            int j = -3;
            for (int i = 0; i < 3; i++)
            {
                int cur = j + 2;
                if (cur == 3 || cur == 2)
                {
                    j--;
                }
                else if (cur == 0)
                {
                    j += 2;
                }
                else
                {
                    j = 0;
                }
                if (j > 0)
                {
                    i = 3;
                }
                else
                {
                    j = 3 - i;
                }
            }
        }

        // readability-wise, I'd say this is quite easy to understand, since the function abstracts the checking per row,
        // increasing the readability of the function
        static void f4(int[][] x)
        {
            for (int i = 0; i < x.Length; i++)
            {
                if (allZeroes(x[i]))
                {
                    Console.WriteLine("First all-zero row: "+i);
                    return;
                }
            }
        }

        static Boolean allZeroes(int[] list)
        {
            for (int i = 0; i < list.Length; i++)
            {
                if (list[i] != 0)
                {
                    return false;
                }
            }
            return true;
        }

        /*  
         * The switch statement +in C# is interesting in the sense that it does not allow cascading cases if the first case has code in it.
         * Eg. 
         * case 1: i--;
         * case 2: j++; break;
         * will not compile. However,
         * case 1:
         * case 2: j++; break;
         * will.
         */

        /*
         * C# provides the traditional for-loop construct available in other languages like C, Java, and Javascript. However, C# also provides
         * a special "foreach x in y" loop that runs through each element x in a collection y, similar to Java's 
         * for(datatype x: Collection<datatype> y).
         * 
         * The following two code blocks are identical:
        int[] fibarray = new int[] { 0, 1, 1, 2, 3, 5, 8, 13 };
        foreach (int element in fibarray)
        {
            System.Console.WriteLine(element);
        }
        System.Console.WriteLine();

        for (int i = 0; i < fibarray.Length; i++)
        {
            System.Console.WriteLine(fibarray[i]);
        }
        System.Console.WriteLine();
         */
    }
}
