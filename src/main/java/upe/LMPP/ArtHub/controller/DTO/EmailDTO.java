package upe.LMPP.ArtHub.controller.DTO;

public class EmailDTO {
    private String destino;
    private String assunto;
    private String corpo;

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getCorpo() { return corpo; }
    public void setCorpo(String corpo) { this.corpo = corpo; }
}
