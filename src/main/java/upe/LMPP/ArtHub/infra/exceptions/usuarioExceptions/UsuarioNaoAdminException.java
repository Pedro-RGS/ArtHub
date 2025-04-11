package upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions;

public class UsuarioNaoAdminException extends RuntimeException {
  public UsuarioNaoAdminException() {
    super("Usuário não autorizado para promover outros usuários.");
  }
}
