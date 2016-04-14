using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangGradebooks
{
    class Grade
    {
        private String courseName;
        private double exerciseScore;
        private double examScore;
        private double finalsScore;
        private double finalGrade;

        public Grade(String courseName) {
            this.courseName = courseName;
        }

        public String CourseName
        {
            get { return courseName; }
            set { courseName = value; }
        }

        public double ExerciseScore{
            get { return exerciseScore; }
            set { exerciseScore = value; }
        }

        public double ExamScore
        {
            get { return examScore; }
            set { examScore = value; }
        }

        public double FinalsScore
        {
            get { return finalsScore; }
            set { finalsScore = value; }
        }

        public double FinalGrade
        {
            get { return finalGrade; }
            set { finalGrade = value; }
        }

        public String ToString()
        {
            return "Course: "+courseName
            +"\nExercises: "+exerciseScore
            +"\nExams: "+examScore
            +"\nFinals: "+finalsScore
            +"\nFinal Grade: "+finalGrade;
        }
    }
}
