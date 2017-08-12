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
@Table(name = "gate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gate.findAll", query = "SELECT g FROM Gate g")
    , @NamedQuery(name = "Gate.findById", query = "SELECT g FROM Gate g WHERE g.id = :id")
    , @NamedQuery(name = "Gate.findByTerminalNo", query = "SELECT g FROM Gate g WHERE g.terminalNo = :terminalNo")
    , @NamedQuery(name = "Gate.findByGate", query = "SELECT g FROM Gate g WHERE g.gate = :gate")})
public class Gate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "terminal_no")
    private int terminalNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "gate")
    private String gate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gateFrom")
    private List<Flight> flightList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gateTo")
    private List<Flight> flightList1;
    @JoinColumn(name = "iata", referencedColumnName = "iata")
    @ManyToOne(optional = false)
    private Airport iata;

    public Gate() {
    }

    public Gate(String id) {
        this.id = id;
    }

    public Gate(String id, int terminalNo, String gate) {
        this.id = id;
        this.terminalNo = terminalNo;
        this.gate = gate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(int terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
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

    public Airport getIata() {
        return iata;
    }

    public void setIata(Airport iata) {
        this.iata = iata;
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
        if (!(object instanceof Gate)) {
            return false;
        }
        Gate other = (Gate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Gate[ id=" + id + " ]";
    }
    
}
