package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class AguardaJogada extends EstadoAdapter {

    public AguardaJogada(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado jogaPeca(int coluna) {
        jogo.addLog(jogo.getJogadorAtual().getNome() + ": Joga peca na coluna " + (coluna + 1));
        if(!jogo.colocaPeca(coluna)){
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Coluna completa");
            return this;
        }
        if (jogo.avaliaVencedor()) {
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Vence o jogo");
            return new TerminaJogo(jogo);
        }
        jogo.trocaJogador();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado jogaPeca(){
        int coluna = jogo.sorteiaColuna();
        jogo.addLog(jogo.getJogadorAtual().getNome() + ": Joga peca na coluna " + (coluna + 1));
        if(!jogo.colocaPeca(coluna)){
            return jogaPeca();
        }
        if (jogo.avaliaVencedor()) {
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Vence o jogo");
            return new TerminaJogo(jogo);
        }
        jogo.trocaJogador();

        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado jogaPecaEspecial(int coluna) {
        jogo.addLog(jogo.getJogadorAtual().getNome() + ": Joga peca especial coluna " + (coluna + 1));
        if(jogo.getJogadorAtual() instanceof JogadorHumano) {
            if(((JogadorHumano)jogo.getJogadorAtual()).getNrPecasEspeciais() == 0) {
                jogo.addLog(jogo.getJogadorAtual().getNome() + ": Nr de pecas especiais insuficientes");
                return this;
            }
        }
        jogo.colocaPecaEspecial(coluna);
        ((JogadorHumano)jogo.getJogadorAtual()).decrementaPecasEspeciais();
        jogo.trocaJogador();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado escolheOpMiniJogo() {
        if(jogo.getJogadorAtual().getNrJogadas() % 4 == 0) {
            jogo.iniciaMiniJogo();
            if (jogo.getJogadorAtual().getMiniJogoAtivo() == 1) {
                jogo.addLog(jogo.getJogadorAtual().getNome() + ": Joga Mini Jogo Calculo");
                return new JogaMiniJogoC(jogo);
            }
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Joga Mini Jogo Palavras");
            return new JogaMiniJogoP(jogo);
        }
        else{
            jogo.addLog(jogo.getJogadorAtual().getNome() + ": Nr de jogadas insuficientes");
        }
        return this;
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AguardaJogada;
    }

}
