/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "flightarchive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FlightArchive.findAll", query = "SELECT f FROM FlightArchive f")
    , @NamedQuery(name = "FlightArchive.findById", query = "SELECT f FROM FlightArchive f WHERE f.id = :id")
    , @NamedQuery(name = "FlightArchive.findByDuration", query = "SELECT f FROM FlightArchive f WHERE f.duration = :duration")
    , @NamedQuery(name = "FlightArchive.findByDepartureDate", query = "SELECT f FROM FlightArchive f WHERE f.departureDate = :departureDate")
    , @NamedQuery(name = "FlightArchive.findByDepartureTime", query = "SELECT f FROM FlightArchive f WHERE f.departureTime = :departureTime")
    , @NamedQuery(name = "FlightArchive.findByPlannedTime", query = "SELECT f FROM FlightArchive f WHERE f.plannedTime = :plannedTime")
    , @NamedQuery(name = "FlightArchive.findByExpectedTime", query = "SELECT f FROM FlightArchive f WHERE f.expectedTime = :expectedTime")
    , @NamedQuery(name = "FlightArchive.findByArrivalTime", query = "SELECT f FROM FlightArchive f WHERE f.arrivalTime = :arrivalTime")
    , @NamedQuery(name = "FlightArchive.findByCharter", query = "SELECT f FROM FlightArchive f WHERE f.charter = :charter")})
public class FlightArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private int duration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departureDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIME)
    private Date departureTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "planned_time")
    @Temporal(TemporalType.TIME)
    private Date plannedTime;
    @Column(name = "expected_time")
    @Temporal(TemporalType.TIME)
    private Date expectedTime;
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIME)
    private Date arrivalTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "charter")
    private int charter;
    @JoinColumn(name = "company", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Airline company;
    @JoinColumn(name = "fa1", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User fa1;
    @JoinColumn(name = "fa2", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User fa2;
    @JoinColumn(name = "fa3", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User fa3;
    @JoinColumn(name = "fa4", referencedColumnName = "username")
    @ManyToOne
    private User fa4;
    @JoinColumn(name = "fa5", referencedColumnName = "username")
    @ManyToOne
    private User fa5;
    @JoinColumn(name = "coplot", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User coplot;
    @JoinColumn(name = "airport_from", referencedColumnName = "iata")
    @ManyToOne(optional = false)
    private Airport airportFrom;
    @JoinColumn(name = "airport_to", referencedColumnName = "iata")
    @ManyToOne(optional = false)
    private Airport airportTo;
    @JoinColumn(name = "gate_from", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gate gateFrom;
    @JoinColumn(name = "gate_to", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gate gateTo;
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Airplane airplaneId;
    @JoinColumn(name = "to_emergency", referencedColumnName = "iata")
    @ManyToOne
    private Airport toEmergency;
    @JoinColumn(name = "pilot", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User pilot;

    public FlightArchive() {
    }

    public FlightArchive(Integer id) {
        this.id = id;
    }

    public FlightArchive(Integer id, int duration, Date departureDate, Date departureTime, Date plannedTime, int charter) {
        this.id = id;
        this.duration = duration;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.plannedTime = plannedTime;
        this.charter = charter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(Date plannedTime) {
        this.plannedTime = plannedTime;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCharter() {
        return charter;
    }

    public void setCharter(int charter) {
        this.charter = charter;
    }

    public Airline getCompany() {
        return company;
    }

    public void setCompany(Airline company) {
        this.company = company;
    }

    public User getFa1() {
        return fa1;
    }

    public void setFa1(User fa1) {
        this.fa1 = fa1;
    }

    public User getFa2() {
        return fa2;
    }

    public void setFa2(User fa2) {
        this.fa2 = fa2;
    }

    public User getFa3() {
        return fa3;
    }

    public void setFa3(User fa3) {
        this.fa3 = fa3;
    }

    public User getFa4() {
        return fa4;
    }

    public void setFa4(User fa4) {
        this.fa4 = fa4;
    }

    public User getFa5() {
        return fa5;
    }

    public void setFa5(User fa5) {
        this.fa5 = fa5;
    }

    public User getCoplot() {
        return coplot;
    }

    public void setCoplot(User coplot) {
        this.coplot = coplot;
    }

    public Airport getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(Airport airportFrom) {
        this.airportFrom = airportFrom;
    }

    public Airport getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(Airport airportTo) {
        this.airportTo = airportTo;
    }

    public Gate getGateFrom() {
        return gateFrom;
    }

    public void setGateFrom(Gate gateFrom) {
        this.gateFrom = gateFrom;
    }

    public Gate getGateTo() {
        return gateTo;
    }

    public void setGateTo(Gate gateTo) {
        this.gateTo = gateTo;
    }

    public Airplane getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Airplane airplaneId) {
        this.airplaneId = airplaneId;
    }

    public Airport getToEmergency() {
        return toEmergency;
    }

    public void setToEmergency(Airport toEmergency) {
        this.toEmergency = toEmergency;
    }

    public User getPilot() {
        return pilot;
    }

    public void setPilot(User pilot) {
        this.pilot = pilot;
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
        if (!(object instanceof FlightArchive)) {
            return false;
        }
        FlightArchive other = (FlightArchive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FlightArchive[ id=" + id + " ]";
    }
    
}
