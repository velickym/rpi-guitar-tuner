package com.velicky.martin.rpigt;

import com.velicky.martin.rpigt.gpio.LEDNote;

public enum Note {

	C	(false, LEDNote.C),
	C_	(true, LEDNote.C),
	D	(false, LEDNote.D),
	D_	(true, LEDNote.D),
	E	(false, LEDNote.E),
	F	(false, LEDNote.F),
	F_	(true, LEDNote.F),
	G	(false, LEDNote.G),
	G_	(true, LEDNote.G),
	A	(false, LEDNote.A),
	A_	(true, LEDNote.A),
	B	(false, LEDNote.B);
	
	private final boolean isSharp;
	private final LEDNote ledLetter;
	
	private Note(boolean sharp, final LEDNote ledLetter) {
		this.isSharp = sharp;
		this.ledLetter = ledLetter;
	}
	
	public boolean isSharp() {
		return isSharp;
	}
	
	public LEDNote getLEDLetter() {
		return ledLetter;
	}
	
}
