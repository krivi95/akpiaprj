package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.Airplane;
import model.Airport;
import model.ControlCheck;
import model.Flight;
import model.Gate;
import model.Licences;
import model.Model;
import model.RadioTower;
import model.User;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FlowEvent;

@Named(value = "adminController")
@SessionScoped
@ManagedBean
public class AdminController {

    //init
    private List<User> unapprovedUsers = null;
    private List<Airport> airports = null;
    private List<Airline> airlines = null;
    private List<Licences> licences = null;
    private List<User> pilots = null;
    private List<String> pilotNames = null;
    private List<Model> models = null;
    private List<String> modelNames = null;
    private List<Airplane> airplanes = null;
    private List<String> airpot_names = null;

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

    //new airplane
    private String airplane_name;
    private int airplane_seats;
    private String airplane_model;
    private String airplane_airline;
    private UploadedFile picture;

    //new flight
    private String flight_from;
    private String flight_to;
    private Date flight_date;
    private boolean charter = false;
    private int duration;
    private List<Gate> gate_from;
    private List<Gate> gate_to;
    private List<String> gateFromNames;
    private List<String> gateToNames;
    private String gate_from_selected;
    private String gate_to_selected;
    private double price;
    private Airplane airplane_selected;
    private String airplane_name_selected;
    private List<Airplane> airplanesRegular;
    private List<Airplane> airplanesCharter;
    private List<String> airplanesRegularNames;
    private List<String> airplanesCharterNames;
    private List<String> pilotsFromSelectedAirline;
    private List<User> fa = null;
    private List<String> faNames = null;
    private String pilot1 = null;
    private String pilot2 = null;
    private String fa1 = null;
    private String fa2 = null;
    private String fa3 = null;
    private String fa4 = null;
    private String fa5 = null;
    private int numOfRadioTowers = 3;
    private List<RadioTower> radioTowers = null;
    private RadioTower radioTower;
    private List<String> radioTowerNames = null;
    private String radioTowerChosen;
    private List<RadioTower> allRadioTowers = null;
    private boolean showFinish = false;

    public boolean isShowFinish() {
        return showFinish;
    }

    public void setShowFinish(boolean showFinish) {
        this.showFinish = showFinish;
    }

    public String getRadioTowerChosen() {
        return radioTowerChosen;
    }

    public void setRadioTowerChosen(String radioTowerChosen) {
        this.radioTowerChosen = radioTowerChosen;
    }

    public int getNumOfRadioTowers() {
        return numOfRadioTowers;
    }

    public void setNumOfRadioTowers(int numOfRadioTowers) {
        this.numOfRadioTowers = numOfRadioTowers;
    }

    public List<RadioTower> getRadioTowers() {
        return radioTowers;
    }

    public void setRadioTowers(List<RadioTower> radioTowers) {
        this.radioTowers = radioTowers;
    }

    public RadioTower getRadioTower() {
        return radioTower;
    }

    public void setRadioTower(RadioTower radioTower) {
        this.radioTower = radioTower;
    }

    public List<String> getRadioTowerNames() {
        return radioTowerNames;
    }

    public void setRadioTowerNames(List<String> radioTowerNames) {
        this.radioTowerNames = radioTowerNames;
    }

    public List<String> getPilotsFromSelectedAirline() {
        return pilotsFromSelectedAirline;
    }

    public void setPilotsFromSelectedAirline(List<String> pilotsFromSelectedAirline) {
        this.pilotsFromSelectedAirline = pilotsFromSelectedAirline;
    }

    public List<User> getFa() {
        return fa;
    }

    public void setFa(List<User> fa) {
        this.fa = fa;
    }

    public List<String> getFaNames() {
        return faNames;
    }

    public void setFaNames(List<String> faNames) {
        this.faNames = faNames;
    }

    public String getPilot1() {
        return pilot1;
    }

    public void setPilot1(String pilot1) {
        this.pilot1 = pilot1;
    }

    public String getPilot2() {
        return pilot2;
    }

