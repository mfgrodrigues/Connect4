package pt.isec.a2019112924.tp.jogo.logica.dados;

public interface IMiniJogo {
    String getPergunta();
    boolean validaResposta(String resposta);
    boolean getJogoTerminou();
    boolean ganhou();
}
