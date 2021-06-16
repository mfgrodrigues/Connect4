package pt.isec.a2019112924.tp.jogo.logica.dados;

public class JogadorHumano extends Jogador{
    private static final long serialVersionUID = 1L;
    private int nrPecasEspeciais;
    private int nrCreditos;

    public JogadorHumano(String nome) {
        super(nome);
        nrPecasEspeciais = 0;
        nrCreditos = 5;
    }

    public int getNrCreditos() {
        return nrCreditos;
    }

    public int getNrPecasEspeciais() {
        return nrPecasEspeciais;
    }

    public void setNrCreditos(int nrCreditos) {
        this.nrCreditos = nrCreditos;
    }

    public void incrementaPecasEspeciais() { nrPecasEspeciais++; }

    public void decrementaPecasEspeciais() { nrPecasEspeciais--; }

    public String toString() {
        return super.toString() + " Nr.Creditos: " + nrCreditos + " Nr. Pecas Especiais: " + nrPecasEspeciais;
    }


}
