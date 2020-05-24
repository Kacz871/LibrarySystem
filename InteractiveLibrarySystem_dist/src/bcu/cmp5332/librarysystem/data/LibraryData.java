package bcu.cmp5332.librarysystem.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

public class LibraryData {

	public static final String LIBRARY_DIRECTORY = "./resources/data";
	public static final String BACKUP_DIRECTORY = "./resources/data/backup";
	private static final List<DataManager> dataManagers = new ArrayList<>();

	// runs only once when the object gets loaded to memory
	static {
		dataManagers.add(new BookDataManager());
		dataManagers.add(new PatronDataManager());
		dataManagers.add(new LoanDataManager());
		dataManagers.add(new MovieDataManager());

	}

	public static Library load() throws LibraryException, IOException {
		return load(true);
	}

	private static Library load(boolean backup) throws LibraryException, IOException {
		if (backup) {
			backupLibrary();
		}
		Library library = new Library();
		for (DataManager dm : dataManagers) {
			dm.loadData(library);
		}
		return library;
	}
	
	public static Library loadFromBackup() throws LibraryException, IOException {
		restoreLibrary();
		return load(false);
	}
	
/**
 * Copy all library data files to backup directory
 * @throws IOException
 */
	public static void backupLibrary() throws IOException {
		File backupDir = new File(BACKUP_DIRECTORY);
		if (!backupDir.exists()) {
			backupDir.mkdirs();
		}
		File[] files = new File(LIBRARY_DIRECTORY).listFiles(File::isFile);
		for (File file : files) {
			Files.copy(file.toPath(), new File(BACKUP_DIRECTORY,file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
	}
	
/**
 * Restore all library data files from backup directory
 * @throws IOException
 */
	public static void restoreLibrary() throws IOException {
		File[] files = new File(BACKUP_DIRECTORY).listFiles(File::isFile);
		for (File file : files) {
			Files.copy(file.toPath(), new File(LIBRARY_DIRECTORY,file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
	}
	
	public static void store(Library library) throws IOException {

		for (DataManager dm : dataManagers) {
			dm.storeData(library);
		}
	}

}
