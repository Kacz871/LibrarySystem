package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

/**
 * 
 * This command printout list all Movies in short form
 *
 */
public class ListMovies implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Movie> movies = library.getMovies();
        for (Movie movie : movies) {
            System.out.println(movie.getDetailsShort());
        }
        System.out.println(movies.size() + " movie(s)");
    }
}
