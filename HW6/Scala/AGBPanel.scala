import scala.swing._

class AGBPanel extends GridBagPanel {
	def con(x : Int, y : Int, gridwidth : Int = 1
						, gridheight : Int = 1,weightx : Int = 100
						, weighty : Int = 100
						, anchor : GridBagPanel.Anchor.Value 
							= GridBagPanel.Anchor.Center
						, fill : GridBagPanel.Fill.Value 
							= GridBagPanel.Fill.None
						,insetTop : Int = 0,insetLeft : Int = 0
						,insetBottom : Int = 0,insetRight : Int = 0)
		: Constraints = {
		val c = new Constraints
		c.gridx = x
		c.gridy = y
		c.gridwidth = gridwidth
		c.gridheight = gridheight
		c.weightx = weightx
		c.weighty = weighty
		c.anchor = anchor
		c.fill = fill
		c.insets = new java.awt.Insets(insetTop,insetLeft,insetBottom,insetRight)
		c
	}
}