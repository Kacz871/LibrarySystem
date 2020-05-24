package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
/**
 * 
 * This Command delete a movie (set deleted property to true and then movie is not shown in the movie list)
 * If the command is completed successfully, data is saved to disk
 */
public class DeleteMovie  implements  Command {


	    private final int movieId;

	    public DeleteMovie(int movieId) {
			this.movieId = movieId;
		}


		@Override
	    public void execute(Library library, LocalDate currentDate) throws LibraryException {
			Movie movie = library.getMovieByID(movieId);
			if (movie.isOnLoan()) {
				throw new LibraryException("Movie #" + movieId + " is on loan and cannot be deleted");
			}
			movie.setDeleted(true);
			System.out.println("Movie #" + movieId + " has been deleted from library");
	        try {
				LibraryData.store(library);
			} catch (IOException e) {
				new LibraryException(e);
			}
	    	}
}
