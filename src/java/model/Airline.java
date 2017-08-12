/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "airline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airline.findAll", query = "SELECT a FROM Airline a")
    , @NamedQuery(name = "Airline.findById", query = "SELECT a FROM Airline a WHERE a.id = :id")
    , @NamedQuery(name = "Airline.findByName", query = "SELECT a FROM Airline a WHERE a.name = :name")
    , @NamedQuery(name = "Airline.findByHeadquarters", query = "SELECT a FROM Airline a WHERE a.headquarters = :headquarters")
    , @NamedQuery(name = "Airline.findByWebsite", query = "SELECT a FROM Airline a WHERE a.website = :website")
    , @NamedQuery(name = "Airline.findByEmail", query = "SELECT a FROM Airline a WHERE a.email = :email")})
public class Airline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "headquarters")
    private String headquarters;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "website")
    private String website;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Flight> flightList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airplaneId")
    private List<Flight> flightList1;
    @OneToMany(mappedBy = "airline")
    private List<Airplane> airplaneList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airline")
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aRenting")
    private List<Rental> rentalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aOffering")
    private List<Rental> rentalList1;

    public Airline() {
    }

    public Airline(Integer id) {
        this.id = id;
    }

    public Airline(Integer id, String name, String headquarters, String website, String email) {
        this.id = id;
        this.name = name;
        this.headquarters = headquarters;
        this.website = website;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public List<Airplane> getAirplaneList() {
        return airplaneList;
    }

    public void setAirplaneList(List<Airplane> airplaneList) {
        this.airplaneList = airplaneList;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Rental> getRentalList() {
        return rentalList;
    }

    public void setRentalList(List<Rental> rentalList) {
        this.rentalList = rentalList;
    }

    @XmlTransient
    public List<Rental> getRentalList1() {
        return rentalList1;
    }

    public void setRentalList1(List<Rental> rentalList1) {
        this.rentalList1 = rentalList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airline)) {
            return false;
        }
        Airline other = (Airline) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Airline[ id=" + id + " ]";
    }
    
}
