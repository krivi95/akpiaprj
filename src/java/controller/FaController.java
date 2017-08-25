package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.CurrentUser;
import model.Flight;
import model.User;
import org.hibernate.Session;

@Named(value = "faController")
@SessionScoped
@ManagedBean
public class FaController {

    private User fa;
    
    private List<Airline> airlines;
    private List<String> airlineNames;
    private String airline_selected;

    private List<Flight> allFlights;
    private List<Flight> finishedFlights;
    private List<Flight> toDoFlights;    
    private int doneFlights;
    private int onGoingFights;
    
    private Flight selectedFlight;

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }
    
    

    public int getDoneFlights() {
        return doneFlights;
    }

    public void setDoneFlights(int doneFlights) {
        this.doneFlights = doneFlights;
    }

    public int getOnGoingFights() {
        return onGoingFights;
    }

    public void setOnGoingFights(int onGoingFights) {
        this.onGoingFights = onGoingFights;
    }
    
    

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(List<Flight> allFlights) {
        this.allFlights = allFlights;
    }

    public List<Flight> getFinishedFlights() {
        return finishedFlights;
    }

    public void setFinishedFlights(List<Flight> finishedFlights) {
        this.finishedFlights = finishedFlights;
    }

    public List<Flight> getToDoFlights() {
        return toDoFlights;
    }

    public void setToDoFlights(List<Flight> toDoFlights) {
        this.toDoFlights = toDoFlights;
    }
    
    

    public User getFa() {
        return fa;
    }

    public void setFa(User fa) {
        this.fa = fa;
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

    public String getAirline_selected() {
        return airline_selected;
    }

    public void setAirline_selected(String airline_selected) {
        this.airline_selected = airline_selected;
    }

    public FaController() {
        
        //getting fa user
        fa = CurrentUser.user;
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        //for airline change
        org.hibernate.Query query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        airlineNames = new ArrayList<String>(airlines.size());
        for (Airline a : airlines) {
            if (!a.getName().equals("Admin sajta")) {
                airlineNames.add(a.getName());
            }
        }

        
        //flights
        query = session.createQuery("SELECT f FROM Flight f WHERE f.fa1 = :fa1 OR f.fa2=:fa2 OR f.fa3=:fa3 "
                    + "OR f.fa4=:fa4 OR f.fa5=:fa5");
        query.setEntity("fa1", fa);
        query.setEntity("fa2", fa);
        query.setEntity("fa3", fa);
        query.setEntity("fa4", fa);
        query.setEntity("fa5", fa);
        allFlights = query.list();        
        finishedFlights = new ArrayList<Flight>();
        toDoFlights = new ArrayList<Flight>();
        Date d = new Date();
        for(Flight f: allFlights){
            if(f.getDepartureDate().before(d))finishedFlights.add(f);
            else toDoFlights.add(f);
        }
        doneFlights = finishedFlights.size();
        onGoingFights = toDoFlights.size();
        
        session.getTransaction().commit();
        session.close();

    }

    public void changeAirline() {

        System.out.println(airline_selected);

        for (Airline a : airlines) {
            if (airline_selected.equals(a.getName())) {
                fa.setAirline(a);
            }
        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(fa);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno ste promenili avio-kompaniju."));
    }

    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }

}
