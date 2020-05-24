package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Movie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class LoanDataManager implements DataManager {

	public final String RESOURCE = "loans.txt";

	@Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int patronId;
                try {
                    patronId = Integer.parseInt(properties[0]);
                } catch (NumberFormatException ex) {
                	throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                			+ "\nError: " + ex);
                }
                int bookId;
                try {
                    bookId = Integer.parseInt(properties[1]);
                } catch (NumberFormatException ex) {
                	throw new LibraryException("Unable to parse book id " + properties[1] + " on line " + line_idx
                			+ "\nError: " + ex);
                }
                    LocalDate startDate;
                    try {
						startDate = LocalDate.parse(properties[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					} catch (DateTimeParseException ex) {
						throw new LibraryException("Unable to parse loan start date from" + properties[2] + " on line "
								+ line_idx + "\nError: " + ex);
					}
                    LocalDate dueDate;
                    try {
                    dueDate = LocalDate.parse(properties[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					} catch (DateTimeParseException ex) {
						throw new LibraryException("Unable to parse loan due date from" + properties[3] + " on line "
								+ line_idx + "\nError: " + ex);
					}
                    Patron patron = library.getPatronByID(patronId);
                    Book book = library.getBookByID(bookId);
                    Loan loan = new Loan(patron, book, startDate, dueDate);
                    book.setLoan(loan);
                    patron.addBook(book);
                line_idx++;
            }
        }
    }

	@Override
	public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE)))) {
            for (Book book : library.getBooks()) {
            	if (book.isOnLoan()) {
            		Loan loan = book.getLoan();
            		out.print(loan.getPatron().getId() + SEPARATOR);
            		out.print(loan.getBook().getId() + SEPARATOR);
            		out.print(loan.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + SEPARATOR);
            		out.print(loan.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + SEPARATOR);
            		out.print(loan.getPenaltyFee() + SEPARATOR);
            		out.println();
            	}
            	
            }
        }
	}


	
}
