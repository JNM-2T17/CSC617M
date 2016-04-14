using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangGradebooks
{
    class FileIO
    {
        public static List<Student> readFromFile()
        {
            List<Student> students = new List<Student>();
            try
            {
                using (StreamReader sr = new StreamReader("Gradebook.txt"))
                {
                    int nStudents = Int32.Parse(sr.ReadLine());
                    int nCourses = Int32.Parse(sr.ReadLine());
                    List<string> courses = new List<string>();

                    for (int i = 0; i < nCourses; i++)
                    {
                        courses.Add(sr.ReadLine());
                    }

                    for (int i = 0; i < nStudents; i++)
                    {
                        Student s = new Student(sr.ReadLine(), sr.ReadLine(), sr.ReadLine());
                        foreach (string course in courses)
                        {
                            Grade g = new Grade(course);
                            g.ExerciseScore = Double.Parse(sr.ReadLine());
                            g.ExamScore = Double.Parse(sr.ReadLine());
                            g.FinalsScore = Double.Parse(sr.ReadLine());
                            g.FinalGrade = Double.Parse(sr.ReadLine());
                            s.addGrade(g);
                        }
                        students.Add(s);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("The file could not be read:");
                Console.WriteLine(e.Message);
            }
            return students;
        }

        public static void writeToFile(List<Student> students, List<string> grade)
        {
            using (StreamWriter sw = new StreamWriter("Gradebook.txt"))
            {
                int nStudents = students.Count;
                int nCourses = grade.Count;
                sw.WriteLine(nStudents);
                sw.WriteLine(nCourses);

                for (int i = 0; i < nCourses; i++)
                {
                    sw.WriteLine(grade[i]);
                }

                for (int i = 0; i < nStudents; i++)
                {
                    Student cur = students[i];
                    sw.WriteLine(cur.Name);
                    sw.WriteLine(cur.Id);
                    sw.WriteLine(cur.Gender);
                    for (int j = 0; j < nCourses; j++)
                    {
                        Grade g = cur.getGrades()[j];
                        sw.WriteLine(g.ExerciseScore);
                        sw.WriteLine(g.ExamScore);
                        sw.WriteLine(g.FinalsScore);
                        sw.WriteLine(g.FinalGrade);
                    }
                }
            }
        }
    }
}
