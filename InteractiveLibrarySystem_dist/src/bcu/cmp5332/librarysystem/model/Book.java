package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {

	private int id;
	private String title;
	private String author;
	private String publisher;
	private String publicationYear;
	private String status;
	private boolean deleted;
	
	private Loan loan;

	
	public Book(int id, String title, String author, String publisher, String publicationYear, String status,
			boolean deleted) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.status = status;
		this.deleted = deleted;
	}

	public Book(int id, String title, String author, String publisher, String publicationYear) {
		this(id, title, author, publisher, publicationYear, "Available", false);
	}

	public Book(int id, String title, String author, String publisher, String publicationYear, String status) {
		this(id, title, author, publisher, publicationYear, status, false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getDetailsShort() {
		return "Book #" + id + " - " + title + " ["+ status + "]";
	}

	public String getDetailsLong() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book details:\n");
		sb.append("Id:\t\t\t");
		sb.append(id);
		sb.append("\n");
		sb.append("Title:\t\t\t");
		sb.append(title);
		sb.append("\n");
		sb.append("Author:\t\t\t");
		sb.append(author);
		sb.append("\n");
		sb.append("Publisher:\t\t\t");
		sb.append(publisher);
		sb.append("\n");
		sb.append("Publication year:\t");
		sb.append(publicationYear);
		sb.append("\n");
		sb.append("Status:\t\t\t");
		sb.append(status);
		sb.append("\n");
		sb.append("Deleted:\t\t\t");
		sb.append(deleted);
		return sb.toString();
	}

	public boolean isOnLoan() {
		return (loan != null);
	}

	public LocalDate getDueDate() {
		if (this.loan != null) {
			return loan.getDueDate();
		} else {
			return null;
		}
	}

	public void setDueDate(LocalDate dueDate) throws LibraryException {
		if (loan != null) {
			loan.setDueDate(dueDate);
		}
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
		if (loan != null) {
			status = "Busy";
		} else {
			status = "Available";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void returnToLibrary() {
		loan = null;
	}

}
