package pt.isec.a2019112924.tp.jogo.iu.texto;

import pt.isec.a2019112924.tp.GestorMaqEstados;
import pt.isec.a2019112924.tp.MaquinaEstados;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.estados.AguardaJogada;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.util.Scanner;

public class IU {
    private GestorMaqEstados maquinaEstados;
    private boolean sair = false;
    private Scanner sc = new Scanner(System.in);

    public IU(GestorMaqEstados maquinaEstados){this.maquinaEstados = maquinaEstados;}

    public void corre() {

        while (!sair) {
            Situacao situacao = maquinaEstados.getSituacaoAtual();
            System.out.println("\n[" + situacao + "]\n");
            switch (situacao) {
                case AguardaInicio -> iuAguardaInicio();
                case AguardaJogada -> iuAguardaJogada();
                case JogaMiniJogoC -> iuMiniJogoC();
                case JogaMiniJogoP -> iuMiniJogoP();
                case TerminaJogo -> iuTerminaJogo();
            }
        }
    }

    private void iuAguardaInicio(){
        System.out.println("""
                --+---+---+---+---+--
                MENU:                
                1 - Novo Jogo
                2 - Historico Jogos 

                0 - Sair
                --+---+---+---+---+--
                Escolha uma opcao:""");

        switch(leInteiro()){
            case 1:
                System.out.println("""
                --+---+---+---+---+--
                1 - Um Jogador
                2 - Dois Jogadores
                3 - Jogo Virtual

                0 - Sair
                --+---+---+---+---+--
                Escolha uma opcao:""");
                int opcao = leInteiro();

                if(opcao == 1){
                    System.out.println("Digite o seu username: ");
                    String nome = sc.nextLine();
                    maquinaEstados.adicionaJogador(nome);
                    maquinaEstados.iniciaJogo();
                    //devo escrever mensagem de erro caso o jogador nao seja adicionado adicionaJogador(metodo bool)
                }
                else if (opcao == 2){
                    for(int i = 0; i < 2; i++) {
                        System.out.println("Jogador " + (i + 1) + "\nDigite o seu username: ");
                        String nome = sc.nextLine();
                        maquinaEstados.adicionaJogador(nome);
                    }
                        maquinaEstados.iniciaJogo();
                }
                else if(opcao == 3){
                    maquinaEstados.iniciaJogo();
                }else if(opcao == 0){
                    sair = true;
                }
                break;
            case 2:
                break;
            case 0: sair = true;
                break;

        }
    }

    private void iuAguardaJogada(){
        int jog = 0;
        for(Jogador jogador : maquinaEstados.getJogadores()){
            System.out.println("Jogador" + (jog + 1) + " -> " + jogador.toString());
            jog++;
        }
        System.out.println();

        mostraTabuleiro();
        System.out.println("\n--+---+---+---+---+--");
        System.out.println("JOGA " + (maquinaEstados.getJogadorAtual().getNome()).toUpperCase() + "\n");
        if(maquinaEstados.getJogadorAtual().getNome().contains("Virtual")) {
            int coluna = maquinaEstados.sorteiaColuna();
            System.out.println("Coloco " + maquinaEstados.getJogadorAtual().getPeca() + " na coluna " + coluna);
            System.out.println("--+---+---+---+---+--");
            maquinaEstados.jogaPeca(coluna);

        }else{
            System.out.println("""
                    1 - Jogar Peca
                    2 - Jogar Peca Especial
                    3 - Voltar Jogada Atras 
                    4 - Jogar Mini-Jogo
        
                    0 - Sair
                    --+---+---+---+---+--
                    Escolha uma opcao:""");
            switch (leInteiro()) {
                case 1:
                    System.out.println("Jogar peca na coluna:");
                    int coluna = leInteiro();
                    maquinaEstados.jogaPeca(coluna);
                    break;
                case 2:
                    System.out.println("Jogar peca especial na coluna:");
                    coluna = leInteiro();
                    maquinaEstados.jogaPecaEspecial(coluna);
                    break;
                case 3:
                    maquinaEstados.voltarAtras();
                    break;
                case 4:
                    // TODO disponibilizar esta opção após 4 jogadas
                    maquinaEstados.escolheOpMiniJogo();
                    break;
                case 0:
                    sair = true;
                    break;
        }
        }
    }

    private void iuMiniJogoC(){
        System.out.println(maquinaEstados.getMiniJogo().getPergunta());
        System.out.println("Resultado:");
        String resposta = String.valueOf(leInteiro());
        maquinaEstados.resolveCalculo(resposta);
    }

    private void iuMiniJogoP(){
        System.out.println(maquinaEstados.getMiniJogo().getPergunta());
        System.out.println("Digite as palavras apresentadas no menor tempo possível. Pressione a tecla [enter] quando terminar.");
        String resposta = sc.nextLine();
        maquinaEstados.digitaPalavras(resposta);
    }

    private void iuTerminaJogo(){
        mostraTabuleiro();
        System.out.println("\nParabéns " + maquinaEstados.getJogadorAtual().getNome() + " !!!\nÉs o grande vencedor.");
        maquinaEstados.novaTentativa();
    }

    private void mostraTabuleiro() {
        for (int i = 0; i < maquinaEstados.getTabuleiro().length; i++) {
            System.out.print("|");
            for (int j = 0; j < maquinaEstados.getTabuleiro()[0].length; j++) {
                System.out.print(maquinaEstados.getTabuleiro()[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private int leInteiro() {
        int opcao = -1;
        do {
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insira uma opcao valida.");
            }
        }while(opcao == -1);
        return opcao;
    }
}
