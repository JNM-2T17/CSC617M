using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangGradebooks
{
    class Student
    {
        private String name;
        private String id;
        private String gender;
        private List<Grade> grades;

        public Student(String name, String id, String gender)
        {
            this.name = name;
            this.id = id;
            this.gender = gender;
            grades = new List<Grade>();
        }

        public String Name
        {
            get { return name; }
            set { name = value; }
        }

        public String Gender
        {
            get { return gender; }
            set { gender = value; }
        }

        public String Id
        {
            get { return id; }
            set { id = value; }
        }

        public List<Grade> getGrades()
        {
            return grades;
        }

        public void addGrade(Grade g)
        {
            this.grades.Add(g);
        }

        public double calculateGPA()
        {
            double gpa = 0;
            int count = grades.Count;
            foreach(Grade g in grades) gpa += g.FinalGrade;
            gpa /= count;
            return gpa;
        }

        public double getFinalGradeFor(string grade)
        {
            foreach (Grade g in grades)
            {
                if (g.CourseName.Equals(grade))
                {
                    return g.FinalGrade;
                }
            }
            return -1;
        }
    }
}
