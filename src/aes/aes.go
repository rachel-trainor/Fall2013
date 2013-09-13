package aes

import (
	"flag"
	"fmt"
)

var state State
var Nb = 4
var bits, Nr, Nk int
var keytouse []byte

type State [4][4]byte
type KeySchedule [][4]byte

func init() {
	flag.IntVar(&bits, "bits", 128, "128, 192, or 256")
	flag.Parse()
	switch bits {
	case 128:
		Nk = 4
		Nr = 10
		keytouse = KEY_128
		return
	case 192:
		Nk = 6
		Nr = 12
		keytouse = KEY_192
		return
	case 256:
		Nk = 8
		Nr = 14
		keytouse = KEY_256
		return
	}
}

func Cipher(in []byte, schedule KeySchedule, key []byte) (*[]byte, error) {
	if len(in) != 4*Nb {
		return nil, fmt.Errorf("input has incorrect length")
	} else if len(schedule) != Nb*(Nr+1) {
		return nil, fmt.Errorf("schedule has incorrect length")
	}
	schedule.KeyExpansion(key)
	state := makeState(in)

	nextKey := [4][4]byte{schedule[0], schedule[1], schedule[2], schedule[3]}
	state.AddRoundKey(nextKey)

	for round := 1; round <= Nr; round++ {
		state.SubBytes()
		state.ShiftRows()
		if round != Nr {
			state.MixColumns()
		}
		nextKey = [4][4]byte{schedule[round*Nb], schedule[round*Nb+1], schedule[round*Nb+2], schedule[round*Nb+3]}
		state.AddRoundKey(nextKey)
	}

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

func (state *State) AddRoundKey(roundKeyValue [4][4]byte) {
	for i := 0; i < Nb; i++ {
		for j := 0; j < Nb; j++ {
			(*state)[i][j] = (*state)[i][j] ^ roundKeyValue[j][i]
		}
	}
}

func (schedule *KeySchedule) KeyExpansion(key []byte) error {
	if len(key) != 4*Nk {
		return fmt.Errorf("key has incorrect length")
	} else if len(*schedule) != Nb*(Nr+1) {
		return fmt.Errorf("schedule has incorrect length")
	}

	var temp [4]byte
	i := 0
	for ; i < Nk; i++ {
		(*schedule)[i] = [4]byte{key[4*i], key[4*i+1], key[4*i+2], key[4*i+3]}
	}
	i = Nk
	for i < Nb*(Nr+1) {
		temp = (*schedule)[i-1]
		if i%Nk == 0 {
			temp = SubWord(RotWord(temp))
			temp[0] = temp[0] ^ Recon[(i/Nk)-1]
		} else if Nk > 6 && i%Nk == 4 {
			temp = SubWord(temp)
		}
		(*schedule)[i][0] = temp[0] ^ (*schedule)[i-Nk][0]
		(*schedule)[i][1] = temp[1] ^ (*schedule)[i-Nk][1]
		(*schedule)[i][2] = temp[2] ^ (*schedule)[i-Nk][2]
		(*schedule)[i][3] = temp[3] ^ (*schedule)[i-Nk][3]
		i = i + 1
	}
	return nil
}

// takes a four-byte input word and applies the S-box to each of the four bytes to produce an output word
func SubWord(input [4]byte) [4]byte {
	return [4]byte{SBOX[input[0]], SBOX[input[1]], SBOX[input[2]], SBOX[input[3]]}
}

// takes a word [a0,a1,a2,a3] as input, performs a cyclic permutation, and returns the word [a1,a2,a3,a0]
func RotWord(input [4]byte) [4]byte {
	return [4]byte{input[1], input[2], input[3], input[0]}
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
