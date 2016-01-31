object HW1_3 {
	def main(args : Array[String]) {
		print("Input: ")
		var num = scala.io.StdIn.readInt()
		var prime = if( isPrime(num) ) "prime" else "composite"
		println( num + " is " + prime )
	}

	def isPrime(num : Int) : Boolean = {
		var i : Int = 2
		var isPrime : Boolean = true
		while(isPrime && i <= math.sqrt(num)) {
			if( num % i == 0 ) {
				isPrime = false
			}
			i += 1
		}
		isPrime
	}
}