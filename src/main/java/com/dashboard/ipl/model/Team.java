package com.dashboard.ipl.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor
@Data
@Entity
public class Team {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	private String teamName;
	
	private long totalMatch;

	private long totalWins;

	private LocalDate latestPlayedYear;
	
	
	@Transient
	private List<Match> matches;
	

	public Team(@NonNull String teamName,  long totalMatch) {
		super();
		this.teamName = teamName;
		this.totalMatch = totalMatch;
	}


}
