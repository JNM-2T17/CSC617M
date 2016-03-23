class Grade(var course : String, var exer : Double, var exams : Double
						, var finals : Double, var fg : Double) {
	override def toString : String  = {
		return exer + "\n" + exams + "\n" + finals + "\n" + fg;
	}
}