package ues.occ.proyeccion.social.ws.app.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageDtoWrapper<T1, T2> {
    private Page<T1> originalPage;
    private List<T2> content;
}
