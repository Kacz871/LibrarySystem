package bcu.cmp5332.librarysystem.model;

public class Movie {
	
	private int id;
	private String title;
	private String director;
	private String genre;
	private String publicationYear;
	private String status;
	private boolean deleted;
	
	private Loan loan;
	
	public Movie(int id, String title, String director, String genre, String publicationYear, String status,
			boolean deleted) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genre = genre;
		this.publicationYear = publicationYear;
		this.status = status;
		this.deleted = deleted;
	}

	public Movie(int id, String title, String director, String genre, String publicationYear) {
		this(id, title, director, genre, publicationYear, "Available", false);
	}

	public Movie(int id, String title, String director, String genre, String publicationYear, String status) {
		this(id, title, director, genre, publicationYear, status, false);
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	
	public String getDetailsShort() {
		return "Movie #" + id + " - " + title + " ["+ status + "]";
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
		sb.append("Director:\t\t\t");
		sb.append(director);
		sb.append("\n");
		sb.append("Genre:\t\t\t");
		sb.append(genre);
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
	
}
