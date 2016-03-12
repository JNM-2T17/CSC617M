import java.awt.Font
import scala.swing._

class ReportsPanel(val control : GBController) 
	extends BoxPanel(Orientation.Vertical) {
	updateModel

	def updateModel {
		var model = control.avg
		contents.clear
		contents += new Label {
			text = "Reports"
			font = new Font("Segoe UI",Font.BOLD,24)
		}
		val content = new BoxPanel(Orientation.Vertical) {
			for(x <- model) {
				contents += new BoxPanel(Orientation.Vertical) {
					border = Swing.BeveledBorder(Swing.Lowered)
					contents += new BoxPanel(Orientation.Vertical) {
						border = Swing.EmptyBorder(10,10,10,10)
						contents += new BorderPanel {
							add(new Label(x._1 + ": Avg = " 
												+ "%.2f".format(x._2)) {
										font = new Font("Segoe UI",Font.PLAIN,14)
									},BorderPanel.Position.West)
						}
						contents += Swing.VStrut(5)
						val top = control.top5(x._1)
						contents += new BorderPanel {
							add(new Label("Top 5") {
									font = new Font("Segoe UI",Font.BOLD,14)
								},BorderPanel.Position.West)
						}
						contents += new BorderPanel{
							add(new BoxPanel(Orientation.Horizontal) {	
								contents += Swing.HStrut(5)
								contents += Swing.Glue
								contents += new BoxPanel(Orientation.Vertical) {
									border 
										= Swing.MatteBorder(1,1,1,1
														,java.awt.Color.BLACK)
									contents += new BoxPanel(Orientation.Vertical) {
										border = Swing.EmptyBorder(5,5,5,5)
										var i = 0
										for( y <- top ) {
											if( i < 5 ) {
												contents += new Label(y._1 + ": " 
																		+ y._2) {
													font = new Font("Segoe UI"
																	,Font.PLAIN,14)
												}
											}
											i += 1
										}
									}
								}
							},BorderPanel.Position.West)
						}
					}
				}
			}
		}
		contents += new ScrollPane(content)
		contents += new FlowPanel(FlowPanel.Alignment.Center)(
			new Button {
				action = new Action("Back") {
					def apply {
						control.switch(GBController.Main)
					}
				}
			}
		)
		repaint
		revalidate
	}
}