import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Map;

object StudentList {
	var students = Map.empty[String,Student]
	var courses = ArrayBuffer.empty[String]

	def addStudent(name : String,id : String,gender : String) {
		var student = new Student(name,id,gender)
		for( x <- courses) {
			student.addGradebook(x)
		}
		students(id) = student
		// updateGUI()
	}

	def student(id : String) : Student = {
		students(id);
	}

	def updateStudent(id : String,name : String,gender: String) {
		if( students contains id ) {
			var student = students(id)
			student.name = name
			student.gender = gender
			// updateGUI();
		}
	}

	def deleteStudent(id : String) {
		students -= id;
		// updateGUI();
	}

	def size: Int = students.size

	def avg : Map[String,Double] = {
		var avgs = Map.empty[String,Double]
		for(name <- courses) {
			var avg = 0.0
			var count = 0.0

			for( s <- students) {
				avg += s._2.grade(name)
				count += 1
			}
			avg /= count;
			avgs += (name -> avg)
		}
		return avgs;
	}

	def addCourse(name : String) {
		courses += name
		for( x <- students ) {
			x._2.addGradebook(name)
		}
		// updateGUI();
	}

	def course(index : Int) : String = courses(index)

	def courseSize : Int = courses.size

	def deleteCourse(name : String) {
		courses -= name
		for( x <- students ) {
			x._2.deleteGradebook(name);
		}	
		// updateGUI();
	}

	def getTop(course : String) : ArrayBuffer[Tuple2[String,Double]]  = {
		var top = ArrayBuffer.empty[Tuple2[String,Double]]
		for( s <- students ) {
			top += new Tuple2(s._2.name,s._2.grade(course))
		}

		for(i <- top.length - 1 until 0; j <- 0 until i) {
			if( top(j)._2 < top(j + 1)._2) {
				var temp = top(j)
				top(j) = top(j + 1)
				top(j + 1) = temp
			}
		}

		top
	}

	override def toString : String = {
		var str = size + "\n" + courseSize + "\n"
		for( x <- courses) {
			str += x + "\n"
		}
		for( s <- students ) {
			str += s._2.toString()
		}
		return str.substring(0,str.length() - 1)
	}
}