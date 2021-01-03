package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Movie;
import ca.sheridancollege.beans.User;

@Repository
public class DataBaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	// to find the User
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where userName=:userName"; // :userName - its our named parameter
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId " + "and userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// add user
	public void addUser(String userName, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User " + "(userName, encryptedPassword, ENABLED)"
				+ " values (:userName, :encryptedPassword, 1)";
		parameters.addValue("userName", userName);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
	}

	// Add role
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId)" + "values (:userId, :roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}

	// ________________________________________________________________________________________


	// add Movie
	public void addMovieDetails(String userSessionID, int movieId, String movieName, int movieOccupation,
			String guestName, int seatChosen, String movieTime, String movieDate, double moviePrice, int seatLeft) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into movie_Details "
				+ "(userSessionID ,movieId, movieName, movieOccupation, guestName, seatChosen, movieTime, movieDate, moviePrice, seatLeft)"
				+ " values (:userSessionID ,:movieId,:movieName, :movieOccupation, :guestName, :seatChosen, :movieTime, :movieDate, :moviePrice, :seatLeft)";
		parameters.addValue("userSessionID", userSessionID);
		parameters.addValue("movieId", movieId);
		parameters.addValue("movieName", movieName);
		parameters.addValue("movieOccupation", movieOccupation);
		parameters.addValue("guestName", guestName);
		parameters.addValue("seatChosen", seatChosen);
		parameters.addValue("movieTime", movieTime);
		parameters.addValue("movieDate", movieDate);
		parameters.addValue("moviePrice", moviePrice);
		parameters.addValue("seatLeft", seatLeft);
		jdbc.update(query, parameters);
	}

	// Get Records based on the tickets Name
	public ArrayList<Movie> getMovieBySessionID(String sessionID) {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		String q = "SELECT * FROM movie_Details WHERE userSessionID= :userSessionID"; // :id is the name parameter

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userSessionID", sessionID);
		List<Map<String, Object>> rows = jdbc.queryForList(q, parameters);

		// itterate through our Database table for each row to store the whole object to
		// arrayList

		for (Map<String, Object> row : rows) {
			Movie m = new Movie();
			m.setMovieId((Integer) row.get("movieId"));
			// System.out.println ("movieId is fine"); TEST
			m.setMovieName((String) (row.get("movieName"))); // must be the same name of the data base Row!
			// System.out.println ("MovieName is fine"); TEST
			m.setMovieOccupation((Integer) (row.get("movieOccupation")));
			// System.out.println ("movieOCC is fine"); TEST
			m.setGuestName(((String) (row.get("guestName"))));
			// System.out.println ("guestName is fine"); TEST
			m.setSeatChosen((Integer) (row.get("seatChosen")));
			// System.out.println ("seat chosen is fine"); TEST
			m.setMovieTime(((String) (row.get("movieTime"))));
			// System.out.println ("movieTime is fine"); TEST
			m.setMovieDate(((String) (row.get("movieDate"))));
			// System.out.println ("movieDate is fine"); TEST
			m.setMoviePrice(((BigDecimal) (row.get("moviePrice"))).doubleValue());
			// System.out.println ("moviePrice is fine");TEST
			m.setSeatLeft(((Integer) (row.get("seatLeft"))));
			System.out.println("Da.getMovieByGuestName is fine");

			// Add to the Object ArrayList
			movieList.add(m);
		}

		return movieList;
	}
}
