/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "airplane")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airplane.findAll", query = "SELECT a FROM Airplane a")
    , @NamedQuery(name = "Airplane.findById", query = "SELECT a FROM Airplane a WHERE a.id = :id")
    , @NamedQuery(name = "Airplane.findByName", query = "SELECT a FROM Airplane a WHERE a.name = :name")
    , @NamedQuery(name = "Airplane.findByMaxSeats", query = "SELECT a FROM Airplane a WHERE a.maxSeats = :maxSeats")})
public class Airplane implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_seats")
    private int maxSeats;
    @JoinColumn(name = "airline", referencedColumnName = "id")
    @ManyToOne
    private Airline airline;
    @JoinColumn(name = "licence", referencedColumnName = "licence")
    @ManyToOne(optional = false)
    private Model licence;

    public Airplane() {
    }

    public Airplane(Integer id) {
        this.id = id;
    }

    public Airplane(Integer id, String name, int maxSeats) {
        this.id = id;
        this.name = name;
        this.maxSeats = maxSeats;
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

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Model getLicence() {
        return licence;
    }

    public void setLicence(Model licence) {
        this.licence = licence;
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
        if (!(object instanceof Airplane)) {
            return false;
        }
        Airplane other = (Airplane) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Airplane[ id=" + id + " ]";
    }
    
}
