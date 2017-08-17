/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Korisnik
 */
@Embeddable
public class ControlCheckPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_id")
    private int flightId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "radio_tower")
    private String radioTower;

    public ControlCheckPK() {
    }

    public ControlCheckPK(int flightId, String radioTower) {
        this.flightId = flightId;
        this.radioTower = radioTower;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getRadioTower() {
        return radioTower;
    }

    public void setRadioTower(String radioTower) {
        this.radioTower = radioTower;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) flightId;
        hash += (radioTower != null ? radioTower.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlCheckPK)) {
            return false;
        }
        ControlCheckPK other = (ControlCheckPK) object;
        if (this.flightId != other.flightId) {
            return false;
        }
        if ((this.radioTower == null && other.radioTower != null) || (this.radioTower != null && !this.radioTower.equals(other.radioTower))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ControlCheckPK[ flightId=" + flightId + ", radioTower=" + radioTower + " ]";
    }
    
}
