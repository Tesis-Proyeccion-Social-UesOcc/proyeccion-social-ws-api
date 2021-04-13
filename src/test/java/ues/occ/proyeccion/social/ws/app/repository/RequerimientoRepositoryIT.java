package ues.occ.proyeccion.social.ws.app.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import ues.occ.proyeccion.social.ws.app.dao.Documento;
import ues.occ.proyeccion.social.ws.app.dao.Requerimiento;
import ues.occ.proyeccion.social.ws.app.repository.projections.RequerimientoIdView;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
class RequerimientoRepositoryIT {

    @Autowired
    RequerimientoRepository requerimientoRepository;
    @Autowired
    DocumentoRepository documentoRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {
        var doc = new Documento("doc", "", LocalDateTime.now());
        documentoRepository.save(doc);

        var requerimiento1 = new Requerimiento(1, true, 2, doc);
        var requerimiento2 = new Requerimiento(2, true, 2, doc);
        var requerimiento3 = new Requerimiento(3, true, 2, doc);
        requerimientoRepository.saveAll(List.of(requerimiento1, requerimiento2, requerimiento3));

        assertEquals(3, requerimientoRepository.count());

        var result = requerimientoRepository.findAllProjectedBy();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertArrayEquals(new Integer[]{1, 2, 3}, result.stream().map(RequerimientoIdView::getId).toArray());
    }
}