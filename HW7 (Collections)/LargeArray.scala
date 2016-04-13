class LargeArray(val size : Int) {
	val ars = new Array[Array[Int]](scala.math.ceil(size / 1000).toInt)
	var i = 0
	while( i < ars.length ) {
		if( i == ars.length - 1 ) {
			ars(i) = new Array[Int](size % 1000);
		} else {
			ars(i) = new Array[Int](1000);
		}
		i += 1
	}

	def get(i : Int) : Int = ars(i / 1000)(i % 1000)

	def set(i : Int, value : Int) {
		ars(i / 1000)(i % 1000) = value	
	}
}