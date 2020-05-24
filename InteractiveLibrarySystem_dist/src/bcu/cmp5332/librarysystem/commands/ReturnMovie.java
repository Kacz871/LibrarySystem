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
 * This Command return Movies to library
 * If Movie is not on loan - exception will throw
 * Command delegate execution to the patron object
 * If the command is completed successfully, data is saved to disk
 */
public class ReturnMovie implements  Command {

    private final int patronId;
    private final int movieId;

    public ReturnMovie(int patronId, int movieId) {
		this.patronId = patronId;
		this.movieId = movieId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Movie movie = library.getMovieByID(movieId);
		if (!movie.isOnLoan()) {
			throw new LibraryException("Movie #"+ movieId + " is not on loan");
		}
		if(movie.getLoan().getDueDate().isBefore(LocalDate.now())) {
			movie.getLoan().setLatePrice();
			System.out.println("Movie is return to late "
					+ "\n fee = " + movie.getLoan().getPenaltyFee());
        }
		Patron patron = library.getPatronByID(patronId);
		patron.returnMovie(movie);
		System.out.println("Movie #" + movieId + " return from Patron #" + patronId);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
