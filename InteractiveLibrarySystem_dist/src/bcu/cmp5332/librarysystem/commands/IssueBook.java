package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
/**
 * 
 * This Command issue books to patrons
 * If book already is on loan - exception will throw
 * Else new Loan object created and added to book, also book added to patron loan book list
 * If the command is completed successfully, data is saved to disk
 */
public class IssueBook implements  Command {

    private final int patronId;
    private final int bookId;

    public IssueBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (book.isOnLoan()) {
			throw new LibraryException("Book #" + bookId + " already is on loan");
		}
		Patron patron = library.getPatronByID(patronId);
		int borrowedBooks = patron.getBooks().size();
		if (borrowedBooks >= library.getMaxBooksToBorrow()) {
			throw new LibraryException(
					"Number of borrowed books for this patron has reached limit: " + library.getMaxBooksToBorrow());
		}
		patron.borrowBook(book, LocalDate.now().plusDays(library.getLoanPeriod()));
		System.out.println("Book #" + bookId + " issued to Patron #" + patronId);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
