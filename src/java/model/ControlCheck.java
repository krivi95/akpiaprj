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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "controlcheck")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlCheck.findAll", query = "SELECT c FROM ControlCheck c")
    , @NamedQuery(name = "ControlCheck.findById", query = "SELECT c FROM ControlCheck c WHERE c.id = :id")
    , @NamedQuery(name = "ControlCheck.findByStatus", query = "SELECT c FROM ControlCheck c WHERE c.status = :status")
    , @NamedQuery(name = "ControlCheck.findByNumber", query = "SELECT c FROM ControlCheck c WHERE c.number = :number")})
public class ControlCheck implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "status")
    private String status;
    @Column(name = "number")
    private Integer number;
    @JoinColumn(name = "radio_tower", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private RadioTower radioTower;
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Flight flightId;

    public ControlCheck() {
    }

    public ControlCheck(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public RadioTower getRadioTower() {
        return radioTower;
    }

    public void setRadioTower(RadioTower radioTower) {
        this.radioTower = radioTower;
    }

    public Flight getFlightId() {
        return flightId;
    }

    public void setFlightId(Flight flightId) {
        this.flightId = flightId;
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
        if (!(object instanceof ControlCheck)) {
            return false;
        }
        ControlCheck other = (ControlCheck) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ControlCheck[ id=" + id + " ]";
    }
    
}
