package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MovieDataManager implements DataManager {
    
    private final String RESOURCE = "movies.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String title = properties[1];
                    String director = properties[2];
                    String genre = properties[3];
                    String publicationYear = properties[4];
                    String status = properties[5];
                    String deleted = properties[6];
                    Movie movie = new Movie(id, title, director, genre, publicationYear, status, Boolean.parseBoolean(deleted));
                    library.addMovie(movie);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse Movie id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE)))) {
            for (Movie movie : library.getAllMovies()) {
                out.print(movie.getId() + SEPARATOR);
                out.print(movie.getTitle() + SEPARATOR);
                out.print(movie.getDirector() + SEPARATOR);
                out.print(movie.getGenre() + SEPARATOR);
                out.print(movie.getPublicationYear() + SEPARATOR);
                out.print(movie.getStatus() + SEPARATOR);
                out.print(movie.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
