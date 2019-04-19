/**
 * 
 */
package us.cartisan.mp3anager;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

/**
 * @author hyungkook.kim
 *
 */
public class Mp3SearchVisitor implements FileVisitor<Path> {
	private Path startDir;
	private String searchWord;
	private List<Path> searchedList;
	
	public Mp3SearchVisitor(Path startDir) {
		this.startDir = startDir;
		this.searchWord = null;
		this.searchedList = new ArrayList<Path>();
	}


	public Mp3SearchVisitor(Path startDir, String searchWord) {
		this.startDir = startDir;
		this.searchWord = searchWord;
		this.searchedList = new ArrayList<Path>();
	}

	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (searchWord == null) {
			searchedList.add(dir);
		} else {
			String[] spliteds = dir.toString().split("\\\\");
			if (spliteds[spliteds.length - 1].contains(searchWord)) {
				searchedList.add(dir);
			}
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		String extension = FilenameUtils.getExtension(file.getFileName().toString());
		if ("MP3".equals(extension.toUpperCase())) {
			final String fileName = file.getFileName().toString();
			if (searchWord == null || fileName.contains(searchWord)) {
				searchedList.add(file);
			}
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		if (searchWord != null) {
			System.out.println("Failed:\t" + file.toString());
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (searchWord != null) {
			boolean finishedSearch = Files.isSameFile(dir, startDir);
			if (finishedSearch && searchedList.size() == 0) {
				System.out.println("=> " + searchWord + " not found.");
				return FileVisitResult.TERMINATE;
			}
		}
		return FileVisitResult.CONTINUE;
	}
	
	public List<Path> getResult() {
		return searchedList;
	}

}
