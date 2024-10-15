package models;

//This class will contain methods to calculate Flesch-Kincaid scores.
public class ReadabilityCalculator {

    public static double calculateFleschReadingEase(int totalWords, int totalSentences, int totalSyllables) {
        if (totalSentences == 0 || totalWords == 0) return 0;
        return 206.835 - 1.015 * ((double) totalWords / totalSentences) - 84.6 * ((double) totalSyllables / totalWords);
    }

    public static double calculateFleschKincaidGradeLevel(int totalWords, int totalSentences, int totalSyllables) {
        if (totalSentences == 0 || totalWords == 0) return 0;
        return 0.39 * ((double) totalWords / totalSentences) + 11.8 * ((double) totalSyllables / totalWords) - 15.59;
    }

    public static int countSyllables(String word) {
        // Simple syllable counting logic; refine as necessary
        word = word.toLowerCase();
        if (word.isEmpty()) return 0;
        int count = 0;
        boolean lastWasVowel = false;
        for (char c : word.toCharArray()) {
            if ("aeiouy".indexOf(c) != -1) {
                if (!lastWasVowel) {
                    count++;
                    lastWasVowel = true;
                }
            } else {
                lastWasVowel = false;
            }
        }
        return count;
    }

    //Add Methods for Counting Words: Add methods to count the total words in a given text.
    public static int countWords(String text) {
        if (text == null || text.isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }
    //Add Methods for Sentences: Add methods to count the total sentences in a given text.
    public static int countSentences(String text) {
        if (text == null || text.isEmpty()) return 0;
        return text.split("[.!?]+").length;
    }

    //Combine Counts for Readability Calculation : Create a method that takes the video description and returns the readability scores.
    public static ReadabilityScores calculateScores(String description) {
        int totalWords = countWords(description);
        int totalSentences = countSentences(description);
        int totalSyllables = 0;
        for (String word : description.split("\\s+")) {
            totalSyllables += countSyllables(word);
        }
        double readingEase = calculateFleschReadingEase(totalWords, totalSentences, totalSyllables);
        double gradeLevel = calculateFleschKincaidGradeLevel(totalWords, totalSentences, totalSyllables);
        return new ReadabilityScores(readingEase, gradeLevel);
    }

    public static class ReadabilityScores {
        public double readingEase;
        public double gradeLevel;

        public ReadabilityScores(double readingEase, double gradeLevel) {
            this.readingEase = readingEase;
            this.gradeLevel = gradeLevel;
        }
    }



}
