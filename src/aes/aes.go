package aes

import (
	"flag"
	"fmt"
)

var state [4][4]byte
var Nb = 4
var bits, Nr, Nk int

type State [][]byte

func init() {
	flag.IntVar(&bits, "bits", 128, "128, 192, or 256")
	flag.Parse()
	switch bits {
	case 128:
		Nk = 4
		Nr = 10
		return
	case 192:
		Nk = 6
		Nr = 12
		return
	case 256:
		Nk = 8
		Nr = 14
		return
	}
}

func Printer() {
	fmt.Printf("INPUT: %+v\n", INPUT)
	fmt.Printf("KEY: %+v\n", KEY)
	out, err := Cipher(INPUT, make([]int16, Nb*(Nr+1)))
	if err != nil {
		fmt.Printf("%s\n", err)
	}
	fmt.Printf("OUTPUT: %+v\n", *out)
}

func Cipher(in []byte, w []int16) (*[]byte, error) {
	if len(in) != 4*Nb {
		return nil, fmt.Errorf("input has incorrect length")
	} else if len(w) != Nb*(Nr+1) {
		return nil, fmt.Errorf("word has incorrect length")
	}

	state := makeState(in)

	state.AddRoundKey(w[0 : Nb-1])

	for round := 1; round < Nr; round++ {
		state.SubBytes()
		state.ShiftRows()
		state.MixColumns()
		state.AddRoundKey(w[round*Nb : (round+1)*Nb-1])
	}

	state.SubBytes()
	state.ShiftRows()
	state.AddRoundKey(w[Nr*Nb : (Nr+1)*Nb-1])

	out := state.ToArray()
	return out, nil
}

func makeState(in []byte) *State {
	newState := &State{{in[0], in[4], in[8], in[12]},
		{in[1], in[5], in[9], in[13]},
		{in[2], in[6], in[10], in[14]},
		{in[3], in[7], in[11], in[15]}}
	return newState
}

func (state *State) AddRoundKey(w []int16) {

}

func KeyExpansion(key []byte, w []int16) {
	if len(key) != 4*Nk {
		return nil, fmt.Errorf("key has incorrect length")
	} else if len(w) != Nb*(Nr+1) {
		return nil, fmt.Errorf("w has incorrect length")
	}

	var temp int16
	i := 0
	for i < Nk {
		w[i] = word(key[4*i], key[4*i+1], key[4*i+2], key[4*i+3])
		i = i + 1
	}
	i = Nk
	for i < Nb*(Nr+1) {
		temp = w[i-1]
		if i%Nk == 0 {
			temp = SubWord(RotWord(temp)) ^ Rcon[i/Nk]
		} else if Nk > 6 && i%Nk == 4 {
			temp = SubWord(temp)
		}
		w[i] = w[i-Nk] ^ temp
		i = i + 1
	}
}

func SubWord(input []byte) int16 {

}

func RotWord() byte {

}

func (state *State) SubBytes() {
	for i := 0; i < Nb; i++ {
		for j := 0; j < Nb; j++ {
			(*state)[i][j] = SBOX[(*state)[i][j]]
		}
	}
}

func (state *State) ShiftRows() {
	for row := 0; row < Nb; row++ {
		for i := 0; i < row; i++ {
			temp := (*state)[row][0]
			(*state)[row][0] = (*state)[row][1]
			(*state)[row][1] = (*state)[row][2]
			(*state)[row][2] = (*state)[row][3]
			(*state)[row][3] = temp
		}
	}
}

func (state *State) MixColumns() {
	result := &State{{0x00, 0x00, 0x00, 0x00}, {0x00, 0x00, 0x00, 0x00}, {0x00, 0x00, 0x00, 0x00}, {0x00, 0x00, 0x00, 0x00}}

	for y := 0; y < Nb; y++ {
		for x := 0; x < Nb; x++ {
			(*result)[x][y] = (ffMult((*state)[0][y], a[x][0])) ^
				(ffMult((*state)[1][y], a[x][1])) ^
				(ffMult((*state)[2][y], a[x][2])) ^
				(ffMult((*state)[3][y], a[x][3]))
		}
	}
	*state = *result
}

func ffMult(a, b byte) byte {
	aa := a
	bb := b
	result := byte(0x00)
	t := byte(0x00)
	for aa != 0 {
		if (aa & 1) != 0 {
			result = result ^ bb
		}
		t = bb & 0x80
		bb = bb << 1
		if t != 0 {
			bb = bb ^ 0x1b
		}
		aa = aa >> 1
	}
	return result
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
	fmt.Println(state)
	return &out
}
