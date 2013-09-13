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
	printBlock("Original", *testState)

	schedule := make(KeySchedule, Nb*(Nr+1))
	schedule.KeyExpansion(KEY_128)
	nextKey := [4][4]byte{schedule[0], schedule[1], schedule[2], schedule[3]}
	testState.AddRoundKey(nextKey)
	printBlock("After first AddRoundKey", *testState)

	testState.SubBytes()
	printBlock("After SubBytes", *testState)

	testState.ShiftRows()
	printBlock("After ShiftRows", *testState)

	testState.MixColumns()
	printBlock("After MixColumns", *testState)

	nextKey = [4][4]byte{schedule[4], schedule[5], schedule[6], schedule[7]}
	testState.AddRoundKey(nextKey)
	printBlock("After AddRoundKey", *testState)
}

func TestInvSubBytes(t *testing.T) {
	actual := makeState(original)
	tmpState := makeState(original)
	printBlock("Original", *tmpState)

	tmpState.SubBytes()
	printBlock("After SubBytes", *tmpState)

	tmpState.InvSubBytes()
	printBlock("After InvSubBytes", *tmpState)

	compareAllValues(t, *actual.ToArray(), *tmpState.ToArray())
}

func TestInvShiftRows(t *testing.T) {
	actual := makeState(original)
	tmpState := makeState(original)
	printBlock("Original", *tmpState)

	tmpState.ShiftRows()
	printBlock("After ShiftRows", *tmpState)

	tmpState.InvShiftRows()
	printBlock("After InvShiftRows", *tmpState)

	compareAllValues(t, *actual.ToArray(), *tmpState.ToArray())
}

func TestInvMixColumns(t *testing.T) {
	actual := makeState(original)
	tmpState := makeState(original)
	printBlock("Original", *tmpState)

	tmpState.MixColumns()
	printBlock("After MixColumns", *tmpState)

	tmpState.InvMixColumns()
	printBlock("After InvMixColumns", *tmpState)

	compareAllValues(t, *actual.ToArray(), *tmpState.ToArray())
}

func compareAllValues(t *testing.T, actual, generated []byte) {
	for i := 0; i < len(actual); i++ {
		if generated[i] != actual[i] {
			t.Errorf("Incorrect value at index %d. Expected: %+v, Generated: %+v", i, actual[i], generated[i])
		}
	}
}
