package ues.occ.proyeccion.social.ws.app.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

public class CycleUtil {
        private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

        @BeforeMapping
        public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
            return (T) knownInstances.get( source );
        }

        @BeforeMapping
        public void storeMappedInstance(Object source, @MappingTarget Object target) {
            knownInstances.put(source, target);
        }
}
