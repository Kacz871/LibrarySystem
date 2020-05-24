package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Patron {

	private int id;
	private String name;
	private String phone;
	private String email;
	private boolean deleted;
	
//	private final List<Book> books = new ArrayList<>();
	//Replace list with a map to avoid iterate each time the list for finding the book by id
    private final Map<Integer, Book> books = new TreeMap<>();
    
    //map for Movies
    private final Map<Integer, Movie> movies = new TreeMap<>();
    
	public Patron(int id, String name, String phone, String email, boolean deleted) {
	this.id = id;
	this.name = name;
	this.phone = phone;
	this.email = email;
	this.deleted = deleted;
}

	public Patron(int id, String name, String phone, String email) {
		this(id, name, phone, email, false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Book> getBooks() {
		return Collections.unmodifiableList(new ArrayList<Book>(books.values()));
	}

	public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
		Loan loan = new Loan(this, book, LocalDate.now(), dueDate);
		book.setLoan(loan);
		addBook(book);
	}

	public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
		if (!books.containsKey(book.getId())) {
			throw new LibraryException("Patroon #" + this.id + " does not loan book #" + book.getId());
		}
		book.getLoan().setDueDate(dueDate);
	}

	public void returnBook(Book book) throws LibraryException {
		if (!books.containsKey(book.getId())) {
			throw new LibraryException("Patroon #" + this.id + " does not loan book #" + book.getId());
		}
		Book removed = books.remove(book.getId());
		removed.setLoan(null);
	}

	public void addBook(Book book) {
		this.books.put(book.getId(),book);
	}

	public List<Movie> getMovies() {
		return Collections.unmodifiableList(new ArrayList<Movie>(movies.values()));
	}

	public void borrowMovie(Movie movie, LocalDate dueDate) throws LibraryException {
		Loan loan = new Loan(this, movie, LocalDate.now(), dueDate);
		movie.setLoan(loan);
		addMovie(movie);
	}

	public void renewMovie(Movie movie, LocalDate dueDate) throws LibraryException {
		if (!movies.containsKey(movie.getId())) {
			throw new LibraryException("Patroon #" + this.id + " does not loan movie #" + movie.getId());
		}
		movie.getLoan().setDueDate(dueDate);
	}

	public void returnMovie(Movie movie) throws LibraryException {
		if (!movies.containsKey(movie.getId())) {
			throw new LibraryException("Patroon #" + this.id + " does not loan movie #" + movie.getId());
		}
		Movie removed = movies.remove(movie.getId());
		removed.setLoan(null);
	}

	public void addMovie(Movie movie) {
		this.movies.put(movie.getId(),movie);
	}
	
	public String getDetailsShort() {
		return "Patron name: " + name + ", phone: " + phone;
	}
	
	public String getDetailsLong() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patrin details:\n");
		sb.append("Id:\t\t\t");
		sb.append(id);
		sb.append("\n");
		sb.append("Name:\t\t\t");
		sb.append(name);
		sb.append("\n");
		sb.append("Phone:\t\t\t");
		sb.append(phone);
		sb.append("\n");
		sb.append("Deleted:\t\t\t");
		sb.append(deleted);
		sb.append("\n");
		sb.append("Loaned books:\n");
		List<Book> books = getBooks();
		for (Book book : books) {
			sb.append(book.getDetailsShort());
			sb.append("\n");
		}
		sb.append("Loaned Movies:\n");
		List<Movie> movies = getMovies();
		for (Movie movie : movies) {
			sb.append(movie.getDetailsShort());
			sb.append("\n");
		}
		return sb.toString();
	}


}
