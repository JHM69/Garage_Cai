package com.bitsnbites.garagecai.model;

public class Leaser extends User{
    public Leaser(String uuid, String email, Boolean emailVerified, String number, String photoUrl, String birthCertificate, Boolean birthCertificateVerified, String nid, String nidVerified, long timestamp) {
        super(uuid, email, emailVerified, number, photoUrl, birthCertificate, birthCertificateVerified, nid, nidVerified, timestamp);
    }

    public Leaser() {
    }
}
