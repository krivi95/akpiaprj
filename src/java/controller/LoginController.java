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
import model.CurrentUser;
import model.Flight;
import model.Ticket;
import model.Tickets;
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
    private String name1;
    private String passport1;
    private String name2;
    private String passport2;
    private String name3;
    private String passport3;
    private String name4;
    private String passport4;
    private String name5;
    private String passport5;
    private String name6;
    private String passport6;
    private String name7;
    private String passport7;
    private String name8;
    private String passport8;
    private List<String> names;
    private List<String> passwports;

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
        }

        return "Registration?faces-redirect=true";
    }

    public static int getTicketId() {
        return ticketId;
    }

    public static void setTicketId(int ticketId) {
        LoginController.ticketId = ticketId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPassport1() {
        return passport1;
    }

    public void setPassport1(String passport1) {
        this.passport1 = passport1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getPassport2() {
        return passport2;
    }

    public void setPassport2(String passport2) {
        this.passport2 = passport2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getPassport3() {
        return passport3;
    }

    public void setPassport3(String passport3) {
        this.passport3 = passport3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public String getPassport4() {
        return passport4;
    }

    public void setPassport4(String passport4) {
        this.passport4 = passport4;
    }

    public String getName5() {
        return name5;
    }

    public void setName5(String name5) {
        this.name5 = name5;
    }

    public String getPassport5() {
        return passport5;
    }

    public void setPassport5(String passport5) {
        this.passport5 = passport5;
    }

    public String getName6() {
        return name6;
    }

    public void setName6(String name6) {
        this.name6 = name6;
    }

    public String getPassport6() {
        return passport6;
    }

    public void setPassport6(String passport6) {
        this.passport6 = passport6;
    }

    public String getName7() {
        return name7;
    }

    public void setName7(String name7) {
        this.name7 = name7;
    }

    public String getPassport7() {
        return passport7;
    }

    public void setPassport7(String passport7) {
        this.passport7 = passport7;
    }

    public String getName8() {
        return name8;
    }

    public void setName8(String name8) {
        this.name8 = name8;
    }

    public String getPassport8() {
        return passport8;
    }

    public void setPassport8(String passport8) {
        this.passport8 = passport8;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getPasswports() {
        return passwports;
    }

    public void setPasswports(List<String> passwports) {
        this.passwports = passwports;
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
        CurrentUser.user=user;

        if (user.getType().equals("pilot") && user.getPilotFirstLogin()==0) {
            return "pilot-home?faces-redirect=true";
        }
        if (user.getType().equals("pilot") && user.getPilotFirstLogin()==1) {
            return "pilot-first-login?faces-redirect=true";
        }
        
        if (user.getType().equals("stjuardesa")) {
            return "fa-home?faces-redirect=true";
        }
        if (user.getType().equals("admin")) {
            return "admin-home?faces-redirect=true";
        }
        if (user.getType().equals("radnik")) {
            return "worker-home?faces-redirect=true";
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
        show2 = false;
        showTickets = false;
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
                Flight f1 = (Flight) o[0];
                Flight f2 = (Flight) o[1];
                Flight f = new Flight();
                f.setDuration(f1.getDuration() + f2.getDuration());
                f.setCompany(f1.getCompany());
                f.setAirportFrom(f1.getAirportFrom());
                f.setAirportTo(f2.getAirportTo());
                f.setDepartureDate(f1.getDepartureDate());
                f.setDepartureTime(f1.getDepartureTime());
                f.setGateFrom(f1.getGateFrom());
                f.setGateTo(f2.getGateTo());
                f.setSeats(f1.getSeats());
                f.setPrice(f1.getPrice() + f2.getPrice());
                searchedFlightsTo.add(f);

                //TODO: slobona sedista
                //TODO: cena
                System.out.println(f1.getCompany());

            }

        }
        if (!oneway) {
            show2 = true;
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
                query.setString("start", finish);
                query.setString("finish", start);
                query.setDate("d", date_to);

                searchedFlightsReturn = query.list();
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
                query.setString("start", finish);
                query.setString("finish", start);
                query.setDate("d", date_to);
                searchedFlightsReturn = query.list();
                session.getTransaction().commit();
                session.close();

                //layover flights only
                session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                query = session.createQuery("select f1,f2 from Flight f1, Flight f2 "
                        + "where f1.airportFrom= :start AND f2.airportTo=:finish "
                        + "AND f1.airportTo = f2.airportFrom AND f1.departureDate=:d AND f1.departureDate=:d ");
                query.setString("start", finish);
                query.setString("finish", start);
                query.setDate("d", date_to);
                List<Object> objs = query.list();
                session.getTransaction().commit();
                session.close();

                for (Object obj : objs) {
                    Object[] o = (Object[]) obj;
                    Flight f1 = (Flight) o[0];
                    Flight f2 = (Flight) o[1];
                    Flight f = new Flight();
                    f.setDuration(f1.getDuration() + f2.getDuration());
                    f.setCompany(f1.getCompany());
                    f.setAirportFrom(f1.getAirportFrom());
                    f.setAirportTo(f2.getAirportTo());
                    f.setDepartureDate(f1.getDepartureDate());
                    f.setDepartureTime(f1.getDepartureTime());
                    f.setGateFrom(f1.getGateFrom());
                    f.setGateTo(f2.getGateTo());
                    f.setSeats(f1.getSeats());
                    f.setPrice(f1.getPrice() + f2.getPrice());
                    searchedFlightsReturn.add(f);

                    //TODO: slobona sedista
                    //TODO: cena
                    System.out.println(f1.getCompany());

                }

            }
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

    private List<Ticket> ticketReservationList;

    public void buyTickets() {

        Tickets t1 = null;
        Tickets t2 = null;
        Tickets t3 = null;
        Tickets t4 = null;
        Tickets t5 = null;
        Tickets t6 = null;
        Tickets t7 = null;
        Tickets t8 = null;

        int size = tickets;

        showTickets = true;
        names = new ArrayList<String>(size);
        passwports = new ArrayList<String>(size);
        names.add(name1);
        passwports.add(passport1);
        t1 = new Tickets();
        t1.setName(name1);
        t1.setPassportNo(passport1);
        if (name2 != null && !name2.isEmpty()) {
            names.add(name2);
            passwports.add(passport2);
            t2 = new Tickets();
            t2.setName(name2);
            t2.setPassportNo(passport2);
        }
        if (name3 != null && !name3.isEmpty()) {
            names.add(name3);
            passwports.add(passport3);
            t3 = new Tickets();
            t3.setName(name3);
            t3.setPassportNo(passport3);
        }
        if (name4 != null && !name4.isEmpty()) {
            names.add(name4);
            passwports.add(passport4);
            t4 = new Tickets();
            t4.setName(name4);
            t4.setPassportNo(passport4);
        }
        if (name5 != null && !name5.isEmpty()) {
            names.add(name5);
            passwports.add(passport5);
            t5 = new Tickets();
            t5.setName(name5);
            t5.setPassportNo(passport5);
        }
        if (name6 != null && !name6.isEmpty()) {
            names.add(name6);
            passwports.add(passport6);
            t6 = new Tickets();
            t6.setName(name6);
            t6.setPassportNo(passport6);
        }
        if (name7 != null && !name7.isEmpty()) {
            names.add(name7);
            passwports.add(passport7);
            t7 = new Tickets();
            t7.setName(name7);
            t7.setPassportNo(passport7);
        }
        if (name8 != null && !name8.isEmpty()) {
            names.add(name8);
            passwports.add(passport8);
            t8 = new Tickets();
            t8.setName(name8);
            t8.setPassportNo(passport8);
        }
        //saving tickets in database
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if (t1 != null) {
            session.save(t1);
        }
        if (t2 != null) {
            session.save(t2);
        }
        if (t3 != null) {
            session.save(t3);
        }
        if (t4 != null) {
            session.save(t4);
        }
        if (t5 != null) {
            session.save(t5);
        }
        if (t6 != null) {
            session.save(t6);
        }
        if (t7 != null) {
            session.save(t7);
        }
        if (t8 != null) {
            session.save(t8);
        }
        selectedFlight.setSeats(selectedFlight.getSeats()-tickets);
        //selectedFlight.setStatus(" ");
        session.saveOrUpdate(selectedFlight);
        session.getTransaction().commit();
        session.close();

        session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery("Tickets.findByPassportNo");
        query.setString("passportNo", passport1);
        List<Tickets> ttt = query.list();
        ticketId = ttt.get(0).getTicketNo()+10000000;
        session.getTransaction().commit();
        session.close();
        
        

        ticketsCode = new ArrayList<Integer>();
        for (int i = 0; i < this.tickets; i++) {
            ticketsCode.add(ticketId);
            ticketId++;
        }

        ticketReservationList = new ArrayList<Ticket>(size);
        for (int i = 0; i < size; i++) {
            Ticket t = new Ticket();
            t.name = names.get(i);
            t.passport = passwports.get(i);
            t.ticket = ticketsCode.get(i).toString();
            ticketReservationList.add(t);
        }

        t1 = null;
        t2 = null;
        t3 = null;
        t4 = null;
        t5 = null;
        t6 = null;
        t7 = null;
        t8 = null;

    }

    public List<Ticket> getTicketReservationList() {
        return ticketReservationList;
    }

    public void setTicketReservationList(List<Ticket> ticketReservationList) {
        this.ticketReservationList = ticketReservationList;
    }

    
    
}
