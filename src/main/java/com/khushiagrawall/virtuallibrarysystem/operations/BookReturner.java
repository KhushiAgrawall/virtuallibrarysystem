package com.khushiagrawall.virtuallibrarysystem.operations;

import com.khushiagrawall.virtuallibrarysystem.models.Book;
import com.khushiagrawall.virtuallibrarysystem.models.TransactionLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookReturner {

//    private Library library;
//    private TransactionLog lg;

     Scanner sc = new Scanner(System.in);


    public void ReturnBook(List<Book> books, List<TransactionLog> log) {
        System.out.println("Enter your UserID to return the book: ");
        int UserID = sc.nextInt();
        System.out.println("Enter ISBN of the Book you want to return: ");
        String isbn = sc.next();

        boolean flag = false;
        for (TransactionLog l : log) {
            if (l.getUserId() == UserID && l.getISBN().equalsIgnoreCase(isbn)) {

                for (Book bk : books) {

                    if (bk.getISBN().equalsIgnoreCase(isbn)) {
                        System.out.println("The book you want to return is : " + bk.getTitle());
                        System.out.println("Confirm to proceed(Y/N) :");
                        String c = sc.next();
                        if (c.equalsIgnoreCase("y")) {
                            bk.setNoOfCopies(bk.getNoOfCopies() + 1);
                            if(bk.getStatus().equalsIgnoreCase("out of stock")){
                                bk.setStatus("Available");
                            }
                            l.setReturned("Yes");
                            l.setReturnDate(LocalDate.now());

                            flag = true;
                        } else {
                            System.out.println("Thank You");
                            System.exit(0);
                        }
                    }
                }

            }
            if (flag) {
                System.out.println("Book returned succesfully");

            } else {
                System.out.println("No log record Exists");
            }
        }

    }

}