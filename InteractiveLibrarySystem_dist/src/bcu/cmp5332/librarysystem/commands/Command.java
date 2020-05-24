package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public interface Command {

    public static final String HELP_MESSAGE = "Commands:\n"
            + "\tlistbooks                       print all books\n"
            + "\tlistpatrons                     print all patrons\n"
            + "\taddbook                         add a new book\n"
            + "\taddpatron                       add a new patron\n"
            + "\tshowbook [book id]              show book details\n"
            + "\tshowpatron [patron id]          show patron details\n"
            + "\tborrowbook [patron id] [book id]    borrow a book\n"
            + "\trenewbook [patron id] [book id]     renew a book\n"
            + "\treturnbook [patron id] [book id]    return a book\n"
            + "\tlistmovies                       print all movies\n"
            + "\taddmovie                         add a new movie\n"
            + "\tshowmovie [movie id]              show movie details\n"
            + "\tborrowmovie [patron id] [movie id]    borrow a movie\n"
            + "\trenewmovie [patron id] [movie id]     renew a movie\n"
            + "\treturnmovie [patron id] [movie id]    return a movie\n"
            + "\tloadgui                         loads the GUI version of the app\n"
            + "\thelp                            prints this help message\n"
            + "\texit                            exits the program";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
}
