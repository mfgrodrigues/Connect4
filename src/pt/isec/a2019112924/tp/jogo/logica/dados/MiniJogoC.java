package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.util.Random;

public class MiniJogoC implements IMiniJogo{
    int respCerta;
    int num1;
    int num2;
    char operador;

    public MiniJogoC(){
        respCerta = 0;
    }

    public void sorteiaCalculo(){
        int num1 = (int)(Math.random() * 50) + 1;
        int num2 = (int)(Math.random() * 50) + 1;
        int rand = (int)(Math.random() * 4) + 1;
        String carateres = "x-+:";
        operador = carateres.charAt(rand);

    }

    public int getNum1(){ return num1;}

    public int getNum2(){ return num2;}

    public char getOperador(){ return operador;}

    @Override
    public String getPergunta() {
        return null;
    }

    @Override
    public boolean validaResposta(String resposta) {
        return false;
    }
}
