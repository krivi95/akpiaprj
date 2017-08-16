package controller;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.User;
import org.hibernate.Session;

@Named(value = "adminController")
@Dependent
@SessionScoped
public class AdminController {

    private List<User> unapprovedUsers = null;

    public List<User> getUnapprovedUsers() {
        return unapprovedUsers;
    }

    public void setUnapprovedUsers(List<User> unapprovedUsers) {
        this.unapprovedUsers = unapprovedUsers;
    }
    

    public AdminController() {
        
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery("User.findByPending");
        query.setInteger("pending", 1);
        unapprovedUsers = query.list();
        session.getTransaction().commit();
        session.close();

    }
    
    public void approveUser(User u){
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
    
    public void deleteUser(User u){
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(u);
        org.hibernate.Query query = session.getNamedQuery("User.findByPending");
        query.setInteger("pending", 1);
        unapprovedUsers = query.list();
        session.getTransaction().commit();
        session.close();
        
    }
    
    public String logOut(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index";
    }

}
