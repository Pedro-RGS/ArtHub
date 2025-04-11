package upe.LMPP.ArtHub.infra.exceptions.perfilExceptions;

public class PerfilInexistenteException extends RuntimeException {
    public PerfilInexistenteException() {
        super("Perfil não encontrado");
    }
}
