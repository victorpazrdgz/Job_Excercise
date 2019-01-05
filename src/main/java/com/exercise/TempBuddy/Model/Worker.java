package com.exercise.TempBuddy.Model;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author VIPR
 * 
 *         This class contains the worker model that we receive through the End
 *         point
 *
 */
@XStreamAlias("Worker")
public class Worker {
	private int id;
	private ArrayList<String> availability;
	private double payrate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> getAvailability() {
		return availability;
	}

	public void setAvailability(ArrayList<String> availability) {
		this.availability = availability;
	}

	public double getPayrate() {
		return payrate;
	}

	public void setPayrate(double payrate) {
		this.payrate = payrate;
	}

}
