package com.epccrm.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Table(name = "CONTACT", indexes = {
        @Index(name = "IDX_CONTACT_PRINCIPAL", columnList = "PRINCIPAL_ID")
})
@JmixEntity
@Entity
public class Contact {

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @JoinColumn(name = "TITLE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue title;

    @NotNull
    @InstanceName
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "IS_DEFAULT_TRUE", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDefaultTrue;

    @Column(name = "LAST_NAME")
    private String lastName;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PRINCIPAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Principal principal;

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Boolean getIsDefaultTrue() {
        return isDefaultTrue;
    }

    public void setIsDefaultTrue(Boolean isDefaultTrue) {
        this.isDefaultTrue = isDefaultTrue;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void setTitle(EnumValue title) {
        this.title = title;
    }

    public EnumValue getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}