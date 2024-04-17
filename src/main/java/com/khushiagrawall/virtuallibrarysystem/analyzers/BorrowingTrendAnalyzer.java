package com.khushiagrawall.virtuallibrarysystem.analyzers;

import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BorrowingTrendAnalyzer {

    public  void analyzeBorrowingTrendsPerMonth(List<TransactionLog> log) {


        if (log.isEmpty()) {
            System.out.println("No transaction logs found. Cannot analyze borrowing trends.");
            return;
        }

        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Initialize an array to store borrowing counts for each month
        int[] borrowingCounts = new int[12];

        // Loop through transaction logs and count borrowings for each month
        for (TransactionLog lg :log) {
            if (lg.getReturned().equalsIgnoreCase("No") && lg.getBorrowDate().getYear() == currentYear) {
                int monthIndex = lg.getBorrowDate().getMonthValue() - 1; // Month index starts from 0
                borrowingCounts[monthIndex]++;
            }
        }

        // Print borrowing trends for each month
        System.out.println("Borrowing Trends per Month:");
        for (int i = 0; i < 12; i++) {
            Month month = Month.of(i + 1);
            int borrowingCount = borrowingCounts[i];
            if (borrowingCount > 0) {
                System.out.println(month.toString() + ": " + borrowingCount + " books borrowed");
            }
        }
    }

    public  void analyzeBorrowingTrendsPerQuarter(List<TransactionLog> log) {


        if (log.isEmpty()) {
            System.out.println("No transaction logs found. Cannot analyze borrowing trends.");
            return;
        }

        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Initialize a map to store borrowing counts for each quarter
        Map<Integer, Integer> quarterBorrowingCounts = new TreeMap<>();

        // Loop through transaction logs and count borrowings for each quarter
        for (TransactionLog lg : log) {
            if (lg.getReturned().equalsIgnoreCase("No") && lg.getBorrowDate().getYear() == currentYear) {
                int month = lg.getBorrowDate().getMonthValue();
                int quarter = (month - 1) / 3 + 1; // Determine quarter based on month
                quarterBorrowingCounts.put(quarter, quarterBorrowingCounts.getOrDefault(quarter, 0) + 1);
            }
        }

        // Print borrowing trends for each quarter
        System.out.println("Borrowing Trends per Quarter:");
        quarterBorrowingCounts.forEach((quarter, count) ->
                System.out.println("Quarter " + quarter + ": " + count + " books borrowed"));
    }

    public  void analyzeBorrowingTrendsPerYear(List<TransactionLog> log, int year) {


        if (log.isEmpty()) {
            System.out.println("No transaction logs found. Cannot analyze borrowing trends.");
            return;
        }

        // Initialize a map to store borrowing counts for each month
        Map<Month, Integer> borrowingCounts = new TreeMap<>();

        // Loop through transaction logs and count borrowings for each month of the specified year
        for (TransactionLog lg : log) {
            if (lg.getReturned().equalsIgnoreCase("No") && lg.getBorrowDate().getYear() == year) {
                Month month = lg.getBorrowDate().getMonth();
                borrowingCounts.put(month, borrowingCounts.getOrDefault(month, 0) + 1);
            }
        }

        // Print borrowing trends for the specified year
        System.out.println("Borrowing Trends for Year " + year + ":");
        borrowingCounts.forEach((month, count) ->
                System.out.println(month.toString() + ": " + count + " books borrowed"));
    }
}