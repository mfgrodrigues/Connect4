package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class AguardaJogada extends EstadoAdapter {

    public AguardaJogada(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado jogaPeca(int coluna) {
        if(!jogo.colocaPeca(coluna)){
            return this;
        }
        if (jogo.avaliaVencedor()) {
            return new TerminaJogo(jogo);
        }
        jogo.trocaJogador();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado jogaPeca(){
        int coluna = jogo.sorteiaColuna();
        if(!jogo.colocaPeca(coluna)){
            return this;
        }
        if (jogo.avaliaVencedor()) {
            return new TerminaJogo(jogo);
        }
        jogo.trocaJogador();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado jogaPecaEspecial(int coluna) {
        jogo.colocaPecaEspecial(coluna);
        jogo.trocaJogador();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado escolheOpMiniJogo() {
        if(jogo.getJogadorAtual().getNrJogadas() == 4) {
            jogo.iniciaMiniJogo();
            if (jogo.getMiniJogoAtivo() == 1) {
                return new JogaMiniJogoC(jogo);
            }
            return new JogaMiniJogoP(jogo);
        }
        return this;
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AguardaJogada;
    }

}
