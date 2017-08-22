/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    , @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender")
    , @NamedQuery(name = "User.findByBirth", query = "SELECT u FROM User u WHERE u.birth = :birth")
    , @NamedQuery(name = "User.findByType", query = "SELECT u FROM User u WHERE u.type = :type")
    , @NamedQuery(name = "User.findByPending", query = "SELECT u FROM User u WHERE u.pending = :pending")
    , @NamedQuery(name = "User.findByPilotFirstLogin", query = "SELECT u FROM User u WHERE u.pilotFirstLogin = :pilotFirstLogin")})
public class User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
    private List<Flight> flightList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fa2")
    private List<Flight> flightList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fa1")
    private List<Flight> flightList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fa3")
    private List<Flight> flightList3;
    @OneToMany(mappedBy = "fa4")
    private List<Flight> flightList4;
    @OneToMany(mappedBy = "fa5")
    private List<Flight> flightList5;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coplot")
    private List<Flight> flightList6;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gender")
    private Character gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birth")
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pending")
    private int pending;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pilot_first_login")
    private int pilotFirstLogin;
    @JoinColumn(name = "airline", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Airline airline;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String name, Character gender, Date birth, String type, int pending, int pilotFirstLogin) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.type = type;
        this.pending = pending;
        this.pilotFirstLogin = pilotFirstLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getPilotFirstLogin() {
        return pilotFirstLogin;
    }

    public void setPilotFirstLogin(int pilotFirstLogin) {
        this.pilotFirstLogin = pilotFirstLogin;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ username=" + username + " ]";
    }

    @XmlTransient
    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    @XmlTransient
    public List<Flight> getFlightList1() {
        return flightList1;
    }

    public void setFlightList1(List<Flight> flightList1) {
        this.flightList1 = flightList1;
    }

    @XmlTransient
    public List<Flight> getFlightList2() {
        return flightList2;
    }

    public void setFlightList2(List<Flight> flightList2) {
        this.flightList2 = flightList2;
    }

    @XmlTransient
    public List<Flight> getFlightList3() {
        return flightList3;
    }

    public void setFlightList3(List<Flight> flightList3) {
        this.flightList3 = flightList3;
    }

    @XmlTransient
    public List<Flight> getFlightList4() {
        return flightList4;
    }

    public void setFlightList4(List<Flight> flightList4) {
        this.flightList4 = flightList4;
    }

    @XmlTransient
    public List<Flight> getFlightList5() {
        return flightList5;
    }

    public void setFlightList5(List<Flight> flightList5) {
        this.flightList5 = flightList5;
    }

    @XmlTransient
    public List<Flight> getFlightList6() {
        return flightList6;
    }

    public void setFlightList6(List<Flight> flightList6) {
        this.flightList6 = flightList6;
    }
    
}
