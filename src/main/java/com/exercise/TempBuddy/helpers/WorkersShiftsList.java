package com.exercise.TempBuddy.helpers;

import java.util.List;

import com.exercise.TempBuddy.Model.Shift;
import com.exercise.TempBuddy.Model.Worker;

/**
 * 
 * @author VIPR
 * 
 *         This class contains the lists received at the end point and the
 *         getters and setters for this list
 * 
 */
public class WorkersShiftsList {

	private List<Worker> workers;

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	private List<Shift> shifts;

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}
	
	public WorkersShiftsList(List<Worker> listWorkers, List<Shift> listShifts) {
		this.workers = listWorkers;
		this.shifts = listShifts;
	}
	public WorkersShiftsList() {
		
	}

}
