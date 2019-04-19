package us.cartisan.mp3anager;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;

import org.junit.Test;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * @author hyungkook.kim
 *
 */
public class Mp3SearchVisitorTest {
	@Test
	public void testMp3SearchVisitor() throws IOException {
		Path startingDir = Paths.get("D:\\hk\\music\\콩순이");
		Mp3SearchVisitor mp3SearchVisitor = new Mp3SearchVisitor(startingDir, null);
		Files.walkFileTree(startingDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), 1, mp3SearchVisitor);
	}

	@Test
	public void testMp3SearchVisitor2() throws IOException, UnsupportedTagException, InvalidDataException {
		Path startingDir = Paths.get("D:\\hk\\music");
		Mp3SearchVisitor mp3SearchVisitor = new Mp3SearchVisitor(startingDir, "원더걸스");
		Files.walkFileTree(startingDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, mp3SearchVisitor);
		List<Path> result = mp3SearchVisitor.getResult();
		if (result.size() > 0) {
			for (Path p : result) {
				File f = p.toFile();
				if (f.isDirectory()) {
					System.out.println("dir:\t" + p);
				} else {
					System.out.println("file:\t" + p);

					Mp3File mp3file = new Mp3File(p.toString());

					System.out.println("\tLength of this mp3 is :" + mp3file.getLengthInSeconds() + " seconds");
					System.out.println(
							"\tBitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
					System.out.println("\tSample rate: " + mp3file.getSampleRate() + " Hz");

					System.out.println("\tHas ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
					if (mp3file.hasId3v1Tag()) {
						ID3v1 id3v1Tag = mp3file.getId3v1Tag();
						System.out.println("\t\tTrack: " + id3v1Tag.getTrack());
						System.out.println("\t\tArtist: " + id3v1Tag.getArtist());
						System.out.println("\t\tTitle: " + id3v1Tag.getTitle());
						System.out.println("\t\tAlbum: " + id3v1Tag.getAlbum());
						System.out.println("\t\tYear: " + id3v1Tag.getYear());
						System.out
								.println("\t\tGenre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
						System.out.println("\t\tComment: " + id3v1Tag.getComment());
					}

					System.out.println("\tHas ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
					if (mp3file.hasId3v2Tag()) {
						ID3v2 id3v2Tag = mp3file.getId3v2Tag();
						System.out.println("\t\tTrack: " + id3v2Tag.getTrack());
						System.out.println("\t\tArtist: " + id3v2Tag.getArtist());
						System.out.println("\t\tTitle: " + id3v2Tag.getTitle());
						System.out.println("\t\tAlbum: " + id3v2Tag.getAlbum());
						System.out.println("\t\tYear: " + id3v2Tag.getYear());
						System.out.println(
								"\t\tGenre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
						System.out.println("\t\tComment: " + id3v2Tag.getComment());
						System.out.println("\t\tComposer: " + id3v2Tag.getComposer());
						System.out.println("\t\tPublisher: " + id3v2Tag.getPublisher());
						System.out.println("\t\tOriginal artist: " + id3v2Tag.getOriginalArtist());
						System.out.println("\t\tAlbum artist: " + id3v2Tag.getAlbumArtist());
						System.out.println("\t\tCopyright: " + id3v2Tag.getCopyright());
						System.out.println("\t\tURL: " + id3v2Tag.getUrl());
						System.out.println("\t\tEncoder: " + id3v2Tag.getEncoder());
					}

					System.out.println("\tHas custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));

				}
			}
		}
	}
}
