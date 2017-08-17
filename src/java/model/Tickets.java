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
@Table(name = "tickets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tickets.findAll", query = "SELECT t FROM Tickets t")
    , @NamedQuery(name = "Tickets.findByTicketNo", query = "SELECT t FROM Tickets t WHERE t.ticketNo = :ticketNo")
    , @NamedQuery(name = "Tickets.findByName", query = "SELECT t FROM Tickets t WHERE t.name = :name")
    , @NamedQuery(name = "Tickets.findByPassportNo", query = "SELECT t FROM Tickets t WHERE t.passportNo = :passportNo")})
public class Tickets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticket_no")
    private Integer ticketNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "passport_no")
    private String passportNo;

    public Tickets() {
    }

    public Tickets(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Tickets(Integer ticketNo, String name, String passportNo) {
        this.ticketNo = ticketNo;
        this.name = name;
        this.passportNo = passportNo;
    }

    public Integer getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketNo != null ? ticketNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tickets)) {
            return false;
        }
        Tickets other = (Tickets) object;
        if ((this.ticketNo == null && other.ticketNo != null) || (this.ticketNo != null && !this.ticketNo.equals(other.ticketNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tickets[ ticketNo=" + ticketNo + " ]";
    }
    
}
