object HW1_4 {
	def main(args : Array[String]) {
		print("Input: ")
		var x = scala.io.StdIn.readDouble()
		println(f(x))
	}

	def f(x : Double) : Double = {
		return x * x + (math.log10(x) - math.sin(x)) / math.sqrt(x)
	}
}