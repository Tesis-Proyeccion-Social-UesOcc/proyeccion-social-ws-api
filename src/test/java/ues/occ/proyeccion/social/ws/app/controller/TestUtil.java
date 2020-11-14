package ues.occ.proyeccion.social.ws.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ues.occ.proyeccion.social.ws.app.model.ProyectoDTO;

public class TestUtil {
    public static String toJson(ProyectoDTO dto) {
        try {
            return new ObjectMapper().writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
