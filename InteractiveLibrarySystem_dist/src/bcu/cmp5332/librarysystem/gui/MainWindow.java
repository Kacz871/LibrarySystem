package bcu.cmp5332.librarysystem.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.DeleteBook;
import bcu.cmp5332.librarysystem.commands.DeleteMovie;
import bcu.cmp5332.librarysystem.commands.DeletePatron;
import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.RenewMovie;
import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.commands.ReturnMovie;
import bcu.cmp5332.librarysystem.data.LibraryData;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Patron;

public class MainWindow extends JFrame implements ActionListener {

	private static final int BOOKS_PANEL = 1;
	private static final int MEMBERS_PANEL = 2;
	private static final int MOVIES_PANEL = 3;
    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu moviesMenu;
    private JMenu membersMenu;

    private JMenuItem adminRestoreLibrary;
    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksRenew;

    private JMenuItem moviesView;
    private JMenuItem moviesAdd;
    private JMenuItem moviesDel;	
    private JMenuItem moviesIssue;
    private JMenuItem moviesReturn;
    private JMenuItem moviesRenew;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    private JTable booksTable;
    private JTable patronsTable;
    private JTable moviesTable;
    private int currentPanel = -1;
    
    public MainWindow(Library library) {

        initialize();
        this.library = library;
    }
    
    /**
     * Disable Menu items when switch between books and patrons table
     * @param items
     */
    void disableMenuItems(JMenuItem ... items) {
    	for (JMenuItem jMenuItem : items) {
    		jMenuItem.setEnabled(false);
		}
    }

