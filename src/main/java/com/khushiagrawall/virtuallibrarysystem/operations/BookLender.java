package com.khushiagrawall.virtuallibrarysystem.operations;

import com.khushiagrawall.virtuallibrarysystem.models.Book;
import com.khushiagrawall.virtuallibrarysystem.models.Library;
import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookLender {
    private Library library;
    private Scanner sc;

    public BookLender(Library library, Scanner scanner) {
        this.library = library;
        this.sc = scanner;
    }
    BookSearcher bookSearcher;
    public void borrowByISBN(List<Book> books, List<TransactionLog> log) {
        System.out.println("Enter the ISBN of book to borrow: ");
        String isbn = sc.next();
        Book book = bookSearcher.searchByISBN(isbn,books).stream().findFirst().orElse(null);
        if (book != null) {
            System.out.println("the book you have selected is : " + book.getTitle());
            library.showStatus(book);
            System.out.println("Do you want to borrow this book(Y/N) ?");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("y")) {

                if(book.getStatus().equalsIgnoreCase("out of stock")){
                    System.out.println("********** ALERT **********");
                    System.out.println("Sorry but the book is out of stock");
                    System.out.println("***************************");
                    provideNavigationOptions();
                }
                else  {
                    System.out.println("Enter your UserId to borrow the book: ");
                    int UserId = sc.nextInt();

                    OverdueNotification(UserId,book,log); // notify if overdue

                    log.add(new TransactionLog(UserId, isbn,"yes", LocalDate.now(),"No",null));


                    System.out.println("The book has been borrowed");

                    book.setNoOfCopies(book.getNoOfCopies() - 1);
                    if (book.getNoOfCopies() < 1) {
                        book.setStatus("Out of stock");
                    } else {
                        book.setStatus(book.getNoOfCopies() + " copies available");
                    }

                }
            } else {
                System.out.println("Borrowing cancelled \nThank You");
            }
        } else {
            System.out.println("Book with given ISBN not found");
        }
    }

    public  void OverdueNotification(int UserId, Book book, List<TransactionLog> log){
        for(TransactionLog lg: log){
            if(lg.getUserId()==UserId && lg.getReturned().equalsIgnoreCase("No") && lg.getBorrowDate().isBefore(LocalDate.now().minusMonths(3))){
                System.out.println("Before borrowing new book we kindly notify that you have previously borrowed the book "+book.getTitle()+"\n"
                        +"It already have been overdue so please return the book otherwise the library will charge you. ");
            }else if(lg.getUserId()==UserId && lg.getReturned().equalsIgnoreCase("No") ){
                System.out.println("You previously have borrowed "+book.getTitle()+" book it is going to overdue on "+lg.getBorrowDate().plusMonths(3)+" please return it before date");

            }
        }
    }

    private void provideNavigationOptions() {
        int option;
        do {
            System.out.println("Please select an option:");
            System.out.println("1 - Return to the main menu.");
            System.out.println("2 - Perform another search.");
            System.out.println("3 - Exit.");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    // logic to return to main menu
                    break;
                case 2:
                    bookSearcher.search();
                    break;
                case 3:
                    System.out.println("Exiting system. Thank you for using our library!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option < 1 || option > 3);
    }


}