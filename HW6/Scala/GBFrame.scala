import scala.swing._

class GBFrame extends MainFrame {
	title = "Gradebook"
	preferredSize = new Dimension(350,400)
	visible = true

	def setMain(panel : Panel) {
		contents = panel
		centerOnScreen
		repaint
	}
}