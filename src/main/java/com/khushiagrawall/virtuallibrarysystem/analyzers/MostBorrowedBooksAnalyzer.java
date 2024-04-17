package com.khushiagrawall.virtuallibrarysystem.analyzers;

import com.khushiagrawall.virtuallibrarysystem.models.Book;
import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MostBorrowedBooksAnalyzer {
    public  void MostBorrowedAnalyzer(List<Book> books, List<TransactionLog>logs, int limit) {
        if (logs.isEmpty() || books.isEmpty()) {
            System.out.println("No transaction logs or books found. Cannot analyze most borrowed books.");
            return;
        }

        Map<String, Integer> borrowCountByISBN = logs.stream()
                .filter(log -> !log.getReturned().equalsIgnoreCase("Yes"))
                .collect(Collectors.groupingBy(TransactionLog::getISBN, Collectors.summingInt(log -> 1)));

        System.out.println("Most Borrowed Books:");
        borrowCountByISBN.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .forEach(entry -> {
                    String isbn = entry.getKey();
                    int borrowCount = entry.getValue();
                    Book book = findBookByISBN(books, isbn);
                    if (book != null) {
                        System.out.println(book.getTitle() + " by " + book.getAuthor() + ": " + borrowCount + " times borrowed");
                    }
                });
    }

    private  Book findBookByISBN(List<Book> books, String isbn) {
        return books.stream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
    }
}