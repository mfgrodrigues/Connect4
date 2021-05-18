package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class JogaMiniJogoP extends EstadoAdapter {

    public JogaMiniJogoP(Jogo jogo){
        super(jogo);
    }

    @Override
    public IEstado digitaPalavras(String resposta){
        jogo.getMiniJogo().validaResposta(resposta);
        return new AguardaJogada(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() { return Situacao.JogaMiniJogoP;}
}
