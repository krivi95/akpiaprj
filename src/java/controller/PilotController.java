package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.Airport;
import model.ControlCheck;
import model.CurrentUser;
import model.Flight;
import model.Gate;
import model.Licences;
import model.Model;
import model.RadioTower;
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
    private Flight currentFlight = null;
    private boolean showCurrent = false;

    private Flight flight_selected;
    private List<Gate> gateFrom;
    private List<Gate> gateTo;
    private List<String> gateFromNames;
    private List<String> gateToNames;
    private String gateFrom_selected;
    private String gateTo_selected;

    private List<ControlCheck> ccList;
    private List<String> radioTowers;
    private String tower_selected;
    private double length;
    private double speed;
    private String status;
    private List<Airport> airport;
    private List<String> airportNames;
    private String emergencyLanding;
    private boolean emergency;

    public List<String> getRadioTowers() {
        return radioTowers;
    }

    public void setRadioTowers(List<String> radioTowers) {
        this.radioTowers = radioTowers;
    }

    public String getTower_selected() {
        return tower_selected;
    }

    public void setTower_selected(String tower_selected) {
        this.tower_selected = tower_selected;
    }

    public List<ControlCheck> getCcList() {
        return ccList;
    }

    public void setCcList(List<ControlCheck> ccList) {
        this.ccList = ccList;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Airport> getAirport() {
        return airport;
    }

    public void setAirport(List<Airport> airport) {
        this.airport = airport;
    }

    public List<String> getAirportNames() {
        return airportNames;
    }

    public void setAirportNames(List<String> airportNames) {
        this.airportNames = airportNames;
    }

    public String getEmergencyLanding() {
        return emergencyLanding;
    }

    public void setEmergencyLanding(String emergencyLanding) {
        this.emergencyLanding = emergencyLanding;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public List<Gate> getGateFrom() {
        return gateFrom;
    }

    public void setGateFrom(List<Gate> gateFrom) {
        this.gateFrom = gateFrom;
    }

    public List<Gate> getGateTo() {
        return gateTo;
    }

    public void setGateTo(List<Gate> gateTo) {
        this.gateTo = gateTo;
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

    public String getGateFrom_selected() {
        return gateFrom_selected;
    }

    public void setGateFrom_selected(String gateFrom_selected) {
        this.gateFrom_selected = gateFrom_selected;
    }

    public String getGateTo_selected() {
        return gateTo_selected;
    }

    public void setGateTo_selected(String gateTo_selected) {
        this.gateTo_selected = gateTo_selected;
    }

    public Flight getFlight_selected() {
        return flight_selected;
    }

    public void setFlight_selected(Flight flight_selected) {
        this.flight_selected = flight_selected;
    }

    public boolean isShowCurrent() {
        return showCurrent;
    }

    public void setShowCurrent(boolean showCurrent) {
        this.showCurrent = showCurrent;
    }

    public Flight getCurrentFlight() {
        return currentFlight;
    }

    public void setCurrentFlight(Flight currentFlight) {
        this.currentFlight = currentFlight;
    }

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
        for (Model m : models) {
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        int min1 = cal.get(Calendar.MINUTE);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        int total1 = hour1 * 60 + min1;
        for (Flight f : allFlights) {
            //check if it is current flight or something else
            cal.setTime(f.getDepartureTime());
            int hour2 = cal.get(Calendar.HOUR_OF_DAY);
            int min2 = cal.get(Calendar.MINUTE);
            cal.setTime(f.getDepartureDate());
            int month2 = cal.get(Calendar.MONTH);
            int day2 = cal.get(Calendar.DAY_OF_MONTH);
            int total2 = hour2 * 60 + min2;
            if (month2 == month1 && day1 == day2 && (total1 > total2 || (total2 - total1 < 30 && total2 - total1 >= 0))
                    && !f.getStatus().equals("sleteo") && !f.getStatus().equals("prinudno sleteo") && !f.getStatus().equals("otkazan")) {
                showCurrent = true;
                currentFlight = f;
            } else if (month2 == month1 && day1 == day2) {
                toDoFlights.add(f);
            } else if (f.getDepartureDate().before(d)) {
                finishedFlights.add(f);
                if (!f.getStatus().equals("sleteo")) {
                    f.setStatus("sleteo");
                }
                if (f.getArrivalTime() == null) {
                    f.setArrivalTime(f.getPlannedTime());
                }
                session.update(f);

            } else {
                toDoFlights.add(f);
            }
        }
        doneFlights = finishedFlights.size();
        onGoingFights = toDoFlights.size();

        session.getTransaction().commit();
        session.close();
    }

    public String addLicence() {
        Licences l = new Licences();
        l.setUser(pilot);
        for (Model m : models) {
            if (selected_model.contains(m.getName())) {
                l.setLicenceNo(m.getLicence() + licenceNo);
                break;
            }
        }
        pilot.setPilotFirstLogin(0);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(l);
        session.update(pilot);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno je dodata licenca za izabrani avion"));
        return "pilot-home";
    }

    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }

    public void changeAirline() {

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

    public void seeFlight(Flight f) {
        flight_selected = f;

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT g FROM Gate g WHERE g.id like '%" + f.getAirportFrom().getIata() + "%'");
        gateFrom = query.list();
        query = session.createQuery("SELECT g FROM Gate g WHERE g.id like '%" + f.getAirportTo().getIata() + "%'");
        gateTo = query.list();
        session.getTransaction().commit();
        session.close();
        gateFromNames = new ArrayList<String>(gateFrom.size());
        gateToNames = new ArrayList<String>(gateTo.size());
        for (Gate g : gateFrom) {
            gateFromNames.add(g.getId());
        }
        for (Gate g : gateTo) {
            gateToNames.add(g.getId());
        }

    }

    public String changeGate() {
        for (Gate g : gateFrom) {
            if (g.getId().equals(gateFrom_selected)) {
                flight_selected.setGateFrom(g);
            }
        }
        for (Gate g : gateTo) {
            if (g.getId().equals(gateTo_selected)) {
                flight_selected.setGateTo(g);
            }
        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(flight_selected);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Uspešno su sačuvane izmene za ovaj let."));
        return null;
    }

    public String syncCurrentFlight() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT c FROM ControlCheck c WHERE c.flightId=:flight");
        query.setEntity("flight", currentFlight);
        ccList = query.list();
        query = session.getNamedQuery("Airport.findAll");
        airport = query.list();
        session.getTransaction().commit();
        session.close();

        radioTowers = new ArrayList<String>(ccList.size());
        for (ControlCheck c : ccList) {
            radioTowers.add(c.getRadioTower().getName());
        }

        airportNames = new ArrayList<String>(airport.size());
        for (Airport a : airport) {
            airportNames.add(a.getCity() + " (" + a.getIata() + ")");
        }

        return "pilot-flight-update?faces-redirect=true";
    }

    public void cancelFlight() {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        int min1 = cal.get(Calendar.MINUTE);
        int total1 = hour1 * 60 + min1;
        cal.setTime(currentFlight.getDepartureTime());
        int hour2 = cal.get(Calendar.HOUR_OF_DAY);
        int min2 = cal.get(Calendar.MINUTE);
        int total2 = hour2 * 60 + min2;
        //can be canceled only if status==waiting and current time<departure time (aplane hasnt yet taken off)
        if (currentFlight.getStatus().equals("waiting") && total1 < total2) {
            currentFlight.setStatus("otkazan");
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(currentFlight);
            session.getTransaction().commit();
            session.close();
            toDoFlights.add(currentFlight);
            currentFlight = null;
            showCurrent = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Let je otkazan."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška", "Avion je već poleteo. Ne možete otkazati ovaj let."));
        }

    }

    public void emergencyLanding() {
        for (Airport a : airport) {
            if (emergencyLanding.contains(a.getCity())) {
                currentFlight.setToEmergency(a);
                currentFlight.setStatus("prinudno sleteo");
                currentFlight.setArrivalTime(new Date());
                break;
            }
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(currentFlight);
        session.getTransaction().commit();
        session.close();
        toDoFlights.add(currentFlight);
        currentFlight = null;
        showCurrent = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info.", "Avio je prinudno sleteo!"));

    }

    public void updateStatus() {
        //check is status==landed, remove flight
        if (status.equals("sleteo")) {
            currentFlight.setStatus("sleteo");
            currentFlight.setArrivalTime(new Date());
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(currentFlight);
            session.getTransaction().commit();
            session.close();
            toDoFlights.add(currentFlight);
            currentFlight = null;
            showCurrent = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "let je zavšen."));
            return;
        }
        ControlCheck c = null;
        for (ControlCheck cc : ccList) {
            if (cc.getRadioTower().getName().equals(tower_selected)) {
                c = cc;
                break;
            }
        }
        if (!status.isEmpty()) {
            c.setStatus(status);
            currentFlight.setStatus(status);
        }

        if (length != 0 && speed != 0) {
            double time = length / speed;
            int time_min = (int) (time * 60);
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            int total = hour * 60 + min + time_min;
            c.setNumber(total);
            hour = total / 60;
            min = total % 60;
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, min);
            currentFlight.setExpectedTime(cal.getTime());

        }

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(currentFlight);
        session.update(c);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešna komunikacija sa radio tornjem"));
        status = "";
        length = 0;
        speed = 0;
    }

}
