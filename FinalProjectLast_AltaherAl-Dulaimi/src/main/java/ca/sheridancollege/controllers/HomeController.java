package ca.sheridancollege.controllers;

import java.util.ArrayList;

import javax.security.auth.AuthPermission;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Invoice;
import ca.sheridancollege.beans.Movie;
import ca.sheridancollege.beans.Ticket;
import ca.sheridancollege.database.DataBaseAccess;


@Controller
public class HomeController {
	
	@Autowired
	@Lazy
	private DataBaseAccess da;
	
	private Invoice in = new Invoice ();
	
	static ArrayList <Integer> list = new ArrayList<Integer> () ;
	
	
	@GetMapping("/")
	public String goHome() {
		
		return "home.html"; // The Unsecure one
	}
	
	
	@GetMapping("/user")
	public String goHomeUser() {
		goLoginPage(); // will tranfer to 'login.html' then to 'user/index.html' (ONE TIME THING)
		return "/user/index.html"; // The secure one
	}
	
	
	@GetMapping("/login")
	public String goLoginPage() {
		return "login.html";
	}
	
	@GetMapping("/register")
	public String goRegisterPage() {
		return "register.html";
	}
	
	@PostMapping("/register")
    public String processRegistration(@RequestParam String name, @RequestParam String password){
        da.addUser(name, password);
        long userId=da.findUserAccount(name).getUserId();
        da.addRole(userId, 2);

        return "redirect:/";
    }
	
	
	
	@GetMapping("/access-denied")
	public String goAccessDenied(Model model) {
		model.addAttribute("seatReserved", "Your are not authorized to Access! Please Do Not!");
		return "home.html";
	}
	

	// ______________________________________________
	
	// direct to movie pages
	@GetMapping("/movieOne")
	public String goMovieOne(Model model) {
		
		model.addAttribute("movieObj", new Movie());
		return "movieOne.html";
		
	}
	@GetMapping("/movieTwo")
	public String goMovieTwo(Model model) {
		model.addAttribute("movieObj", new Movie());
		return "movieTwo.html";
		
	}
	@GetMapping("/movieThree")
	public String goMovieThree(Model model) {
		model.addAttribute("movieObj", new Movie());
		return "movieThree.html";
		
	}
	@GetMapping("/movieFour")
	public String goMovieFour(Model model) {
		model.addAttribute("movieObj", new Movie());
		return "movieFour.html";
		
	}
	@GetMapping("/movieFive")
	public String goMovieFive(Model model) {
		model.addAttribute("movieObj", new Movie());
		return "movieFive.html";
		
	}
	// _______________________________________________________
	
	// direct to the seat page
	@PostMapping("/placeOrder")
	public String goSeats(Model model, @ModelAttribute Movie secondMovieObj, HttpSession session) {
		System.out.println (secondMovieObj.getMovieId() + "Id"); //test 
		System.out.println (secondMovieObj.getMovieName() + "Mname"); // test
		System.out.println (secondMovieObj.getMovieOccupation()+"occ"); //test
		System.out.println (secondMovieObj.getGuestName()+"Gname"); //test
		System.out.println (secondMovieObj.getSeatChosen()+ "seat"); //test
		
		System.out.println (secondMovieObj.getMovieTime() + "time"); //test
		System.out.println (secondMovieObj.getMoviePrice()+ "price"); //test
		System.out.println (secondMovieObj.getMovieDate()+ "price"); //test
		System.out.println (session.getId()); // Rely on the seesion Id.

		Ticket ticket = new Ticket();
		secondMovieObj.setMoviePrice(ticket.discountCalc(secondMovieObj.getMovieOccupation(),
				secondMovieObj.getMoviePrice()));
		
		// Seats
		System.out.println(secondMovieObj.checkSeat(list, (secondMovieObj.getSeatChosen())));
		if (secondMovieObj.isFlag()) 
			list.add(secondMovieObj.getSeatChosen());
			else {
				return goHomeWrongSeat(model);
			}
		
		//System.out.println(list.size());
		secondMovieObj.setSeatLeft(100-list.size());
		// seats End
		
		da.addMovieDetails(session.getId() ,secondMovieObj.getMovieId(),secondMovieObj.getMovieName(), secondMovieObj.getMovieOccupation(),
				secondMovieObj.getGuestName(), secondMovieObj.getSeatChosen(), secondMovieObj.getMovieTime(),
				secondMovieObj.getMovieDate(),secondMovieObj.getMoviePrice(), secondMovieObj.getSeatLeft());
		model.addAttribute("movieSumm", secondMovieObj.stringDesign(secondMovieObj));
		
		in.writeToFile(secondMovieObj.stringToFileAndBlock(secondMovieObj)); // must be moved to the next method of Cart page
		
		
		// ___________________________________ Carry with 'HTTPsession' to goOrderHistory
		
		session.setAttribute("getMovieId", secondMovieObj.getMovieId());
		session.setAttribute("getMovieName", secondMovieObj.getMovieName());
		session.setAttribute("getMovieOccupation", secondMovieObj.getMovieOccupation());
		session.setAttribute("getGuestName", secondMovieObj.getGuestName());
		session.setAttribute("getSeatChosen", secondMovieObj.getSeatChosen());
		session.setAttribute("getMovieTime", secondMovieObj.getMovieTime());
		session.setAttribute("getMoviePrice", secondMovieObj.getMoviePrice());
		session.setAttribute("getMovieDate", secondMovieObj.getMovieDate());
		
		return "placeOrder.html";
	}
	
	
	//________________________________________________________________________________________________________
	
	@GetMapping("/orderHistory")
	public String goOrderHistory(HttpSession session, Model model) {
		
		System.out.println ("in orderHistory"); //test
		System.out.println ((String)(session.getAttribute("getGuestName"))); // test
		System.out.println ((String)session.getId()); // test
		
		 model.addAttribute("movies", da.getMovieBySessionID((String) session.getId()));
		 model.addAttribute("dollarSign", "$");
		
		return "orderHistory.html";
		
	}
	
	// Called when user enter the same
	public String goHomeWrongSeat(Model model) {
		model.addAttribute("seatReserved", "The Seat is reserved! Please repeat the process");
		return "home.html";
	}
	
}
