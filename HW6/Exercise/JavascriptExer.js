//1
var k = (j + 13) / 27;

while( k <= 10 ){
	k = k + 1;
	i = 3 * k - 1;
}

//2
switch(k) {
	case 1:
	case 2:
		j = 2 * k - 1;
		break;
	case 3:
	case 5:
		j = 3 * k + 1;
		break;
	case 4:
		j = 4 * k - 1;
		break;
	case 6:
	case 7:
	case 8:
		j = k - 2;
		break;
	default:
}

//3
var exit = false;
var out = false;
j = -3;
for(i = 0; !exit && i < 3; i++) {
	switch( j + 2 ) {
		case 3:
		case 2:
			j--;
			out = true;
		case 0:
			if( !out ) {
				j += 2;
				out = true;
			}
		default:
			if( !out ) {
				j = 0;
			}
	}
	if( j > 0 ) {
		exit = true;
		i--;
	}
	if( !exit ) {
		j = 3 - i;
	}
}

//4
var exit = false;
for( i = 0; i < n; i++) {
	for(j = 0; j < n; j++) {
		if( x[i][j] != 0 ) {
			exit = true;
			break;
		}
	}
	if( !exit ) {
		console.log("First all-zero row is: " + i);
		break;
	}
}