package pt.isec.a2019112924.tp.jogo.logica.dados;

public class MiniJogoC implements IMiniJogo {
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

    @Override
    public void sorteiaPergunta() {
        num1 = (int) (Math.random() * 30) + 10;
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
            int n = numRespCertas;
            switch (operador) {
                case 'x':
                    if (res == num1 * num2) {
                        numRespCertas++;
                    }
                case '-':
                    if (res == num1 - num2) {
                        numRespCertas++;
                    }
                case '+':
                    if (res == num1 + num2) {
                        numRespCertas++;
                    }
                case ':':
                    if (res == num1 / num2) {
                        numRespCertas++;
                    }
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
}
