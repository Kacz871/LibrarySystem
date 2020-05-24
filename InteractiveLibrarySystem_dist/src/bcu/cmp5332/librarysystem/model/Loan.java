package bcu.cmp5332.librarysystem.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



public class Loan {
    
    private Patron patron;
    private Book book;
    private Movie movie;
    private LocalDate startDate;
    private LocalDate dueDate;
    private double penaltyfee;

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
		this.patron = patron;
		this.book = book;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.movie = null;
		this.penaltyfee = 0;
	}
    //Loan constructor for movie
    public Loan(Patron patron, Movie movie, LocalDate startDate, LocalDate dueDate) {
		this.patron = patron;
		this.movie = movie;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.book = null;
		this.penaltyfee = 0;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	//penaltyFee
    public void setLatePrice(){
    	long duration = ChronoUnit.DAYS.between(this.dueDate, LocalDate.now());
    	double cost = (double) duration * 20;
    	double pounds = cost % 100;
    	double penny = (cost - (100*pounds))/100;
    	this.penaltyfee = pounds + (penny);
    }
    
    public double getPenaltyFee() {
    	
    	return this.penaltyfee;
    }
}
