package com.velicky.martin.rpigt.gpio;

import com.velicky.martin.rpigt.Note;
import com.velicky.martin.rpigt.Pair;

public class ExpanderTest extends Thread {
	
	public static void main(String[] args) throws Exception {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				
				try {
					System.out.println("----------------------------------------------");
					System.out.println("Undisplaying all frequency LEDs + note display");
					GPIO.undisplay();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Runtime.getRuntime().exec("i2cset -y 0 0x20 0x00 0x00");
		Runtime.getRuntime().exec("i2cset -y 0 0x20 0x01 0x00");
		System.out.println("Undisplaying all frequency LEDs + note display");
		System.out.println("----------------------------------------------");
		GPIO.undisplay();
		
		for (Note note : Note.values()) {
			GPIO.undisplayNote();
			for (Integer ledIndex : LEDFrequency.LED_FREQUENCIES.keySet()) {
				GPIO.display(new Pair<Note, Integer>(note, ledIndex));
				Thread.sleep(10);
			}
		}
	}
}
