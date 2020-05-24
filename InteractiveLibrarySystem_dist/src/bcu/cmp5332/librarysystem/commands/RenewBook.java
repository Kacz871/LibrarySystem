package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;
/**
 * 
 * This Command issue books to patrons
 * If book already is on loan - exception will throw
 * Else new Loan object created and added to book, also book added to patron loan book list
 * If the command is completed successfully, data is saved to disk
 */
public class RenewBook implements  Command {

    private final int patronId;
    private final int bookId;

    public RenewBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (!book.isOnLoan()) {
			throw new LibraryException("Book #"+bookId+ " is not on loan");
		}
		Patron patron = library.getPatronByID(patronId);
		LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod());
		patron.renewBook(book, dueDate);
		System.out.printf("Book #%1$d renew to new date: %2$tY-%2$tm-%2$td", bookId, dueDate);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