    public void setPilot2(String pilot2) {
        this.pilot2 = pilot2;
    }

    public String getFa1() {
        return fa1;
    }

    public void setFa1(String fa1) {
        this.fa1 = fa1;
    }

    public String getFa2() {
        return fa2;
    }

    public void setFa2(String fa2) {
        this.fa2 = fa2;
    }

    public String getFa3() {
        return fa3;
    }

    public void setFa3(String fa3) {
        this.fa3 = fa3;
    }

    public String getFa4() {
        return fa4;
    }

    public void setFa4(String fa4) {
        this.fa4 = fa4;
    }

    public String getFa5() {
        return fa5;
    }

    public void setFa5(String fa5) {
        this.fa5 = fa5;
    }

    public String getAirplane_name_selected() {
        return airplane_name_selected;
    }

    public void setAirplane_name_selected(String airplane_name_selected) {
        this.airplane_name_selected = airplane_name_selected;
    }

    public List<String> getAirplanesRegularNames() {
        return airplanesRegularNames;
    }

    public void setAirplanesRegularNames(List<String> airplanesRegularNames) {
        this.airplanesRegularNames = airplanesRegularNames;
    }

    public List<String> getAirplanesCharterNames() {
        return airplanesCharterNames;
    }

    public void setAirplanesCharterNames(List<String> airplanesCharterNames) {
        this.airplanesCharterNames = airplanesCharterNames;
    }

    public Airport getFlight_selected_Airport_From() {
        return flight_selected_Airport_From;
    }

    public void setFlight_selected_Airport_From(Airport flight_selected_Airport_From) {
        this.flight_selected_Airport_From = flight_selected_Airport_From;
    }

    public Airport getFlight_selected_Airport_To() {
        return flight_selected_Airport_To;
    }

