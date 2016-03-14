import scala.swing._

class GBFrame extends MainFrame {
	title = "Gradebook"
	setSize
	visible = true

	def setMain(panel : Panel) {
		contents = panel
		centerOnScreen
		repaint
	}

	def setSize(width: Int, height: Int) {
		preferredSize = new Dimension(width,height)
	}

	def setSize {
		preferredSize = new Dimension(350,400)
	}
}