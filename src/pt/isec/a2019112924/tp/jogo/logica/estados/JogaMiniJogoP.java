package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class JogaMiniJogoP extends EstadoAdapter {

    public JogaMiniJogoP(Jogo jogo){
        super(jogo);
    }

    @Override
    public IEstado digitaPalavras(String resposta){
        if(jogo.getMiniJogo().validaResposta(resposta)) {
            if(jogo.getJogadorAtual() instanceof JogadorHumano) {
                ((JogadorHumano)jogo.getJogadorAtual()).incrementaPecasEspeciais();
                jogo.addLog(jogo.getJogadorAtual().getNome() + ": Venceu Mini Jogo Palavras");
            }
        }
        else{
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Perdeu Mini Jogo Palavras");
            jogo.trocaJogador();
        }
        return new AguardaJogada(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() { return Situacao.JogaMiniJogoP;}
}