    public void setFlight_selected_Airport_To(Airport flight_selected_Airport_To) {
        this.flight_selected_Airport_To = flight_selected_Airport_To;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Airplane getAirplane_selected() {
        return airplane_selected;
    }

    public void setAirplane_selected(Airplane airplane_selected) {
        this.airplane_selected = airplane_selected;
    }

    public List<Airplane> getAirplanesRegular() {
        return airplanesRegular;
    }

    public void setAirplanesRegular(List<Airplane> airplanesRegular) {
        this.airplanesRegular = airplanesRegular;
    }

    public List<Airplane> getAirplanesCharter() {
        return airplanesCharter;
    }

    public void setAirplanesCharter(List<Airplane> airplanesCharter) {
        this.airplanesCharter = airplanesCharter;
    }

    public List<Gate> getGate_from() {
        return gate_from;
    }

    public void setGate_from(List<Gate> gate_from) {
        this.gate_from = gate_from;
    }

    public List<Gate> getGate_to() {
        return gate_to;
    }

    public void setGate_to(List<Gate> gate_to) {
        this.gate_to = gate_to;
    }

    public List<String> getGateFromNames() {
        return gateFromNames;
    }

    public void setGateFromNames(List<String> gateFromNames) {
        this.gateFromNames = gateFromNames;
    }

    public List<String> getGateToNames() {
        return gateToNames;
    }

    public void setGateToNames(List<String> gateToNames) {
        this.gateToNames = gateToNames;
    }

    public String getGate_from_selected() {
        return gate_from_selected;
    }

    public void setGate_from_selected(String gate_from_selected) {
        this.gate_from_selected = gate_from_selected;
    }

    public String getGate_to_selected() {
        return gate_to_selected;
    }

    public void setGate_to_selected(String gate_to_selected) {
        this.gate_to_selected = gate_to_selected;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFlight_from() {
        return flight_from;
    }

    public void setFlight_from(String flight_from) {
        this.flight_from = flight_from;
    }

    public String getFlight_to() {
        return flight_to;
    }

    public void setFlight_to(String flight_to) {
        this.flight_to = flight_to;
    }

    public Date getFlight_date() {
        return flight_date;
    }

    public void setFlight_date(Date flight_date) {
        this.flight_date = flight_date;
    }

    public boolean isCharter() {
        return charter;
    }

    public void setCharter(boolean charter) {
        this.charter = charter;
    }

    public Airplane getA() {
        return a;
    }

    public void setA(Airplane a) {
        this.a = a;
    }

    public List<String> getAirpot_names() {
        return airpot_names;
    }

    public void setAirpot_names(List<String> airpot_names) {
        this.airpot_names = airpot_names;
    }

    public UploadedFile getPicture() {
        return picture;
    }

    public void setPicture(UploadedFile picture) {
        this.picture = picture;
    }

    public String getAirplane_name() {
        return airplane_name;
    }

    public void setAirplane_name(String airplane_name) {
        this.airplane_name = airplane_name;
    }

    public int getAirplane_seats() {
        return airplane_seats;
    }

    public void setAirplane_seats(int airplane_seats) {
        this.airplane_seats = airplane_seats;
    }

    public String getAirplane_model() {
        return airplane_model;
    }

    public void setAirplane_model(String airplane_model) {
        this.airplane_model = airplane_model;
    }

    public String getAirplane_airline() {
        return airplane_airline;
    }

    public void setAirplane_airline(String airplane_airline) {
        this.airplane_airline = airplane_airline;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

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
        airpot_names = new ArrayList<String>();
        for (Airport a : airports) {
            airpot_names.add(a.getCity() + " (" + a.getIata() + ")");
        }

        query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        airlines.remove(8);

        query = session.getNamedQuery("Licences.findAll");
        licences = query.list();

        query = session.getNamedQuery("User.findByType");
        query.setString("type", "pilot");
        pilots = query.list();
        pilotNames = new ArrayList<String>(licences.size());
        for (User u : pilots) {
            pilotNames.add(u.getName());
        }

        query = session.getNamedQuery("Model.findAll");
        models = query.list();
        modelNames = new ArrayList<String>(models.size());
        for (Model m : models) {
            modelNames.add(m.getMName().getName() + " " + m.getName());
        }

        query = session.getNamedQuery("Airplane.findAll");
        airplanes = query.list();

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
        return "index?faces-redirect=true";
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
        gate.setId(iata.toUpperCase() + " T" + gate.getTerminalNo() + " " + gate.getGate());
        gate = new Gate();
        return null;
    }

    public String reinitTower() {
        for (RadioTower r : allRadioTowers) {
            if (radioTowerChosen.equals(r.getName())) {
                radioTower.setIata(r.getIata());
                radioTower.setLatitude(r.getLatitude());
                radioTower.setLongitude(r.getLongitude());
                radioTower.setName(r.getName());
                break;
            }
        }
        radioTower = new RadioTower();
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

    public String addLicence() {
        Licences l = new Licences();
        for (User u : pilots) {
            if (u.getName().equals(name)) {
                l.setUser(u);
            }
        }
        for (Model m : models) {
            if (model.contains(m.getName())) {
                l.setLicenceNo(m.getLicence() + licenceNo);
            }
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

    public void uploadFile() throws IOException {
        if (picture != null) {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String filename = FilenameUtils.getName(picture.getFileName());
            System.out.println(filename);
            if (filename.endsWith("jpg") || filename.endsWith("png") || filename.endsWith("jpeg")) {
                String end = "";
                if (filename.endsWith("jpg")) {
                    end = "jpg";
                }
                if (filename.endsWith("png")) {
                    end = "png";
                }
                if (filename.endsWith("jpeg")) {
                    end = "jpeg";
                }
                filename = a.getId() + "";
                InputStream input = picture.getInputstream();
                //OutputStream output = new FileOutputStream(new File("C:\\Users\\Korisnik\\Documents\\NetBeansProjects\\akpiaprj\\web\\resources", filename + "." + end));
                File destFile = new File("C:\\Users\\Korisnik\\Documents\\NetBeansProjects\\akpiaprj\\web\\resources\\" + filename + "." + end);
                FileUtils.copyInputStreamToFile(input, destFile);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspresno dodata slika"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Samo jpeg, jpg i png fajlovi."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Dogodila se greska."));
        }
    }

    private Airplane a;

    public String addAirplane() {

        a = new Airplane();
        a.setMaxSeats(airplane_seats);
        a.setName(airplane_name);
        for (Model m : models) {
            if (airplane_model.contains(m.getName())) {
                a.setLicence(m);
            }
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(a);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno dodat novi avion."));

        airplanes.add(a);

        return null;
    }

    public String onFlowProcess(FlowEvent event) {
        loadFromGates();
        loadtoGates();
        checkForRunwayOverloadFrom();
        checkForRunwayOverloadTo();
        sortAirplanes();
        setTheCrew();
        showFinish = false;
        return event.getNewStep();
    }

    public boolean checkForRunwayOverloadFrom() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.airportFrom =:airportFrom AND f.departureDate = :departureDate");
        query.setEntity("airportFrom", flight_selected_Airport_From);
        query.setDate("departureDate", flight_date);
        List<Flight> f1 = query.list();
        session.getTransaction().commit();
        session.close();

        int num = 0;
        for (Flight f : f1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(f.getDepartureTime());
            int hour1 = cal.get(Calendar.HOUR);
            int min1 = cal.get(Calendar.MINUTE);
            cal.setTime(flight_date);
            int hour2 = cal.get(Calendar.HOUR);
            int min2 = cal.get(Calendar.MINUTE);
            if (min1 == min2 && hour1 == hour2) {
                num++;
            }
        }
        if (num == flight_selected_Airport_From.getRunway()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Izabrani polazni aerodrom nema dovoljno pisti za izabrano vreme."));
            return true;
        }
        return false;
    }

    public boolean checkForRunwayOverloadTo() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.airportTo =:airportTo AND f.departureDate = :departureDate");
        query.setEntity("airportTo", flight_selected_Airport_To);
        query.setDate("departureDate", flight_date);
        List<Flight> f1 = query.list();
        session.getTransaction().commit();
        session.close();

        int num = 0;
        for (Flight f : f1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(f.getPlannedTime());
            int hour1 = cal.get(Calendar.HOUR);
            int min1 = cal.get(Calendar.MINUTE);
            cal.setTime(flight_date);
            int hour2 = cal.get(Calendar.HOUR) + duration / 60;
            if (hour2 > 12) {
                hour2 -= 12;
            }
            int min2 = cal.get(Calendar.MINUTE) + duration % 60;
            if (min2 > 60) {
                min2 -= 60;
            }
            if (min1 == min2 && hour1 == hour2) {
                num++;
            }
        }
        if (num == flight_selected_Airport_To.getRunway()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Izabrani dolazni aerodrom nema dovoljno pisti za izabrano vreme."));
            return true;
        }
        return false;
    }

    private Airport flight_selected_Airport_From;
    private Airport flight_selected_Airport_To;

    public void loadFromGates() {
        gate_from = null;
        gateFromNames = null;
        String iata = null;
        for (Airport a : this.airports) {
            if (flight_from.contains(a.getIata())) {
                iata = a.getIata();
                flight_selected_Airport_From = a;
            }
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT g FROM Gate g WHERE g.id like '%" + iata + "%'");
        //query.setString("iata", iata);
        gate_from = query.list();
        gateFromNames = new ArrayList<String>();
        for (Gate g : gate_from) {
            gateFromNames.add(g.getId());
        }
        session.getTransaction().commit();
        session.close();
    }

    public void loadtoGates() {
        gate_to = null;
        gateToNames = null;
        String iata = null;
        for (Airport a : this.airports) {
            if (flight_to.contains(a.getIata())) {
                iata = a.getIata();
                flight_selected_Airport_To = a;
            }
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT g FROM Gate g WHERE g.id like '%" + iata + "%'");
        //query.setString("iata", iata);
        gate_to = query.list();
        gateToNames = new ArrayList<String>();
        for (Gate g : gate_to) {
            gateToNames.add(g.getId());
        }
        session.getTransaction().commit();
        session.close();
    }

    public void sortAirplanes() {
        if (!charter) {
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            org.hibernate.Query query = session.getNamedQuery("Airplane.findAll");
            airplanes = query.list();
            session.getTransaction().commit();
            session.close();

            airplanesRegular = new ArrayList<Airplane>();
            airplanesRegularNames = new ArrayList<String>();

            for (Airplane a : airplanes) {
                if (a.getAirlineRenting() == null && a.getRented() == 0 && a.getAirline() != null) {
                    airplanesRegular.add(a);
                    airplanesRegularNames.add(a.getName() + " (" + a.getLicence().getMName().getName() + " " + a.getLicence().getName() + ") - " + a.getAirline().getName());
                }
            }
        } else {
            airplanesCharter = new ArrayList<Airplane>();
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            org.hibernate.Query query = session.getNamedQuery("Airplane.findByRented");
            query.setInteger("rented", 1);
            airplanesCharter = query.list();
            System.out.println(airplanesCharter.size());
            session.getTransaction().commit();
            session.close();
            airplanesCharterNames = new ArrayList<String>();
            for (Airplane a : airplanesCharter) {
                airplanesCharterNames.add(a.getName() + " (" + a.getLicence().getMName().getName() + " " + a.getLicence().getName() + ")" + a.getAirlineRenting().getName());
            }
        }
    }

    public void setTheCrew() {
        if (airplane_name_selected != null && !airplane_name_selected.isEmpty()) {
            //adding the towers
            //RadioTower.findAll
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            org.hibernate.Query query = session.getNamedQuery("RadioTower.findAll");
            allRadioTowers = query.list();
            session.getTransaction().commit();
            session.close();

            radioTower = new RadioTower();
            radioTowers = new ArrayList<RadioTower>();
            radioTowerNames = new ArrayList<String>();
            for (RadioTower r : allRadioTowers) {
                if (r.getName().equals(flight_selected_Airport_From.getCity())) {
                    radioTowers.add(r);
                } else if (r.getName().equals(flight_selected_Airport_To.getCity())) {
                    radioTowers.add(r);
                } else {

                    radioTowerNames.add(r.getName());
                }
            }

            if (!charter) {                
                session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                for (Airplane a : airplanesRegular) {
                    if (airplane_name_selected.contains(a.getName())) {
                        airplane_selected = a;
                        break;
                    }
                }
                pilotsFromSelectedAirline = new ArrayList<String>();
                for (User u : pilots) {
                    if (u.getAirline().getId() == airplane_selected.getAirline().getId()) {
                        boolean ok = false;
                        query = session.createQuery("SELECT l from Licences l where l.user=:pilot");
                        query.setEntity("pilot", u);
                        List<Licences> licences = query.list();
                        for(Licences l: licences)if(l.getLicenceNo().contains(airplane_selected.getLicence().getLicence()))ok=true;
                        if (ok) {
                            pilotsFromSelectedAirline.add(u.getName());
                        }
                    }
                }
                Airline aa = airplane_selected.getAirline();
                query = session.createQuery("SELECT u FROM User u WHERE u.type = 'stjuardesa' AND u.airline = :airline");
                query.setEntity("airline", aa);
                fa = query.list();
                session.getTransaction().commit();
                session.close();
                faNames = new ArrayList<String>();
                faNames.add("");
                for (User u : fa) {
                    faNames.add(u.getName());
                }
            } else {
                //charter flight                
                session = hibernate.HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                for (Airplane a : airplanesCharter) {
                    if (airplane_name_selected.contains(a.getName())) {
                        airplane_selected = a;
                        break;
                    }
                }
                pilotsFromSelectedAirline = new ArrayList<String>();
                for (User u : pilots) {
                    if (u.getAirline().getId() == airplane_selected.getAirlineRenting().getId()) {
                        boolean ok = false;
                        query = session.createQuery("SELECT l from Licences l where l.user=:pilot");
                        query.setEntity("pilot", u);
                        List<Licences> licences = query.list();
                        for(Licences l: licences)if(l.getLicenceNo().contains(airplane_selected.getLicence().getLicence()))ok=true;
                        if (ok) {
                            pilotsFromSelectedAirline.add(u.getName());
                        }
                    }
                }
                Airline aa = airplane_selected.getAirlineRenting();
                query = session.createQuery("SELECT u FROM User u WHERE u.type = 'stjuardesa' AND u.airline = :airline");
                query.setEntity("airline", aa);
                fa = query.list();
                session.getTransaction().commit();
                session.close();
                faNames = new ArrayList<String>();
                faNames.add("");
                for (User u : fa) {
                    faNames.add(u.getName());
                }
            }

        }
    }

    public String addFlight() {
        //checkings
        if (checkForRunwayOverloadFrom()) {
            return "admin-flight-add";
        }
        if (checkForRunwayOverloadTo()) {
            return "admin-flight-add";
        }
        if (flight_selected_Airport_From == flight_selected_Airport_To) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Isti dolazni i odlazni aerodrom."));
            return "admin-flight-add";
        }
        if (pilot1.equals(pilot2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Dva puta izabran isti pilot."));
            return "admin-flight-add";
        }
        if (fa1.equals(fa2) || fa1.equals(fa3) || fa1.equals(fa4) || fa1.equals(fa5) || fa2.equals(fa3) || fa2.equals(fa4) || fa2.equals(fa5) || fa3.equals(fa4) || fa3.equals(fa5) || (fa4 != null && fa4.equals(fa5))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška.", "Više puta izabrana jedna stjuardesa."));
            return "admin-flight-add";
        }

        Flight f = new Flight();
        //setting airports
        f.setAirportFrom(flight_selected_Airport_From);
        f.setAirportTo(flight_selected_Airport_To);
        f.setAirplaneId(airplane_selected);
        //setting gates
        for (Gate g : gate_from) {
            if (g.getId().equals(gate_from_selected)) {
                f.setGateFrom(g);
            }
        }
        for (Gate g : gate_to) {
            if (g.getId().equals(gate_to_selected)) {
                f.setGateTo(g);
            }
        }
        //setting airline
        if (airplane_selected.getRented() == 0) {
            f.setCharter(0);
            f.setCompany(airplane_selected.getAirline());
        } else {
            f.setCharter(1);
            f.setCompany(airplane_selected.getAirlineRenting());
        }

        //seting departure time
        f.setDuration(duration);
        f.setDepartureDate(flight_date);
        f.setDepartureTime(flight_date);
        //setting planned time
        Calendar cal = Calendar.getInstance();
        cal.setTime(flight_date);
        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        int min1 = cal.get(Calendar.MINUTE);
        int totalMin = hour1 * 60 + min1 + duration;
        int hour2 = totalMin / 60;
        int min2 = totalMin % 60;
        if (hour2 > 24) {
            hour2 -= hour2 - 24;
        }
        cal.set(Calendar.HOUR_OF_DAY, hour2);
        cal.set(Calendar.MINUTE, min2);
        f.setPlannedTime(cal.getTime());
        f.setExpectedTime(cal.getTime());

        f.setPrice(price);
        f.setSeats(airplane_selected.getMaxSeats());
        f.setStatus("waiting");

        //seting crew
        for (User u : pilots) {
            if (u.getName().equals(pilot1)) {
                f.setPilot(u);
            }
            if (u.getName().equals(pilot2)) {
                f.setCoplot(u);
            }
        }
        for (User u : fa) {
            if (fa1.equals(u.getName())) {
                f.setFa1(u);
            }
            if (fa2.equals(u.getName())) {
                f.setFa2(u);
            }
            if (fa3.equals(u.getName())) {
                f.setFa3(u);
            }
            if (fa4 != null && !fa4.isEmpty() && fa4.equals(u.getName())) {
                f.setFa4(u);
            }
            if (fa5 != null && !fa5.isEmpty() && fa5.equals(u.getName())) {
                f.setFa5(u);
            }
        }

        //reorganizing raio towers(first two are from and to)
        RadioTower r1 = null;
        RadioTower r2 = null;
        int i = 0;
        int pos = 0;
        for (RadioTower r : radioTowers) {
            if (r.getName().equals(f.getAirportFrom().getCity())) {
                pos = i;
            }
            i++;
        }
        r1 = radioTowers.remove(pos);
        i = 0;
        for (RadioTower r : radioTowers) {
            if (r.getName().equals(f.getAirportTo().getCity())) {
                pos = i;
            }
            i++;
        }
        r2 = radioTowers.remove(pos);
        if (r1.getName().equals(f.getAirportFrom().getCity())) {
            radioTowers.add(0, r1);
            radioTowers.add(r2);
        } else {
            radioTowers.add(0, r2);
            radioTowers.add(r1);
        }

        //ControlCheck
        List<ControlCheck> cc = new ArrayList<ControlCheck>(radioTowers.size());
        for (RadioTower r : radioTowers) {
            ControlCheck c = new ControlCheck();
            c.setFlightId(f);
            c.setRadioTower(r);
            c.setStatus("waiting");
            cc.add(c);
        }

        Flight f1 = null;
        Flight f2 = null;
        Flight f3 = null;
        Flight f4 = null;
        //weakly flight
        if (!charter) {
            cal = Calendar.getInstance();
            cal.setTime(flight_date);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            if (month == 0 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
                if ((day + 7) < 31) {
                    f1 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 7);
                    f1.setDepartureDate(cal.getTime());

                }
                if ((day + 14) < 31) {
                    f2 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 14);
                    f2.setDepartureDate(cal.getTime());

                }
                if ((day + 21) < 31) {
                    f3 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 21);
                    f3.setDepartureDate(cal.getTime());
                }
                if ((day + 28) < 31) {
                    f4 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 28);
                    f4.setDepartureDate(cal.getTime());
                }
            } else {
                if ((day + 7) < 30) {
                    f1 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 7);
                    f1.setDepartureDate(cal.getTime());

                }
                if ((day + 14) < 30) {
                    f2 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 14);
                    f2.setDepartureDate(cal.getTime());

                }
                if ((day + 21) < 30) {
                    f3 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 21);
                    f3.setDepartureDate(cal.getTime());
                }
                if ((day + 28) < 30) {
                    f4 = new Flight(f);
                    cal.set(Calendar.DAY_OF_MONTH, day + 28);
                    f4.setDepartureDate(cal.getTime());
                }

            }
        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(f);
        for (ControlCheck c : cc) {
            session.save(c);
        }
        if (!charter) {
            if (f1 != null) {
                session.save(f1);
                for (ControlCheck c : cc) {
                    ControlCheck c1 = new ControlCheck();
                    c1.setFlightId(f1);
                    c1.setRadioTower(c.getRadioTower());
                    c1.setStatus("waiting");
                    session.save(c1);
                }
            }
            if (f2 != null) {
                session.save(f2);
                for (ControlCheck c : cc) {
                    ControlCheck c1 = new ControlCheck();
                    c1.setFlightId(f2);
                    c1.setRadioTower(c.getRadioTower());
                    c1.setStatus("waiting");
                    session.save(c1);
                }
            }
            if (f3 != null) {
                session.save(f3);
                for (ControlCheck c : cc) {
                    ControlCheck c1 = new ControlCheck();
                    c1.setFlightId(f3);
                    c1.setRadioTower(c.getRadioTower());
                    c1.setStatus("waiting");
                    session.save(c1);
                }
            }
            if (f4 != null) {
                session.save(f4);
                for (ControlCheck c : cc) {
                    ControlCheck c1 = new ControlCheck();
                    c1.setFlightId(f4);
                    c1.setRadioTower(c.getRadioTower());
                    c1.setStatus("waiting");
                    session.save(c1);
                }
            }
        }
        session.getTransaction().commit();
        session.close();

        /*
         session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for(ControlCheck c:cc){
            session.save(c);
        }
        session.getTransaction().commit();
        session.close();
         */
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno dodat novi let."));
        return "admin-flight-add";
    }

}
