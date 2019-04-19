/**
 * 
 */
package us.cartisan.mp3anager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * @author hyungkook.kim
 * 
 * {@link : https://www.javacodegeeks.com/2014/05/playing-with-java-8-lambdas-paths-and-files.html }
 *
 */
public class Mp3FileBrowserTest {
	@Test
	public void testSearch() throws IOException, UnsupportedTagException, InvalidDataException {
		String query = "태연";
		List<Path> pathList = Mp3FileBrowser.search("D:\\hk\\music", query);
		if (pathList.size() > 0) {
			for (Path p : pathList) {
				File f = p.toFile();
				if (f.isDirectory()) {
					System.out.println("DIRECTORY: " + p);
				} else {
					System.out.println("FILE: " + p);
					Mp3TagReader.printId3Tag(p);
				}
			}
			
			System.out.println("\nFILE & DIRECTORY['" + query + "'] is found. totalCount: " + pathList.size() + "\n");
		} else {
			System.out.println("FILE & DIRECTORY['" + query + "'] is not found.");
		}
	}
	
	@Test
	public void testRetrieve() throws IOException, UnsupportedTagException, InvalidDataException {
		String path = "D:\\hk\\music";
		List<Path> pathList = Mp3FileBrowser.retrieve(path);
		if (pathList.size() > 0) {
			for (Path p : pathList) {
				File f = p.toFile();
				if (f.isDirectory()) {
					System.out.println("DIRECTORY: " + p);
				} else {
					System.out.println("FILE: " + p);
					Mp3TagReader.printId3Tag(p);
				}
			}
			
			System.out.println("\nDIRECTORY['" + path + "'] is retrieved. totalCount: " + pathList.size() + "\n");
		} else {
			System.out.println("DIRECTORY['" + path + "'] is not retrieved.");
		}
	}
}
