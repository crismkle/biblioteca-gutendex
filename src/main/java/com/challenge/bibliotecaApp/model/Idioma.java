package com.challenge.bibliotecaApp.model;

public enum Idioma {

    ESPANOL("es", "Español"),
    INGLES("en", "Inglés"),
    FRANCES("fr", "Francés"),
    PORTUGUES("pt", "Portugués");

    private String idiomaGutendex;
    private String idiomaPretty;

    Idioma(String idiomaGutendex, String idiomaPretty){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaPretty = idiomaPretty;
    }

    public static Idioma fromGutendex(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException(("Ningún idioma encontrado: " + text));
    }

    public static Idioma fromPretty(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaPretty.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }

    public String getString() {
        return idiomaGutendex;
    }

    public String getIdiomaPretty(){
        return idiomaPretty;
    }
}
