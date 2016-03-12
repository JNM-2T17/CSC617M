class GBController {
	FileLoader.loadFile
	val gbframe = new GBFrame
	val mPanel = new MainPanel(this)

	gbframe.setMain(mPanel)

	def switch(to : Int) {
		to match {
			case GBController.Student => gbframe.setMain(mPanel)
							println("Student")
			case GBController.Course => gbframe.setMain(mPanel)
							println("Course")
			case GBController.Main => gbframe.setMain(mPanel)
		}
	}

	def save { 
		FileLoader.writeFile 
	}
}

object GBController {
	val Student : Int = 0
	val Course : Int = 1
	val Main : Int = 2
}