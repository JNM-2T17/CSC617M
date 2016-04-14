#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;

class Grade
{
	public:
		string courseName;
		float exercise;
		float exam;
		float finals;
		float finalGrade;
};

class Student
{
	public:
		string studentName;
		int studentID;
		string gender;
		vector<Grade *> grades;
		
	Grade getGrade(string courseName)
	{
		int size = grades.size();
		for(int i = 0; i < size; i++)
			if(grades[i]->courseName.compare(courseName) == 0)
			return *grades[i];
	}
		
	void removeGrade(string courseName)
	{
		int size = grades.size();
		for(int i = 0; i < size; i++)
			if(grades[i]->courseName.compare(courseName) == 0)
			{
				grades.erase(grades.begin() + i);
				break;
			}
	}
	
	Grade *getGradePtr(string courseName)
	{
		int size = grades.size();
		for(int i = 0; i < size; i++)
			if(grades[i]->courseName.compare(courseName) == 0)
			return grades[i];
	}
	
	void printName()
	{
		cout << studentName;
	}
};

class Gradebook
{
	public:
		string courseName;
		vector<Student *> *students;
};

void printGrade(Grade grade)
{
	cout << "\nExercise: " << grade.exercise
		<< "\nExam: " << grade.exam << "\nFinals: " << grade.finals
		<< "\nFinal Grade: " << grade.finalGrade << "\n\n";
}
		
void printStudent(Student student)
{
	cout << "Student Name: " << student.studentName << "\nStudent ID: " << student.studentID
		<< "\nGender: " << student.gender << "\n";
}

void printGradebook(Gradebook gradebook)
{
	cout << "Course: " << gradebook.courseName  << "\n";
	int size = gradebook.students->size();
	for(int i = 0; i < size; i++)
	{
		printStudent(*(*gradebook.students)[i]);
		printGrade((*(*gradebook.students)[i]).getGrade(gradebook.courseName));
	}
}

int studentCount;
int courseCount;
vector<Student *> students;
vector<Gradebook *> gradebooks;
vector<string> courseNames;

string sDump;

void readFromFile()
{
	ifstream inf("Gradebook.txt");
	
	inf >> studentCount;
	inf >> courseCount;
	
	getline(inf, sDump);
	
	for(int j = 0; j < courseCount; j++)
	{
		string courseName;
		getline(inf, courseName);
		courseNames.push_back(courseName);
	}
	
	for(int i = 0; i < studentCount; i++)
	{
		Student *student = new Student;
		
		getline(inf, student->studentName);
		inf >> student->studentID;
		getline(inf, sDump);
		getline(inf, student->gender);
		
		vector<Grade *> grades;
		
		for(int j = 0; j < courseCount; j++)
		{
			Grade *grade = new Grade;
			grade->courseName = courseNames[j];
			inf >> grade->exercise;
			inf >> grade->exam;
			inf >> grade->finals;
			inf >> grade->finalGrade;
			grades.push_back(grade);
		}
		student->grades = grades;
		students.push_back(student);
		getline(inf, sDump);
	}
	
	for(int i = 0; i < courseCount; i++)
	{
		Gradebook *gradebook = new Gradebook;
		gradebook->courseName = courseNames[i];
		gradebook->students = &students;
		gradebooks.push_back(gradebook);
	}
}

void updateStudent(Student *student, int choice)
{
	system("cls");
	cout << "Current student info:\n";
	printStudent(*student);
	switch(choice)
	{
		case 1:
			cout << "\nEnter new name: ";
			cin >> student->studentName;
			cout << "Student info has been updated! Student name is now " << student->studentName << "\n";
			break;
		case 2:
			cout << "\nEnter student ID: ";
			cin >> student->studentID;
			cout << "Student info has been updated! Student ID is now " << student->studentID << "\n";
			break;
		case 3:
			cout << "\nEnter gender: ";
			cin >> student->gender;
			cout << "Student info has been updated! Gender is now " << student->gender << "\n";
			break;
	}
}

