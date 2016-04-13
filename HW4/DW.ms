play {
	intro < 2
}

seq {
	E#(3,1/8) * 3
	D(3,1/8)
} -> intro_1

seq {
	intro_1[1]
	F(3,1/8)
	G(3,1/8) * 3
	intro_1[2]
} -> intro_2

seq {
	seq {
		intro_1 * 2
		intro_2
	} * 2
} -> intro