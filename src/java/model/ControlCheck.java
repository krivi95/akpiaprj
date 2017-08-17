/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    , @NamedQuery(name = "ControlCheck.findByFlightId", query = "SELECT c FROM ControlCheck c WHERE c.controlCheckPK.flightId = :flightId")
    , @NamedQuery(name = "ControlCheck.findByRadioTower", query = "SELECT c FROM ControlCheck c WHERE c.controlCheckPK.radioTower = :radioTower")
    , @NamedQuery(name = "ControlCheck.findByStatus", query = "SELECT c FROM ControlCheck c WHERE c.status = :status")
    , @NamedQuery(name = "ControlCheck.findByNumber", query = "SELECT c FROM ControlCheck c WHERE c.number = :number")})
public class ControlCheck implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ControlCheckPK controlCheckPK;
    @Size(max = 30)
    @Column(name = "status")
    private String status;
    @Column(name = "number")
    private Integer number;
    @JoinColumn(name = "flight_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Flight flight;
    @JoinColumn(name = "radio_tower", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RadioTower radioTower1;

    public ControlCheck() {
    }

    public ControlCheck(ControlCheckPK controlCheckPK) {
        this.controlCheckPK = controlCheckPK;
    }

    public ControlCheck(int flightId, String radioTower) {
        this.controlCheckPK = new ControlCheckPK(flightId, radioTower);
    }

    public ControlCheckPK getControlCheckPK() {
        return controlCheckPK;
    }

    public void setControlCheckPK(ControlCheckPK controlCheckPK) {
        this.controlCheckPK = controlCheckPK;
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

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public RadioTower getRadioTower1() {
        return radioTower1;
    }

    public void setRadioTower1(RadioTower radioTower1) {
        this.radioTower1 = radioTower1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlCheckPK != null ? controlCheckPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlCheck)) {
            return false;
        }
        ControlCheck other = (ControlCheck) object;
        if ((this.controlCheckPK == null && other.controlCheckPK != null) || (this.controlCheckPK != null && !this.controlCheckPK.equals(other.controlCheckPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ControlCheck[ controlCheckPK=" + controlCheckPK + " ]";
    }
    
}
