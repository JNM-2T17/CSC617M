import scala.swing._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

class Frame extends MainFrame {
	title = "Gradebook"
	contents = new BoxPanel(Orientation.Vertical) {
	    contents += new Label("Look at me!")
	    contents += Button("Press me, please") { println("Thank you") }
	    contents += Button("Close") { sys.exit(0) }
	    border = Swing.EmptyBorder(10,10,10,10)
  	}
}

object Gradebook {
	def main(args: Array[String]) {
		val ui = new Frame
		ui.visible = true
		loadFile
		//read students
	}

	def loadFile {
		val in = Source.fromFile("gradebook.txt")
		var lines = ArrayBuffer.empty[String]

		for(x <- in.getLines()) {
			lines += x
		}

		in.close();

		var i = 0;
		var studentCount = lines(i).toInt
		i = i + 1
		var courseCount = lines(i).toInt
		i = i + 1
		for(j <- 1 to courseCount) {
			StudentList.addCourse(lines(i))
			i += 1
		}
		for( j <- 1 to studentCount) {
			StudentList.addStudent(lines(i),lines(i + 1),lines(i + 2))
			var id = lines(i + 1)
			i += 3
			for( k <- 0 until courseCount ) {
				var name = StudentList.course(k)
				var exer = lines(i).toDouble
				i += 1
				var exams = lines(i).toDouble
				i += 1
				var finals = lines(i).toDouble
				i += 1
				var fg = lines(i).toDouble
				i += 1
				StudentList.student(id).updateGradebook(name,exer,exams,finals,fg)
			}
		}
		println(StudentList)
	}
}