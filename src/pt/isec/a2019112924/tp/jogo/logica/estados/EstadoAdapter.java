package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;

import java.io.Serializable;

public abstract class EstadoAdapter implements IEstado, Serializable {

    protected Jogo jogo;

    protected EstadoAdapter(Jogo jogo){this.jogo = jogo;}

    @Override
    public IEstado iniciaJogo(){return this;}

    @Override
    public IEstado jogaPeca(int coluna){return this;}

    @Override
    public IEstado jogaPeca(){return this;}

    @Override
    public IEstado jogaPecaEspecial(int coluna){return this;}

    @Override
    public IEstado escolheOpMiniJogo(){return this;}

    @Override
    public IEstado resolveCalculo(String resposta){return this;}

    @Override
    public IEstado digitaPalavras(String resposta){return this;}

    @Override
    public IEstado novaTentativa(){return this;}
}
