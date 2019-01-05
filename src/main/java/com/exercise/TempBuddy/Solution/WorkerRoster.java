package com.exercise.TempBuddy.Solution;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.exercise.TempBuddy.Model.Matching;
import com.exercise.TempBuddy.Model.Shift;
import com.exercise.TempBuddy.Model.Worker;

/**
 * 
 * @author VIPR
 *
 *         This solution class contains all the problem facts, planning entities
 *         and a score. The data collection facts getter that is annotated
 *         with @ProblemFactCollectionProperty Planner needs to extract the
 *         entity instances from the solution instance. It gets those
 *         collections by calling every getter that is annotated
 *         with @PlanningEntityCollectionProperty. A Solution requires a score
 *         property , which is annotated with a @PlanningScore annotation. The
 *         score property is null if the the score hasn’t been calculated yet.
 */
@PlanningSolution
public class WorkerRoster {

	private List<Worker> workersList = new ArrayList<>();
	private List<Shift> shiftsList = new ArrayList<>();
	private List<Matching> matchingList = new ArrayList<>();
	private HardSoftScore score;

	public WorkerRoster() {
		workersList = new ArrayList<>();
		shiftsList = new ArrayList<>();
		matchingList = new ArrayList<>();
	}

	@ValueRangeProvider(id = "availableWorkers")
	@ProblemFactCollectionProperty
	public List<Worker> getWorkersList() {
		return workersList;
	}

	@ValueRangeProvider(id = "availableShifts")
	@ProblemFactCollectionProperty
	public List<Shift> getShiftsList() {
		return shiftsList;
	}

	@PlanningEntityCollectionProperty
	public List<Matching> getMatchingList() {

		return matchingList;
	}

	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	public void setWorkersList(List<Worker> workersList) {
		this.workersList = workersList;
	}

	public void setShiftsList(List<Shift> shiftsList) {
		this.shiftsList = shiftsList;
	}

	public void setMatchingList(List<Matching> matchingList) {
		this.matchingList = matchingList;
	}

}
