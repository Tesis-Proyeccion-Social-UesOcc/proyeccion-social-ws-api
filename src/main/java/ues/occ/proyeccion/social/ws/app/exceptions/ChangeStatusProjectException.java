package ues.occ.proyeccion.social.ws.app.exceptions;

public class ChangeStatusProjectException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ChangeStatusProjectException() {
    }

    public ChangeStatusProjectException(String message) {
        super(message);
    }

    public ChangeStatusProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChangeStatusProjectException(Throwable cause) {
        super(cause);
    }
}
