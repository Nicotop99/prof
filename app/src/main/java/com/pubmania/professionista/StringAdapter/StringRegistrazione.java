package com.pubmania.professionista.StringAdapter;

public class StringRegistrazione {

    public String email;
    public String nome;
    public String cognome;
    public String numero;
    public String id;
    public String partitaIva;
    public String follower;
    public String fotoProfilo;


    public StringRegistrazione(){

    }

    public StringRegistrazione(String partitaIva,String email, String nome, String cognome, String numero, String id,String follower, String fotoProfilo) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.numero = numero;
        this.id = id;
        this.partitaIva = partitaIva;
        this.follower = follower;
        this.fotoProfilo = fotoProfilo;
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
}
