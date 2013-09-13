package aes

import (
	"encoding/hex"
	"fmt"
	"strings"
	"testing"
)

var testState *State
var before, after string
var original = []byte{0x32, 0x43, 0xf6, 0xa8, 0x88, 0x5a, 0x30, 0x8d, 0x31, 0x31, 0x98, 0xa2, 0xe0, 0x37, 0x07, 0x34}
var in1 = []byte{0x19, 0x3d, 0xe3, 0xbe, 0xa0, 0xf4, 0xe2, 0x2b, 0x9a, 0xc6, 0x8d, 0x2a, 0xe9, 0xf8, 0x48, 0x08}
var out1 = []byte{0xd4, 0x27, 0x11, 0xae, 0xe0, 0xbf, 0x98, 0xf1, 0xb8, 0xb4, 0x5d, 0xe5, 0x1e, 0x41, 0x52, 0x30}
var debug = false

func init() {
	testState = makeState(original)
}

func TestKeyExpansion(t *testing.T) {
	schedule128 := make(KeySchedule, Nb*(10+1))
	err := schedule128.KeyExpansion(KEY_128)
	if err != nil {
		t.Errorf("error in KeyExpansion: %s\n", err)
	}

	schedule192 := make(KeySchedule, Nb*(12+1))
	Nk = 6
	Nr = 12
	err = schedule192.KeyExpansion(KEY_192)
	if err != nil {
		t.Errorf("error in KeyExpansion: %s\n", err)
	}

	schedule256 := make(KeySchedule, Nb*(14+1))
	Nk = 8
	Nr = 14
	err = schedule256.KeyExpansion(KEY_256)
	if err != nil {
		t.Errorf("error in KeyExpansion: %s\n", err)
	}

}

func TestAll(t *testing.T) {
	if debug {
		fmt.Println("Original")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}
	schedule := make(KeySchedule, Nb*(Nr+1))
	schedule.KeyExpansion(KEY_128)
	nextKey := [4][4]byte{schedule[0], schedule[1], schedule[2], schedule[3]}
	testState.AddRoundKey(nextKey)
	if debug {
		fmt.Println("After first AddRoundKey")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}

	testState.SubBytes()
	if debug {
		fmt.Println("After SubBytes")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}

	testState.ShiftRows()
	if debug {
		fmt.Println("After ShiftRows")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}

	testState.MixColumns()
	if debug {
		fmt.Println("After MixColumns")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}

	nextKey = [4][4]byte{schedule[4], schedule[5], schedule[6], schedule[7]}
	testState.AddRoundKey(nextKey)
	if debug {
		fmt.Println("After AddRoundKey")
		lines := strings.Split(hex.EncodeToString(*testState.ToArray()), "\n")
		SpecialPrint(lines[0])
	}
}

func compareAllValues(t *testing.T, actual, generated []byte) {
	for i := 0; i < len(actual); i++ {
		if generated[i] != actual[i] {
			t.Errorf("Incorrect value at index %d. Expected: %+v, Generated: %+v", i, actual[i], generated[i])
		}
	}
}
