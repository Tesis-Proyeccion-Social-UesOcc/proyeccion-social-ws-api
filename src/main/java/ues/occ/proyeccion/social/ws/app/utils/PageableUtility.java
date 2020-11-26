package ues.occ.proyeccion.social.ws.app.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ues.occ.proyeccion.social.ws.app.dao.Proyecto;

import java.util.Collections;
import java.util.List;

public class PageableUtility<T> {
    protected Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }

    protected List<T> getData(Page<T> entityPage){
        if(entityPage.hasContent()){
            return entityPage.getContent();
        }
        else{
            return Collections.emptyList();
        }
    }
}
