package pt.isec.a2019112924.tp.jogo.logica.dados;

public class JogadorVirtual extends Jogador{
    private static final long serialVersionUID = 1L;
    private static int contador = 0;

    private static int geraId(){ return ++contador; }

    public JogadorVirtual() {
        super("Virtual" + geraId());
    }

}
