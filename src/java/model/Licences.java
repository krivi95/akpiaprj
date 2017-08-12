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
@Table(name = "licences")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Licences.findAll", query = "SELECT l FROM Licences l")
    , @NamedQuery(name = "Licences.findByLicenceNo", query = "SELECT l FROM Licences l WHERE l.licenceNo = :licenceNo")})
public class Licences implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "licence_no")
    private String licenceNo;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User user;

    public Licences() {
    }

    public Licences(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licenceNo != null ? licenceNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Licences)) {
            return false;
        }
        Licences other = (Licences) object;
        if ((this.licenceNo == null && other.licenceNo != null) || (this.licenceNo != null && !this.licenceNo.equals(other.licenceNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Licences[ licenceNo=" + licenceNo + " ]";
    }
    
}
