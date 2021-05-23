package com.dashboard.ipl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.ipl.model.Team;
import com.dashboard.ipl.repository.MatchRepo;
import com.dashboard.ipl.repository.TeamRepo;

@Service
public class TeamsData {

	@Autowired
	private TeamRepo teamRepo;
	@Autowired
	private MatchRepo matchRepo;
	
	
	public Team getTeamDetails(String teamName,int count ) {
		Team team = teamRepo.findByTeamName(teamName);

		team.setMatches(matchRepo.findLatestMatchesByTeam(teamName, count));
		
		return team;
	}
	
	
	public List<Team> getAllTeamsDataAndMatches(Iterable<Team> allTeamNames){
		
		List<Team> allTeamsData = new ArrayList<>();
		

		for (Team team : allTeamNames) {
			
			allTeamsData.add(getTeamDetails(team.getTeamName(),5));
		}
		
		return allTeamsData;
		
	}

	
	
}
