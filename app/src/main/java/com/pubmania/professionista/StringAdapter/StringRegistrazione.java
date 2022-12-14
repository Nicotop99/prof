package com.pubmania.professionista.StringAdapter;

import java.util.List;

public class StringRegistrazione {

    public String email;
    public String nome;
    public String cognome;
    public String numero;
    public String profiloPubblico,nascondiPost,nascondiFollower,nascondiCoupon;
    public String id;
    public String partitaIva;
    public String follower;
    public String fotoProfilo;
    public String lati,longi,viaLocale,nomeLocale;
    public List<String> token;

    public StringRegistrazione(){

    }

    public StringRegistrazione(List<String> token, String partitaIva,String email, String nome, String cognome, String numero, String id,String follower, String fotoProfilo,
                               String profiloPubblico, String nascondiFollower, String nascondiPost, String nascondiCoupon, String lati, String longi, String viaLocale,
                               String nomeLocale
    ) {
        this.nomeLocale = nomeLocale;
        this.lati = lati;
        this.viaLocale = viaLocale;
        this.longi = longi;
        this.nascondiCoupon = nascondiCoupon;

        this.profiloPubblico = profiloPubblico;
        this.nascondiFollower = nascondiFollower;
        this.nascondiPost = nascondiPost;

        this.email = email;
        this.token = token;
        this.nome = nome;
        this.cognome = cognome;
        this.numero = numero;
        this.id = id;
        this.partitaIva = partitaIva;
        this.follower = follower;
        this.fotoProfilo = fotoProfilo;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getViaLocale() {
        return viaLocale;
    }

    public void setViaLocale(String viaLocale) {
        this.viaLocale = viaLocale;
    }

    public List<String> getToken() {
        return token;
    }

    public String getNascondiCoupon() {
        return nascondiCoupon;
    }

    public void setNascondiCoupon(String nascondiCoupon) {
        this.nascondiCoupon = nascondiCoupon;
    }

    public String getProfiloPubblico() {
        return profiloPubblico;
    }

    public void setProfiloPubblico(String profiloPubblico) {
        this.profiloPubblico = profiloPubblico;
    }

    public String getNascondiPost() {
        return nascondiPost;
    }

    public void setNascondiPost(String nascondiPost) {
        this.nascondiPost = nascondiPost;
    }

    public String getNascondiFollower() {
        return nascondiFollower;
    }

    public void setNascondiFollower(String nascondiFollower) {
        this.nascondiFollower = nascondiFollower;
    }

    public void setToken(List<String> token) {
        this.token = token;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
