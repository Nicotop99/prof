package com.pubmania.professionista.StringAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayPost {

    public ArrayPost(){

    }
    public String id,descrizione,like,pinnato,categoria,email,fotoProfilo,nomeLocale;
    public List<String> foto;


    public ArrayPost(String id, String descrizione, String like, String pinnato, List<String> foto,String categoria
    ,String email,String fotoProfilo,String nomeLocale) {
        this.email = email;
        this.fotoProfilo = fotoProfilo;
        this.nomeLocale = nomeLocale;
        this.id = id;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.like = like;
        this.pinnato = pinnato;
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPinnato() {
        return pinnato;
    }

    public void setPinnato(String pinnato) {
        this.pinnato = pinnato;
    }

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }
}
