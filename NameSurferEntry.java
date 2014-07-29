/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
	
/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		parseLine(line);
	}
	
	private void parseLine(String line) {
		
		//Breaks up the line from the database to create the name entry.
		int nameEntryStart = 0; //First letter of name starts at index of 0.
		int nameEntryEnd = line.indexOf(" ");
		
		/* Doesn't include the space, " ".
		 * Only includes the letters in the name.
		 */
		name = line.substring(nameEntryStart, nameEntryEnd); 
		
		/* The string tokenizer class allows to break a string into tokens.
		 * The line from the database has its 11 integers put into the rank array. 
		 */
		 String numbers = line.substring(nameEntryEnd + 1);
	     StringTokenizer tokenizer = new StringTokenizer(numbers);
	     for(int i= 0; tokenizer.hasMoreTokens(); i++) {
	    	 ranks[i] = Integer.parseInt(tokenizer.nextToken());
	     }
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return ranks[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String nameAndRanks = (" "+ getName() +" ");
		for(int i = 0; i < NDECADES; i++) {
			nameAndRanks = nameAndRanks + (" " + getRank(i) +" ");
		}
		return nameAndRanks;
	}
	
	private String name;
	private int ranks[] = new int[NDECADES]; 
}

