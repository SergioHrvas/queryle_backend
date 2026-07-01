package com.github.sergiohrvas.queryle;


import java.time.LocalDate;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

@SpringBootApplication
public class QueryleApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryleApplication.class, args);
	}
}
