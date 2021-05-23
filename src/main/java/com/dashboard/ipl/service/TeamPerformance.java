package com.dashboard.ipl.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.ipl.model.Match;
import com.dashboard.ipl.repository.MatchRepo;

@Service
public class TeamPerformance {

	@Autowired
	private MatchRepo matchRepo;

	LocalDate startDate;

	LocalDate endDate;

	public Map<Integer, Integer> getAllTeamWinsOverAllYear(String teamName) {

		Map<Integer, Integer> winOverYear = new HashMap<>();

		

		for (int i = 2008; i <= 2020; i++) {

			startDate = LocalDate.of(i, 1, 1);

			endDate = LocalDate.of(i + 1, 1, 1);

			List<Match> matches = matchRepo.getMatchesByTeamBetweenDates(teamName, startDate, endDate);

			int winCount = 0;

			for (Match match : matches) {

				if (teamName.equals(match.getMatchWinner())) {
					winCount++;
				}
			}

//			winList.add(winCount);

			winOverYear.put(i, winCount);

		}

		return winOverYear;
	}

}
