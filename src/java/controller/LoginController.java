package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.Airport;
import model.Flight;
import model.User;
import org.hibernate.Session;

@Named(value = "loginController")
@SessionScoped
@ManagedBean
public class LoginController {

    private static int ticketId = 10000000;

    //login data
    private String username;
    private String passwoord;
    private User currentUser;

    //change password data
    private String newpassword;

    //new user data
    private String name_new;
    private char gender_new;
    private Date birth_new;
    private String airline_new;
    private String username_new;
    private String password_new;
    private String type_new;
    private List<Airline> airlines = null;
    private List<String> airlineNames = null;

    //flight search
    private boolean oneway = false;
    private String from;
    private String to;
    private Date date_from;
    private Date date_to;
    private boolean direct;
    private int tickets = 1;
    private List<Flight> searchedFlightsTo = null;
    private List<Flight> searchedFlightsReturn = null;
    private Flight selectedFlight;
    private boolean show1 = false;
    private boolean show2 = false;

    //list of airports
    private List<Airport> airports;
    private List<String> cities;
    private String autocomplete;

    //today's flights
    private List<Flight> flights;

    //reservation
    private List<Integer> ticketsCode;
    private boolean showTickets = false;

    public LoginController() {

        //list of airports for flight search 
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery("Airport.findAll");
        airports = query.list();
        session.getTransaction().commit();
        session.close();

        cities = new ArrayList<String>();

        for (Airport aa : airports) {
            cities.add(aa.getCity() + " (" + aa.getIata() + ")");
            System.out.println(aa.getCity());
        }

        //list of today's flights Flight.findByDepartureDate
        session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        query = session.getNamedQuery("Flight.findByDepartureDate");
        query.setDate("departureDate", new Date());
        flights = query.list();
        session.getTransaction().commit();
        session.close();

    }

    public List<String> autocompleteMethod(String query) {
        List<String> results = new ArrayList<String>();
        for (String s : cities) {
            if (s.toLowerCase().contains(query.toLowerCase())) {
                results.add(s);
            }
        }

        return results;
    }

    public String toRegistration() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        session.getTransaction().commit();
        session.close();

        airlineNames = new ArrayList<String>(airlines.size() - 1);

        for (Airline a : airlines) {
            if (a.getName().equals("Admin sajta")) {
                continue;
            }
            airlineNames.add(a.getName());
            System.out.println(a.getName());
        }