    /**
     * Enable Menu items when switch between books and patrons table
     * @param items
     */
    void enableMenuItems(JMenuItem ... items) {
    	for (JMenuItem jMenuItem : items) {
    		jMenuItem.setEnabled(true);
		}
    }
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);
        adminRestoreLibrary = new JMenuItem("Restore library");
        adminMenu.add(adminRestoreLibrary);
        adminRestoreLibrary.addActionListener(this);
        
        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksRenew = new JMenuItem("Renew");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksRenew);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }
        //adding MovieMenu menu and menu items
        moviesMenu = new JMenu("Movie");
        menuBar.add(moviesMenu);

        moviesView = new JMenuItem("View");
        moviesAdd = new JMenuItem("Add");
        moviesDel = new JMenuItem("Delete");
        moviesIssue = new JMenuItem("Issue");
        moviesReturn = new JMenuItem("Return");
        moviesRenew = new JMenuItem("Renew");
        moviesMenu.add(moviesView);
        moviesMenu.add(moviesAdd);
        moviesMenu.add(moviesDel);
        moviesMenu.add(moviesIssue);
        moviesMenu.add(moviesReturn);
        moviesMenu.add(moviesRenew);
        for (int i = 0; i < moviesMenu.getItemCount(); i++) {
            moviesMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

 //Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, LibraryException {
       Library library = LibraryData.load();
       new MainWindow(library);			
   }



    @Override
    public void actionPerformed(ActionEvent ae) {
    	if (ae.getSource() == adminRestoreLibrary) {
            restoreLibrary();
        }else if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
        } else if (ae.getSource() == booksDel) {
        	deleteBook();
        } else if (ae.getSource() == booksIssue) {
			issueBook();
        } else if (ae.getSource() == booksReturn) {
            returnBook();
        } else if (ae.getSource() == booksRenew) {
            renewBook();
        } else if (ae.getSource() == memView) {
            displayPatrons();
        } else if (ae.getSource() == memAdd) {
        	new AddPatronWindow(this);
        } else if (ae.getSource() == memDel) {
        	deletePatron();
        } else if (ae.getSource() == moviesView) {
            displayMovies();
        } else if (ae.getSource() == moviesAdd) {
            new AddMovieWindow(this);
        } else if (ae.getSource() == moviesDel) {
        	deleteMovie();
        } else if (ae.getSource() == moviesIssue) {
			issueMovie();
        } else if (ae.getSource() == moviesReturn) {
            returnMovie();
        } else if (ae.getSource() == moviesRenew) {
            renewMovie();
    }
    }

    /**
     * Call DeletePatron command or display error message if patron has loaned books
     */
	private void deletePatron() {
		int row = patronsTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) patronsTable.getModel().getValueAt(row, 4);
			try {
				Patron patron = library.getPatronByID(id);
				Command deletePatron = new DeletePatron(patron.getId());
				deletePatron.execute(library, LocalDate.now());
				String msg = patron.getDetailsShort() + "\nhas been deleted from library";
				JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
				displayPatrons();;
			} catch (LibraryException e) {
				JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Call DeleteBook command or display error message if book is on loan or other exception
	 */
	private void deleteBook() {
		int row = booksTable.getSelectedRow();
		if (row >= 0) {
			try {
				int id = (int) booksTable.getModel().getValueAt(row, 4);
				Book book = library.getBookByID(id);
				Command deleteBook = new DeleteBook(book.getId());
				deleteBook.execute(library, LocalDate.now());
				String msg = book.getDetailsShort() + "\nhas been deleted from library";
				JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
				displayBooks();
			} catch (LibraryException e) {
				JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
     * Restore library from backup and re-render current view
     */
	private void restoreLibrary() {
		try {
			this.library = LibraryData.loadFromBackup();
			switch (currentPanel) {
			case BOOKS_PANEL: {
				displayBooks();
				break;
			}
			case MEMBERS_PANEL: {
				displayPatrons();
				break;
			}
			case MOVIES_PANEL:{
				displayMovies();
				break;
			}
			default:
				break;
			}
		} catch (LibraryException | IOException e) {
			JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Call RenewBook command for given book or display error message if book is not on loan
	 */
	private void renewBook() {
		int row = booksTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) booksTable.getModel().getValueAt(row, 4);
			Book book = null;
			try {
				book = library.getBookByID(id);
			} catch (LibraryException e1) {
	            JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (book != null && book.isOnLoan()) {
				Command renewBook = new RenewBook(book.getLoan().getPatron().getId(), book.getId()); 
				try {
					renewBook.execute(library, LocalDate.now());
					displayBooks();
				} catch (LibraryException e) {
		            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Open IssueBookWindow dialog
	 */
	void issueBook() {
		int row = booksTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) booksTable.getModel().getValueAt(row, 4);
			Book book = null;
			try {
				book = library.getBookByID(id);
			} catch (LibraryException e1) {
				JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (book != null && !book.isOnLoan()) {
				new IssueBookWindow(this, book);
			}
		}
	}

	/**
	 * Call ReturnBook command or display error message if book is not on loan
	 */
	void returnBook() {
		int row = booksTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) booksTable.getModel().getValueAt(row, 4);
			Book book = null;
			try {
				book = library.getBookByID(id);
			} catch (LibraryException e1) {
				JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (book != null && book.isOnLoan()) {
				
				if(book.getLoan().getDueDate().isBefore(LocalDate.now())) {
					book.getLoan().setLatePrice();
					String msg = "Book is return to late \n fee = " + book.getLoan().getPenaltyFee();
					JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
					displayBooks();
				}
				Command returnbook = new ReturnBook(book.getLoan().getPatron().getId(), book.getId());
				try {
					returnbook.execute(library, LocalDate.now());
					String msg = "book " + book.getDetailsShort() + "\nhas been returned from loan to library";
					JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
					displayBooks();
				} catch (LibraryException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Display list of books in table
	 */
	public void displayBooks() {
        List<Book> booksList = library.getBooks();
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};
        DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public int getRowCount() {
				return booksList.size();
			}

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			@Override
			public String getColumnName(int column) {
				return columns[column];
			}

			@Override
			public Object getValueAt(int row, int column) {
				Book book = booksList.get(row);
				Object val = null;
				switch (column) {
				case 0: {
					val = book.getTitle();
					break;
				}
				case 1: {
					val = book.getAuthor();
					break;
				}
				case 2: {
					val = book.getPublicationYear();
					break;
				}
				case 3: {
					val = book.getStatus();
					break;
				}
				case 4: {
					val = book.getId();
					break;
				}

				default:
					break;
				}
				return val;
			}
			
        };

        JTable table = new JTable(model);
        booksTable = table;
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        Cursor pointertCursor = new Cursor(Cursor.HAND_CURSOR);
        
        table.addMouseMotionListener(new MouseMotionAdapter() {
        	
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3 && "Busy".equals((String) table.getModel().getValueAt(row, col))) {
					setCursor(pointertCursor);
				} else {
					setCursor(defaultCursor);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3) {
					String status = (String) table.getModel().getValueAt(row, col);
					if ("Busy".equals(status)) {
						int id = (int) table.getModel().getValueAt(row, 4);
						Book book;
						try {
							book = library.getBookByID(id);
							JOptionPane.showMessageDialog(table, "Loan info:\n" + book.getLoan().getPatron().getDetailsShort());
						} catch (LibraryException e1) {
							JOptionPane.showMessageDialog(e.getComponent(), e1, "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		table.setDefaultRenderer(String.class, new BookCellRenderer());
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        disableMenuItems(memAdd,memDel);
        enableMenuItems(booksAdd,booksDel,booksIssue,booksReturn,booksRenew);

        this.revalidate();
        currentPanel = BOOKS_PANEL;

    }

	/**
	 * Display list of patrons in table
	 */
    public void displayPatrons() {
        List<Patron> patrons = library.getPatrons();
        String[] columns = new String[]{"Name", "Phone", "Email", "Num. of books", "Num. of Movie"};
        DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
			@Override
			public int getRowCount() {
				return patrons.size();
			}

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			@Override
			public String getColumnName(int column) {
				return columns[column];
			}

			@Override
			public Object getValueAt(int row, int column) {
				Patron patron = patrons.get(row);
				Object val = null;
				switch (column) {
				case 0: {
					val = patron.getName();
					break;
				}
				case 1: {
					val = patron.getPhone();
					break;
				}
				case 2: {
					val = patron.getEmail();
					break;
				}
				case 3: {
					val = patron.getBooks().size();
					break;
				}
				case 4: {
					val = patron.getMovies().size();
					break;
				}
				case 5: {
					val = patron.getId();
					break;
				}

				default:
					break;
				}
				return val;
			}
        	
        };

        JTable table = new JTable(model);
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        Cursor pointertCursor = new Cursor(Cursor.HAND_CURSOR);
        
        table.addMouseMotionListener(new MouseMotionAdapter() {
        	
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3 && ((Integer) table.getModel().getValueAt(row, col)) > 0) {
					setCursor(pointertCursor);
					//changing cursor on num. of movie
				}else if (col == 4 && ((Integer) table.getModel().getValueAt(row, col)) > 0) {
					setCursor(pointertCursor);
				} else {
					setCursor(defaultCursor);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3 && ((Integer) table.getModel().getValueAt(row, col)) > 0) {
					int id = (int) table.getModel().getValueAt(row, 5);
					try {
						Patron patron = library.getPatronByID(id);
						List<Book> books = patron.getBooks();
						StringBuilder sb = new StringBuilder();
						sb.append("Loaned books:\n");
						for (Book book : books) {
							sb.append(book.getDetailsShort());
							sb.append("\n");
						}
						
						JOptionPane.showMessageDialog(table, sb.toString());
					} catch (LibraryException e1) {
						JOptionPane.showMessageDialog(e.getComponent(), e1, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		// link to num of movie
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
		if (col == 4 && ((Integer) table.getModel().getValueAt(row, col)) > 0) {
			int id = (int) table.getModel().getValueAt(row, 5);
			try {
				Patron patron = library.getPatronByID(id);
				List<Movie> movies = patron.getMovies();
				StringBuilder sb = new StringBuilder();
				sb.append("Loaned movies:\n");
				for (Movie movie : movies) {
					sb.append(movie.getDetailsShort());
					sb.append("\n");
				}
				
				JOptionPane.showMessageDialog(table, sb.toString());
			} catch (LibraryException e1) {
				JOptionPane.showMessageDialog(e.getComponent(), e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
			}
		});
		
		table.setDefaultRenderer(String.class, new PatronCellRenderer());
		patronsTable = table;
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        enableMenuItems(memAdd,memDel);
        disableMenuItems(booksAdd,booksDel,booksIssue,booksReturn,booksRenew);

        this.revalidate();
        currentPanel = MEMBERS_PANEL;
    }

	/**
	 * 
	 * Make patron table cells with number of loaned books looks like hyperlink
	 *
	 */
	public static class PatronCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
			int modelRow = table.convertRowIndexToModel(row);
			int modelCol = table.convertColumnIndexToModel(column);
			if (modelCol == 3 && ((Integer) table.getModel().getValueAt(modelRow, modelCol)) > 0 || modelCol == 4 && ((Integer) table.getModel().getValueAt(modelRow, modelCol)) > 0 ) {
				Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
				fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				Font boldUnderline = new Font("Verdana", Font.ITALIC, 12).deriveFont(fontAttributes);
				cell.setFont(boldUnderline);
			}
			return cell;
		}
	}

	/**
	 * 
	 * Make book status cells looks like hyperlink
	 *
	 */
	
	public static class BookCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
			int modelRow = table.convertRowIndexToModel(row);
			int modelCol = table.convertColumnIndexToModel(column);
			if (modelCol == 3) {
				String status = (String) table.getModel().getValueAt(modelRow, modelCol);
				if ("Busy".equals(status)) {
					Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
					fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					Font boldUnderline = new Font("Verdana",Font.ITALIC, 12).deriveFont(fontAttributes);
					cell.setFont(boldUnderline);
				} 
				}
				

			return cell;
		}
	}
	
	/**
	 * Call Delete Movie command or display error message if movie is on loan or other exception
	 */
	private void deleteMovie() {
		int row = moviesTable.getSelectedRow();
		if (row >= 0) {
			try {
				int id = (int) moviesTable.getModel().getValueAt(row, 4);
				Movie movie = library.getMovieByID(id);
				Command deletemovie = new DeleteMovie(movie.getId());
				deletemovie.execute(library, LocalDate.now());
				String msg = movie.getDetailsShort() + "\nhas been deleted from library";
				JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
				displayMovies();
			} catch (LibraryException e) {
				JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 * Call Renew Movie command for given movie or display error message if movie is not on loan
	 */
	private void renewMovie() {
		int row = moviesTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) moviesTable.getModel().getValueAt(row, 4);
			Movie movie = null;
			try {
				movie = library.getMovieByID(id);
			} catch (LibraryException e1) {
	            JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (movie != null && movie.isOnLoan()) {
				Command renewmovie = new RenewMovie(movie.getLoan().getPatron().getId(), movie.getId()); 
				try {
					renewmovie.execute(library, LocalDate.now());
					displayMovies();
				} catch (LibraryException e) {
		            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Open IssuemovieWindow dialog
	 */
	void issueMovie() {
		int row = moviesTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) moviesTable.getModel().getValueAt(row, 4);
			Movie movie = null;
			try {
				movie = library.getMovieByID(id);
			} catch (LibraryException e1) {
				JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (movie != null && !movie.isOnLoan()) {
				new IssueMovieWindow(this, movie);
			}
		}
	}

	/**
	 * Call Return Movie command or display error message if movie is not on loan
	 */
	void returnMovie() {
		int row = moviesTable.getSelectedRow();
		if (row >= 0) {
			int id = (int) moviesTable.getModel().getValueAt(row, 4);
			Movie movie = null;
			try {
				movie = library.getMovieByID(id);
			} catch (LibraryException e1) {
				JOptionPane.showMessageDialog(this, e1, "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (movie != null && movie.isOnLoan()) {
				Command returnmovie = new ReturnMovie(movie.getLoan().getPatron().getId(), movie.getId());
				try {
					if(movie.getLoan().getDueDate().isBefore(LocalDate.now())) {
						movie.getLoan().setLatePrice();
						String msg = "Movie is return to late \n fee = " + movie.getLoan().getPenaltyFee();
						JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
						displayBooks();
					}
					returnmovie.execute(library, LocalDate.now());
					String msg = "movie " + movie.getDetailsShort() + "\nhas been returned from loan to library";
					JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
					displayMovies();
				} catch (LibraryException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Display list of movies in table
	 */
	public void displayMovies() {
        List<Movie> moviesList = library.getMovies();
        String[] columns = new String[]{"Title", "Director", "Pub Date", "Status"};
        DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public int getRowCount() {
				return moviesList.size();
			}

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			@Override
			public String getColumnName(int column) {
				return columns[column];
			}

			@Override
			public Object getValueAt(int row, int column) {
				Movie movie = moviesList.get(row);
				Object val = null;
				switch (column) {
				case 0: {
					val = movie.getTitle();
					break;
				}
				case 1: {
					val = movie.getDirector();
					break;
				}
				case 2: {
					val = movie.getPublicationYear();
					break;
				}
				case 3: {
					val = movie.getStatus();
					break;
				}
				case 4: {
					val = movie.getId();
					break;
				}

				default:
					break;
				}
				return val;
			}
			
        };

        JTable table = new JTable(model);
        moviesTable = table;
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        Cursor pointertCursor = new Cursor(Cursor.HAND_CURSOR);
        
        table.addMouseMotionListener(new MouseMotionAdapter() {
        	
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3 && "Busy".equals((String) table.getModel().getValueAt(row, col))) {
					setCursor(pointertCursor);
				} else {
					setCursor(defaultCursor);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				int col = table.convertColumnIndexToModel(table.columnAtPoint(e.getPoint()));
				if (col == 3) {
					String status = (String) table.getModel().getValueAt(row, col);
					if ("Busy".equals(status)) {
						int id = (int) table.getModel().getValueAt(row, 4);
						Movie movie;
						try {
							movie = library.getMovieByID(id);
							JOptionPane.showMessageDialog(table, "Loan info:\n" + movie.getLoan().getPatron().getDetailsShort());
						} catch (LibraryException e1) {
							JOptionPane.showMessageDialog(e.getComponent(), e1, "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		table.setDefaultRenderer(String.class, new movieCellRenderer());
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        disableMenuItems(memAdd,memDel);
        enableMenuItems(moviesAdd,moviesDel,moviesIssue,moviesReturn,moviesRenew);

        this.revalidate();
        currentPanel = MOVIES_PANEL;
    }
	
	
	public static class movieCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
			int modelRow = table.convertRowIndexToModel(row);
			int modelCol = table.convertColumnIndexToModel(column);
			if (modelCol == 3) {
				String status = (String) table.getModel().getValueAt(modelRow, modelCol);
				if ("Busy".equals(status)) {
					Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
					fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					Font boldUnderline = new Font("Verdana",Font.ITALIC, 12).deriveFont(fontAttributes);
					cell.setFont(boldUnderline);
				} 
				}
				

			return cell;
		}
	}

	
}
