package utils;

import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.nio.charset.StandardCharsets;
	import java.util.ArrayList;
	import java.util.List;

	public class Input {

		public static List<String> getInput(Class<?> resourceClass, String filename) {
			List<String> lines = new ArrayList<>();
			try (InputStream inputStream = resourceClass.getResourceAsStream(filename);
				 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					lines.add(line);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return lines;
		}

	}
