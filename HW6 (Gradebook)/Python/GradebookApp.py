import os

file = "Gradebook.txt"
courses = []
students = []
grades = []

# Classes- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

class Course:
	def __init__(self, name):
		self.name = name

	def getCourseAvg(self):
		studCount = 0
		total = 0

		for grade in grades:
			if grade.course.name == self.name:
				total += grade.finalgrade
				studCount += 1
				if studCount == len(students):
					break

		return total/len(students)

	def getTop(self, num):
		return sorted([{ 'student' : grade.student, 'grade' : grade.finalgrade } for grade in grades if grade.course == self],
					  key = lambda grade: grade['grade'],
					  reverse = True)[:num]

		# if num > len(students):
		# 	num = len(students)

		# topList = []

		# for i in range(num):
		# 	topList.append({'student': None, 'grade': 0.0})
		# studCount = 0

		# for grade in grades:
		# 	if grade.course.name == self.name:
		# 		for i in range(num):
		# 			if grade.finalgrade >= topList[i]['grade']:
		# 				newG = grade.finalgrade
		# 				newS = grade.student

		# 				for j in range(i, num):
		# 					tempG = topList[j]['grade']
		# 					topList[j]['grade'] = newG
		# 					newG = tempG

		# 					tempS = topList[j]['student']
		# 					topList[j]['student'] = newS
		# 					newS = tempS
						
		# 				break

		# 		studCount += 1

		# 		if studCount == len(students):
		# 			break

		# return topList

class Student:
	def __init__(self, name, id, gender):
		self.name   = name
		self.id     = id
		self.gender = gender

	def getGPA(self):
		gradeCount = 0
		total = 0

		for grade in grades:
			if grade.student.id == self.id:
				total += grade.finalgrade
				gradeCount += 1
				if gradeCount == len(courses):
					break

		return total/len(courses)

class Gradebook:
	def __init__(self, course, student, exercises, exams, finals, finalgrade):
		self.course     = course
		self.student    = student
		self.exercises  = exercises
		self.exams      = exams
		self.finals     = finals
		self.finalgrade = finalgrade

# Reading- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def readline2(file):
	line = file.readline()
	if line.endswith('\n'):
		line = line[:-1]
	return line

def readGradesFile(filename):
	f = open(filename, "r")

	numStudents = int(readline2(f))
	numCourses  = int(readline2(f))

	for i in range(numCourses):
		courses.append(Course(readline2(f)))

	for i in range(numStudents):
		students.append(Student(readline2(f),
								readline2(f),
								readline2(f)))

		for course in courses:
			grades.append(Gradebook(course,
									students[len(students) - 1],
									float(readline2(f)),
									float(readline2(f)),
									float(readline2(f)),
									float(readline2(f))))

	f.close()

# Writing- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def writeGradesFile(filename):
	f = open(filename, "w")
	f.truncate()

	f.write(str(len(students)) + "\n")
	f.write(str(len(courses)) + "\n")

	for course in courses:
		f.write(course.name + "\n")

	for student in students:
		f.write(student.name + "\n")
		f.write(student.id + "\n")
		f.write(student.gender + "\n")

		for course in courses:
			for grade in grades:
				if grade.student.id == student.id and grade.course.name == course.name:
					f.write(str(grade.exercises) + "\n")
					f.write(str(grade.exams) + "\n")
					f.write(str(grade.finals) + "\n")
					f.write(str(grade.finalgrade) + "\n")
					break

	f.close()

# CRUD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def addCourse(course):
	courses.append(course)
	for s in students:
		grades.append(Gradebook(course, s, 60, 60, 60, 1.0))

def addStudent(student):
	students.append(student)
	for c in courses:
		grades.append(Gradebook(c, student, 60, 60, 60, 1.0))

def updateCourse(index, newCourse):
	if index < len(courses) and index >= 0:
		courses[index].name = newCourse.name

def updateStudent(index, newStudent):
	if index < len(students) and index >= 0:
		students[index].name   = newStudent.name
		students[index].id     = newStudent.id
		students[index].gender = newStudent.gender

def updateGradebook(index, newGradebook):
	if index < len(grades) and index >= 0:
		grades[index].exercises  = newGradebook.exercises
		grades[index].exams      = newGradebook.exams
		grades[index].finals     = newGradebook.finals
		grades[index].finalgrade = newGradebook.finalgrade

def deleteCourse(index):
	if index < len(courses) and index >= 0:
		courseName = courses[index].name
		count = 0
		i = 0
		while(i < range(len(grades))):
			if grades[i].course.name == courseName:
				print "delete grades"
				grades.remove(grades[i])
				count += 1
				i -= 1
				if count == len(courses):
					break
			i += 1
		del courses[index]

