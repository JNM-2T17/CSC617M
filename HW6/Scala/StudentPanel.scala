import java.awt.Font
import scala.swing._
import scala.collection.mutable.ArrayBuffer

class StudentPanel(val control : GBController) extends BorderPanel {
	add(new BoxPanel(Orientation.Vertical) {
		contents += new BorderPanel {
			add(new Label("Students") {
				font = new Font("Segoe UI",Font.BOLD,24)
			},BorderPanel.Position.Center)
			add(new Button {
				action = new Action("Back") {
					def apply {
						control.switch(GBController.Main)
					}
				}
			},BorderPanel.Position.East)
		}
		contents += new BoxPanel(Orientation.Horizontal) {
			contents += new Label("Name: ") {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			val nameField = new TextField {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			contents += nameField
			contents += new Label("ID: ") {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			val idField = new TextField {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			contents += idField
			contents += new Label("Gender: ") {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			val genderField = new TextField {
				font = new Font("Segoe UI",Font.PLAIN,14)
			}
			contents += genderField
			contents += new Button {
				font = new Font("Segoe UI",Font.PLAIN,14)
				action = new Action("Add Student") {
					def apply {
						if( nameField.text.length > 0 && idField.text.length > 0 
								&& genderField.text.length > 0 ) {
							control.addStudent(nameField.text,idField.text,genderField.text)
							nameField.text = ""
							idField.text = ""
							genderField.text = ""
						} else {
							Dialog.showMessage(null,"At least one of the fields is empty","Empty Field")
						}
					}
				}
			}
		}
	},BorderPanel.Position.North)
	val display = new BoxPanel(Orientation.Vertical) {
		def setModel {
			contents.clear
			contents += new ScrollPane( new BoxPanel(Orientation.Vertical) {
				for( x <- control.getStudents ) {
					contents += new StudentDisplayPanel(control, x._2)
				}	
			}) {
				verticalScrollBar.unitIncrement = 16
			}
			repaint
			revalidate
		}
	}

	def setModel {
		display.setModel	
	}
	setModel
	add(display,BorderPanel.Position.Center)
}

class StudentDisplayPanel(val control: GBController, val model : Student) 
															extends AGBPanel {
	val nameField = new TextField {
		text = model.name
		font = new Font("Segoe UI",Font.BOLD,24)
		columns = 30
	}
	val genderField = new TextField {
		text = model.gender
		font = new Font("Segoe UI",Font.PLAIN,14)
		columns = 30
	}
	add(nameField,con(0,0,6,1,0,0,GridBagPanel.Anchor.Center,
									GridBagPanel.Fill.None))
	add(new Label(model.id) {
			font = new Font("Segoe UI",Font.PLAIN,14)
		},con(0,1,3,1,3,0,GridBagPanel.Anchor.East,
									GridBagPanel.Fill.None,0,0,0,10))
	add(genderField,con(3,1,3,1,2,0,GridBagPanel.Anchor.West,
									GridBagPanel.Fill.None,0,10,0,0))
	add(new Button {
			font = new Font("Segoe UI",Font.PLAIN,14)
			action = new Action("Delete") {
				def apply {
					control.deleteStudent(model.id)
				}
			}
		},con(0,2,3,1,3,0,GridBagPanel.Anchor.East,
									GridBagPanel.Fill.None,0,0,0,10))
	add(new Button {
			font = new Font("Segoe UI",Font.PLAIN,14)
			action = new Action("Save Changes") {
				def apply {
					control.updateStudent(model.id, nameField.text, genderField.text)
				}
			}
		},con(3,2,3,1,2,0,GridBagPanel.Anchor.West,
									GridBagPanel.Fill.None,0,10,0,0))
	add(new Label("Course") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(0,3,1,1))
	add(new Label("Exercises") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(1,3,1,1))
	add(new Label("Exams") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(2,3,1,1))
	add(new Label("Finals") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(3,3,1,1))
	add(new Label("Final Grade") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(4,3,1,1))

	var i = 4
	val exer = ArrayBuffer.empty[TextField]
	val exam = ArrayBuffer.empty[TextField]
	val finals = ArrayBuffer.empty[TextField]
	val fg = ArrayBuffer.empty[TextField]
	for( x <- model.grades) {
		val grade = x._2
		add(new Label {
			font = new Font("Segoe UI",Font.BOLD,14)
			text = grade.course
		},con(0,i,1,1))
		exer += new TextField {
			horizontalAlignment = Alignment.Right
			font = new Font("Segoe UI",Font.PLAIN,14)
			text = grade.exer + ""
		}
		add(exer(i - 4),con(1,i,1,1,1,1,GridBagPanel.Anchor.Center
							,GridBagPanel.Fill.Horizontal))
		exam += new TextField {
			horizontalAlignment = Alignment.Right
			font = new Font("Segoe UI",Font.PLAIN,14)
			text = grade.exams + ""
		}
		add(exam(i - 4),con(2,i,1,1,1,1,GridBagPanel.Anchor.Center
							,GridBagPanel.Fill.Horizontal))
		finals += new TextField {
			horizontalAlignment = Alignment.Right
			font = new Font("Segoe UI",Font.PLAIN,14)
			text = grade.finals + ""
		}
		add(finals(i - 4),con(3,i,1,1,1,1,GridBagPanel.Anchor.Center
							,GridBagPanel.Fill.Horizontal))
		fg += new TextField {
			horizontalAlignment = Alignment.Right
			font = new Font("Segoe UI",Font.PLAIN,14)
			text = grade.fg + ""
		}
		add(fg(i - 4),con(4,i,1,1,1,1,GridBagPanel.Anchor.Center
							,GridBagPanel.Fill.Horizontal))
		add(new Button {
				val index = i - 4
				font = new Font("Segoe UI",Font.PLAIN,14)
				action = new Action("Save Changes") {
					def apply {
						try {
							control.updateGrade(model.id,x._1
												,exer(index).text.toDouble
												,exam(index).text.toDouble
												,finals(index).text.toDouble
												,fg(index).text.toDouble)
							} catch{
								case e : Exception => Dialog.showMessage(null,"Please input only numbers","Invalid input")
							}
					}
				}
			},con(5,i,1,1))
		i += 1
	}
	add(new Label("GPA") {
			font = new Font("Segoe UI",Font.BOLD,14)
		},con(0,i,1,1))
	add(new Label("%2.1f".format(model.gpa)) {
			font = new Font("Segoe UI",Font.PLAIN,14)
		},con(4,i,1,1,1,1,GridBagPanel.Anchor.East))
}