void forStudents()
{
	int choice;
	int studentSize, studentChoice;
	do
	{
		system("cls");
		studentSize = students.size();
		cout << "Students operations\n";
		cout << "[1] Add student\n";
		cout << "[2] View student info\n";
		cout << "[3] Update student info\n";
		cout << "[4] Remove student\n";
		cout << "Enter 0 to go back to main menu.\n\n";
		cout << "Choice: ";
		cin >> choice;
		switch(choice)
		{
			case 1:
				{
					system("cls");
					Student *student = new Student;
					cout << "Enter student name: ";
					cin >> student->studentName;
					cout << "Enter student ID: ";
					cin >> student->studentID;
					cout << "Enter gender: ";
					cin >> student->gender;
					int gradebookSize = gradebooks.size();
					for(int i = 0; i < gradebookSize; i++)
					{
						Grade *grade = new Grade;
						grade->courseName = gradebooks[i]->courseName;
						cout << "\nFor " << gradebooks[i]->courseName << "\n";
						cout << "Enter exercise score: ";
						cin >> grade->exercise;
						cout << "Enter exam score: ";
						cin >> grade->exam;
						cout << "Enter finals score: ";
						cin >> grade->finals;
						cout << "Enter final grade: ";
						cin >> grade->finalGrade;
						student->grades.push_back(grade);
					}
					cout << "\n" << student->studentName << " has been added!\n";
					students.push_back(student);
					system("pause");
				}
				break;
			case 2:
				{
					system("cls");
					cout << "View which student?\n";
					for(int i = 0; i < studentSize; i++)
						cout << "[" << i + 1 << "]" + students[i]->studentName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> studentChoice;
					if(studentChoice != 0)
					{
						system("cls");
						printStudent(*students[studentChoice - 1]);
						system("pause");
					}
				}
				break;
			case 3:
				{
					system("cls");
					cout << "Update which student?\n";
					for(int i = 0; i < studentSize; i++)
						cout << "[" << i + 1 << "]" + students[i]->studentName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> studentChoice;
					if(studentChoice != 0)
					{
						int updateChoice;
						do
						{
							system("cls");
							printStudent(*students[studentChoice - 1]);
							cout << "Choose what to update:\n";
							cout << "[1] Update name\n";
							cout << "[2] Update ID\n";
							cout << "[3] Update gender\n";
							cout << "Enter 0 to go back to students operations menu.\n\nChoice: ";
							cin >> updateChoice;
							if(updateChoice != 0)
							updateStudent(students[studentChoice - 1], updateChoice);
						} while(updateChoice < 0 || updateChoice > 3);
						system("pause");
					}
				}
				break;
			case 4:
				{
					system("cls");
					cout << "Remove which student?\n";
					for(int i = 0; i < studentSize; i++)
						cout << "[" << i + 1 << "]" + students[i]->studentName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> studentChoice;
					if(studentChoice != 0)
					{
						cout << students[studentChoice - 1]->studentName << " has been removed.\n";
						students.erase(students.begin() + (studentChoice - 1));
						system("pause");
					}
				}
				break;
			case 0: break;
			default:
				cout << "Choose a valid option.\n\n";
				system("pause");
				break;
		}
	} while(choice != 0 || choice < 0 || choice > 4);
}

void updateGrade(string studentName, Grade *grade, int choice)
{
	system("cls");
	cout << studentName << "'s current grade info:\n";
	printGrade(*grade);
	switch(choice)
	{
		case 1:
			cout << "\nEnter new score for exercise: ";
			cin >> grade->exercise;
			cout << "Grade info has been updated! Exercise is now " << grade->exercise << "\n";
			break;
		case 2:
			cout << "\nEnter new score for exam: ";
			cin >> grade->exam;
			cout << "Grade info has been updated! Exam is now " << grade->exam << "\n";
			break;
		case 3:
			cout << "\nEnter new score for finals: ";
			cin >> grade->finals;
			cout << "Grade info has been updated! Finals is now " << grade->finals << "\n";
			break;
		case 4:
			cout << "\nEnter new final grade: ";
			cin >> grade->finalGrade;
			cout << "Grade info has been updated! Final grade is now " << grade->finalGrade << "\n";
			break;
	}
}

