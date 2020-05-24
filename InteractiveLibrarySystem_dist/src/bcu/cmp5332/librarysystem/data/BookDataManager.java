package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookDataManager implements DataManager {
    
    private final String RESOURCE = "books.txt";
    
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
                    String author = properties[2];
                    String publisher = properties[3];
                    String publicationYear = properties[4];
                    String status = properties[5];
                    String deleted = properties[6];
                    Book book = new Book(id, title, author, publisher, publicationYear, status, Boolean.parseBoolean(deleted));
                    library.addBook(book);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE)))) {
            for (Book book : library.getAllBooks()) {
                out.print(book.getId() + SEPARATOR);
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublisher() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                out.print(book.getStatus() + SEPARATOR);
                out.print(book.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
