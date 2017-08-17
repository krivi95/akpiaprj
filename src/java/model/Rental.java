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
@Table(name = "rental")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rental.findAll", query = "SELECT r FROM Rental r")
    , @NamedQuery(name = "Rental.findById", query = "SELECT r FROM Rental r WHERE r.id = :id")
    , @NamedQuery(name = "Rental.findByOffer", query = "SELECT r FROM Rental r WHERE r.offer = :offer")
    , @NamedQuery(name = "Rental.findByDateFrom", query = "SELECT r FROM Rental r WHERE r.dateFrom = :dateFrom")
    , @NamedQuery(name = "Rental.findByDateTo", query = "SELECT r FROM Rental r WHERE r.dateTo = :dateTo")
    , @NamedQuery(name = "Rental.findByPending", query = "SELECT r FROM Rental r WHERE r.pending = :pending")})
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "offer")
    private int offer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_from")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_to")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pending")
    private int pending;
    @JoinColumn(name = "a_renting", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Airline aRenting;
    @JoinColumn(name = "a_offering", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Airline aOffering;

    public Rental() {
    }

    public Rental(Integer id) {
        this.id = id;
    }

    public Rental(Integer id, int offer, Date dateFrom, Date dateTo, int pending) {
        this.id = id;
        this.offer = offer;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.pending = pending;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public Airline getARenting() {
        return aRenting;
    }

    public void setARenting(Airline aRenting) {
        this.aRenting = aRenting;
    }

    public Airline getAOffering() {
        return aOffering;
    }

    public void setAOffering(Airline aOffering) {
        this.aOffering = aOffering;
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
        if (!(object instanceof Rental)) {
            return false;
        }
        Rental other = (Rental) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Rental[ id=" + id + " ]";
    }
    
}
