import java.awt.Font;
import scala.collection.mutable.ArrayBuffer
import scala.swing._

class CoursePanel(val control: GBController) extends AGBPanel {
	val clPanel = new CourseListPanel(control.getCourses,control)
	add(new Label("Courses") {
			font = new Font("Segoe UI",Font.BOLD,24)
		},con(0,0,1,1,0,0,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both))
	add(new BoxPanel(Orientation.Vertical) {
			contents +=  new ScrollPane(clPanel)
		},con(0,1,1,1,1,1,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both))
	add(new BoxPanel(Orientation.Horizontal) {
			val tf = new TextField;
			contents += tf
			contents += new Button{
				font = new Font("Segoe UI",Font.PLAIN,14)
				action = new Action("Add Course") {
					def apply {
						if(tf.text.length() > 0) {
							control.addCourse(tf.text)
						} else {
							Dialog.showMessage(null,"Input a course name"
								,title="No Course")
						}
					}
				}
			}
		},con(0,2,1,1,0,0,GridBagPanel.Anchor.Center
				,GridBagPanel.Fill.Horizontal,10,10,10,10))
	add(new BorderPanel{
			add(new Button{
					font = new Font("Segoe UI",Font.PLAIN,14)
					action = new Action("Back"){
						def apply {
							control.switch(GBController.Main)
						}
					}
				},BorderPanel.Position.Center)
		},con(0,3,1,1,0,0,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both))

	def setModel(model : ArrayBuffer[String]) {
		clPanel.setModel(model)
	}
}

class CourseListPanel(var model: ArrayBuffer[String],val control: GBController) 
	extends BoxPanel(Orientation.Vertical) {
	setModel(model)

	def setModel(model:ArrayBuffer[String]) {
		this.model = model
		contents.clear
		for(x <- model) {
			contents += new BoxPanel(Orientation.Horizontal) {
				border = Swing.BeveledBorder(Swing.Lowered)
				contents += new BoxPanel(Orientation.Horizontal) {
					border = Swing.EmptyBorder(10,10,10,10)
					contents += new Label(x) {
						font = new Font("Segoe UI",Font.PLAIN,14)
					}
					contents += Swing.HStrut(5)
					contents += Swing.Glue
					contents += new Button{
						font = new Font("Segoe UI",Font.PLAIN,14)
						action = new Action("Delete") {
							def apply {
								control.deleteCourse(x)
							}
						}
					}
				}
			}
		}
		repaint
		revalidate
	}
}