function SubArray(size) {
	this.array = [];

	for(var i = 0; i < size; i++) {
		this.array[i] = 0;
	}		
}

function LargeArray(size) {
	this.array = [0,0,0];
	this.size = size
	this.chunks = Math.floor((size - 1) / 1000 + 1);

	for(var i = 0; i < this.chunks; i++) {
		if( i < this.chunks - 1 ) {
			this.array[i] = new SubArray(1000);
		} else {
			this.array[i] = new SubArray(size % 1000);
		}
	}

	this.set = function(position,value) {
		this.array[Math.floor(position / 1000)]
			.array[position % 1000] = value;
	}

	this.get = function(position) {
		return this.array[Math.floor(position / 1000)]
			.array[position % 1000];
	}
}