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
@Table(name = "airport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")
    , @NamedQuery(name = "Airport.findByIata", query = "SELECT a FROM Airport a WHERE a.iata = :iata")
    , @NamedQuery(name = "Airport.findByName", query = "SELECT a FROM Airport a WHERE a.name = :name")
    , @NamedQuery(name = "Airport.findByCity", query = "SELECT a FROM Airport a WHERE a.city = :city")
    , @NamedQuery(name = "Airport.findByState", query = "SELECT a FROM Airport a WHERE a.state = :state")
    , @NamedQuery(name = "Airport.findByRunway", query = "SELECT a FROM Airport a WHERE a.runway = :runway")
    , @NamedQuery(name = "Airport.findByTerminal", query = "SELECT a FROM Airport a WHERE a.terminal = :terminal")})
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "iata")
    private String iata;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "City")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "State")
    private String state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "runway")
    private int runway;
    @Basic(optional = false)
    @NotNull
    @Column(name = "terminal")
    private int terminal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airportTo")
    private List<Flight> flightList;
    @OneToMany(mappedBy = "toEmergency")
    private List<Flight> flightList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airportFrom")
    private List<Flight> flightList2;
    @OneToMany(mappedBy = "iata")
    private List<RadioTower> radioTowerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iata")
    private List<Gate> gateList;

    public Airport() {
    }

    public Airport(String iata) {
        this.iata = iata;
    }

    public Airport(String iata, String name, String city, String state, int runway, int terminal) {
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.state = state;
        this.runway = runway;
        this.terminal = terminal;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRunway() {
        return runway;
    }

    public void setRunway(int runway) {
        this.runway = runway;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
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
    public List<RadioTower> getRadioTowerList() {
        return radioTowerList;
    }

    public void setRadioTowerList(List<RadioTower> radioTowerList) {
        this.radioTowerList = radioTowerList;
    }

    @XmlTransient
    public List<Gate> getGateList() {
        return gateList;
    }

    public void setGateList(List<Gate> gateList) {
        this.gateList = gateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iata != null ? iata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.iata == null && other.iata != null) || (this.iata != null && !this.iata.equals(other.iata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Airport[ iata=" + iata + " ]";
    }
    
}
