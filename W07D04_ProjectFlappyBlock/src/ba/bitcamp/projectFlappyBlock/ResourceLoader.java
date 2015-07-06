package ba.bitcamp.projectFlappyBlock;

import java.io.InputStream;

public class ResourceLoader {
	/**
	 * Loads a image from a resource folder.
	 * 
	 * @param path
	 *            - Location of a file.
	 * @return Path of a file that needs to be loaded.
	 */
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/" + path);
		}
		return input;
	}
}
