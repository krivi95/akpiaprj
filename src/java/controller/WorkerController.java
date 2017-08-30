package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airplane;
import model.ControlCheck;
import model.CurrentUser;
import model.Flight;
import model.Manufacturer;
import model.Model;
import model.Rental;
import model.User;
import org.hibernate.Session;
import org.primefaces.event.DragDropEvent;
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

    private List<Airplane> airplanes;
    private List<Airplane> selectedAirplanes;

    private List<Flight> currentFlights;
    private Flight flight_selected;
    private List<ControlCheck> radioTowers;
    private MapModel googleMap;

    private List<Manufacturer> manufacturers;
    private List<String> manufacturerNames;
    private String manufacturer_selected;
    private List<Model> models;
    private List<String> modelNames;
    private String model_selected;
    private int minSeates;
    private int price;
    private Date from;
    private Date to;    

    private boolean show = false;
    private List<Airplane> airplanes_search;
    
    private List<Rental> offers;

    public List<Rental> getOffers() {
        return offers;
    }

    public void setOffers(List<Rental> offers) {
        this.offers = offers;
    }
    

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
    

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<Airplane> getAirplanes_search() {
        return airplanes_search;
    }

    public void setAirplanes_search(List<Airplane> airplanes_search) {
        this.airplanes_search = airplanes_search;
    }

    public String getManufacturer_selected() {
        return manufacturer_selected;
    }

    public void setManufacturer_selected(String manufacturer_selected) {
        this.manufacturer_selected = manufacturer_selected;
    }

    public String getModel_selected() {
        return model_selected;
    }

    public void setModel_selected(String model_selected) {
        this.model_selected = model_selected;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<String> getManufacturerNames() {
        return manufacturerNames;
    }

    public void setManufacturerNames(List<String> manufacturerNames) {
        this.manufacturerNames = manufacturerNames;
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

    public int getMinSeates() {
        return minSeates;
    }

    public void setMinSeates(int minSeates) {
        this.minSeates = minSeates;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public List<Airplane> getSelectedAirplanes() {
        return selectedAirplanes;
    }

    public void setSelectedAirplanes(List<Airplane> selectedAirplanes) {
        this.selectedAirplanes = selectedAirplanes;
    }

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

        query = session.getNamedQuery("Manufacturer.findAll");
        manufacturers = query.list();
        manufacturerNames = new ArrayList<String>(manufacturers.size());
        for (Manufacturer m : manufacturers) {
            manufacturerNames.add(m.getName());
        }

        query = session.getNamedQuery("Model.findAll");
        models = query.list();
        modelNames = new ArrayList<String>(models.size());
        modelNames.add("sve jedno");
        for (Model m : models) {
            modelNames.add(m.getName());
        }
        
        query = session.createQuery("SELECT r FROM Rental r WHERE r.aOffering=:airline AND r.pending=1");
        query.setEntity("airline", worker.getAirline());
        offers = query.list();

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

        int i = 0;
        for (ControlCheck c : radioTowers) {
            double longitude = c.getRadioTower().getLongitude();
            double latitude = c.getRadioTower().getLatitude();
            LatLng coord1 = new LatLng(longitude, latitude);
            if (i == 0) {
                googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName(), "konyaalti.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            } else if (i == radioTowers.size() - 1) {
                googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName(), "karaalioglu.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
            } else {
                googleMap.addOverlay(new Marker(coord1, c.getRadioTower().getName()));
            }
            polyline.getPaths().add(coord1);
            i++;
        }
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        googleMap.addOverlay(polyline);
    }

    public String chooseNewAirplane() {

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.createQuery("SELECT a FROM Airplane a WHERE a.airline=NULL");
        airplanes = query.list();
        session.getTransaction().commit();
        session.close();

        selectedAirplanes = new ArrayList<Airplane>();

        return "worker-airplane-add?faces-redirect=true";

    }

    public void onAirplaneDrop(DragDropEvent ddEvent) {
        Airplane a = ((Airplane) ddEvent.getData());

        selectedAirplanes.add(a);
        airplanes.remove(a);
    }

    public void addNewPlane() {
        Airplane a = selectedAirplanes.get(0);
        a.setAirline(worker.getAirline());

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(a);
        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Uspešno je dodan novi avion u flotu."));

    }

    public void updateModelList() {
        modelNames = new ArrayList<String>();
        modelNames.add("sve jedno");
        for (Model m : models) {
            if (m.getMName().getName().equals(manufacturer_selected)) {
                modelNames.add(m.getName());
            }
        }
    }

    public void search() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Model mm = null;
        List<Airplane> all;
        if (!model_selected.equals("sve jedno")) {
            //not any model, but chosen one
            for (Model m : models) {
                if (m.getName().equals(model_selected)) {
                    mm = m;
                    break;
                }
            }

            org.hibernate.Query query = session.createQuery("SELECT a FROM Airplane a WHERE a.licence=:model AND a.maxSeats>=:seats AND a.airline!=NULL");
            query.setEntity("model", mm);
            query.setInteger("seats", minSeates);
            all = query.list();
        } else {
            //any model from the chosen manufacturer
            Manufacturer mm1 = null;
            for (Model m : models) {
                if (m.getMName().getName().equals(manufacturer_selected)) {
                    mm1 = m.getMName();
                    break;
                }
            }
            org.hibernate.Query query = session.createQuery("SELECT a FROM Airplane a WHERE a.licence.mName=:manufacturer AND a.maxSeats>=:seats AND a.airline!=NULL");
            query.setEntity("manufacturer", mm1);
            query.setInteger("seats", minSeates);
            all = query.list();

        }

        airplanes_search = new ArrayList<Airplane>();
        for (Airplane a : all) {
            org.hibernate.Query query = session.createQuery("SELECT f FROM Flight f WHERE f.airplaneId=:airplane");
            query.setEntity("airplane", a);
            List<Flight> flights = query.list();
            boolean ok = true;
            for (Flight f : flights) {
                if (f.getDepartureDate().after(from) && f.getDepartureDate().before(to)) {
                    ok = false;
                }
            }
            if (ok) {
                airplanes_search.add(a);
            }
        }

        show = true;
        session.getTransaction().commit();
        session.close();
    }

    public void makeAnOffer(Airplane a) {
        Rental r = new Rental();
        r.setAOffering(a.getAirline());
        r.setARenting(worker.getAirline());
        r.setAirplane(a);
        r.setDateFrom(from);
        r.setDateTo(to);
        r.setOffer(price);
        r.setPending(1);
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(r);
        session.getTransaction().commit();
        session.close();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Ponuda je prihvacena."));
    }
    
    public void approveOffer(Rental r){
        offers.remove(r);
        r.setPending(0);
        Airplane a = r.getAirplane();
        a.setAirlineRenting(r.getARenting());
        a.setRented(1);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(r);
        session.update(a);
        session.getTransaction().commit();
        session.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Ponuda je prihvaćena."));
        
    }
    
    public void deleteOffer(Rental r){
        offers.remove(r);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(r);
        session.getTransaction().commit();
        session.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info.", "Ponuda je odbijena."));
        
    }
    
    
    
}
