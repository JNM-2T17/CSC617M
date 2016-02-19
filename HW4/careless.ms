play {
	chordTest + 6
	fuck
	fuck[1] < 2 > 3
	fuck[2~3]
	fuck[4] < 2 > 3
}

seq {
	maj(C,3,1/2)
	m(C,3,1/2)
	aug(C,3,1/2)
	dim(C,3,1/2)
	dim7(C,3,1/2)
	min7(C,3,1/2)
	mmaj7(C,3,1/2)
	dom7(C,3,1/2)
	maj7(C,3,1/2)
	aug7(C,3,1/2)
	dom9(C,3,1/2)
	dom11(C,3,1/2)
	dom13(C,3,1/2)
	add9(C,3,1/2)
	add11(C,3,1/2)
	sus2(C,3,1/2)
	sus4(C,3,1/2)
} -> chordTest

seq {
	A(3,1)
	B(5,1/2)
	C(4,1/4)
} -> test1
seq {
	C(3,1/4)
	B(5,1/2)
	A(4,1)
} -> test2

seq {
	E(5,1/8)
	D(5,1/16)
	A(4,1/8)
	F(4,1/8)
} -> fuck