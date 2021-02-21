package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import ues.occ.proyeccion.social.ws.app.model.EstudianteDTO;
import ues.occ.proyeccion.social.ws.app.model.ProyectoCreationDTO.ProyectoDTO;

import java.util.IdentityHashMap;
import java.util.Map;

public class CycleUtil <T> {
        private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

        @BeforeMapping
        public <T> EstudianteDTO getMappedInstance(Object source, @TargetType Class<T> targetType) {
            return  (EstudianteDTO) knownInstances.get( source );
        }

        @BeforeMapping
        public void storeMappedInstance(Object source, @MappingTarget Object target) {
            knownInstances.put(source, target);
        }
}
