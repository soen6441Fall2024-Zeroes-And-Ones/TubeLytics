package controllers;

import models.ReadabilityCalculator;
import play.mvc.*;
import services.YouTubeService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class YouTubeController extends Controller {

    private YouTubeService youtubeService;

    public YouTubeController(YouTubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    // Action to handle video readability calculation
    public CompletionStage<Result> getReadability(String searchQuery) {
        // Fetch video descriptions asynchronously
        return youtubeService.getVideoDescriptions(searchQuery).thenApply(descriptions -> {
            // Calculate the average readability score for all descriptions
            double totalReadingEase = 0;
            double totalGradeLevel = 0;
            int count = descriptions.size();

            for (String description : descriptions) {
                ReadabilityCalculator.ReadabilityScores scores = ReadabilityCalculator.calculateScores(description);
                totalReadingEase += scores.readingEase;
                totalGradeLevel += scores.gradeLevel;
            }

            double averageReadingEase = totalReadingEase / count;
            double averageGradeLevel = totalGradeLevel / count;

            return ok(views.html.readability.render(averageReadingEase, averageGradeLevel, descriptions));
        });
    }
}
