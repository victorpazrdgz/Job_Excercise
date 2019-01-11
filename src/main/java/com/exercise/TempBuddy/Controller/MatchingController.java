package com.exercise.TempBuddy.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exercise.TempBuddy.Model.Matching;
import com.exercise.TempBuddy.Model.Shift;
import com.exercise.TempBuddy.Model.Worker;
import com.exercise.TempBuddy.Solution.WorkerRoster;
import com.exercise.TempBuddy.helpers.WorkersShiftsList;

/**
 * 
 * @author VIPR
 * 
 *         This Class contain the Endpoint for the application
 *
 */

@Controller
@RequestMapping(value = "/api", produces = { "application/json" })
public class MatchingController {

	/**
	 * 
	 * @param workerShiftList
	 * @return
	 * @throws Exception
	 * 
	 *             This method receives a list of workers and another list of shifts
	 *             through the body of a POST request in JSON format, calls the
	 *             scheduler with these parameters, and if the solution satisfies
	 *             the requirements posed a list of workers and shifts is sent to
	 *             the origin of the request in the body of a JSON message, if it
	 *             does not return an error.
	 */
	private static final Logger logger = LogManager.getLogger(MatchingController.class);

	@RequestMapping(value = "/matchings", method = RequestMethod.POST, produces = { "application/json" })

	public @ResponseBody List<Matching> listMatchings(@RequestBody WorkersShiftsList workerShiftList) throws Exception {

		List<Worker> listWorkers = new ArrayList<Worker>();
		List<Shift> listShifts = new ArrayList<Shift>();

		WorkerRoster unsolvedWorkerRoster = new WorkerRoster();
		WorkerRoster solvedWorkerRoster = new WorkerRoster();

		boolean isOptimal = true;

		List<Integer> workersId = new ArrayList<Integer>();

		try {
			for (Worker worker : workerShiftList.getWorkers()) {
				listWorkers.add(worker);
			}

			unsolvedWorkerRoster.getWorkersList().addAll(listWorkers);

			for (Shift shift : workerShiftList.getShifts()) {
				listShifts.add(shift);
			}

			unsolvedWorkerRoster.getShiftsList().addAll(listShifts);

			for (int i = 0; i < listShifts.size(); i++) {
				unsolvedWorkerRoster.getMatchingList().add(new Matching());

			}
		} catch (Exception e) {
			logger.error("Problem Recieving Data");
			e.printStackTrace();
			throw new Exception(String.format("Problem Recieving Data"));
		}
		try {
			SolverFactory<WorkerRoster> solverFactory = SolverFactory
					.createFromXmlResource("solver/workerRosterSolverConfig.xml");

			Solver<WorkerRoster> solver = solverFactory.buildSolver();

			solvedWorkerRoster = solver.solve(unsolvedWorkerRoster);

			List<Matching> listMatchigs = new ArrayList<Matching>();

			listMatchigs.addAll(solvedWorkerRoster.getMatchingList());

			for (int i = 0; i < listMatchigs.size(); i++) {

				if (listMatchigs.get(i).getShift() != null) {
					if (!listMatchigs.get(i).getWorker().getAvailability()
							.contains(listMatchigs.get(i).getShift().getDay().get(0)))
						isOptimal = false;
				} else
					isOptimal = false;
				if (listMatchigs.get(i).getWorker() != null) {
					if (workersId != null) {

//						for (int k = 0; k < workersId.size(); k++) {
//
//							if (workersId.get(k) == listMatchigs.get(i).getWorker().getId()
//									&& listWorkers.size() == listShifts.size())
//								isOptimal = false;
//						}
						if (isOptimal && !workersId.contains(listMatchigs.get(i).getWorker().getId()))
							workersId.add(listMatchigs.get(i).getWorker().getId());

					}
				}
			}
			if (workersId.size() != listWorkers.size())
				isOptimal = false;
			else
				isOptimal = true;
			if (isOptimal) {
				return solvedWorkerRoster.getMatchingList();
			} else {
				for (int i = 0; i < solvedWorkerRoster.getMatchingList().size(); i++)
					if (listMatchigs.get(i).getShift() != null)
						logger.info("Worker Assigned shift : "
								+ solvedWorkerRoster.getMatchingList().get(i).getWorker().getId()
								+ " Shift Assigned  : "
								+ solvedWorkerRoster.getMatchingList().get(i).getShift().getId());
				throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT, "Can't Get an Optimal Solution",
						new Exception());
			}

		} catch (Exception e) {
			logger.error("Can't Get an Optimal Solution");
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT, "Can't Get an Optimal Solution", e);
		}
	}
}
