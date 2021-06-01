package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.Serializable;

public class MiniJogoC implements IMiniJogo, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MIN_RESPOSTAS_CERTAS = 5;
    private int numRespCertas;
    private int num1;
    private int num2;
    private char operador;
    private long tempo;
    private static long DURACAO_JOGO_MILLIS = 30000;

    public MiniJogoC() {
        numRespCertas = 0;
        tempo = System.currentTimeMillis();
        sorteiaPergunta();
    }

    private void sorteiaPergunta() {
        num1 = (int) (Math.random() * 20) + 10;
        num2 = (int) (Math.random() * 10) + 1;
        int rand = (int) (Math.random() * 100) % 4;
        String carateres = "x-+:";
        operador = carateres.charAt(rand);
    }

    @Override
    public String getPergunta() {
        return String.format("%d %c %d", num1, operador, num2);
    }

    @Override
    public boolean validaResposta(String resposta) {
        if (System.currentTimeMillis() - tempo < DURACAO_JOGO_MILLIS) {
            int res = Integer.parseInt(resposta);
            int n= numRespCertas;
            switch (operador) {
                case 'x':
                    if (res == num1 * num2) {
                        System.out.println("acertou *");
                        numRespCertas++;
                    }
                    break;
                case '-':
                    if (res == num1 - num2) {
                        System.out.println("acertou -");
                        numRespCertas++;
                    }
                    break;
                case '+':
                    if (res == num1 + num2) {
                        System.out.println("acertou +");
                        numRespCertas++;
                    }
                    break;
                case ':':
                    if (res == num1 / num2) {
                        System.out.println("acertou /");
                        numRespCertas++;
                    }
                    break;
            }
            sorteiaPergunta();
            return numRespCertas > n;
        }
        return false;
    }

    @Override
    public boolean getJogoTerminou() {
        return System.currentTimeMillis() - tempo >= DURACAO_JOGO_MILLIS;
    }

    @Override
    public boolean ganhou() {
        return numRespCertas >= MIN_RESPOSTAS_CERTAS;
    }
}