def deleteStudent(index):
	if index < len(students) and index >= 0:
		studId = students[index].id
		count = 0
		i = 0
		while(i < range(len(grades))):
			if grades[i].student.id == studId:
				print "delete grades"
				grades.remove(grades[i])
				count += 1
				i -= 1
				if count == len(courses):
					break
			i += 1
		del students[index]

# Display- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def displayCourses(numbered):
	print "Courses:"
	i = 1
	for c in courses:
		if numbered == True:
			string = "[" + str(i) + "] "
			i += 1
		else:
			string = ""
		string += c.name + ": Average Grade = " + str(c.getCourseAvg())
		print string

def displayTopStudents(c):
	print "Top Students in " + c.name + "\n"
	topGrades = c.getTop(5)
	for top in topGrades:
		print top['student'].name + " (" + str(top['student'].id) + "): " + str(top['grade'])

def displayStudents(numbered):
	print "Students:"
	i = 1
	for s in students:
		if numbered == True:
			string = "[" + str(i) + "] "
			i += 1
		else:
			string = ""
		string += s.name + ", " + s.id + ", " + s.gender + ": GPA = " + str(s.getGPA())
		print string

def displayGrades(numbered):
	count = 1
	for g in grades:
		if numbered == True:
			string = "[" + str(count) + "] "
			count += 1
		else:
			string = ""
		string += g.course.name + ", " + g.student.name
		if numbered == False:
			string += "\n\tExercises: " + str(g.exercises) + "\n\tExams: " + str(g.exams) + "\n\tFinals: " + str(g.finals) + "\n\tFinal Grade: " + str(g.finalgrade)
		print string

# "Pages"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def pageMain(invalid):
	os.system("clear")
	print "[1] Courses"
	print "[2] Students"
	print "[3] Grades"
	print "[4] EXIT"

	if invalid == True:
		print "Invalid input"

	choice = input('Which do you wish to access? ')

	if choice == 1:
		pageCourses(False)

	elif choice == 2:
		pageStudents(False)

	elif choice == 3:
		pageGrades(False)

	elif choice == 4:
		os.system("clear")
		print "Goodbye!"

	else:
		pageMain(True)

def pageCourses(invalid):
	os.system("clear")
	displayCourses(False)

	writeGradesFile(file)

	print "\n[1] Add"
	print "[2] Edit"
	print "[3] Delete"
	print "[4] View Top Students"
	print "[5] Go Back"

	if invalid == True:
		print "Invalid input"

	choice = raw_input('What do you want to do? ')

	if choice == "1":
		pageAddCourse(False)

	elif choice == "2":
		pageEditCourse(False)

	elif choice == "3":
		pageDeleteCourse(False)

	elif choice == "4":
		pageTopOfCourses(False)

	elif choice == "5":
		pageMain(False)

	else:
		pageCourses(True)

def pageAddCourse(invalid):
	os.system("clear")

	if invalid == True:
		print "Invalid input"

	name = raw_input('Name: ')

	if(name != ""):
		addCourse(Course(name))
		pageCourses(False)

	else:
		pageAddCourse(True)

