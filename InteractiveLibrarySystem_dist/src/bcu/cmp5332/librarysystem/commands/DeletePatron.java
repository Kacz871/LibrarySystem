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
 * This Command delete a patron (set deleted property to true and then patron is not shows in the patrons list)
 * If the command is completed successfully, data is saved to disk
 */
public class DeletePatron implements  Command {

    private final int patronId;

    public DeletePatron(int patronId) {
		this.patronId = patronId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		if (!patron.getBooks().isEmpty()) {
			throw new LibraryException("Patron #" + patronId + " has loaned books and cannot be deleted");
		}
		patron.setDeleted(true);
		System.out.println("Patron #" + patronId + " has been deleted from library");
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
