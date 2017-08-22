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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "radiotower")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RadioTower.findAll", query = "SELECT r FROM RadioTower r")
    , @NamedQuery(name = "RadioTower.findByName", query = "SELECT r FROM RadioTower r WHERE r.name = :name")
    , @NamedQuery(name = "RadioTower.findByLongitude", query = "SELECT r FROM RadioTower r WHERE r.longitude = :longitude")
    , @NamedQuery(name = "RadioTower.findByLatitude", query = "SELECT r FROM RadioTower r WHERE r.latitude = :latitude")})
public class RadioTower implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private double longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private double latitude;
    @JoinColumn(name = "iata", referencedColumnName = "iata")
    @ManyToOne
    private Airport iata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "radioTower")
    private List<ControlCheck> controlCheckList;

    public RadioTower() {
    }

    public RadioTower(String name) {
        this.name = name;
    }

    public RadioTower(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Airport getIata() {
        return iata;
    }

    public void setIata(Airport iata) {
        this.iata = iata;
    }

    @XmlTransient
    public List<ControlCheck> getControlCheckList() {
        return controlCheckList;
    }

    public void setControlCheckList(List<ControlCheck> controlCheckList) {
        this.controlCheckList = controlCheckList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RadioTower)) {
            return false;
        }
        RadioTower other = (RadioTower) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RadioTower[ name=" + name + " ]";
    }

  
}
