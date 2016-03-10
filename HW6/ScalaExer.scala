object ScalaExer {
	def main(args : Array[String]) {
		//1
		var i : Int = 0
		var k : Int = 0
		var j : Int = 0
		k = (j + 13) / 27
		while( k <= 10 ){
			k = k + 1
			i = 3 * k - 1
		}

		//2
		k match {
			case 1 => j = 2 * k - 1
			case 2 => j = 2 * k - 1
			case 3 => j = 3 * k + 1
			case 5 => j = 3 * k + 1
			case 4 => j = 4 * k - 1
			case 6 => j = k - 2
			case 7 => j = k - 2
			case 8 => j = k - 2
			case _ => 
		}

		//3
		var exit = false
		i = 0
		j = -3
		while(!exit && i < 3) {
			(j + 2) match {
				case 3 => j = j - 1
				case 2 => j = j - 1
				case 0 => j += 2
				case _ => j = 0
			}
			
			if( j > 0 ) {
				exit = true
			}
			if( !exit ) {
				j = 3 - i
				i = i + 1
			}
		}

		//4
		var n : Int = 3
		var x = Array.ofDim[Int](n,n)
		x(0)(1) = 1
		var stop = false
		i = 0
		while( !stop && i < n ) {
			exit = false
			var j = 0
			while(!exit && j < n) {
				if( x(i)(j) != 0 ) {
					exit = true
				}
				if( !exit ) {
					j = j + 1
				}
			}
			if( !exit ) {
				println("First all-zero row is: " + i)
				stop = true;
			}
			if( !stop ) {
				i = i + 1
			}
		}
	}
}