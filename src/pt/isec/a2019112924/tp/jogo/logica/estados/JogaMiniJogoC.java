package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class JogaMiniJogoC extends EstadoAdapter {

    public JogaMiniJogoC(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado resolveCalculo(String resposta) {
        jogo.getMiniJogo().validaResposta(resposta);
        if (!jogo.getMiniJogo().getJogoTerminou()) {
            return this;
        }
        if (jogo.getMiniJogo().ganhou()) {
            jogo.getJogadorAtual().incrementaPecasEspeciais();
        }
        return new AguardaJogada(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.JogaMiniJogoC;
    }
}
