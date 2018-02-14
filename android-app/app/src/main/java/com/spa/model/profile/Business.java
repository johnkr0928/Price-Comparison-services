
package com.spa.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Business {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_type")
    @Expose
    private Integer businessType;
    @SerializedName("business_status")
    @Expose
    private Object businessStatus;
    @SerializedName("business_dba")
    @Expose
    private String businessDba;
    @SerializedName("federal_number")
    @Expose
    private String federalNumber;
    @SerializedName("db_number")
    @Expose
    private String dbNumber;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("ssn")
    @Expose
    private String ssn;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("manager_name")
    @Expose
    private String managerName;
    @SerializedName("manager_contact")
    @Expose
    private String managerContact;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName The business_name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return The businessType
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType The business_type
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * @return The businessStatus
     */
    public Object getBusinessStatus() {
        return businessStatus;
    }

    /**
     * @param businessStatus The business_status
     */
    public void setBusinessStatus(Object businessStatus) {
        this.businessStatus = businessStatus;
    }

    /**
     * @return The businessDba
     */
    public Object getBusinessDba() {
        return businessDba;
    }

    /**
     * @param businessDba The business_dba
     */
    public void setBusinessDba(String businessDba) {
        this.businessDba = businessDba;
    }

    /**
     * @return The federalNumber
     */
    public String getFederalNumber() {
        return federalNumber;
    }

    /**
     * @param federalNumber The federal_number
     */
    public void setFederalNumber(String federalNumber) {
        this.federalNumber = federalNumber;
    }

    /**
     * @return The dbNumber
     */
    public Object getDbNumber() {
        return dbNumber;
    }

    /**
     * @param dbNumber The db_number
     */
    public void setDbNumber(String dbNumber) {
        this.dbNumber = dbNumber;
    }

    /**
     * @return The dob
     */
    public Object getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return The ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * @param ssn The ssn
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * @return The contactNumber
     */
    public Object getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber The contact_number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return The status
     */
    public Object getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The managerName
     */
    public Object getManagerName() {
        return managerName;
    }

    /**
     * @param managerName The manager_name
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    /**
     * @return The managerContact
     */
    public Object getManagerContact() {
        return managerContact;
    }

    /**
     * @param managerContact The manager_contact
     */
    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

}
