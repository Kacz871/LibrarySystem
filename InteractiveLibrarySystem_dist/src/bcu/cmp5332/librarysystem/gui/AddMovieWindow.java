package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddMovie;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddMovieWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField publisherText = new JTextField();
    private JTextField pubDateText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddMovieWindow(MainWindow mw) {
        this.mw = mw;
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

        setTitle("Add a New Movie");

        setSize(450, 300);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2));
        topPanel.add(new JLabel("Title : "));
        topPanel.add(titleText);
        topPanel.add(new JLabel("Director : "));
        topPanel.add(authText);
        topPanel.add(new JLabel("Studio : "));
        topPanel.add(publisherText);
        topPanel.add(new JLabel("Publishing Date : "));
        topPanel.add(pubDateText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addMovie();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void addMovie() {
        try {
            String title = titleText.getText();
            String author = authText.getText();
            String publisher = publisherText.getText();
            String publicationYear = pubDateText.getText();
            // create and execute the AddMovie Command
            Command addMovie = new AddMovie(title, author, publisher, publicationYear);
            addMovie.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of Movies
            mw.displayMovies();
            // hide (close) the AddMovieWindow
            this.setVisible(false);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
