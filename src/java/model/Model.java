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
@Table(name = "model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m")
    , @NamedQuery(name = "Model.findByLicence", query = "SELECT m FROM Model m WHERE m.licence = :licence")
    , @NamedQuery(name = "Model.findByName", query = "SELECT m FROM Model m WHERE m.name = :name")})
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "licence")
    private String licence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "licence")
    private List<Airplane> airplaneList;
    @JoinColumn(name = "m_name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Manufacturer mName;

    public Model() {
    }

    public Model(String licence) {
        this.licence = licence;
    }

    public Model(String licence, String name) {
        this.licence = licence;
        this.name = name;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Airplane> getAirplaneList() {
        return airplaneList;
    }

    public void setAirplaneList(List<Airplane> airplaneList) {
        this.airplaneList = airplaneList;
    }

    public Manufacturer getMName() {
        return mName;
    }

    public void setMName(Manufacturer mName) {
        this.mName = mName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licence != null ? licence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.licence == null && other.licence != null) || (this.licence != null && !this.licence.equals(other.licence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Model[ licence=" + licence + " ]";
    }
    
}
