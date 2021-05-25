package com.dashboard.ipl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.ipl.model.Team;
import com.dashboard.ipl.repository.MatchRepo;
import com.dashboard.ipl.repository.TeamRepo;
import com.dashboard.ipl.service.TeamsData;

@RestController
@CrossOrigin
public class TeamController {

	@Autowired
	private  TeamRepo teamRepo;
	
	//service class
	@Autowired
	private  TeamsData teamsData;
	

	// Return list of all teams that ever played IPL
	@GetMapping("/team/all-teamname")
	public Iterable<Team> getAllTeamNames() {

		return teamRepo.findAll();
	}

	// Return Team with list of latest 4 matches
	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {

		return teamsData.getTeamDetails(teamName, 4);

	}

	@GetMapping("/All-Team-History")
	public List<Team> getAllTeamHistory() {

		return teamsData.getAllTeamsDataAndMatches(getAllTeamNames());

	}

}
