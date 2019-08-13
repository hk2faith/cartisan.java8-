/**
 * 
 */
package us.cartisan.mp3anager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * @author hyungkook.kim
 * 
 * {@link : https://github.com/mpatric/mp3agic }
 * {@link : https://github.com/mpatric/mp3agic-examples/tree/master/src/main/java/com/mpatric/mp3agic/app }
 *
 */
public class Mp3TagReader {
	public static void printId3Tag(Path p, boolean withImage) throws UnsupportedTagException, InvalidDataException, IOException {
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
			
			if (withImage) {
				extractPics(id3v2Tag);
			}
		}

		System.out.println("\tHas custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
		
	}
	
	private static void extractPics(ID3v2 id3v2tag) {
		if (id3v2tag != null) {
			String mimeType = id3v2tag.getAlbumImageMimeType();
			byte[] data = id3v2tag.getAlbumImage();
			String encodedString = Base64.getEncoder().encodeToString(data);
			
			System.out.println("\t\tencodedAlbumImageBase64: data:" + mimeType + ";base64," + encodedString);
		}
	}
}
