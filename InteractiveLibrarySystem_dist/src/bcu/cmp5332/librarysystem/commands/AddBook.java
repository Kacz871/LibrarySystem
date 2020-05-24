package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.DataManager;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * 
 * This command add a new book to the library
 * If the command is completed successfully, data is saved to disk
 */
public class AddBook implements  Command {

    private final String title;
    private final String author;
    private final String publisher;
    private final String publicationYear;

    public AddBook(String title, String author, String publisher, String publicationYear) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
    	//No sense add book without title and author
		if (title == null || title.trim().isEmpty()) {
			throw new LibraryException("Title cannot be empty");
		}
		if (author == null || author.trim().isEmpty()) {
			throw new LibraryException("Author cannot be empty");
		}
        int lastIndex = library.getBooks().size() - 1;
        int maxId = library.getBooks().get(lastIndex).getId();
        Book book = new Book(++maxId, title, author, publisher, publicationYear);
        library.addBook(book);
        System.out.println("Book #" + book.getId() + " added.");
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
