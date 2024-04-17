package com.khushiagrawall.virtuallibrarysystem.analyzers;

import com.khushiagrawall.virtuallibrarysystem.models.Book;
import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenreTrendAnalyzer {
    public  void analyzeGenreTrends(List<Book> books, List<TransactionLog> logs) {
        Map<String, Integer> genrePopularity = calculateGenrePopularity(books, logs);
        printGenrePopularity(genrePopularity);
    }

    private  Map<String, Integer> calculateGenrePopularity(List<Book> books, List<TransactionLog> logs) {
        Map<String, Integer> genrePopularity = new HashMap<>();
        for (Book book : books) {
            int borrowCount = getBorrowCountForBook(book, logs);
            if (borrowCount > 0) {
                genrePopularity.put(book.getGenre(), genrePopularity.getOrDefault(book.getGenre(), 0) + borrowCount);
            }
        }
        return genrePopularity.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private  int getBorrowCountForBook(Book book, List<TransactionLog> logs) {
        return (int) logs.stream()
                .filter(log -> log.getISBN().equals(book.getISBN()))
                .count();
    }

    private  void printGenrePopularity(Map<String, Integer> genrePopularity) {
        System.out.println("Genre Popularity:");
        genrePopularity.forEach((genre, count) ->
                System.out.println(genre + ": " + count));
    }
}