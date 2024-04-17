package com.khushiagrawall.virtuallibrarysystem.analyzers;

import com.khushiagrawall.virtuallibrarysystem.models.Book;
import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularAuthorAnalyzer {

    public  void analyzeAuthorTrends(List<Book> books, List<TransactionLog> logs) {
        Map<String, Integer> authorPopularity = calculateAuthorPopularity(books, logs);
        printAuthorPopularity(authorPopularity);
    }

    private  Map<String, Integer> calculateAuthorPopularity(List<Book> books, List<TransactionLog> logs) {
        Map<String, Integer> authorPopularity = new HashMap<>();
        for (Book book : books) {
            int borrowCount = getBorrowCountForBook(book, logs);
            if (borrowCount > 0) {
                authorPopularity.put(book.getAuthor(), authorPopularity.getOrDefault(book.getAuthor(), 0) + borrowCount);
            }
        }
        return authorPopularity.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, HashMap::new));
    }

    private  int getBorrowCountForBook(Book book, List<TransactionLog> logs) {
        return (int) logs.stream()
                .filter(log -> log.getISBN().equals(book.getISBN()))
                .count();
    }

    private  void printAuthorPopularity(Map<String, Integer> authorPopularity) {
        System.out.println("Author Popularity:");
        authorPopularity.forEach((author, count) ->
                System.out.println(author + ": " + count));
    }
}