package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;

public class AddMovie implements Command {
	
	private final String title;
    private final String director;
    private final String genre;
    private final String publicationYear;

    public AddMovie(String title, String director, String genre, String publicationYear) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }
    
    // same as for Movie no sense with adding Movie without Title or director
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
		if (title == null || title.trim().isEmpty()) {
			throw new LibraryException("Title cannot be empty");
		}
		if (director == null || director.trim().isEmpty()) {
			throw new LibraryException("director cannot be empty");
		}
        int lastIndex = library.getMovies().size() - 1;
        int maxId = library.getMovies().get(lastIndex).getId();
        Movie movie = new Movie(++maxId, title, director, genre, publicationYear);
        library.addMovie(movie);
        System.out.println("Movie #" + movie.getId() + " added.");
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			new LibraryException(e);
		}
    }
}
