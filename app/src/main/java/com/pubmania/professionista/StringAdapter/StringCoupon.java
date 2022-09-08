package com.pubmania.professionista.StringAdapter;

public class StringCoupon {
    public StringCoupon(){

    }

    public String id,titolo,email,tipo,giorno,mese,ora,prezzo,quanteVolte,chi,qualeProdotto,volteUtilizzate,categoria,token;

    public StringCoupon(String token, String categoria, String id, String titolo, String email, String tipo, String giorno, String mese, String ora, String prezzo, String quanteVolte, String chi, String qualeProdotto, String volteUtilizzate) {
        this.id = id;
        this.token = token;
        this.titolo = titolo;
        this.email = email;
        this.tipo = tipo;
        this.giorno = giorno;
        this.mese = mese;
        this.ora = ora;
        this.prezzo = prezzo;
        this.quanteVolte = quanteVolte;
        this.volteUtilizzate = volteUtilizzate;
        this.chi = chi;
        this.categoria = categoria;
        this.qualeProdotto = qualeProdotto;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getVolteUtilizzate() {
        return volteUtilizzate;
    }

    public void setVolteUtilizzate(String volteUtilizzate) {
        this.volteUtilizzate = volteUtilizzate;
    }

    public String getQualeProdotto() {
        return qualeProdotto;
    }

    public void setQualeProdotto(String qualeProdotto) {
        this.qualeProdotto = qualeProdotto;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getQuanteVolte() {
        return quanteVolte;
    }

    public void setQuanteVolte(String quanteVolte) {
        this.quanteVolte = quanteVolte;
    }

    public String getChi() {
        return chi;
    }

    public void setChi(String chi) {
        this.chi = chi;
    }
}
