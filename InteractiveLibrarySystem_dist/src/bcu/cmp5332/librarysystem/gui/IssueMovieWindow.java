package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.IssueMovie;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Movie;
import bcu.cmp5332.librarysystem.model.Patron;

public class IssueMovieWindow extends JFrame  implements ActionListener{

    private MainWindow mw;

    private JButton issueBtn = new JButton("Issue");
    private JButton cancelBtn = new JButton("Cancel");
    private Movie movie;
    private JTable patronTable;
    
	public IssueMovieWindow(MainWindow mw, Movie movie) {
		this.mw = mw;
		this.movie = movie;
		initialize();
	}

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Issue movie to Patron");

        setSize(500, 300);
        JPanel topPanel = new JPanel();
		topPanel.add(new JTextArea("Select Patron from the list below to issue movie:\n" + movie.getDetailsShort()));
        
        JPanel centerPanel = new JPanel();
        displayPatrons(centerPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(issueBtn);
        bottomPanel.add(cancelBtn);

        issueBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel,BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        revalidate();

        setVisible(true);

    }

    public void displayPatrons(JPanel panel) {
        List<Patron> patrons = mw.getLibrary().getPatrons();
        String[] columns = new String[]{"Name", "Phone", "Email", "Num. of movies"};

        Object[][] data = new Object[patrons.size()][5];
        for (int i = 0; i < patrons.size(); i++) {
        	Patron patron = patrons.get(i);
            data[i][0] = patron.getName();
            data[i][1] = patron.getPhone();
            data[i][2] = patron.getEmail();
            data[i][3] = patron.getMovies().size();
        }

        JTable table = new JTable(data, columns);
        patronTable = table;
        panel.add(new JScrollPane(table));
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == issueBtn) {
            issuemovie();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

	private void issuemovie() {
		int row = patronTable.getSelectedRow();
		if (row >= 0) {
			Patron patron = mw.getLibrary().getPatrons().get(row);
			try {
				//create and execute IssueMovie command
				Command issueCommand = new IssueMovie(patron.getId(), movie.getId());
				issueCommand.execute(mw.getLibrary(), LocalDate.now());
	            // refresh the view with the list of movies
				mw.displayMovies();
	            // hide (close) the IssuemovieWindow
				this.setVisible(false);
			} catch (LibraryException e) {
	            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
 
}
