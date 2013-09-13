package aes

import (
	"encoding/hex"
	"fmt"
	"strings"
)

func Printer() {
	fmt.Println("INPUT:")
	lines := strings.Split(hex.EncodeToString(INPUT), "\n")
	SpecialPrint(lines[0])
	fmt.Printf("KEY: %+v\n", keytouse)
	out, err := Cipher(INPUT, make(KeySchedule, Nb*(Nr+1)), keytouse)
	if err != nil {
		fmt.Printf("%s\n", err)
	}
	fmt.Println("OUTPUT:")
	lines = strings.Split(hex.EncodeToString(*out), "\n")
	SpecialPrint(lines[0])
}

func (state *State) ToArray() *[]byte {
	out := make([]byte, 16)
	count := 0
	for i := 0; i < Nb; i++ {
		for j := 0; j < Nb; j++ {
			out[count] = (*state)[j][i]
			count++
		}
	}
	return &out
}

func SpecialPrint(s string) {
	if len(s) < 32 {
		return
	}
	block := &[][]string{
		{s[:2], s[8:10], s[16:18], s[24:26]},
		{s[2:4], s[10:12], s[18:20], s[26:28]},
		{s[4:6], s[12:14], s[20:22], s[28:30]},
		{s[6:8], s[14:16], s[22:24], s[30:32]}}

	for row := 0; row < Nb; row++ {
		fmt.Printf("%s %s %s %s\n", (*block)[row][0], (*block)[row][1], (*block)[row][2], (*block)[row][3])
	}
}
