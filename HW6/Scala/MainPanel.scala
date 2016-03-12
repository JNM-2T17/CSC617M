import java.awt.Font
import scala.swing.{Action,Button,Dialog,GridBagPanel,Label,Swing}

class MainPanel(val control : GBController) extends AGBPanel {
	border = Swing.EmptyBorder(20,20,20,20)
	add(new Label {
			text = "Gradebook Program"
			font = new Font("Segoe UI",Font.BOLD,24)
		},con(0,0,1,1,10,10
		,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Horizontal,10,10,10,10))
	add(new Button("Courses") {
			font = new Font("Segoe UI",Font.PLAIN,14)
			action = new Action("Courses"){
				def apply() {
					control.switch(GBController.Course)
				}
			}
		},con(0,1,1,2,100,100
		,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both,10,10,5,10))
	add(new Button("Students") {
			font = new Font("Segoe UI",Font.PLAIN,14)
			action = new Action("Students"){
				def apply() {
					control.switch(GBController.Student)
				}
			}
		},con(0,3,1,2,100,100
		,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both,5,10,5,10))
	add(new Button("Save") {
			font = new Font("Segoe UI",Font.PLAIN,14)
			action = new Action("Save"){
				def apply() {
					control.save
					Dialog.showMessage(null,"Gradebooks saved",title = "Save Successful")
				}
			}
		},con(0,5,1,2,100,100
		,GridBagPanel.Anchor.Center,GridBagPanel.Fill.Both,5,10,10,10))
}