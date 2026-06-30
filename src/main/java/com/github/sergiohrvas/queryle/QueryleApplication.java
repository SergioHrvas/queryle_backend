package com.github.sergiohrvas.queryle;


import java.time.LocalDate;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.sergiohrvas.queryle.challenges.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.models.valueobjects.Query;

@SpringBootApplication
public class QueryleApplication {

	public static void main(String[] args) {
		//SpringApplication.run(QueryleApplication.class, args);
		try {
			Query valid = new Query(" SELECT * FROM   usuarios WHERE id=3");
			System.out.println("OK: " + valid.value());
		} catch (Exception e){
			System.out.println("ERROR: " + e.getMessage());
		}

		try {
			Query valid = new Query("   DELETE FROM usuarios   WHERE id=3");
			System.out.println("OK: " + valid.value());
		} catch (Exception e){
			System.out.println("ERROR: " + e.getMessage());
		}

		try {
			DailyChallenge dailyChallenge = new DailyChallenge("Obtén los usuarios que se llamen Pepe", new Query("  DELETE FROM usuarios WHERE id = 3"), Difficulty.EASY, LocalDate.now(), UUID.randomUUID());
			System.out.println("OK" + dailyChallenge.getFormulation());
		} catch (Exception e){
			System.out.println("ERROR: " + e.getMessage());
		}

		try {
			DailyChallenge dailyChallenge = new DailyChallenge("Obtén los usuarios que se llamen Pepe", new Query("  SELECT * FROM usuarios WHERE name=\'Pepe\'"), Difficulty.EASY, LocalDate.now(), UUID.randomUUID());
			System.out.println("OK" + dailyChallenge.getFormulation());
		} catch (Exception e){
			System.out.println("ERROR: " + e.getMessage());
		}

		try {
			DailyChallenge dailyChallenge = new DailyChallenge("Obtén los usuarios que se llamen Pepe", new Query("  SELECT * FROM usuarios WHERE name=\'Pepe\'"), null, LocalDate.now(), UUID.randomUUID());
			System.out.println("OK" + dailyChallenge.getFormulation());
		} catch (Exception e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}
