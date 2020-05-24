package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;
/**
 * 
 * This command printout Movie details in long format
 *
 */
public class ShowMovie implements Command {

	private final int movieId;

	public ShowMovie(int movieId) {
		this.movieId = movieId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Movie movie = library.getMovieByID(movieId);
		System.out.println(movie.getDetailsLong());
	}
}
