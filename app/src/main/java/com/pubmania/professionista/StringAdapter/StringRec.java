package com.pubmania.professionista.StringAdapter;

import java.util.List;

public class StringRec {
    public String id,emailpub,emailPubblico,desc,titolo,valStruttura,valProdotti,valBagni,valQuantitaGente,valRagazze,valRagazzi,
            valPrezzi,valDivertimento,valServizio,ora,media;
    public List<String> arrayList;
    public StringRec(){

    }

    public StringRec(String media, String id, String emailpub, String emailPubblico, String desc, String titolo, String valStruttura,
                     String valProdotti, String valBagni, String valQuantitaGente, String valRagazze, String valRagazzi,
                     String valPrezzi, String valDivertimento, String valServizio, String ora, List<String> arrayList) {
        this.id = id;
        this.media = media;
        this.emailpub = emailpub;
        this.emailPubblico = emailPubblico;
        this.desc = desc;
        this.titolo = titolo;
        this.valStruttura = valStruttura;
        this.valProdotti = valProdotti;
        this.valBagni = valBagni;
        this.valQuantitaGente = valQuantitaGente;
        this.valRagazze = valRagazze;
        this.valRagazzi = valRagazzi;
        this.valPrezzi = valPrezzi;
        this.valDivertimento = valDivertimento;
        this.valServizio = valServizio;
        this.ora = ora;
        this.arrayList = arrayList;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailpub() {
        return emailpub;
    }

    public void setEmailpub(String emailpub) {
        this.emailpub = emailpub;
    }

    public String getEmailPubblico() {
        return emailPubblico;
    }

    public void setEmailPubblico(String emailPubblico) {
        this.emailPubblico = emailPubblico;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getValStruttura() {
        return valStruttura;
    }

    public void setValStruttura(String valStruttura) {
        this.valStruttura = valStruttura;
    }

    public String getValProdotti() {
        return valProdotti;
    }

    public void setValProdotti(String valProdotti) {
        this.valProdotti = valProdotti;
    }

    public String getValBagni() {
        return valBagni;
    }

    public void setValBagni(String valBagni) {
        this.valBagni = valBagni;
    }

    public String getValQuantitaGente() {
        return valQuantitaGente;
    }

    public void setValQuantitaGente(String valQuantitaGente) {
        this.valQuantitaGente = valQuantitaGente;
    }

    public String getValRagazze() {
        return valRagazze;
    }

    public void setValRagazze(String valRagazze) {
        this.valRagazze = valRagazze;
    }

    public String getValRagazzi() {
        return valRagazzi;
    }

    public void setValRagazzi(String valRagazzi) {
        this.valRagazzi = valRagazzi;
    }

    public String getValPrezzi() {
        return valPrezzi;
    }

    public void setValPrezzi(String valPrezzi) {
        this.valPrezzi = valPrezzi;
    }

    public String getValDivertimento() {
        return valDivertimento;
    }

    public void setValDivertimento(String valDivertimento) {
        this.valDivertimento = valDivertimento;
    }

    public String getValServizio() {
        return valServizio;
    }

    public void setValServizio(String valServizio) {
        this.valServizio = valServizio;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }
}