def pageEditCourse(invalid):
	os.system("clear")
	displayCourses(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which course do you want to edit? ')

	if choice.isdigit() and int(choice) <= len(courses):
		pageEditCourseDetails(int(choice) - 1, False)

	else:
		pageEditCourse(True)

def pageEditCourseDetails(index, invalid):
	os.system("clear")

	if invalid == True:
		print "Invalid input"

	print "Old Name: " + courses[index].name
	name = raw_input('New Name: ')

	if(name != ""):
		updateCourse(index, Course(name))
		pageCourses(False)

	else:
		pageEditCourseDetails(True)

def pageDeleteCourse(invalid):
	os.system("clear")
	displayCourses(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which course do you want to delete? ')

	if choice.isdigit() and int(choice) <= len(courses):
		deleteCourse(int(choice) - 1)
		pageCourses(False)

	else:
		pageDeleteCourse(True)

def pageTopOfCourses(invalid):
	os.system("clear")
	displayCourses(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which course do you want to view the top students of? ')

	if choice.isdigit() and int(choice) <= len(courses):
		pageTopOfCourse(int(choice) - 1, False)

	else:
		pageTopOfCourses(True)

def pageTopOfCourse(index, invalid):
	os.system("clear")
	displayTopStudents(courses[index])

	print "\n[1] View Top Students of Another Course"
	print "[2] Go Back to Courses"

	if invalid == True:
		print "Invalid input"

	choice = raw_input('What do you want to do? ')

	if choice == "1":
		pageTopOfCourses(False)

	elif choice == "2":
		pageCourses(False)

	else:
		pageTopOfCourse(index, True)

def pageStudents(invalid):
	os.system("clear")
	displayStudents(False)

	writeGradesFile(file)

	print "\n[1] Add"
	print "[2] Edit"
	print "[3] Delete"
	print "[4] Go Back"

	if invalid == True:
		print "Invalid input"

	choice = raw_input('What do you want to do? ')

	if choice == "1":
		pageAddStudent(False)

	elif choice == "2":
		pageEditStudent(False)

	elif choice == "3":
		pageDeleteStudent(False)

	elif choice == "4":
		pageMain(False)

	else:
		pageCourses(True)

def pageAddStudent(invalid):
	os.system("clear")

	if invalid == True:
		print "Invalid input"

	name   = raw_input('Name: ')
	studId = raw_input('ID Number: ')
	gender = raw_input('Gender (M/F): ')

	if name != "" and studId != "" and studId.isdigit() and (gender == "M" or gender == "F"):
		addStudent(Student(name, studId, gender))
		pageStudents(False)

	else:
		pageAddStudent(True)

def pageEditStudent(invalid):
	os.system("clear")
	displayStudents(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which student do you want to edit? ')

	if choice.isdigit() and int(choice) <= len(students):
		pageEditStudentDetails(int(choice) - 1, False)

	else:
		pageEditStudent(True)

def pageEditStudentDetails(index, invalid):
	os.system("clear")

	if invalid == True:
		print "Invalid input"

	print "Old Name: " + students[index].name
	name   = raw_input('New Name: ')
	print "Old ID Number: " + students[index].id
	studId = raw_input('New ID Number: ')
	print "Old Gender: " + students[index].gender
	gender = raw_input('New Gender (M/F): ')

	if name != "" and studId != "" and studId.isdigit() and (gender == "M" or gender == "F"):
		updateStudent(index, Student(name, studId, gender))
		pageStudents(False)

	else:
		pageEditStudentDetails(True)

def pageDeleteStudent(invalid):
	os.system("clear")
	displayStudents(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which student do you want to delete? ')

	if choice.isdigit() and int(choice) <= len(students):
		deleteStudent(int(choice) - 1)
		pageStudents(False)

	else:
		pageDeleteStudent(True)

def pageGrades(invalid):
	os.system("clear")
	displayGrades(False)

	writeGradesFile(file)

	print "\n[1] Edit"
	print "[2] Go Back"

	if invalid == True:
		print "Invalid input"

	choice = raw_input('What do you want to do? ')

	if choice == "1":
		pageEditGrades(False)

	elif choice == "2":
		pageMain(False)

	else:
		pageGrades(True)

def pageEditGrades(invalid):
	os.system("clear")
	displayGrades(True)

	if invalid == True:
		print "Invalid input"

	choice = raw_input('Which gradebook do you want to edit? ')

	if choice.isdigit() and int(choice) <= len(grades):
		pageEditGradeDetails(int(choice) - 1, False)

	else:
		pageEditGrades(True)

def pageEditGradeDetails(index, invalid):
	os.system("clear")

	if invalid == True:
		print "Invalid input"

	print "Course: " + grades[index].course.name
	print "Student: " + grades[index].student.name
	print "Old Exercises Grade: " + str(grades[index].exercises)
	exercises  = raw_input('New Exercises Grade: ')
	print "Old Exams Grade: " + str(grades[index].exams)
	exams      = raw_input('New Exams Grade: ')
	print "Old Final Exam Grade: " + str(grades[index].finals)
	finals     = raw_input('New Final Exam Grade: ')
	print "Old Final Grade: " + str(grades[index].finalgrade)
	finalgrade = raw_input('New Final Grade: ')

	if exercises != "" and exams != "" and finals != "" and finalgrade != "":
		try:
			exercises = float(exercises)
			exams = float(exams)
			finals = float(finals)
			finalgrade = float(finalgrade)

			if exercises >= 0 and exercises <= 100 and exams >= 0 and exams <= 100 and finals >= 0 and finals <= 100 and finalgrade >= 0 and finalgrade <= 4:
				updateGradebook(index, Gradebook(None, None, exercises, exams, finals, finalgrade))
				pageGrades(False)

			else:
				pageEditGradeDetails(index, True)

		except ValueError: 
			pageEditGradeDetails(index, True)

	else:
		pageEditGradeDetails(index, True)

# Actual Program - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

readGradesFile(file)

pageMain(False);
