package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.CurrentUser;
import model.Flight;
import model.Licences;
import model.Model;
import model.User;
import org.hibernate.Session;



@SessionScoped
@ManagedBean
@Named(value = "pilotController")
public class PilotController {
    
    private User pilot;
    
    private List<Model> models;
    private List<String> modelNames;
    private String selected_model;
    private String licenceNo;
    
    private List<Airline> airlines;
    private List<String> airlineNames;
    private String airline_selected;
    
    private List<Flight> allFlights;
    private List<Flight> finishedFlights;
    private List<Flight> toDoFlights;    
    private int doneFlights;
    private int onGoingFights;

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public List<Flight> getFinishedFlights() {
        return finishedFlights;
    }

    public List<Flight> getToDoFlights() {
        return toDoFlights;
    }

    public int getDoneFlights() {
        return doneFlights;
    }

    public int getOnGoingFights() {
        return onGoingFights;
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
    
    

    public void setAllFlights(List<Flight> allFlights) {
        this.allFlights = allFlights;
    }

    public void setFinishedFlights(List<Flight> finishedFlights) {
        this.finishedFlights = finishedFlights;
    }

    public void setToDoFlights(List<Flight> toDoFlights) {
        this.toDoFlights = toDoFlights;
    }

    public void setDoneFlights(int doneFlights) {
        this.doneFlights = doneFlights;
    }

    public void setOnGoingFights(int onGoingFights) {
        this.onGoingFights = onGoingFights;
    }
    

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }
    

    public String getSelected_model() {
        return selected_model;
    }

    public void setSelected_model(String selected_model) {
        this.selected_model = selected_model;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<String> getModelNames() {
        return modelNames;
    }

    public void setModelNames(List<String> modelNames) {
        this.modelNames = modelNames;
    }
    

    public User getPilot() {
        return pilot;
    }

    public void setPilot(User pilot) {
        this.pilot = pilot;
    }
    
    public PilotController() {    
        
        pilot = CurrentUser.user;
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        
        //for adding licence
        org.hibernate.Query query = session.getNamedQuery("Model.findAll");
        models = query.list();
        modelNames = new ArrayList<String>(models.size());
        for(Model m: models){
            modelNames.add(m.getMName().getName() + " " + m.getName());
        }
        
        //changing airline
        query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        airlineNames = new ArrayList<String>(airlines.size());
        for (Airline a : airlines) {
            if (!a.getName().equals("Admin sajta")) {
                airlineNames.add(a.getName());
            }
        }
        
        //flights
        query = session.createQuery("SELECT f FROM Flight f WHERE f.pilot =:p1 OR f.coplot=:p2 ");
        query.setEntity("p1", pilot);
        query.setEntity("p2", pilot);
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
    
    public String addLicence(){     
        System.out.println("usli");
        Licences l = new Licences();
        l.setUser(pilot);
        for(Model m: models){
            if(selected_model.contains(m.getName())){
                l.setLicenceNo(m.getLicence() + licenceNo); System.out.println(l.getLicenceNo()); break;
            }
        }   
        pilot.setPilotFirstLogin(0);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();System.out.print("*");
        session.beginTransaction();System.out.print("*");
        session.save(l);System.out.print("*");
        session.update(pilot);System.out.print("*");
        session.getTransaction().commit();System.out.print("*");
        session.close();        System.out.print("*");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno je dodata licenca za izabrani avion"));
        return "pilot-home";
    }
    
    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }
    
    public void changeAirline() {

        System.out.println(airline_selected);

        for (Airline a : airlines) {
            if (airline_selected.equals(a.getName())) {
                pilot.setAirline(a);
            }
        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(pilot);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno ste promenili avio-kompaniju."));
    }

    
}
