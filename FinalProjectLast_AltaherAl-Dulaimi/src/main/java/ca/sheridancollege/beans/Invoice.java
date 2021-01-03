package ca.sheridancollege.beans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2302279052415405693L;
	private static int counter;

	public void writeToFile(String block) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:/receipts/Invoice" + counter + ".txt"));

			bw.write(block);
			bw.newLine();
			bw.flush();
			++counter;
			System.out.println("Folder created");
		} catch (IOException e) {
			System.out.println("File Error Is: " + e.getMessage());
		}
	}
}
