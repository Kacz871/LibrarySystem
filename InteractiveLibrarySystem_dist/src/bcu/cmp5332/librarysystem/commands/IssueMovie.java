package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
/**
 * 
 * This Command issue Movies to patrons
 * If Movie already is on loan - exception will throw
 * Else new Loan object created and added to Movie, also Movie added to patron loan Movie list
 * If the command is completed successfully, data is saved to disk
 */
public class IssueMovie implements  Command {

    private final int patronId;
    private final int movieId;

    public IssueMovie(int patronId, int movieId) {
		this.patronId = patronId;
		this.movieId = movieId;
	}


	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Movie movie = library.getMovieByID(movieId);
		if (movie.isOnLoan()) {
			throw new LibraryException("Movie #" + movieId + " already is on loan");
		}
		Patron patron = library.getPatronByID(patronId);
		int borrowedMovies = patron.getMovies().size();
		if (borrowedMovies >= library.getMaxMoviesToBorrow()) {
			throw new LibraryException(														
					"Number of borrowed Movies for this patron has reached limit: " + library.getMaxMoviesToBorrow());
		}
		patron.borrowMovie(movie, LocalDate.now().plusDays(library.getLoanPeriod()));
		System.out.println("Movie #" + movieId + " issued to Patron #" + patronId);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
