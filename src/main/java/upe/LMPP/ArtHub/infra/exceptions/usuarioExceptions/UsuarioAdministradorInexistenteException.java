package upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions;

public class UsuarioAdministradorInexistenteException extends RuntimeException {
    public UsuarioAdministradorInexistenteException() {
        super("Não encontramos este admim nos nossos registros! Tente Novamente!\n");
    }
}
