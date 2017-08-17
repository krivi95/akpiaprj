package controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.Airport;
import model.Gate;
import model.Licences;
import model.Model;
import model.RadioTower;
import model.User;
import org.hibernate.Session;

@Named(value = "adminController")
@SessionScoped
@ManagedBean
public class AdminController {

    //init
    private List<User> unapprovedUsers = null;
    private List<Airport> airports = null;
    private List<Airline> airlines = null;
    private List<Licences> licences=null;
    private List<User> pilots=null;
    private List<String> pilotNames=null;
    private List<Model> models;
    private List<String> modelNames=null;

    //new airport data
    private String iata;
    private String airportName;
    private String airportCity;
    private String airportState;
    private int runways;
    private int terminals;
    private Gate gate;
    private List<Gate> gates;
    private double longitude;
    private double latitude;
    
    //new licence;
    private String name;
    private String licenceNo;
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }
    
    

    public List<String> getPilotNames() {
        return pilotNames;
    }

    public void setPilotNames(List<String> pilotNames) {
        this.pilotNames = pilotNames;
    }
    

    public List<User> getPilots() {
        return pilots;
    }

    public void setPilots(List<User> pilots) {
        this.pilots = pilots;
    }
    
    

    public List<Licences> getLicences() {
        return licences;
    }

    public void setLicences(List<Licences> licences) {
        this.licences = licences;
    }
    
    

    public List<Airline> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Airport getNewAirport() {
        return newAirport;
    }

    public void setNewAirport(Airport newAirport) {
        this.newAirport = newAirport;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    public String getAirportState() {
        return airportState;
    }

    public void setAirportState(String airportState) {
        this.airportState = airportState;
    }

    public int getRunways() {
        return runways;
    }

    public void setRunways(int runways) {
        this.runways = runways;
    }

    public int getTerminals() {
        return terminals;
    }

    public void setTerminals(int terminals) {
        this.terminals = terminals;
    }

    public List<User> getUnapprovedUsers() {
        return unapprovedUsers;
    }

    public void setUnapprovedUsers(List<User> unapprovedUsers) {
        this.unapprovedUsers = unapprovedUsers;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public AdminController() {

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.getNamedQuery("User.findByPending");
        query.setInteger("pending", 1);
        unapprovedUsers = query.list();

        query = session.getNamedQuery("Airport.findAll");
        airports = query.list();

        query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        airlines.remove(8);
        
        query = session.getNamedQuery("Licences.findAll");
        licences = query.list();
        
        query = session.getNamedQuery("User.findByType");
        query.setString("type", "pilot");
        pilots = query.list();   
        pilotNames = new ArrayList<String>(licences.size());
        for(User u : pilots){
            pilotNames.add(u.getName());
        }
        
        query = session.getNamedQuery("Model.findAll");
        models = query.list();   
        modelNames = new ArrayList<String>(models.size());
        for(Model m : models){
            modelNames.add(m.getMName().getName() + " " + m.getName());
        }
        
        
        
        

        session.getTransaction().commit();
        session.close();

    }

    public void approveUser(User u) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        u.setPending(0);
        session.update(u);
        org.hibernate.Query query = session.getNamedQuery("User.findByPending");
        query.setInteger("pending", 1);
        unapprovedUsers = query.list();
        session.getTransaction().commit();
        session.close();

    }

    public void deleteUser(User u) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(u);
        org.hibernate.Query query = session.getNamedQuery("User.findByPending");
        query.setInteger("pending", 1);
        unapprovedUsers = query.list();
        session.getTransaction().commit();
        session.close();

    }

    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }

    public String toAirportLook() {
        return "admin-airpor-look";
    }

    private Airport newAirport;

    public String addAirport() {
        if (iata.length() != 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "iata oznaka mora da ima 3 karaktera."));
            return null;

        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery("Airport.findByIata");
        query.setString("iata", iata.toUpperCase());
        List<Airport> l = query.list();
        session.getTransaction().commit();
        session.close();

        if (l.size() == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Aerodrom vec postoji."));
            return null;
        }

        newAirport = new Airport();
        newAirport.setIata(iata.toUpperCase());
        newAirport.setName(airportName);
        newAirport.setCity(airportCity);
        newAirport.setState(airportState);
        newAirport.setTerminal(terminals);
        newAirport.setRunway(runways);

        RadioTower rt = new RadioTower();
        rt.setName(airportCity);
        rt.setIata(newAirport);
        rt.setLongitude(longitude);
        rt.setLatitude(latitude);

        session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(newAirport);
        session.saveOrUpdate(rt);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno dadat aerodrom."));

        query = session.getNamedQuery("Airport.findAll");
        airports = query.list();

        gates = new ArrayList<Gate>();
        gate = new Gate();

        return null;

    }

    public String reinit() {
        gate.setIata(newAirport);
        gate.setId(iata + " " + gate.getTerminalNo() + " " + gate.getGate());
        gate = new Gate();
        return null;
    }

    public String addGates() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (Gate g : gates) {
            session.save(g);
        }

        session.getTransaction().commit();
        session.close();

        return null;
    }

    public String saveCompanies() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (Airline g : airlines) {
            session.update(g);
        }

        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno sačuvane izmene."));

        return null;
    }
    
    public String addLicence(){
        Licences l = new Licences();
        for(User u: pilots){
            if(u.getName().equals(name))l.setUser(u);
        }
        for(Model m: models){
            if(model.contains(m.getName()))l.setLicenceNo(m.getLicence() + licenceNo);
        }
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(l);
        org.hibernate.Query query = session.getNamedQuery("Licences.findAll");
        licences = query.list();
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno dodata licenca."));
        
        

        return null;
        
    }

}
