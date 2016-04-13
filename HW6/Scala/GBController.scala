import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

class GBController {
	FileLoader.loadFile
	val gbframe = new GBFrame
	val mPanel = new MainPanel(this)
	val cPanel = new CoursePanel(this)
	val sPanel = new StudentPanel(this)
	val rPanel = new ReportsPanel(this)

	gbframe.setMain(mPanel)

	def switch(to : Int) {
		to match {
			case GBController.Student 	=> 	gbframe.setSize(700,600)
											sPanel.setModel
											gbframe.setMain(sPanel)
			case GBController.Course 	=> 	gbframe.setSize
											gbframe.setMain(cPanel)
			case GBController.Main 		=> 	gbframe.setSize
											gbframe.setMain(mPanel)
			case GBController.Reports 	=> 	gbframe.setSize
											rPanel.updateModel
											gbframe.setMain(rPanel)
		}
	}

	def save { 
		FileLoader.writeFile 
	}

	def getCourses : ArrayBuffer[String] = StudentList.courses

	def addCourse(course : String) {
		StudentList.addCourse(course)
		cPanel.setModel(getCourses)
	}

	def deleteCourse(course : String) {
		println("Deleting " + course)
		StudentList.deleteCourse(course)
		cPanel.setModel(getCourses)
	}

	def avg : Map[String,Double] = StudentList.avg

	def top5(course : String) : ArrayBuffer[Tuple2[String,Double]] 
		= StudentList.getTop(course)

	def getStudents : Map[String,Student] = StudentList.students

	def addStudent(name : String, id : String, gender : String ) {
		StudentList.addStudent(name,id,gender)
		sPanel.setModel
	}

	def deleteStudent(id : String) { 
		StudentList.deleteStudent(id)
		sPanel.setModel 
	}

	def updateStudent(id : String, name : String, gender : String) {
		StudentList.updateStudent(id,name,gender)
		sPanel.setModel
	}

	def updateGrade(id : String, course : String, exer : Double, exams : Double
					, finals : Double, fg : Double) {
		StudentList.student(id).updateGradebook(course,exer,exams,finals,fg)
		sPanel.setModel
	}
}

object GBController {
	val Student : Int = 0
	val Course : Int = 1
	val Main : Int = 2
	val Reports : Int = 3
}