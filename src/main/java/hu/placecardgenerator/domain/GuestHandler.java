package hu.placecardgenerator.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GuestHandler {

	private List<String> guests;

	public GuestHandler() {
		guests = new ArrayList<>();
	}

	public void add(String guest) {
		guests.add(guest);
	}

	public void load(File file) throws IOException {
		guests = new ArrayList<String>();

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {
			guests.add(line);
		}
		br.close();
	}

	/**
	 * Returns the number of Guest name from the list. The guests are removed from
	 * the data source.
	 * 
	 * @param count
	 * @return
	 */
	public Queue<String> getNext(int count) {
		Queue<String> result = getNextInternal(count);
		remove(result);
		return result;
	}
	
	private Queue<String> getNextInternal(int count) {
		if (guests.size() > 0) {
			int res = Math.min(count, guests.size());
			return new ArrayDeque<String>(guests.subList(0, res));
		} else {
			return new ArrayDeque<String>();
		}
	}
	
	private boolean remove(Queue<String> c) {
		return guests.removeAll(c);
	}

	public float size() {
		return guests.size();
	}
}
