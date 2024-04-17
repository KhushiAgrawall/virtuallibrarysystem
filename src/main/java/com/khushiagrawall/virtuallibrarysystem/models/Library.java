package com.khushiagrawall.virtuallibrarysystem.models;
import com.khushiagrawall.virtuallibrarysystem.analyzers.BorrowingTrendAnalyzer;
import com.khushiagrawall.virtuallibrarysystem.analyzers.BorrowingTrendAnalyzer;
import com.khushiagrawall.virtuallibrarysystem.analyzers.PopularAuthorAnalyzer;
import com.khushiagrawall.virtuallibrarysystem.analyzers.GenreTrendAnalyzer;
import com.khushiagrawall.virtuallibrarysystem.analyzers.MostBorrowedBooksAnalyzer;
import com.khushiagrawall.virtuallibrarysystem.isbn.ISBNChecker;
import com.khushiagrawall.virtuallibrarysystem.operations.*;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Library {

    public  List<Book> books = new ArrayList<>();
    private ISBNChecker check = new ISBNChecker();
    public List<TransactionLog> log = new ArrayList<>();
    private BookLender bookLender;
    private BookReturner bookReturner;
    BookStatisticsCalculator bookStatisticsCalculator;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Scanner sc = new Scanner(System.in);
    BorrowingTrendAnalyzer borrowingTrendAnalyzer;
    GenreTrendAnalyzer genreTrendAnalyzer;
    PopularAuthorAnalyzer popularAuthorAnalyzer;
    public Library() {
        this.books = new ArrayList<>();
        this.check = new ISBNChecker();
    }

    MostBorrowedBooksAnalyzer mostBorrowedBooksAnalyzer;
    // add book
    public void add(Book book) {
        boolean isbnUnique = check.isISBNUnique(book.getISBN(), books);
        if (isbnUnique) {
            books.add(book);
        }
    }

    //-------------------------------------------------------------------------------------------------


    public void showStatus(Book bk) {

        System.out.println("Here is the details of the book: \nTitle : " + bk.getTitle() + "\n"
                + "Author : " + bk.getAuthor() + "\n" + "ISBN : " + bk.getISBN() + "\n"
                + "Genre : " + bk.getGenre() + "\n"
                + "Publication date : " + bk.getPublication_Date());
        if (bk.getNoOfCopies() < 1) {
            bk.setStatus("Out of stock");
            System.out.println("Current availability status : " + bk.getStatus());
        } else {
            bk.setStatus("Available");
            System.out.println("Current availability status : " + bk.getStatus());
        }
    }


    //-----------------------------------------------------------------------------------------------

    public void viewLog() {
        for (TransactionLog lg : log) {
            System.out.println("UserID : " + lg.getUserId() + "," + "ISBN : " + lg.getISBN() + "," +"Borrowed : "+lg.getBorrowed()+","+ "Borrow Date : " + lg.getBorrowDate()
                    +","+"Returned : "+lg.getReturned()+","+"Return Date : "+lg.getReturnDate());
        }
    }

    //----------------------------------------------------------------------------------------------------


    public void bookInventory() {
        for (Book bk : books) {
            System.out.println("Title : " + bk.getTitle() +" , "+ "No of copies : " + bk.getNoOfCopies());
        }
    }
    BookSearcher bookSearcher;
    public void searchBook(){
        bookSearcher.search();
    }

    public void borrowBook()
    {
        bookLender.borrowByISBN(books,log);
    }


    public void returnBook(){
        bookReturner.ReturnBook(books,log);
    }

    public void UploadBook(String path){
        BookUploader upload=new BookUploader();
        upload.uploadBook(path,books);
    }

    public void showStatistics(){
        while (true) {
            System.out.println("\nBooks Statistics Overview:");
            System.out.println("1. Show All Books ");
            System.out.println("2. Total No. of Books Present");
            System.out.println("3. Number of currently borrowed books");
            System.out.println("4. List of titles of all borrowed books");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int statsChoice = sc.nextInt();

            switch (statsChoice) {
                case 1:
                    bookStatisticsCalculator.displayLibraryStatistics(books,log);
                    break;
                case 2:
                    System.out.println("Total number of books present: " + bookStatisticsCalculator.getTotalBooks(books));
                    break;
                case 3:
                    System.out.println("Number of currently borrowed books: " + bookStatisticsCalculator.calculateCurrentlyBorrowedBooksCount(log));
                    break;
                case 4:
                    System.out.println("List of titles of all borrowed books:");
                    List<String> borrowedTitles = bookStatisticsCalculator.getAllBorrowedBookTitles(log,books);
                    for (String title : borrowedTitles) {
                        System.out.println(title);
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            if (statsChoice == 5)
                break;
        }

    }

    public void analyzer(){
        while (true) {
            System.out.println("\nAnalyzers:");
            System.out.println("1. Analyze Borrowing Trends per Month");
            System.out.println("2. Analyze Borrowing Trends per Quarter");
            System.out.println("3. Analyze Borrowing Trends per Year");
            System.out.println("4. Analyze Trending Genres");
            System.out.println("5. Analyze Trending Authors");
            System.out.println("6. Analyze Most Popular Book");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");
            int analyzerChoice = sc.nextInt();

            switch (analyzerChoice) {
                case 1:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerMonth(log);
                    break;
                case 2:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerQuarter(log);
                    break;
                case 3:
                    System.out.println("Enter the year to analyze : ");
                    int year=sc.nextInt();
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerYear(log, year);
                    break;
                case 4:
                    genreTrendAnalyzer.analyzeGenreTrends(books, log);
                    break;
                case 5:
                    popularAuthorAnalyzer.analyzeAuthorTrends(books,log);
                    break;
                case 6:
                    System.out.println("Display Top - ");
                    int limit = sc.nextInt();
                    mostBorrowedBooksAnalyzer.MostBorrowedAnalyzer(books,log, limit);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            if (analyzerChoice == 7)
                break;
        }
    }

}