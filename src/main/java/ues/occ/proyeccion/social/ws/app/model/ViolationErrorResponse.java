package ues.occ.proyeccion.social.ws.app.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ViolationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}
