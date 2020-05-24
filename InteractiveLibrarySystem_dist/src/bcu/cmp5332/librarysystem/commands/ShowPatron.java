package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/**
 * 
 * This command printout patron details in long format
 *
 */
public class ShowPatron implements Command {

	private final int patronId;

	public ShowPatron(int patronId) {
		this.patronId = patronId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		System.out.println(patron.getDetailsLong());
	}
}
