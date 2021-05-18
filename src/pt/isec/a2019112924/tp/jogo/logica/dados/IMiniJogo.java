package pt.isec.a2019112924.tp.jogo.logica.dados;

public interface IMiniJogo {
    String getPergunta();
    void sorteiaPergunta();
    boolean validaResposta(String resposta);
    boolean getJogoTerminou();
}
