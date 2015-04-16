package com.velicky.martin.rpigt;

import static com.velicky.martin.rpigt.Util.correctFrequency;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.velicky.martin.rpigt.gpio.GPIO;

/**
 * Original code by John Montgomery
 * http://www.psychicorigami.com/2009/01/17/a-5k-java-guitar-tuner/
 * 
 */
public class Tuner {

	public static void main(String[] args) throws Exception {

		final float sampleRate = 16000;
		final int sampleSizeInBits = 16;
		final int channels = 1;
		final boolean signed = true;
		final boolean bigEndian = false;

		final AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

		// read about a second at a time
		targetDataLine.open(format, (int) sampleRate);
		targetDataLine.start();

		final byte[] buffer = new byte[2 * 1200];
		final int[] a = new int[buffer.length / 2];

		int n = -1;
		while ((n = targetDataLine.read(buffer, 0, buffer.length)) > 0) {

			for (int i = 0; i < n; i += 2) {
				// convert two bytes into single value
				final int value = (short) (buffer[i] & 0xFF | (buffer[i + 1] & 0xFF) << 8);
				a[i >> 1] = value;
			}

			double prevDiff = 0;
			double prevDx = 0;
			double maxDiff = 0;

			int sampleLen = 0;

			final int len = a.length / 2;
			for (int i = 0; i < len; i++) {
				double diff = 0;
				for (int j = 0; j < len; j++) {
					diff += Math.abs(a[j] - a[i + j]);
				}

				final double dx = prevDiff - diff;

				// change of sign in dx
				if (dx < 0 && prevDx > 0) {
					// only look for troughs that drop to less than 30% of peak
					if (diff < 0.3 * maxDiff) {
						if (sampleLen == 0) {
							sampleLen = i - 1;
						}
					}
				}

				prevDx = dx;
				prevDiff = diff;
				maxDiff = Math.max(diff, maxDiff);
			}

			if (sampleLen > 0) {
				double frequency = format.getSampleRate() / sampleLen;
				System.out.println("Frequency: " + frequency);

				frequency = correctFrequency(frequency);
				
				final Pair<Note,Integer> pair = Util.getNoteAndLEDIndexFromFrequency(frequency);
				if (pair != null) {
					GPIO.display(pair);
				} else {
					GPIO.undisplay();
				}
			} else {
				GPIO.undisplay();
			}
		}
	}
}