void forGradebooks()
{
	int choice;
	int gradebookSize, gradebookChoice;
	do
	{
		system("cls");
		gradebookSize = gradebooks.size();
		cout << "Gradebooks operations\n";
		cout << "[1] Add gradebook\n";
		cout << "[2] View gradebook info\n";
		cout << "[3] Update gradebook info\n";
		cout << "[4] Remove gradebook\n";
		cout << "Enter 0 to go back to main menu.\n\n";
		cout << "Choice: ";
		cin >> choice;
		switch(choice)
		{
			case 1:
				{
					system("cls");
					Gradebook *gradebook = new Gradebook;
					cout << "Enter course name: ";
					cin >> gradebook->courseName;
					cout << "Enter scores for each student:\n";
					gradebook->students = &students;
					int studentSize = students.size();
					for(int i = 0; i < studentSize; i++)
					{
						cout << "For " << students[i]->studentName << ":\n";
						Grade *grade = new Grade;
						grade->courseName = gradebook->courseName;
						cout << "Enter exercise score: ";
						cin >> grade->exercise;
						cout << "Enter exam score: ";
						cin >> grade->exam;
						cout << "Enter finals score: ";
						cin >> grade->finals;
						cout << "Enter final grade: ";
						cin >> grade->finalGrade;
						students[i]->grades.push_back(grade);	
					}
					cout << "\n" << gradebook->courseName << " has been added!\n";
					gradebooks.push_back(gradebook);
					system("pause");
				}
				break;
			case 2:
				{
					system("cls");
					cout << "View which gradebook?\n";
					for(int i = 0; i < gradebookSize; i++)
						cout << "[" << i + 1 << "]" + gradebooks[i]->courseName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> gradebookChoice;
					if(gradebookChoice != 0)
					{
						system("cls");
						printGradebook(*gradebooks[gradebookChoice - 1]);
						system("pause");
					}
				}
				break;
			case 3:
				system("cls");
				{
					system("cls");
					cout << "Update which gradebook?\n";
					for(int i = 0; i < gradebookSize; i++)
						cout << "[" << i + 1 << "]" + gradebooks[i]->courseName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> gradebookChoice;
					if(gradebookChoice != 0)
					{
						system("cls");
						cout << "Update which student's grades?\n";
						int choice = gradebookChoice - 1;
						vector<Student *> tempStudents = *(gradebooks[choice]->students);
						int studentSize = tempStudents.size();
						for(int i = 0; i < studentSize; i++)
							cout << "[" << i + 1 << "]" + tempStudents[i]->studentName + "\n";
						cout << "Enter 0 to go back\n\nChoice: ";
						int studentChoice;
						cin >> studentChoice;
						if(studentChoice != 0)
						{
							system("cls");
							printStudent(*students[studentChoice - 1]);
							Grade grade = tempStudents[studentChoice - 1]->getGrade(gradebooks[choice]->courseName);
							cout << "[1] Exercise: " << grade.exercise << "\n";
							cout << "[2] Exam: " << grade.exam << "\n";
							cout << "[3] Finals: " << grade.finals << "\n";
							cout << "[4] Final Grade: " << grade.finalGrade << "\n";
							cout << "Update which component?\n";
							cout << "Enter 0 to go back\n\nChoice: ";
							int componentChoice;
							cin >> componentChoice;
							updateGrade(tempStudents[studentChoice - 1]->studentName, tempStudents[studentChoice - 1]->getGradePtr(gradebooks[choice]->courseName), componentChoice);
						}
						system("pause");
					}
				}
				break;
			case 4:
				{
					system("cls");
					cout << "Remove which gradebook?\n";
					for(int i = 0; i < gradebookSize; i++)
						cout << "[" << i + 1 << "]" + gradebooks[i]->courseName + "\n";
					cout << "Enter 0 to go back\n\nChoice: ";
					cin >> gradebookChoice;
					if(gradebookChoice != 0)
					{
						cout << gradebooks[gradebookChoice - 1]->courseName << " has been removed.\n";
						vector<Student *> tempStudents = *(gradebooks[gradebookChoice - 1]->students);
						int studentSize = tempStudents.size();
						for(int i = 0; i < studentSize; i++)
							tempStudents[i]->removeGrade(gradebooks[gradebookChoice - 1]->courseName);
						gradebooks.erase(gradebooks.begin() + (gradebookChoice - 1));
						system("pause");
					}
				}
				break;
				break;
			case 0: break;
			default:
				cout << "Choose a valid option.\n\n";
				system("pause");
				break;
		}
	} while(choice != 0 || choice < 0 || choice > 4);
}

