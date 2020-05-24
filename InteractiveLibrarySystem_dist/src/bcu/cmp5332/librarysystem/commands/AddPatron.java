package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/**
 * 
 * This command add a new patron to library
 * If the command is completed successfully, data is saved to disk
 */
public class AddPatron implements Command {

	private final String name;
	private final String phone;
	private final String email;

	public AddPatron(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		//No sense to add Patron without name
		if (name == null || name.trim().isEmpty()) {
			throw new LibraryException("Name cannot be empty");
		}
		int lastIndex = library.getPatrons().size() - 1;
		int maxId = library.getPatrons().get(lastIndex).getId();
		Patron patron = new Patron(++maxId, name, phone, email);
		library.addPatron(patron);
		System.out.println("Patron #" + patron.getId() + " added.");
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
	}
}
