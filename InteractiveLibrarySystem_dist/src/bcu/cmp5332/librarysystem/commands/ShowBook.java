package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;
/**
 * 
 * This command printout book details in long format
 *
 */
public class ShowBook implements Command {

	private final int bookId;

	public ShowBook(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		System.out.println(book.getDetailsLong());
	}
}
