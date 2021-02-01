package main;

import service.BowlingScoreEngine;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if(args != null && args.length > 0){
            BowlingScoreEngine b = new BowlingScoreEngine();
            HashMap<String,Integer> response = b.processScoreGame(args[0]);

            if(response != null) {
                System.out.println("\nSummarize:");
                response.entrySet().stream().forEach(e -> System.out.println(e.getKey() + " - " + e.getValue()));
            }
        }
    }
}
