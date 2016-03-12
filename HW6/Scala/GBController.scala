import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

class GBController {
	FileLoader.loadFile
	val gbframe = new GBFrame
	val mPanel = new MainPanel(this)
	val cPanel = new CoursePanel(this)
	val rPanel = new ReportsPanel(this)

	gbframe.setMain(mPanel)

	def switch(to : Int) {
		to match {
			case GBController.Student => gbframe.setMain(mPanel)
					println("Student")
			case GBController.Course => gbframe.setMain(cPanel)
			case GBController.Main => gbframe.setMain(mPanel)
			case GBController.Reports => rPanel.updateModel
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
}

object GBController {
	val Student : Int = 0
	val Course : Int = 1
	val Main : Int = 2
	val Reports : Int = 3
}