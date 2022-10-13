package com.pubmania.professionista.StringAdapter;

public class StringNotifiche {
    String id,emailCliente,nomecognomeCliente,emailPub,ora,fotoProfilo,categoria,idPost,visualizzato;


    public StringNotifiche(){

    }

    public StringNotifiche(String id, String emailCliente, String nomecognomeCliente,
                           String emailPub, String ora, String fotoProfilo, String categoria,String idPost,String visualizzato) {
        this.id = id;
        this.visualizzato = visualizzato;
        this.idPost = idPost;
        this.emailCliente = emailCliente;
        this.nomecognomeCliente = nomecognomeCliente;
        this.emailPub = emailPub;
        this.ora = ora;
        this.fotoProfilo = fotoProfilo;
        this.categoria = categoria;
    }

    public String getVisualizzato() {
        return visualizzato;
    }

    public void setVisualizzato(String visualizzato) {
        this.visualizzato = visualizzato;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNomecognomeCliente() {
        return nomecognomeCliente;
    }

    public void setNomecognomeCliente(String nomecognomeCliente) {
        this.nomecognomeCliente = nomecognomeCliente;
    }

    public String getEmailPub() {
        return emailPub;
    }

    public void setEmailPub(String emailPub) {
        this.emailPub = emailPub;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
