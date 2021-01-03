package ca.sheridancollege.beans;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6953452271823595008L;
	private int movieId;
	private String movieName;
	private int movieOccupation;
	private String guestName;
	private int seatChosen;
	private String movieTime;
	private double moviePrice = 15;
	boolean flag = true;
	private int seatLeft;
	private String movieDate;

	private ArrayList<Integer> list = new ArrayList<Integer>(99);

	// Design String to be returned
	public String stringDesign(Movie objOfMovie) {
		String design = "";
		String occ = "";
		if (objOfMovie.getMovieOccupation() == 0)
			occ = "No Ocuupation Assigned";
		else if (objOfMovie.getMovieOccupation() == 1)
			occ = "Sheridan college Student";
		else if (objOfMovie.getMovieOccupation() == 2)
			occ = "PROG 32758 Student";
		else if (objOfMovie.getMovieOccupation() == 3)
			occ = "Senior over 65 or Child";
		else if (objOfMovie.getMovieOccupation() == 4)
			occ = "A Tuesday Today";

		
		design = "Movie Name: " + objOfMovie.getMovieName() + "\n";
		design += "Current Occupaion: " + occ + "\n";
		design += "Client Name: " + objOfMovie.getGuestName() + "\n";
		design += "Seat Number: " + objOfMovie.getSeatChosen() + "\n";
		design += "Movie Show Time: " + objOfMovie.getMovieTime() + " P.M" + "\n";
		design += "Movie Date: " + objOfMovie.getMovieDate() + "\n";
		design += "Movie Price: " + objOfMovie.getMoviePrice() + "\n";
		design += "Seats Available: " + objOfMovie.getSeatLeft() + "\n";

		return design;
	}

	// ArrayList

	public boolean checkSeat(ArrayList<Integer> list, int x) {

		for (Integer i : list) {
			if (list.contains((Integer) x)) {
				System.out.println("from ceckseat  " + list);
				System.out.println("from ceckseat " + x);
				flag = false;

				
				break;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	// Print to File
	public String stringToFileAndBlock(Movie objOfMovie) {

		DateFormat d = new SimpleDateFormat("dd/MM/yy");
		DateFormat t = new SimpleDateFormat("HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		System.out.println(d.format(calobj.getTime()));
		System.out.println(t.format(calobj.getTime()));

		String design = "";
		String occ = "";
		if (objOfMovie.getMovieOccupation() == 0)
			occ = "No Ocuupation Assigned";
		else if (objOfMovie.getMovieOccupation() == 1)
			occ = "Sheridan college Student";
		else if (objOfMovie.getMovieOccupation() == 2)
			occ = "PROG 32758 Student";
		else if (objOfMovie.getMovieOccupation() == 3)
			occ = "Senior over 65 or Child";
		else if (objOfMovie.getMovieOccupation() == 4)
			occ = "A Tuesday Today";

		
		design = "Invoice Of Purchase: " + "\n";
		design += " ___________________________" + "\n";
		design += "The Date of Purchase: " + d.format(calobj.getTime()) + "\n";
		design += "The Time of Purchase: " + t.format(calobj.getTime()) + "\n";
		design += " ___________________________" + "\n";
		design += "Movie Name: " + objOfMovie.getMovieName() + "\n";
		design += "Current Occupaion: " + occ + "\n";
		design += "Client Name: " + objOfMovie.getGuestName() + "\n";
		design += "Seat Number: " + objOfMovie.getSeatChosen() + "\n";
		design += "Movie Show Time: " + objOfMovie.getMovieTime() + " P.M" + "\n";
		design += "Movie Date: " + objOfMovie.getMovieDate() + "\n";
		design += "Movie Price: " + "$" + objOfMovie.getMoviePrice() + "\n";
		design += "Seats Available: " + objOfMovie.getSeatLeft() + "\n";
		design += " ___________________________" + "\n";

		return design;
	}

}
