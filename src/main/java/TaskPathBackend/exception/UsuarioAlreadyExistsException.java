package TaskPathBackend.exception;

public class UsuarioAlreadyExistsException extends RuntimeException {
    public UsuarioAlreadyExistsException(String identificacion) {
        super("El usuario con identificación " + identificacion + " ya está registrado.");
    }
}