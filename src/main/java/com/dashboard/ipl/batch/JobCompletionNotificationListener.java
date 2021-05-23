package com.dashboard.ipl.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dashboard.ipl.model.Match;
import com.dashboard.ipl.model.Team;
import com.dashboard.ipl.repository.MatchRepo;
import com.dashboard.ipl.repository.TeamRepo;


@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	private  MatchRepo matchRepo;
	
	@Autowired
	private TeamRepo teamRepo;
	

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<String> list1 = matchRepo.getTeam1NameAndMatch();

			List<String> list2 = matchRepo.getTeam2NameAndMatch();

			List<String> winnerList = matchRepo.getTotalMatchWinner();

			Map<String, Team> mp = new HashMap<>();

			for (int i = 0; i < list1.size(); i++) {

				String[] split = list1.get(i).split(",");
				
				
				mp.put(split[0], new Team(split[0], Long.parseLong(split[1])));

			}

			for (int i = 0; i < list2.size(); i++) {

				String[] split = list2.get(i).split(",");
				
				
				if (mp.containsKey(split[0])) {

					Team team = mp.get(split[0]);

					long totalMatch = team.getTotalMatch();

					long newTotal = totalMatch + Long.parseLong(split[1]);

					team.setTotalMatch(newTotal);
					
					List<Match> latestMatch = matchRepo.findLatestMatchesByTeam(split[0], 1);
					
					
					team.setLatestPlayedYear(latestMatch.get(0).getDate());

					mp.put(split[0], team);
				} else {
					mp.put(split[0], new Team(split[0], Long.parseLong(split[1])));

				}

			}
			
			for (int i = 0; i < winnerList.size(); i++) {

				String[] split = winnerList.get(i).split(",");
				
				

				if (mp.containsKey(split[0])) {

					Team team = mp.get(split[0]);

					team.setTotalWins(Long.parseLong(split[1]));
				

					mp.put(split[0], team);
				}
				

			}

			Set<Entry<String, Team>> entrySet = mp.entrySet();

			for (Entry<String, Team> entry : entrySet) {

				teamRepo.save(entry.getValue());

			}

		}
	}

}
