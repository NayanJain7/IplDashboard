package com.dashboard.ipl.repository;

import org.springframework.data.repository.CrudRepository;


import com.dashboard.ipl.model.Team;


public interface TeamRepo extends CrudRepository<Team, Long> {

	Team findByTeamName(String teamname);
}