package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;
/**
 * 
 * This Command issue Movies to patrons
 * If Movie already is on loan - exception will throw
 * Else new Loan object created and added to Movie, also Movie added to patron loan Movie list
 * If the command is completed successfully, data is saved to disk
 */
public class RenewMovie implements  Command {

    private final int patronId;
    private final int movieId;

    public RenewMovie(int patronId, int movieId) {
		this.patronId = patronId;
		this.movieId = movieId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Movie movie = library.getMovieByID(movieId);
		if (!movie.isOnLoan()) {
			throw new LibraryException("Movie #"+movieId+ " is not on loan");
		}
		Patron patron = library.getPatronByID(patronId);
		LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod());
		patron.renewMovie(movie, dueDate);
		System.out.printf("Movie #%1$d renew to new date: %2$tY-%2$tm-%2$td", movieId, dueDate);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
