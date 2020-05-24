package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    
    private final int loanPeriod = 7;
    private final int maxBooksToBorrow = 3;
    private final int maxMoviesToBorrow = 3;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();
    private final Map<Integer, Movie> movies = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public int getMaxBooksToBorrow() {
		return maxBooksToBorrow;
	}
    public int getMaxMoviesToBorrow() {
		return maxMoviesToBorrow;
	}

	/**
     * 
     * @return all library books include deleted 
     */
	public List<Book> getAllBooks() {
		List<Book> out = new ArrayList<>(books.values());
		return Collections.unmodifiableList(out);
	}
    
	/**
	 * 
	 * @return library books exclude deleted
	 */
	public List<Book> getBooks() {
		List<Book> out = new ArrayList<>(books.values());
		return Collections.unmodifiableList(out.stream().filter(b -> !b.isDeleted()).collect(Collectors.toList()));
	}

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }

    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no such patron with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);
    }
    
    /**
     * 
     * @return all registered patrons include deleted
     */
    public List<Patron> getAllPatrons() {
        List<Patron> out = new ArrayList<>(patrons.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * 
     * @return registered patrons exclude deleted
     */
    public List<Patron> getPatrons() {
        List<Patron> out = new ArrayList<>(patrons.values());
		return Collections.unmodifiableList(out.stream().filter(p -> !p.isDeleted()).collect(Collectors.toList()));
    }
   
    //Implementing movies to library
    public void addMovie(Movie movie) {
    	if (movies.containsKey(movie.getId())) {
            throw new IllegalArgumentException("Duplicate movie ID.");
        }
        movies.put(movie.getId(), movie);
    	
    }
    
    public Movie getMovieByID(int id) throws LibraryException {
        if (!movies.containsKey(id)) {
            throw new LibraryException("There is no such movie with that ID.");
        }
        return movies.get(id);
    }

    /**
    * 
    * @return all library books include deleted 
    */
    public List<Movie> getAllMovies() {
    			List<Movie> out = new ArrayList<>(movies.values());
    			return Collections.unmodifiableList(out);
    }
    	    
   /**
    * 
    * @return library movies exclude deleted
    */
    public List<Movie> getMovies() {
    			List<Movie> out = new ArrayList<>(movies.values());
    			return Collections.unmodifiableList(out.stream().filter(b -> !b.isDeleted()).collect(Collectors.toList()));
   }

}
