package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class TerminaJogo extends EstadoAdapter{
    public TerminaJogo(Jogo jogo) {super(jogo);}

    @Override
    public IEstado novaTentativa() {
        return new AguardaInicio(jogo);
    }

    public Situacao getSituacaoAtual() {
        return Situacao.TerminaJogo;
    }
}
