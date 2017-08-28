package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.ControlCheck;
import model.CurrentUser;
import model.Flight;
import model.User;
import org.hibernate.Session;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

@Named(value = "workerController")
@SessionScoped
@ManagedBean
public class WorkerController {

    private User worker;

    private List<Flight> currentFlights;
    private Flight flight_selected;
    private List<ControlCheck> radioTowers;
    private MapModel googleMap;

    public MapModel getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(MapModel googleMap) {
        this.googleMap = googleMap;
    }

    public Flight getFlight_selected() {
        return flight_selected;
    }

    public void setFlight_selected(Flight flight_selected) {
        this.flight_selected = flight_selected;
    }

    public List<ControlCheck> getRadioTowers() {
        return radioTowers;
    }

    public void setRadioTowers(List<ControlCheck> radioTowers) {
        this.radioTowers = radioTowers;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public List<Flight> getCurrentFlights() {
        return currentFlights;
    }

    public void setCurrentFlights(List<Flight> currentFlights) {
        this.currentFlights = currentFlights;
    }

    public WorkerController() {
        worker = CurrentUser.user;

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.company=:airline ");
        query.setEntity("airline", worker.getAirline());
        List<Flight> allFlights = query.list();
        currentFlights = new ArrayList<Flight>(allFlights.size());
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        for (Flight f : allFlights) {
            cal.setTime(f.getDepartureDate());
            int month2 = cal.get(Calendar.MONTH);
            int day2 = cal.get(Calendar.DAY_OF_MONTH);
            if (f.getDepartureDate().after(d)) {
                currentFlights.add(f);
            }
            if (month1 == month2 && day1 == day2) {
                currentFlights.add(f);
            }
        }

        session.getTransaction().commit();
        session.close();

    }

    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }

    public void showFlight(Flight f) {
        flight_selected = f;

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT c FROM ControlCheck c WHERE c.flightId=:flight");
        query.setEntity("flight", flight_selected);
        radioTowers = query.list();
        session.getTransaction().commit();
        session.close();

        googleMap = new DefaultMapModel();        
        Polyline polyline = new Polyline();

        int i=0;
        for (ControlCheck c : radioTowers) {
            double longitude = c.getRadioTower().getLongitude();
            double latitude = c.getRadioTower().getLatitude();
            LatLng coord1 = new LatLng(longitude, latitude);
            if(i==0)googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName(),"konyaalti.png","http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            else if(i==radioTowers.size()-1)googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName(),"karaalioglu.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
            else googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName()));
            polyline.getPaths().add(coord1);         
            i++;
        }        
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        googleMap.addOverlay(polyline);
    }

}
