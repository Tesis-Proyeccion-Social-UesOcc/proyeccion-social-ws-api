package ues.occ.proyeccion.social.ws.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ues.occ.proyeccion.social.ws.app.utils.PageDtoWrapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private int totalPages;
    private boolean first;
    private long totalElements;
    private int size;
    private boolean last;

    public PageDTO(PageDtoWrapper<? extends Serializable, T> pageDtoWrapper){
        setContent(pageDtoWrapper.getContent());
        setTotalPages(pageDtoWrapper.getOriginalPage().getTotalPages());
        setFirst(pageDtoWrapper.getOriginalPage().isFirst());
        setTotalElements(pageDtoWrapper.getOriginalPage().getTotalElements());
        setSize(pageDtoWrapper.getOriginalPage().getSize());
        setLast(pageDtoWrapper.getOriginalPage().isLast());
    }

    public PageDTO(){
        setContent(Collections.emptyList());
        setTotalPages(0);
        setFirst(false);
        setTotalElements(0);
        setSize(0);
        setLast(false);
    }

}
