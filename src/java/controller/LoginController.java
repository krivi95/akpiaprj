package controller;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.User;
import org.hibernate.Session;


@Named(value = "loginController")
@SessionScoped
@ManagedBean
public class LoginController {
    
    private String username;
    private String passwoord;
    private User currentUser;

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
    
    
    
    public String LogIn(){
        
        System.out.println("login metoda");
        System.out.println(this.passwoord);
        System.out.println(this.username);
        
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpsession = (HttpSession)context.getExternalContext().getSession(true);
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession(); 
        session.beginTransaction();
        
        org.hibernate.Query query =  session.getNamedQuery("User.findByUsername");  
        query.setString("username", username);
        List<User> users = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        if(users.size()!=0)System.out.println(users.get(0).getName());
        else System.out.println(users.size());
        
        User user=null;
        if(users.size()==1)user = users.get(0);
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Nepostojeće korisničko ime."));
            return "index";
        }
        
        if(!user.getPassword().equals(this.passwoord)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!", "Neispravna lozinka."));
            return "index";
        }
        if(user.getPending()==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Čekanje na odobrenje od administratora"));
            return "index";
        }
        currentUser=user;
        httpsession.setAttribute("currentuser", user);
        
        if(user.getType().equals("pilot")) return "pilot/home";
        if(user.getType().equals("stjuardesa")) return "fa/home";
        if(user.getType().equals("admin")) return "admin/home";
        if(user.getType().equals("radnik")) return "worker/home";
        
        
        
        
        return "index";
    }
}
