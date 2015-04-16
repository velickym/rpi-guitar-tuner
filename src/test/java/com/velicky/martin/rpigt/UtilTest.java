package com.velicky.martin.rpigt;

import junit.framework.Assert;

import org.junit.Test;

import com.velicky.martin.rpigt.Note;
import com.velicky.martin.rpigt.Pair;
import com.velicky.martin.rpigt.Util;

public class UtilTest {

	@Test
	public void testNoteAndFrequency() {
	
		Pair<Note,Integer> pair = null;
			
		//frequency above the range of guitar
		pair = Util.getNoteAndLEDIndexFromFrequency(1240);
		Assert.assertNull(pair);
		//frequency below the range of guitar
		pair = Util.getNoteAndLEDIndexFromFrequency(20);
		Assert.assertNull(pair);
		
		//exactly E2
		pair = Util.getNoteAndLEDIndexFromFrequency(Util.E2_FREQ);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(0, pair.getValue().intValue());
		//tiny bit above E2
		pair = Util.getNoteAndLEDIndexFromFrequency(Util.E2_FREQ + 0.01);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(0, pair.getValue().intValue());
		//tiny bit below E2
		pair = Util.getNoteAndLEDIndexFromFrequency(Util.E2_FREQ - 0.01);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(0, pair.getValue().intValue());
		//tiny bit above first frequency frame
		pair = Util.getNoteAndLEDIndexFromFrequency(Util.E2_FREQ + Util.FREQUENCY_FRAMES[1] + 0.02);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(1, pair.getValue().intValue());
		//exactly at first frame
		pair = Util.getNoteAndLEDIndexFromFrequency(Util.E2_FREQ + Util.FREQUENCY_FRAMES[1]);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(1, pair.getValue().intValue());
		
		//exactly in the middle between E2 and F2
		pair = Util.getNoteAndLEDIndexFromFrequency((Util.FREQUENCIES[1] + Util.FREQUENCIES[2]) / 2);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(7, pair.getValue().intValue());
		//tiny bit below
		pair = Util.getNoteAndLEDIndexFromFrequency((Util.FREQUENCIES[1] + Util.FREQUENCIES[2]) / 2 - 0.02);
		Assert.assertEquals(Note.E, pair.getKey());
		Assert.assertEquals(7, pair.getValue().intValue());
		//tiny bit above
		pair = Util.getNoteAndLEDIndexFromFrequency((Util.FREQUENCIES[1] + Util.FREQUENCIES[2]) / 2 + 0.01);
		Assert.assertEquals(Note.F, pair.getKey());
		Assert.assertEquals(-7, pair.getValue().intValue());
		
	}
}
