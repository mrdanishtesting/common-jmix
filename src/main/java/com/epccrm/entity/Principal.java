package com.epccrm.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "PRINCIPAL", indexes = {
        @Index(name = "IDX_PRINCIPAL_TYPE", columnList = "TYPE_ID"),
        @Index(name = "IDX_PRINCIPAL_COLLECT_ACCESS", columnList = "COLLECT_ACCESS_ID"),
        @Index(name = "IDX_PRINCIPAL_DEFAULT_NOMINAL", columnList = "DEFAULT_NOMINAL_ID"),
        @Index(name = "IDX_PRINCIPAL_VAT_STATUS", columnList = "VAT_STATUS_ID"),
        @Index(name = "IDX_PRINCIPAL_GENERATE_ALERTS", columnList = "GENERATE_ALERTS_ID"),
        @Index(name = "IDX_PRINCIPAL_ACTIVE", columnList = "ACTIVE_ID"),
        @Index(name = "IDX_PRINCIPAL_INVOICE_TYPE", columnList = "INVOICE_TYPE_ID"),
        @Index(name = "IDX_PRINCIPAL_MANDATE_REQUIRED", columnList = "MANDATE_REQUIRED_ID")
})
@Entity
public class Principal {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "LOGO")
    private FileRef logo;

    @Column(name = "SOR")
    private String sor;

    @Column(name = "EMAIL")
    private String email;

    @JoinColumn(name = "ACTIVE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue active;

    @JoinColumn(name = "INVOICE_TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue invoiceType;

    @JoinColumn(name = "MANDATE_REQUIRED_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue mandateRequired;

    @Column(name = "HIERARCHY")
    private String hierarchy;

    @Column(name = "DELEGATED_AUTHORITY")
    private String delegatedAuthority;

    @Column(name = "REPUTATION_FEE")
    private String repudiationFee;

    @JoinColumn(name = "GENERATE_ALERTS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue generateAlerts;

    @Column(name = "PRINCIPAL_REF")
    private String principalRef;

    @JoinColumn(name = "EXPORTED_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue exported;

    @JoinColumn(name = "VAT_STATUS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue vatStatus;

    @Column(name = "OWNER")
    private String owner;

    @Composition
    @OneToMany(mappedBy = "principal")
    private List<MarketingNote> marketingNotes;

    @JoinTable(name = "PRINCIPAL_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "PRINCIPAL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Document> document;

    @Column(name = "WEB")
    private String web;

    @JoinColumn(name = "TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue type;

    @JoinColumn(name = "COLLECT_ACCESS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue collectAccess;

    @JoinTable(name = "PRINCIPAL_ADDRESS_LINK",
            joinColumns = @JoinColumn(name = "PRINCIPAL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Address> address;

    @Composition
    @OneToMany(mappedBy = "principal")
    private List<Contact> contact;

    @JoinColumn(name = "DEFAULT_NOMINAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EnumValue defaultNominal;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    public String getSor() {
        return sor;
    }

    public void setSor(String sor) {
        this.sor = sor;
    }

    public FileRef getLogo() {
        return logo;
    }

    public void setLogo(FileRef logo) {
        this.logo = logo;
    }

    public EnumValue getMandateRequired() {
        return mandateRequired;
    }

    public void setMandateRequired(EnumValue mandateRequired) {
        this.mandateRequired = mandateRequired;
    }

    public EnumValue getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(EnumValue invoiceType) {
        this.invoiceType = invoiceType;
    }

    public EnumValue getActive() {
        return active;
    }

    public void setActive(EnumValue active) {
        this.active = active;
    }

    public void setExported(EnumValue exported) {
        this.exported = exported;
    }

    public EnumValue getExported() {
        return exported;
    }

    public EnumValue getGenerateAlerts() {
        return generateAlerts;
    }

    public void setGenerateAlerts(EnumValue generateAlerts) {
        this.generateAlerts = generateAlerts;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public EnumValue getVatStatus() {
        return vatStatus;
    }

    public void setVatStatus(EnumValue vatStatus) {
        this.vatStatus = vatStatus;
    }

    public String getRepudiationFee() {
        return repudiationFee;
    }

    public void setRepudiationFee(String repudiationFee) {
        this.repudiationFee = repudiationFee;
    }

    public String getDelegatedAuthority() {
        return delegatedAuthority;
    }

    public void setDelegatedAuthority(String delegatedAuthority) {
        this.delegatedAuthority = delegatedAuthority;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String getPrincipalRef() {
        return principalRef;
    }

    public void setPrincipalRef(String principalRef) {
        this.principalRef = principalRef;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Address> getAddress() {
        return address;
    }

    public List<MarketingNote> getMarketingNotes() {
        return marketingNotes;
    }

    public void setMarketingNotes(List<MarketingNote> marketingNotes) {
        this.marketingNotes = marketingNotes;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    public EnumValue getDefaultNominal() {
        return defaultNominal;
    }

    public void setDefaultNominal(EnumValue defaultNominal) {
        this.defaultNominal = defaultNominal;
    }

    public EnumValue getCollectAccess() {
        return collectAccess;
    }

    public void setCollectAccess(EnumValue collectAccess) {
        this.collectAccess = collectAccess;
    }

    public EnumValue getType() {
        return type;
    }

    public void setType(EnumValue type) {
        this.type = type;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}