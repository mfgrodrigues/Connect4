package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class JogaMiniJogoC extends EstadoAdapter {

    public JogaMiniJogoC(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado resolveCalculo(String resposta) {
        if(jogo.getMiniJogo().validaResposta(resposta)){
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Acertou");
        }
        else{
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Nao acertou");
        }

        if (!jogo.getMiniJogo().getJogoTerminou()) {
            return this;
        }
        if (jogo.getMiniJogo().ganhou()) {
            if(jogo.getJogadorAtual() instanceof JogadorHumano) {
                ((JogadorHumano)jogo.getJogadorAtual()).incrementaPecasEspeciais();
                jogo.addLog(jogo.getJogadorAtual().getNome() + ": Venceu Mini Jogo Calculo");
            }
        }else{
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Perdeu Mini Jogo Calculo");
            jogo.trocaJogador();
        }
        return new AguardaJogada(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.JogaMiniJogoC;
    }
}

