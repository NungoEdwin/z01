package utils

import (
	"fmt"
	"os"
)

/*
color function is used to return ANSI escape codes for different colors.
ANSI escape codes are sequences of characters used to control the formatting,
color, and other output options on text terminals.
*/
func color(color string) string {
	mapColor := map[string]string{
		"black":           "\033[38;2;0;0;0m",
		"white":           "\033[38;2;255;255;255m",
		"red":             "\033[31m",
		"green":           "\033[38;2;0;255;0m",
		"blue":            "\033[38;2;0;0;255m",
		"yellow":          "\033[38;2;255;255;0m",
		"cyan":            "\033[38;2;0;255;255m",
		"magenta":         "\033[38;2;255;0;255m",
		"pink":            "\033[38;2;255;192;203m",
		"orange":          "\033[38;2;255;165;0m",
		"purple":          "\033[38;2;128;0;128m",
		"brown":           "\033[38;2;165;42;42m",
		"gray":            "\033[38;2;128;128;128m",
		"light_gray":      "\033[38;2;211;211;211m",
		"dark_gray":       "\033[38;2;169;169;169m",
		"light_blue":      "\033[38;2;173;216;230m",
		"light_green":     "\033[38;2;144;238;144m",
		"light_cyan":      "\033[38;2;224;255;255m",
		"light_magenta":   "\033[38;2;255;182;193m",
		"light_yellow":    "\033[38;2;255;255;224m",
		"dark_red":        "\033[38;2;139;0;0m",
		"dark_green":      "\033[38;2;0;100;0m",
		"dark_blue":       "\033[38;2;0;0;139m",
		"dark_yellow":     "\033[38;2;204;204;0m",
		"dark_cyan":       "\033[38;2;0;139;139m",
		"dark_magenta":    "\033[38;2;139;0;139m",
		"salmon":          "\033[38;2;250;128;114m",
		"tomato":          "\033[38;2;255;99;71m",
		"orange_red":      "\033[38;2;255;69;0m",
		"gold":            "\033[38;2;255;215;0m",
		"lime":            "\033[38;2;0;255;0m",
		"sea_green":       "\033[38;2;46;139;87m",
		"sky_blue":        "\033[38;2;135;206;235m",
		"deep_sky_blue":   "\033[38;2;0;191;255m",
		"midnight_blue":   "\033[38;2;25;25;112m",
		"indigo":          "\033[38;2;75;0;130m",
		"violet":          "\033[38;2;238;130;238m",
		"goldenrod":       "\033[38;2;218;165;32m",
		"peru":            "\033[38;2;205;133;63m",
		"chocolate":       "\033[38;2;210;105;30m",
		"sienna":          "\033[38;2;160;82;45m",
		"khaki":           "\033[38;2;240;230;140m",
		"lavender":        "\033[38;2;230;230;250m",
		"thistle":         "\033[38;2;216;191;216m",
		"orchid":          "\033[38;2;218;112;214m",
		"plum":            "\033[38;2;221;160;221m",
		"golden_yellow":   "\033[38;2;255;223;0m",
		"coral":           "\033[38;2;255;127;80m",
		"turquoise":       "\033[38;2;64;224;208m",
		"dark_turquoise":  "\033[38;2;0;206;209m",
		"steel_blue":      "\033[38;2;70;130;180m",
		"cadet_blue":      "\033[38;2;95;158;160m",
		"pale_green":      "\033[38;2;152;251;152m",
		"spring_green":    "\033[38;2;0;255;127m",
		"light_sea_green": "\033[38;2;32;178;170m",
		"light_sky_blue":  "\033[38;2;135;206;250m",
		"powder_blue":     "\033[38;2;176;224;230m",
		"wheat":           "\033[38;2;245;222;179m",
		"moccasin":        "\033[38;2;255;228;181m",
		"beige":           "\033[38;2;245;245;220m",
		"bisque":          "\033[38;2;255;228;196m",
		"peach_puff":      "\033[38;2;255;218;185m",
		"seashell":        "\033[38;2;255;245;238m",
		"linen":           "\033[38;2;250;240;230m",
		"ivory":           "\033[38;2;255;255;240m",
		"lavender_blush":  "\033[38;2;255;240;245m",
		"honeydew":        "\033[38;2;240;255;240m",
		"alice_blue":      "\033[38;2;240;248;255m",
		"mint_cream":      "\033[38;2;245;255;250m",
		"ghost_white":     "\033[38;2;248;248;255m",
		"azure":           "\033[38;2;240;255;255m",
	}

	clr, found := mapColor[color]
	if !found {
		fmt.Printf("Color %v not found\n", color)
		os.Exit(0)
	}

	return clr
}
