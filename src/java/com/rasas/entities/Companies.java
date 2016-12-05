/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "COMPANIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Companies.findAll", query = "SELECT c FROM Companies c"),
    @NamedQuery(name = "Companies.findByDecCod", query = "SELECT c FROM Companies c WHERE c.decCod = :decCod"),
    @NamedQuery(name = "Companies.findByDecNam", query = "SELECT c FROM Companies c WHERE c.decNam = :decNam")})
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "DEC_COD")
    private String decCod;
    @Size(max = 35)
    @Column(name = "DEC_NAM")
    private String decNam;

    public Companies() {
    }

    public String getDecCod() {
        return decCod;
    }

    public void setDecCod(String decCod) {
        this.decCod = decCod;
    }

    public String getDecNam() {
        return decNam;
    }

    public void setDecNam(String decNam) {
        this.decNam = decNam;
    }
    
}
