package com.bitsnbites.garagecai.model;

public class User {
    String uuid;
    String email;
    Boolean emailVerified;
    String number;
    String photoUrl;
    String birthCertificate;
    Boolean birthCertificateVerified;

    String nid;
    String nidVerified;
    long timestamp;

    public User(String uuid, String email, Boolean emailVerified, String number, String photoUrl, String birthCertificate, Boolean birthCertificateVerified, String nid, String nidVerified, long timestamp) {
        this.uuid = uuid;
        this.email = email;
        this.emailVerified = emailVerified;
        this.number = number;
        this.photoUrl = photoUrl;
        this.birthCertificate = birthCertificate;
        this.birthCertificateVerified = birthCertificateVerified;
        this.nid = nid;
        this.nidVerified = nidVerified;
        this.timestamp = timestamp;
    }

    public User() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public Boolean getBirthCertificateVerified() {
        return birthCertificateVerified;
    }

    public void setBirthCertificateVerified(Boolean birthCertificateVerified) {
        this.birthCertificateVerified = birthCertificateVerified;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNidVerified() {
        return nidVerified;
    }

    public void setNidVerified(String nidVerified) {
        this.nidVerified = nidVerified;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
