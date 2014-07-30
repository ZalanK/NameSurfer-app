/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this); //Used for resizing the program.
		entriesShown = new ArrayList<NameSurferEntry>();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entriesShown.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		entriesShown.add(entry);
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		 removeAll();
	     drawGraph();
	     checkForEntry();
	}
	
	private void drawGraph() {
		drawGraphLines();
		drawDecadeLabels();
	}
	
	private void drawGraphLines() {
		drawVerticalLines();
		drawHorizontalLines();
	}
	
	private void drawVerticalLines() {
		double totalWidth = getWidth();
		double totalHeight = getHeight();
		
		 //There only needs to be 10 lines to separate 11 decade brackets
		double numOfLines = NDECADES - 1;
		
		//Separation between each vertical line
		double xLineSep = totalWidth / NDECADES;
		double startX = xLineSep;
		
		//Creates 10 vertical lines that are evenly spaced throughout the layout
		for(int i = 0; i < numOfLines; i++) {
			GLine line = new GLine(startX, 0, startX, totalHeight);
			add(line); 
			startX = startX + xLineSep;
		}
	}
	
	private void drawHorizontalLines() {
		double totalWidth = getWidth();
		double totalHeight = getHeight();
		
		double yLineSep = GRAPH_MARGIN_SIZE; //Separation from the top and bottom of the graphs
		
		//The upper line helps organize the graph
		GLine upperLine = new GLine(0, yLineSep, totalWidth, yLineSep);
		add(upperLine);
		
		//The lower line helps construct a series of boxes where the decade number will be placed
		GLine lowerLine = new GLine(0, (totalHeight - yLineSep),
				totalWidth, (totalHeight - yLineSep));
		add(lowerLine);
	}
	
	
	private void drawDecadeLabels() {
		double totalWidth = getWidth();
		double totalHeight = getHeight();
		
		double xSep = totalWidth / NDECADES;  //Separation between each GLabel
		double xStart = 0; //Starting point of the first GLabel
		
		int decadeNumber = START_DECADE; 
		int decadeInterval = 10; //10 year intervals
		
		
		
		for(int i = 0; i < NDECADES; i++) {
			GLabel decades = new GLabel(" "+ decadeNumber +" ");
			add(decades, xStart, totalHeight);
			
			decadeNumber = decadeNumber + decadeInterval;
			xStart = xStart + xSep;
		}
	}
	
	private void checkForEntry() {
		if(entriesShown.size() >= 0) {
			for(int i = 0; i < entriesShown.size(); i++) {
                NameSurferEntry entries = entriesShown.get(i);
                drawEntry(entries, i);
            }
		}
	}
	
	private void drawEntry(NameSurferEntry entry, int colourNumber) {
		/* This portion of the program consists of creating a line where
		 * its two ends present a name's rank and consecutive rank.
		 * Each end of the line consists of an x, y coordinate.
		 * 
		 * The x-coordinate is simple because it changes by a certain amount of pixels.
		 * The y-coordinate uses the rank and factors in:
		 * 		-Space at the top and bottom
		 * 		-Uses a ratio to convert the y-pixels to a rank of 1000.
		 */

		double totalWidth = getWidth();
		double totalHeight = getHeight();
	
		double xSep = totalWidth / NDECADES; //Separation between each decade
		
		double xPoint1 = 0;  //X-coordinate of the first point 
		double xPoint2 = xSep; //X-coordinate of the second point
		double yPoint1 = 0; //Y-coordinate of the first point 
		double yPoint2 = 0; //Y-coordinate of the second point
		
		//Number of actual times the name and rank will be displayed.
		int numOfReps = NDECADES - 1;		
		
		for(int i = 0; i < numOfReps; i++) {
			double rank1 = entry.getRank(i); //Rank of first point
	        double rank2 = entry.getRank(i+1); //Rank of second point
	        
	        if(rank1 == 0 && rank2 == 0) {
	        	yPoint1 = totalHeight - GRAPH_MARGIN_SIZE;
	        	yPoint2 = totalHeight - GRAPH_MARGIN_SIZE;
	        }
	        
	        else if(rank1 != 0 && rank2 != 0) {
	        	yPoint1 = ((getHeight() - GRAPH_MARGIN_SIZE * 2) * (rank1 / MAX_RANK))
	        			+ GRAPH_MARGIN_SIZE;
	        	yPoint2 = ((getHeight() - GRAPH_MARGIN_SIZE * 2) * (rank2 / MAX_RANK))
	        			+ GRAPH_MARGIN_SIZE;
	        }
	        
	        else if(rank1 !=0 && rank2 == 0) {
	        	yPoint1 = ((getHeight() - GRAPH_MARGIN_SIZE * 2) * (rank1 / MAX_RANK))
	        			+ GRAPH_MARGIN_SIZE;
	        	yPoint2 = totalHeight - GRAPH_MARGIN_SIZE;
	        }
	        
	        else if(rank1 == 0 && rank2 != 0) {
	        	yPoint1 = totalHeight - GRAPH_MARGIN_SIZE;
	        	yPoint2 = ((getHeight() - GRAPH_MARGIN_SIZE * 2) * (rank2 / MAX_RANK))
	        			+ GRAPH_MARGIN_SIZE;
	        }
	        
			GLine line = new GLine(xPoint1, yPoint1,
					xPoint2, yPoint2);
			
			if((colourNumber % 4) == 1) {
				line.setColor(Color.RED);
				}
			
			else if((colourNumber % 4) == 2) {
				line.setColor(Color.CYAN);
				}
			
			else if((colourNumber % 4) == 3) {
				line.setColor(Color.BLUE);
			}
			
			add(line);
			
			xPoint1 = xPoint1 + xSep; //Updates the first x-coordinates
			xPoint2 = xPoint2 + xSep;  //Updates the second x-coordinates
		}
		
		double xLabel = 0; //X-coordinate of the GLabel
		
		//Add the labels at each point containing the name and the rank.
		for(int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);
			String name = entry.getName();
		
			double yLabel;
			
			//To find the y-coordinate of the GLabel
			if(rank != 0) {
				yLabel = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) 
						* rank / MAX_RANK - 5;
			}
			else{
				yLabel = getHeight() - GRAPH_MARGIN_SIZE - 5;
			}			
			
			GLabel label = new GLabel(" "+ name +" "+ rank +" ");
			if(rank == 0) {
				label = new GLabel(" "+ name +" *");
			}
			
			if((colourNumber % 4) == 1) {
				label.setColor(Color.RED);
			}
			
			else if((colourNumber % 4) == 2) {
				label.setColor(Color.CYAN);
			}
			
			else if((colourNumber % 4) == 3) {
				label.setColor(Color.BLUE);
			}
			
			add(label, xLabel, yLabel);
			xLabel = xLabel + xSep;
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	private ArrayList <NameSurferEntry> entriesShown;
}
