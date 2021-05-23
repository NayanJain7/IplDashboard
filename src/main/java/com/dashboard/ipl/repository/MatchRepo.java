package com.dashboard.ipl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dashboard.ipl.model.Match;


public interface MatchRepo extends CrudRepository<Match, Long> {

	@Query("select team1, count(team1) from Match m group by team1")
	public List<String> getTeam1NameAndMatch();

	@Query("select team2, count(team2) from Match m group by team2")
	public List<String> getTeam2NameAndMatch();

	@Query("select matchWinner, count(matchWinner) from Match m group by matchWinner")
	public List<String> getTotalMatchWinner();

	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

	@Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc")
    List<Match> getMatchesByTeamBetweenDates(
        @Param("teamName") String teamName, 
        @Param("dateStart") LocalDate dateStart, 
        @Param("dateEnd") LocalDate dateEnd
    );
	

	default List<Match> findLatestMatchesByTeam(String teamName, int count) {

		Pageable pageable = PageRequest.of(0, count);

		return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
	}

}
