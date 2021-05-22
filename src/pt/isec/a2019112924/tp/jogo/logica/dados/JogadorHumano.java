package pt.isec.a2019112924.tp.jogo.logica.dados;

public class JogadorHumano extends Jogador{
    private int nrPecasEspeciais;
    private int nrCreditos;

    public JogadorHumano(String nome) {
        super(nome);
        nrPecasEspeciais = 0;
        nrCreditos = 0;
    }
}
