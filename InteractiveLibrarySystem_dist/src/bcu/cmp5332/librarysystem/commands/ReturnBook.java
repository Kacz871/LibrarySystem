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
 * This Command return books to library
 * If book is not on loan - exception will throw
 * Command delegate execution to the patron object
 * If the command is completed successfully, data is saved to disk
 */
public class ReturnBook implements  Command {

    private final int patronId;
    private final int bookId;

    public ReturnBook(int patronId, int bookId) {
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
		if(book.getLoan().getDueDate().isBefore(LocalDate.now())) {
			book.getLoan().setLatePrice();
			System.out.println("Movie is return to late "
					+ "\n fee = " + book.getLoan().getPenaltyFee());
        }
		patron.returnBook(book);
		System.out.println("Book #" + bookId + " return from Patron #" + patronId);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
