package bcu.cmp5332.librarysystem.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bcu.cmp5332.librarysystem.commands.IssueMovie;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.IssueBook;
import bcu.cmp5332.librarysystem.main.CommandParser;
import bcu.cmp5332.librarysystem.main.LibraryException;

class LibraryTest {
	Library library;
	
	@BeforeEach
	void init() {
		library = new Library();
	}
	
	@Test
	void testAddBook() throws LibraryException {
		Book book = new Book(1, "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "The Strand Magazine", "1892");
		library.addBook(book);
		
		Book fromLibrary = library.getBookByID(1);
		assertTrue(fromLibrary.getPublisher().equals("The Strand Magazine"));
	}

	@Test
	void testAddPatron() throws LibraryException {
		Patron patron = new Patron(1, "Sherlock Holmes", "0123456789", "sherlock.holmes@mail.com");
		library.addPatron(patron);
		Patron fromLibrary = library.getPatronByID(1);
		assertTrue(fromLibrary.getEmail().equals("sherlock.holmes@mail.com"));
		
	}
	
	@Test
	void testAddTwoItemToOnePerson() throws LibraryException{
		Patron patron = new Patron(1, "Sherlock Holmes", "0123456789", "sherlock.holmes@mail.com");
		library.addPatron(patron);
		Book newBook = new Book(1, "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "The Strand Magazine", "1892");
		library.addBook(newBook);
		Movie newMovie = new Movie(15, "The Gentlemen","Guy Ritchie","Action Comedy","2019");
		library.addMovie(newMovie);
		IssueBook issuebook = new IssueBook(1, 1);
		IssueMovie issuemovie = new IssueMovie(1,15);
		issuemovie.execute(library, LocalDate.now());
		assertFalse(library.getPatronByID(1).getMovies().isEmpty()&&library.getPatronByID(1).getBooks().isEmpty());
				
	}

	}

