package com.velicky.martin.rpigt;

import static com.velicky.martin.rpigt.Note.A;
import static com.velicky.martin.rpigt.Note.A_;
import static com.velicky.martin.rpigt.Note.B;
import static com.velicky.martin.rpigt.Note.C;
import static com.velicky.martin.rpigt.Note.C_;
import static com.velicky.martin.rpigt.Note.D;
import static com.velicky.martin.rpigt.Note.D_;
import static com.velicky.martin.rpigt.Note.E;
import static com.velicky.martin.rpigt.Note.F;
import static com.velicky.martin.rpigt.Note.F_;
import static com.velicky.martin.rpigt.Note.G;
import static com.velicky.martin.rpigt.Note.G_;

public class Util {

	public static final int UNRECOGNIZED = -1;
	public static final int LEDS_FOR_TONE = 16; // 7 reds on each side (=14) + 2 greens signifying the exact matched frequency = 16

	public static final int MAX_FREQUENCY = 220;
	public static final int MIN_FREQUENCY = 40;

	public static final double E2_FREQ = 82.41;
	public static final double E3_FREQ = 164.81;

	public static final double[] FREQUENCIES = { 077.78, E2_FREQ, 087.31, 092.50, 098.00, 103.83, 110.00, 116.54, 123.47, 130.81, 138.59,
			146.83, 155.56, E3_FREQ, 174.61 };
	public static final Note[] NOTES = { D_, E, F, F_, G, G_, A, A_, B, C, C_, D, D_, E, F };
	public static double[] FREQUENCY_FRAMES = new double[NOTES.length - 1];

	static {
		for (int i = 1; i < FREQUENCIES.length; i++) {
			final double range = FREQUENCIES[i] - FREQUENCIES[i - 1];
			FREQUENCY_FRAMES[i - 1] = range / LEDS_FOR_TONE;
		}
	}

	/**
	 * Get HZ in standard range in order to read it easier
	 * 
	 * @param hz
	 * @return
	 */
	public static double correctFrequency(double hz) {
		if (hz < E2_FREQ) {
			return 2 * hz;
		}
		if (hz > E3_FREQ) {
			return 0.5 * hz;
		}
		return hz;
	}

	public static Pair<Note, Integer> getNoteAndLEDIndexFromFrequency(double frequency) {

		if (frequency < MIN_FREQUENCY || frequency > MAX_FREQUENCY)
			return null;

		Integer noteIndex = null;
		double frequencyPerLed = 0.3;

		if (frequency < FREQUENCIES[0]) {
			noteIndex = 0;
			frequencyPerLed = FREQUENCY_FRAMES[0];
		}

		if (frequency > FREQUENCIES[FREQUENCIES.length - 1]) {
			noteIndex = FREQUENCIES.length - 1;
			frequencyPerLed = FREQUENCY_FRAMES[FREQUENCY_FRAMES.length - 1];
		}
		
		if (noteIndex == null) {
			for (int i = 1; i < FREQUENCIES.length; i++) {

				double higher = FREQUENCIES[i];

				if (frequency > higher)
					continue;

				double lower = FREQUENCIES[i - 1];

				if (((higher + lower) / 2) < frequency) {
					noteIndex = i;
					frequencyPerLed = FREQUENCY_FRAMES[i - 1];
				} else {
					noteIndex = i - 1;
					frequencyPerLed = FREQUENCY_FRAMES[i - 2];
				}
				break;
			}
		}

		Note note = NOTES[noteIndex];
		double closestNoteFrequency = FREQUENCIES[noteIndex];

		if (frequency == closestNoteFrequency) {
			// frequency is exactly the same, very poor chance to happen
			return new Pair<Note, Integer>(note, 0);
		}

		double dx = closestNoteFrequency;

		if (frequency > closestNoteFrequency) {
			// higher half

			for (int i = 0; i < 7; i++) {
				dx += frequencyPerLed;
				if (frequency >= dx) {
					continue;
				} else {
					return new Pair<Note, Integer>(note, i);
				}
			}
			
			return new Pair<Note, Integer>(note, 7);

		} else {
			// lower half

			for (int i = 0; i > -7; i--) {
				dx -= frequencyPerLed;
				if (frequency <= dx) {
					continue;
				} else {
					return new Pair<Note, Integer>(note, i);
				}
			}
			return new Pair<Note, Integer>(note, -7);
		}
	}
}
