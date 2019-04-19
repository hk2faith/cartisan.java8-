/**
 * 
 */
package us.cartisan.mp3anager;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

/**
 * @author hyungkook.kim
 *
 */
public class Mp3FileBrowser {
	public static List<Path> retrieve(final String rootPath) {
		final Path rootP = Paths.get(rootPath);

		final List<Path> matchedList = new ArrayList<>();
		try (final Stream<Path> pathStream = Files.walk(rootP, 1, FileVisitOption.FOLLOW_LINKS)) {
			pathStream.filter((p) -> isMatched(rootP, p, null)).forEach(p -> matchedList.add(p));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return matchedList;
	}

	public static List<Path> search(final String rootPath, final String query) {
		final Path rootP = Paths.get(rootPath);

		final List<Path> matchedList = new ArrayList<>();
		try (final Stream<Path> pathStream = Files.walk(Paths.get(rootPath), FileVisitOption.FOLLOW_LINKS)) {
			pathStream.filter((p) -> isMatched(rootP, p, query)).forEach(p -> matchedList.add(p));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return matchedList;
	}

	private static boolean isMatched(final Path rootP, final Path p, final String query) {
		File f = p.toFile();
		if (f.isDirectory()) {
			return isMatchedDirectory(rootP, p, query);
		} else {
			return isMatchedMp3(p, query);
		}
	}

	private static boolean isMatchedDirectory(final Path rootP, final Path p, final String query) {
		if (isRootDir(rootP, p)) {
			return false;
		} else if (query == null) {
			return true;
		}

		String[] splits = p.toString().split("\\\\");
		if (splits[splits.length - 1].contains(query)) {
			return true;
		}
		return false;
	}

	private static boolean isRootDir(final Path rootP, final Path p) {
		boolean isRootDir = false;
		try { isRootDir = Files.isSameFile(p, rootP); } catch (IOException e) { }
		return isRootDir;
	}

	private static boolean isMatchedMp3(final Path p, final String query) {
		final String fileName = p.getFileName().toString();
		final String fileExt = FilenameUtils.getExtension(fileName);
		if ("MP3".equals(fileExt.toUpperCase())) {
			if (query == null || fileName.contains(query)) {
				return true;
			}
		}
		return false;
	}
}
