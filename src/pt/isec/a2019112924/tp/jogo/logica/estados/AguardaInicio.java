package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class AguardaInicio extends EstadoAdapter{

    public AguardaInicio(Jogo jogo){super(jogo);}

    @Override
    public IEstado adicionaJogador(String nome) {
        jogo.adicionaJogador(nome);
        return new AguardaInicio(jogo);
    }

    @Override
    public IEstado iniciaJogo() {
        jogo.inicia();
        return new AguardaJogada(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AguardaInicio;
    }
}
