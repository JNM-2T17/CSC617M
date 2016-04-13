console.log("Hello, World!");

/*****************************************/

function problem2() {
	var str = document.getElementById("input1").value;
	console.log(str);
}

/******************************************/

function problem3() {
	var prime = document.getElementById("input2").value;
	var isPrime = true;
	for(var i = 2; isPrime && i <= Math.sqrt(prime); i++ ) {
		if( prime % i === 0 ) {
			isPrime = false;
		}
		i++;
	}
	console.log(prime + " is " + (isPrime ? "prime" : "composite."));
}

/******************************************/

function problem4() {
	var x = document.getElementById("input3").value;

	console.log((x * x + (Math.log(x)/Math.LN10 - Math.sin(x))/Math.sqrt(x)));
}
