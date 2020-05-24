package bcu.cmp5332.librarysystem.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class PatronDataManager implements DataManager {

    private final String RESOURCE = "patrons.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3];
                    String deleted = properties[4];
                    Patron patron = new Patron(id, name, phone, email, Boolean.parseBoolean(deleted));
                    library.addPatron(patron);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File(LibraryData.LIBRARY_DIRECTORY,RESOURCE)))) {
            for (Patron patron : library.getAllPatrons()) {
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhone() + SEPARATOR);
                out.print(patron.getEmail() + SEPARATOR);
                out.print(patron.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
