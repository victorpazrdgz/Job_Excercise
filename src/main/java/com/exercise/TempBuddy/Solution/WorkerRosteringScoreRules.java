package com.exercise.TempBuddy.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.exercise.TempBuddy.Model.Matching;

/**
 * 
 * @author VIPR
 *
 *         This class calculates the score to obtain the best possible solution.
 */
public class WorkerRosteringScoreRules implements EasyScoreCalculator<WorkerRoster> {

	/**
	 * This method receives the class annotated with @PlanningSolution and first performs a for loop
	 * to sort in an array the pay rate of the workers from highest to lowest and
	 * then another for loop in which the score for assigning shifts and workers is
	 * calculated.   The highest score is the best solution.
	 */
	public HardSoftScore calculateScore(WorkerRoster workerRoster) {
		int hardScore = 0;
		int softScore = 0;
		boolean containWorker = false;
		LinkedHashSet<String> assignedShifts = new LinkedHashSet<>();
		LinkedHashSet<String> assignedworkers = new LinkedHashSet<>();
		List<Double> payratelist = new ArrayList<Double>();

		for (Matching matchaux : workerRoster.getMatchingList()) {
			if (matchaux.getWorker() != null) {

				payratelist.add(matchaux.getWorker().getPayrate());
				Comparator<Double> comparador = Collections.reverseOrder();
				Collections.sort(payratelist, comparador);
			}

		}

		for (Matching match : workerRoster.getMatchingList()) {

			if (match.getWorker() != null && match.getShift() != null) {

				String assignShift = match.getShift().getId() + ":";
				String assignWorker = ":" + match.getWorker().getId();
				if (assignedShifts.contains(assignShift) && assignedworkers.contains(assignWorker)) {
					hardScore += -1;
				} else {
					containWorker = false;
					if (!assignedShifts.contains(assignShift)) {
						for (int i = 0; i < match.getWorker().getAvailability().size(); i++) {

							if (match.getWorker().getAvailability().get(i).equals(match.getShift().getDay().get(0))) {

								if (!assignedworkers.contains(assignWorker)) {
									hardScore += 100;
									assignedworkers.add(assignWorker);

								} else {
									hardScore += 1 + payratelist.indexOf(match.getWorker().getPayrate());

								}

								assignedShifts.add(assignShift);
								containWorker = true;

							}

						}
						if (!containWorker) {
							hardScore += -1;
						}

					} else
						hardScore += -1;
				}

			} else {
				hardScore += -1;
			}

		}
		return HardSoftScore.of(hardScore, softScore);
	}

}
