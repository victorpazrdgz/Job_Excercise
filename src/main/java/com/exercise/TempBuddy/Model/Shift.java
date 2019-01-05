package com.exercise.TempBuddy.Model;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author VIPR
 * 
 *         This class contains the shift model that we receive through the End
 *         point
 *
 */
@XStreamAlias("Shift")
public class Shift {

	private int id;
	private ArrayList<String> day;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> getDay() {
		return day;
	}

	public void setDay(ArrayList<String> day) {
		this.day = day;
	}

}
