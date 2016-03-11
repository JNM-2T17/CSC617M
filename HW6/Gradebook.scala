import java.io.PrintWriter
import scala.swing._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Gradebook {
	def main(args: Array[String]) {
		loadFile
		writeFile
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

	def writeFile {
		val f = new PrintWriter("Gradebook.txt")
		f.print(StudentList)
		f.close()
	}
}