package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
/**
 * 
 * This Command delete a book (set deleted property to true and then book is not shown in the books list)
 * If the command is completed successfully, data is saved to disk
 */
public class DeleteBook implements  Command {

    private final int bookId;

    public DeleteBook(int bookId) {
		this.bookId = bookId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (book.isOnLoan()) {
			throw new LibraryException("Book #" + bookId + " is on loan and cannot be deleted");
		}
		book.setDeleted(true);
		System.out.println("Book #" + bookId + " has been deleted from library");
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
