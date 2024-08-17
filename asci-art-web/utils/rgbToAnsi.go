package utils

/*
RGBtoAnsi function converts RGB color values
(in the range 0-255) to an ANSI color code.
*/
func RGBtoAnsi(R, G, B int) int {
	r := R / 51
	g := G / 51
	b := B / 51

	colorNum := 16 + 36*r + 6*g + b

	return colorNum
}
