using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangGradebooks
{
    class Program
    {
        static List<Student> students;
        static List<string> gradebooks;

        static void Main(string[] args)
        {
            try
            {
                students = new List<Student>();
                gradebooks = new List<string>();

                readFromFile();
                while (true)
                {
                    Console.Clear();
                    Console.WriteLine("1- View Students");
                    Console.WriteLine("2- View Courses");
                    Console.WriteLine("3- Add Student");
                    Console.WriteLine("4- Add Course");
                    Console.WriteLine("5- Read from File");
                    Console.WriteLine("6- Write to File");
                    Console.Write("Please make your choice: ");
                    int choice = Int32.Parse(Console.ReadLine());
                    switch (choice)
                    {
                        case 1:
                            showStudents();
                            break;
                        case 2:
                            showCourses();
                            break;
                        case 3:
                            addStudent();
                            break;
                        case 4:
                            addGrade();
                            break;
                        case 5:
                            readFromFile();
                            break;
                        case 6:
                            writeToFile();
                            break;
                        default:
                            break;
                    }
                }
            }
            catch { }
        }

        static void addStudent()
        {
            Console.Clear();
            Console.Write("Please enter name: ");
            String name = Console.ReadLine();
            Console.Write("Please enter ID: ");
            String id = Console.ReadLine();
            Console.Write("Please enter gender: ");
            String gender = Console.ReadLine();
            Student s = new Student(name, id, gender);

            foreach (string str in gradebooks)
            {
                s.addGrade(new Grade(str));
            }
            students.Add(s);
        }

        static void showStudents()
        {
            Console.Clear();
            Console.WriteLine("Students: ");
            int i = 1;
            foreach (Student s in students)
            {
                Console.WriteLine(i+++": "+s.Name);
            }
            Console.Write("Choose a student by entering their number, exit by entering anything else: ");
            try
            {
                int index = Int32.Parse(Console.ReadLine())-1;
                if (index>=0 && index < students.Count)
                {
                    Student s = students[index];
                    viewStudent(s);
                }
            }
            catch{}
        }

        static void viewStudent(Student s)
        {
            Console.Clear();
            Console.WriteLine("Name: "+s.Name);
            Console.WriteLine("ID: "+s.Id);
            Console.WriteLine("Gender: "+s.Gender);
            Console.WriteLine("Grades:");
            List<Grade> studentGrades = s.getGrades();
            int i = 1;
            foreach (Grade g in studentGrades)
            {
                Console.WriteLine(i+++": "+g.ToString());
            }
            Console.WriteLine("GPA: " + s.calculateGPA());
            Console.Write("Choose a course to edit by entering its number, exit by entering anything else: ");
            try
            {
                int index = Int32.Parse(Console.ReadLine()) - 1;
                if (index >= 0 && index < studentGrades.Count)
                {
                    Grade g = s.getGrades()[index];
                    editGrade(g);
                }
            }
            catch { }
        }

        static void editGrade(Grade g)
        {
            Console.WriteLine("Course name: " + g.CourseName);
            Console.WriteLine("1- Exercise Score: " + g.ExerciseScore);
            Console.WriteLine("2- Exam Score: " + g.ExamScore);
            Console.WriteLine("3- Finals Score: " + g.FinalsScore);
            Console.WriteLine("4- Final Grade: " + g.FinalGrade);
            Console.Write("Choose a grade to edit by entering its number, exit by entering anything else: ");
            try
            {
                int index = Int32.Parse(Console.ReadLine()) - 1;
                if (index >= 0 && index < 4)
                {
                    double score;
                    switch (index)
                    {
                        case 0:
                            Console.Write("Please enter the new Exercise Score: ");
                            score = Double.Parse(Console.ReadLine());
                            g.ExerciseScore = score;
                            break;
                        case 1:
                            Console.Write("Please enter the new Exam Score: ");
                            score = Double.Parse(Console.ReadLine());
                            g.ExamScore = score;
                            break;
                        case 2:
                            Console.Write("Please enter the new Finals Score: ");
                            score = Double.Parse(Console.ReadLine());
                            g.FinalsScore = score;
                            break;
                        case 3:
                            Console.Write("Please enter the new Final Grade: ");
                            score = Double.Parse(Console.ReadLine());
                            g.FinalGrade = score;
                            break;
                        default:
                            break;
                    }
                }
            }
            catch { }
        }

        static void addGrade()
        {
            Console.Clear();
            Console.Write("Enter course name: ");
            string course = Console.ReadLine();
            gradebooks.Add(course);
            foreach (Student cur in students)
            {
                cur.addGrade(new Grade(course));
            }
        }

        static void showCourses()
        {
            Console.Clear();
            foreach (string grade in gradebooks)
            {
                Console.WriteLine("Course name: "+grade);
                Console.WriteLine("Average grade: "+calculateAverage(grade));
                Console.WriteLine("Top students: ");
                string[] top = getTopStudentsFor(grade);
                foreach (string name in top)
                {
                    Console.WriteLine(name);
                }
                Console.WriteLine();
            }
            Console.Write("Press enter to continue...");
            Console.ReadLine();
        }

        static string[] getTopStudentsFor(string grade)
        {
            for (int i = 0; i < students.Count; i++)
            {
                for (int j = 0; j < students.Count-i-1; j++)
                {
                    double leftGrade = students[i].getFinalGradeFor(grade);
                    double rightGrade = students[j].getFinalGradeFor(grade);

                    if (leftGrade < rightGrade)
                    {
                        Student temp = students[i];
                        students[i] = students[j];
                        students[j] = temp;
                    }
                }
            }
            string[] top = new string[5];
            for (int i = 0; i < 5; i++)
            {
                top[i] = students[i].Name;
            }
            return top;
        }

        static double calculateAverage(string grade)
        {
            double average = 0;
            double total = 0;
            foreach (Student cur in students)
            {
                double curGrade = cur.getFinalGradeFor(grade);
                if (curGrade != -1)
                {
                    total++;
                    average += curGrade;
                }
            }
            return average/total;
        }

        static void readFromFile()
        {
            students = FileIO.readFromFile();
            List<Grade> grades = students[0].getGrades();
            foreach (Grade grade in grades)
            {
                gradebooks.Add(grade.CourseName);
            }
        }

        static void writeToFile()
        {
            FileIO.writeToFile(students, gradebooks);
        }
    }
}