void showTop5(Gradebook gradebook)
{
	vector<Student *> students = *(gradebook.students);
	int studentSize = students.size();
	string studentNames[studentSize];
	float finalGrade[studentSize];
	for(int i = 0; i < studentSize; i++)
	{
		studentNames[i] = students[i]->studentName;
		finalGrade[i] = students[i]->getGrade(gradebook.courseName).finalGrade;
	}
	for(int i = 0; i < studentSize - 1; i++)
	{
		string tempString;
		float tempFinalGrade;
		for(int j = 0; j < studentSize - i - 1; j++)
		{
			if(finalGrade[j] < finalGrade[j + 1])
			{
				tempString = studentNames[j];
				studentNames[j] = studentNames[j + 1];
				studentNames[j + 1] = tempString;
				
				tempFinalGrade = finalGrade[j];
				finalGrade[j] = finalGrade[j + 1];
				finalGrade[j + 1] = tempFinalGrade;
			}
		}
	}
	
	for(int i = 0; i < studentSize; i++)
		cout << "[" << i + 1 << "] " << studentNames[i] << + " - " << finalGrade[i] << "\n"; 
	
	cout << "\n";
}

void saveFile()
{
	ofstream off("Gradebook2.txt");
	
	int studentSize = students.size();
	int gradebookSize = gradebooks.size();
	off << studentSize << "\n";
	off << gradebookSize << "\n";
	for(int i = 0; i < gradebookSize; i++)
		off << gradebooks[i]->courseName << "\n";
	for(int i = 0; i < studentSize; i++)
	{
		off << students[i]->studentName << "\n";
		off << students[i]->studentID << "\n";
		off << students[i]->gender << "\n";
		vector<Grade *> grades = students[i]->grades;
		int gradeSize = grades.size();
		for(int j = 0; j < gradeSize; j++)
		{
			off << grades[j]->exercise << "\n";
			off << grades[j]->exam << "\n";
			off << grades[j]->finals << "\n";
			off << grades[j]->finalGrade;
			if(j != gradeSize - 1)
				off << "\n";
		}
	}
	off.close();
}

void mainMenu()
{
	int choice;
	do
	{
		system("cls");
		cout << "GRADEBOOK\n";
		cout << "[1] Students operations\n";
		cout << "[2] Gradebooks operations\n";
		cout << "[3] View GPA per student\n";
		cout << "[4] View Average Grade per course\n";
		cout << "[5] View Top 5 students per course\n";
		cout << "[6] Save\n";
		cout << "Enter 0 to exit.\n\nChoice: ";
		cin >> choice;
		switch(choice)
		{
			case 1:
				system("cls");
				forStudents();
				break;
			case 2:
				forGradebooks();
				break;
			case 3:
				system("cls");
				{
					int size = students.size();
					cout << "GPA of each student:\n";
					for(int i = 0; i < size; i++)
					{
						float sum = 0.0;
						vector<Grade *> grades = students[i]->grades;
						int gradeSize = grades.size();
						for(int j = 0; j < gradeSize; j++)
							sum += grades[j]->finalGrade;
						cout << students[i]->studentName + ": " << sum / gradeSize << "\n";
					}
				}
				system("pause");
				break;
			case 4:
				system("cls");
				{
					int size = gradebooks.size();
					cout << "Average grade in each course:\n";
					for(int i = 0; i < size; i++)
					{
						float sum = 0.0;
						vector<Student *> students = *(gradebooks[i]->students);
						int studentSize = students.size();
						for(int j = 0; j < studentSize; j++)
							sum += students[j]->getGrade(gradebooks[i]->courseName).finalGrade;
						cout << gradebooks[i]->courseName + ": " << sum / studentSize << "\n";
					}
				}
				system("pause");
				break;
			case 5:
				system("cls");
				{
					int size = gradebooks.size();
					cout << "Top 5 per course:\n";
					for(int i = 0; i < size; i++)
					{
						cout << gradebooks[i]->courseName << ":\n";
						showTop5(*gradebooks[i]);
					}
				}
				system("pause");
				break;
			case 6:
				saveFile();
				cout << "Grades saved!\n";
				system("pause");
				break;
			case 0: break;
			default:
				cout << "Choose a valid option.\n\n";
				system("pause");
				break;
		}
	} while(choice != 0 || choice < 0 || choice > 6);
}
int main()
{
	readFromFile();
	mainMenu();
	return 0;
}
