package com.pubmania.professionista.StringAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayProdotto {

    public String id;
    public List<String> ingredienti;
    public String nome;
    public String prezzo;
    public String giorno;
    public String mese;
    public String ora;
    public String categoria;
    public String email;
    public List<String> foto;


    public ArrayProdotto(){

    }

    public ArrayProdotto(String email, String categoria, String id, List<String> ingredienti, String nome, String prezzo, String giorno, String mese, String ora, List<String> foto) {
        this.id = id;
        this.email =email;
        this.ingredienti = ingredienti;
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.giorno = giorno;
        this.mese = mese;
        this.ora = ora;
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
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

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String>  foto) {
        this.foto = foto;
    }
}
