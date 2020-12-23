package ues.occ.proyeccion.social.ws.app.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class PageableResource<R, T> {
    protected Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }

    protected abstract Function<R, T> getMapperFunction();

    protected List<T> getData(Page<R> entityPage){
        if(entityPage.hasContent()){
            return entityPage.getContent().stream()
                    .map(this.getMapperFunction())
                    .collect(Collectors.toList());
        }
        else{
            return Collections.emptyList();
        }
    }
}
