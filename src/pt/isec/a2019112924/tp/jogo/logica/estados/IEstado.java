package pt.isec.a2019112924.tp.jogo.logica.estados;

import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public interface IEstado {
    IEstado iniciaJogo();
    IEstado jogaPeca(int coluna);
    IEstado jogaPeca();
    IEstado jogaPecaEspecial(int coluna);
    IEstado escolheOpMiniJogo();
    IEstado resolveCalculo(String resposta);
    IEstado digitaPalavras(String resposta);
    IEstado novaTentativa();

    Situacao getSituacaoAtual();
}
