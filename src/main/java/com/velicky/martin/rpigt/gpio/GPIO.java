package com.velicky.martin.rpigt.gpio;

import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO10;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO11;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO23;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO24;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO25;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO7;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO8;
import static com.velicky.martin.rpigt.gpio.GPIOChannel.GPIO9;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.velicky.martin.rpigt.Note;
import com.velicky.martin.rpigt.Pair;

public class GPIO {

	private static final String CHANNE_DIRECTIONL_PATH = "/sys/class/gpio/gpio%s/direction";

	private static final String I2C_COMMAND = "i2cset -y 0 0x20 ";
	private static final GPIOChannel[] noteGpioChannels = {GPIO7, GPIO8, GPIO9, GPIO10, GPIO11, GPIO23, GPIO24, GPIO25};
	
	public static final String BANK_A = "0x13";
	public static final String BANK_B = "0x12";
	
	private static String lastBank = null;
	
	private static void gpioHandleNote(final int channelId, final Direction direction) throws Exception {
		final String channelDirectionPath = String.format(CHANNE_DIRECTIONL_PATH, channelId);
		final BufferedWriter bwChannel = new BufferedWriter(new FileWriter(channelDirectionPath));
		bwChannel.write(direction.getValue());
		bwChannel.flush();
		bwChannel.close();
	}
	
	public static void display(Pair<Note, Integer> noteAndFrequency) throws Exception {
		
		if (noteAndFrequency == null) return;
		
		final Note note = noteAndFrequency.getKey();
		final Integer ledIndex = noteAndFrequency.getValue();
		
		System.out.println("Displaying note " + note + " and LED index " + ledIndex);
		
		//display note
		GPIOChannel[] gpioChannels = note.getLEDLetter().getGPIOChannels();
		for (GPIOChannel gpioChannel : gpioChannels) {
			gpioHandleNote(gpioChannel.getChannelId(), Direction.OUT);
		}
		if (note.isSharp()) {
			gpioHandleNote(GPIO11.getChannelId(), Direction.OUT);
		}
		
		//turn on frequency LED
		final String bank = (ledIndex > 0)? BANK_A : BANK_B; 
		if (!bank.equals(lastBank)) {
			undisplayBank(lastBank);
			lastBank = bank;
		}
		final String value = LEDFrequency.LED_FREQUENCIES.get(ledIndex);
		final String command = I2C_COMMAND + bank + " " + value;
		Runtime.getRuntime().exec(command);
	}
	
	public static void undisplay() throws Exception {
		
		//turning off note display
		undisplayNote();
		
		//turning off LEDs
		undisplayBank(BANK_A);
		undisplayBank(BANK_B);
	}

	private static void undisplayBank(String bank) throws IOException {
		if (bank == null) return; 
		final String lowerHalfLeds = I2C_COMMAND + bank + " 0x00";
		Runtime.getRuntime().exec(lowerHalfLeds);
	}
	
	protected static void undisplayNote() throws Exception {
		//turning off note display
		for (GPIOChannel gpioChannel : noteGpioChannels) {
			gpioHandleNote(gpioChannel.getChannelId(), Direction.IN);
		}
	}
	
}
