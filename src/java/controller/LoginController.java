package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Airline;
import model.User;
import org.hibernate.Session;

@Named(value = "loginController")
@SessionScoped
@ManagedBean
public class LoginController {

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

    public String toRegistration() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.getNamedQuery("Airline.findAll");
        airlines = query.list();
        session.getTransaction().commit();
        session.close();

        airlineNames= new ArrayList<String>(airlines.size()-1);  
        
        for(Airline a: airlines){
            if(a.getName().equals("Admin sajta"))continue;
            airlineNames.add(a.getName());
            System.out.println(a.getName());
        }
        
        return "Registration";
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

    public LoginController() {
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
    
    public String register(){
        
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
        
        for(Airline a: airlines){
            if(a.getName().equals(this.airline_new)){
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

}
