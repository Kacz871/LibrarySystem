package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.commands.LoadGUI;
import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.commands.ShowBook;
import bcu.cmp5332.librarysystem.commands.ShowPatron;
import bcu.cmp5332.librarysystem.commands.ListBooks;
import bcu.cmp5332.librarysystem.commands.ListPatrons;
import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.Help;
import bcu.cmp5332.librarysystem.commands.IssueBook;
import bcu.cmp5332.librarysystem.commands.RenewMovie;
import bcu.cmp5332.librarysystem.commands.ReturnMovie;
import bcu.cmp5332.librarysystem.commands.ShowMovie;
import bcu.cmp5332.librarysystem.commands.ListMovies;
import bcu.cmp5332.librarysystem.commands.AddMovie;
import bcu.cmp5332.librarysystem.commands.IssueMovie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, LibraryException {
        try {
            System.out.println(line);
            String[] parts = line.split(" ", 3);
            System.out.println(Arrays.toString(parts));
            String cmd = parts[0];

            
            if (cmd.equals("addbook")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Author: ");
                String author = br.readLine();
                System.out.print("Publisher: ");
                String publisher = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                
                return new AddBook(title, author, publisher, publicationYear);
                //add movie command
            } if (cmd.equals("addMovie")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Director: ");
                String director = br.readLine();
                System.out.print("Genre: ");
                String genre = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                
                return new AddMovie(title, director, genre, publicationYear);
            }else if (cmd.equals("addpatron")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("name: ");
                String name = br.readLine();
                System.out.print("phone: ");
                String phone = br.readLine();
                System.out.print("email: ");
                String email = br.readLine();
                
                return new AddPatron(name, phone, email);
                
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
                // command has second part
            } else if (parts.length == 1) {
                if (line.equals("listbooks")) {
                    return new ListBooks();
                } else if (line.equals("listpatrons")) {
                    return new ListPatrons();
                } else if (line.equals("listmovie")) {
                    return new ListMovies();
                } else if (line.equals("help")) {
                    return new Help();
                }
                
                // command has third part
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showbook")) {
                    return new ShowBook(id);
                } else if (cmd.equals("showpatron")) {
                    return new ShowPatron(id);
                } else if (cmd.equals("showmovie")) {
                    return new ShowMovie(id);
                }
            } else if (parts.length == 3) {
               

                if (cmd.equals("borrowbook")) {
                	 int patronID = Integer.parseInt(parts[1]);
                     int bookID = Integer.parseInt(parts[2]);
                    return new IssueBook(patronID, bookID);
                } else if (cmd.equals("renewbook")) {
                	int patronID = Integer.parseInt(parts[1]);
                    int bookID = Integer.parseInt(parts[2]);
                    return new RenewBook(patronID, bookID);
                } else if (cmd.equals("returnbook")) {
                	int patronID = Integer.parseInt(parts[1]);
                    int bookID = Integer.parseInt(parts[2]);
                    
                    return new ReturnBook(patronID, bookID);   
                }
                if (cmd.equals("borrowmovie")) {
               	 int patronID = Integer.parseInt(parts[1]);
                    int movieID = Integer.parseInt(parts[2]);
                   return new IssueMovie(patronID, movieID);
               } else if (cmd.equals("renewmovie")) {
               	int patronID = Integer.parseInt(parts[1]);
                   int movieID = Integer.parseInt(parts[2]);
                   return new RenewMovie(patronID, movieID);
               } else if (cmd.equals("returnmovie")) {
               	int patronID = Integer.parseInt(parts[1]);
                   int movieID = Integer.parseInt(parts[2]);
                   return new ReturnMovie(patronID, movieID);   
               }
            }else if (cmd.equals("addmovie")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Director: ");
                String director = br.readLine();
                System.out.print("Studio: ");
                String studio = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                
                return new AddMovie(title, director, studio, publicationYear);
            }
        } catch (NumberFormatException ex) {

        }

        throw new LibraryException("Invalid command.");
    }
}
