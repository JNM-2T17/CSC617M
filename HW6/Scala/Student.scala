import scala.collection.mutable.Map

class Student(
	var name: String, 
	var id : String, 
	var gender : String) {
	var grades = Map.empty[String,Grade]

	def addGradebook(course : String) {
		grades += (course -> new Grade(course,60,60,60,1.0))
	}

	def updateGradebook(course : String, exer : Double, exams : Double
						, finals : Double, fg : Double) {
		if( grades contains course ) {
			var x = grades(course)
			x.exer = exer
			x.exams = exams
			x.finals = finals
			x.fg = fg
		}
	}

	def grade(course : String) : Double = {
		if( grades contains course ) {
			return grades(course).fg
		} else {
			return 0
		}
	}

	def deleteGradebook(course : String) {
		if( grades contains course ) {
			grades -= course
		}
	}

	def gpa : Double = {
		var gpa : Double = 0;
		var ctr : Double = 0;

		for( x <- grades) {
			gpa += x._2.fg
			ctr += 1
		}

		return gpa / ctr
	}

	def size : Int = grades.size

	override def toString : String = {
		var str = name + "\n" + id + "\n" + gender + "\n"
		for(g <- grades) {
			str += g._2.toString() + "\n"
		}
		return str
	}
}