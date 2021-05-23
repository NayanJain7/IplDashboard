package com.dashboard.ipl.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.ipl.model.Match;
import com.dashboard.ipl.repository.MatchRepo;
import com.dashboard.ipl.service.TeamPerformance;

@RestController
@CrossOrigin
@RequestMapping("/team")
public class MatchController {

	
	@Autowired
	private MatchRepo matchRepo;

	@Autowired
	private TeamPerformance teamPerformance;

	// Return list of all matches played by a team specific year
	@GetMapping("/{teamName}/matches")
	public List<Match> getMatchesByDate(@PathVariable String teamName, @RequestParam int year) {

		LocalDate startDate = LocalDate.of(year, 1, 1);

		LocalDate endDate = LocalDate.of(year + 1, 1, 1);

		return matchRepo.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
	}

	// Return a list of all team wins per year

	@GetMapping("/all-wins/{teamName}")
	public Map<Integer, Integer> getTeamPerformance(@PathVariable String teamName) {

		return teamPerformance.getAllTeamWinsOverAllYear(teamName);

	}

}
