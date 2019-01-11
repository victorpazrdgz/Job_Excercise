package com.exercise.TempBuddy.Model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author VIPR
 *
 *         The class annotated with @PlanningEntity is the class that changes
 *         during solving. A genuine planning variable getter needs to be
 *         annotated with the @PlanningVariable annotation, which needs a
 *         non-empty valueRangeProviderRefs property.
 */
@PlanningEntity
@XStreamAlias("Matchings")
public class Matching {

	private Shift shift;

	private Worker worker;

	@PlanningVariable(valueRangeProviderRefs = { "availableShifts" }, nullable = true)

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	@PlanningVariable(valueRangeProviderRefs = { "availableWorkers" }, nullable = true)
	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

}
