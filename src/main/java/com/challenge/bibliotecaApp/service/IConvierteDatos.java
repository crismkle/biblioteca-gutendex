package com.challenge.bibliotecaApp.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
