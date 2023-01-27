package com.bitsnbites.garagecai.model;

import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;

public class User implements Serializable {
    String uuid;
    String email;
    String name;
    Boolean emailVerified;
    String number;
    String photoUrl;
    String birthCertificate;
    Boolean birthCertificateVerified;
    String nid;
    String nidVerified;
    String type = "none";
    long timestamp;

    public User(String uuid, String email, String name, Boolean emailVerified, String number, String photoUrl, String birthCertificate, Boolean birthCertificateVerified, String nid, String nidVerified, String type, long timestamp) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.emailVerified = emailVerified;
        this.number = number;
        this.photoUrl = photoUrl;
        this.birthCertificate = birthCertificate;
        this.birthCertificateVerified = birthCertificateVerified;
        this.nid = nid;
        this.nidVerified = nidVerified;
        this.type = type;
        this.timestamp = timestamp;
    }

    public User() {
        this.type = "none";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