        return "Registration";
    }

    public boolean isShowTickets() {
        return showTickets;
    }

    public void setShowTickets(boolean showTickets) {
        this.showTickets = showTickets;
    }

    public List<Integer> getTicketsCode() {
        return ticketsCode;
    }

    public void setTicketsCode(List<Integer> ticketsCode) {
        this.ticketsCode = ticketsCode;
    }

    public boolean isShow1() {
        return show1;
    }

    public void setShow1(boolean show1) {
        this.show1 = show1;
    }

    public boolean isShow2() {
        return show2;
    }

    public void setShow2(boolean show2) {
        this.show2 = show2;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Flight> getSearchedFlightsTo() {
        return searchedFlightsTo;
    }

    public void setSearchedFlightsTo(List<Flight> searchedFlightsTo) {
        this.searchedFlightsTo = searchedFlightsTo;
    }

    public List<Flight> getSearchedFlightsReturn() {
        return searchedFlightsReturn;
    }

    public void setSearchedFlightsReturn(List<Flight> searchedFlightsReturn) {
        this.searchedFlightsReturn = searchedFlightsReturn;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        cities = cities;
    }

    public String getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public boolean isOneway() {
        return oneway;
    }

    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }

    public String getType_new() {
        return type_new;
    }

    public void setType_new(String type_new) {
        this.type_new = type_new;
    }

    public String getName_new() {
        return name_new;
    }

    public void setName_new(String name_new) {
        this.name_new = name_new;
    }

    public char getGender_new() {
        return gender_new;
    }

    public void setGender_new(char gender_new) {
        this.gender_new = gender_new;
    }

    public Date getBirth_new() {
        return birth_new;
    }

    public void setBirth_new(Date birth_new) {
        this.birth_new = birth_new;
    }

    public String getAirline_new() {
        return airline_new;
    }

    public void setAirline_new(String airline_new) {
        this.airline_new = airline_new;
    }

    public String getUsername_new() {
        return username_new;
    }

    public void setUsername_new(String username_new) {
        this.username_new = username_new;
    }

    public String getPassword_new() {
        return password_new;
    }

    public void setPassword_new(String password_new) {
        this.password_new = password_new;
    }

    public List<Airline> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public List<String> getAirlineNames() {
        return airlineNames;
    }

    public void setAirlineNames(List<String> airlineNames) {
        this.airlineNames = airlineNames;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswoord() {
        return passwoord;
    }

    public void setPasswoord(String passwoord) {
        this.passwoord = passwoord;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String Home() {
        return "index";
    }

    public String LogIn() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpsession = (HttpSession) context.getExternalContext().getSession(true);

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.getNamedQuery("User.findByUsername");
        query.setString("username", username);
        List<User> users = query.list();

        session.getTransaction().commit();
        session.close();

        User user = null;
        if (users.size() == 1) {
            user = users.get(0);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Nepostojeće korisničko ime."));
            return "index";
        }

        if (!user.getPassword().equals(this.passwoord)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Neispravna lozinka."));
            return "index";
        }
        if (user.getPending() == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Čekanje na odobrenje od administratora"));
            return "index";
        }
        currentUser = user;
        httpsession.setAttribute("currentuser", user);

        if (user.getType().equals("pilot")) {
            return "pilot/home";
        }
        if (user.getType().equals("stjuardesa")) {
            return "fa/home";
        }
        if (user.getType().equals("admin")) {
            return "admin/home";
        }
        if (user.getType().equals("radnik")) {
            return "worker/home";
        }

        return "index";
    }

    public String changePassword() {

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.getNamedQuery("User.findByUsername");
        query.setString("username", username);
        List<User> users = query.list();
        session.getTransaction().commit();
        session.close();

        User user = null;
        if (users.size() == 1) {
            user = users.get(0);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Nepostojeće korisničko ime."));
            return "Password";
        }

        if (!user.getPassword().equals(this.passwoord)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Neispravna lozinka."));
            return "Password";
        }

        session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        user.setPassword(newpassword);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno promenjana šifra."));
        return "index";

    }

    public String register() {

        System.out.println(username_new);
        System.out.println(password_new);
        System.out.println(name_new);
        System.out.println(gender_new);
        System.out.println(birth_new);
        System.out.println(type_new);

        User user = new User();
        user.setUsername(username_new);
        user.setPassword(password_new);
        user.setName(name_new);
        user.setGender(gender_new);
        user.setBirth(birth_new);
        user.setType(type_new);
        user.setPending(1);

        for (Airline a : airlines) {
            if (a.getName().equals(this.airline_new)) {
                user.setAirline(a);
                break;
            }
        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery("User.findByUsername");
        query.setString("username", username_new);
        List<User> users = query.list();
        session.getTransaction().commit();
        session.close();

        if (users.size() == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Korisničko ime već postoji."));
            return "Registration";
        }

        session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Poslat je zahtev za registaciju."));

        return "index";
    }

    public void searchFlight() {
        show1 = true;
        if (oneway) {
            //one way flight
            if (direct) {
                //direct one way flight
                String start = "";
                String finish = "";
                for (Airport a : airports) {
                    if (this.from.toLowerCase().contains(a.getCity().toLowerCase())) {
                        start = a.getIata();
                    }
                    if (this.to.toLowerCase().contains(a.getCity().toLowerCase())) {
                        finish = a.getIata();
                    }
                }

                Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.airportFrom = :start AND f.airportTo = :finish AND f.departureDate=:d");
                query.setString("start", start);
                query.setString("finish", finish);
                query.setDate("d", date_from);

                searchedFlightsTo = query.list();
                session.getTransaction().commit();
                session.close();

                /*
                ListIterator<Flight> iter = searchedFlightsTo.listIterator();
                while (iter.hasNext()) {
                    if (iter.next().getDepartureDate().compareTo(date_from) != 0) {
                        iter.remove();
                    }
                }
                 */
            } else {
                //with layover flights
                String start = "";
                String finish = "";
                for (Airport a : airports) {
                    if (this.from.toLowerCase().contains(a.getCity().toLowerCase())) {
                        start = a.getIata();
                    }
                    if (this.to.toLowerCase().contains(a.getCity().toLowerCase())) {
                        finish = a.getIata();
                    }
                }

                //direct flights only
                Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.airportFrom = :start AND f.airportTo = :finish AND f.departureDate=:d");
                query.setString("start", start);
                query.setString("finish", finish);
                query.setDate("d", date_from);
                searchedFlightsTo = query.list();
                session.getTransaction().commit();
                session.close();

                //layover flights only
                session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                query = session.createQuery("select f1,f2 from Flight f1, Flight f2 "
                        + "where f1.airportFrom= :start AND f2.airportTo=:finish "
                        + "AND f1.airportTo = f2.airportFrom AND f1.departureDate=:d AND f1.departureDate=:d ");
                query.setString("start", start);
                query.setString("finish", finish);
                query.setDate("d", date_from);
                List<Object> objs = query.list();
                session.getTransaction().commit();
                session.close();

                for (Object obj : objs) {
                    Object[] o = (Object[]) obj;
                    Flight f1 = (Flight)o[0];
                    Flight f2 = (Flight)o[1];
                    System.out.println("Dupli letovi: " + f1.getId() + " - " + f2.getId());
                }

            }

        } else {
            //return flight also

        }

    }

    private List<Flight> pom;

    public List<Flight> getPom() {
        return pom;
    }

    public void setPom(List<Flight> pom) {
        this.pom = pom;
    }

    public String rezervisi(Flight f) {
        this.selectedFlight = f;
        pom = new ArrayList<Flight>(1);
        pom.add(f);
        return "Reservation";
    }

    public void buyTickets() {
        ticketsCode = new ArrayList<Integer>();
        for (int i = 0; i < this.tickets; i++) {
            ticketsCode.add(ticketId);
        }
        ticketId++;
        showTickets = true;
    }

}
