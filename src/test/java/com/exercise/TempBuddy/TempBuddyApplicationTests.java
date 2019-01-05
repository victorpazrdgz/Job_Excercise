package com.exercise.TempBuddy;

import java.io.IOException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.exercise.TempBuddy.Controller.*;
import com.exercise.TempBuddy.Model.Matching;
import com.exercise.TempBuddy.Model.Shift;
import com.exercise.TempBuddy.Model.Worker;
import com.exercise.TempBuddy.helpers.WorkersShiftsList;

import junit.framework.Assert;

/**
 * 
 * @author VIPR
 * 
 *         This Class contain a simple test for the End point
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TempBuddyApplicationTests {
	String solution;
	List<Worker> listWorkers;
	List<Shift> listShifts;
	WorkersShiftsList workersShiftsList;
	List<Matching> listMatching;

	@Before
	public void setupTest() {
		String json = "{\\r\\n  \\\"workers\\\": [\\r\\n    {\\r\\n      \\\"id\\\": 1,\\r\\n      \\\"availability\\\": [\\\"Tuesday\\\"],\\r\\n      \\\"payrate\\\": 7.50\\r\\n    },\\r\\n    {\\r\\n      \\\"id\\\": 2,\\r\\n      \\\"availability\\\": [\\\"Monday\\\"],\\r\\n      \\\"payrate\\\": 9.00\\r\\n    },\\r\\n    {\\r\\n      \\\"id\\\": 3,\\r\\n      \\\"availability\\\": [\\\"Friday\\\"],\\r\\n      \\\"payrate\\\": 8.00\\r\\n    }\\r\\n  ],\\r\\n  \\\"shifts\\\": [\\r\\n    {\\r\\n      \\\"id\\\": 1,\\r\\n      \\\"day\\\": [\\\"Monday\\\"]\\r\\n    },\\r\\n    {\\r\\n      \\\"id\\\": 2,\\r\\n      \\\"day\\\": [\\\"Tuesday\\\"]\\r\\n    },\\r\\n    {\\r\\n      \\\"id\\\": 3,\\r\\n      \\\"day\\\": [\\\"Friday\\\"]\\r\\n    }\\r\\n  ]\\r\\n}\\r\\n";
		ObjectMapper mapper = new ObjectMapper();

		try {
			listWorkers = mapper.readValue(json, new TypeReference<List<Worker>>() {
			});
			listShifts = mapper.readValue(json, new TypeReference<List<Shift>>() {
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		workersShiftsList = new WorkersShiftsList(listWorkers, listShifts);
	}

	@Test
	public void matchingControllerTest() {

		MatchingController matching = new MatchingController();
		try {
			listMatching = matching.listMatchings(workersShiftsList);
			for(Matching match : listMatching) {
				if (!match.getWorker().getAvailability().contains(match.getShift().getDay().get(0)))
					Assert.fail();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
