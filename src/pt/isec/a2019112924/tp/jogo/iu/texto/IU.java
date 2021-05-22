package pt.isec.a2019112924.tp.jogo.iu.texto;

import pt.isec.a2019112924.tp.Gestor;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.util.Scanner;

public class IU {
    private Gestor gestor;
    private boolean sair = false;
    private Scanner sc = new Scanner(System.in);

    public IU(Gestor maquinaEstados) {
        this.gestor = maquinaEstados;
    }

    public void corre() {

        while (!sair) {
            Situacao situacao = gestor.getSituacaoAtual();
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

    private void iuAguardaInicio() {
        System.out.println("""
                --+---+---+---+---+--
                MENU:                
                1 - Novo Jogo
                2 - Carregar Jogo Anterior
                3 - Replay Historico Jogos 

                0 - Sair
                --+---+---+---+---+--
                Escolha uma opcao:""");

        switch (leInteiro(0, 3)) {
            case 1:
                System.out.println("""
                        --+---+---+---+---+--
                        1 - Um Jogador
                        2 - Dois Jogadores
                        3 - Jogo Virtual

                        0 - Sair
                        --+---+---+---+---+--
                        Escolha uma opcao:""");
                int opcao = leInteiro(0, 3);
                if (opcao == 1) {
                    System.out.println("Digite o seu username: ");
                    String nome = sc.nextLine();
                    gestor.adicionaJogador(nome);
                    gestor.iniciaJogo();
                    //TODO feedback do jogo
                } else if (opcao == 2) {
                    for (int i = 0; i < 2; i++) {
                        System.out.println("Jogador " + (i + 1) + "\nDigite o seu username: ");
                        String nome = sc.nextLine();
                        gestor.adicionaJogador(nome);
                        //TODO feedback do jogo: jogadores com o mesmo nome
                    }
                    gestor.iniciaJogo();
                } else if (opcao == 3) {
                    gestor.iniciaJogo();
                } else if (opcao == 0) {
                    sair = true;
                }
                break;
            case 2:
                gestor.loadEstadoJogoFicheiro("jogo.bin");
                break;
            case 3:
                System.out.println("Indique o nome do ficheiro: ");
                String nomeFich = sc.nextLine();
                gestor.loadReplayJogo(nomeFich);
                do {
                    mostraReplay(gestor.avancaReplay());
                    System.out.println("Pressione [ENTER] para avancar");
                    sc.nextLine();
                }while(gestor.getStackJogoSize() > 0);
                break;
            case 0:
                sair = true;
                break;

        }
    }

    private void iuAguardaJogada() {
        mostraTabuleiro();
        mostraInfoJogadores();
        System.out.println();
        System.out.println("\n--+---+---+---+---+--");
        System.out.println("JOGA " + (gestor.getJogadorAtual().getNome()).toUpperCase() + "\n");
        if (gestor.getJogadorAtual().getNome().contains("Virtual")) {
            System.out.println("Pressione [ENTER] para avancar");
            sc.nextLine();
            System.out.println("--+---+---+---+---+--");
            gestor.jogaPeca();
        } else {
            System.out.println("""
                    1 - Jogar Peca
                    2 - Jogar Peca Especial
                    3 - Voltar Jogada Atras 
                    4 - Jogar Mini Jogo
                    
                    0 - Sair
                    --+---+---+---+---+--
                    Escolha uma opcao:""");

            switch (leInteiro(0,4)) {
                case 1:
                    System.out.println("Jogar peca na coluna:");
                    int coluna = leInteiro(1, 7);
                    gestor.jogaPeca(coluna - 1);
                    break;
                case 2:
                    System.out.println("Jogar peca especial na coluna:");
                    coluna = leInteiro(1, 7);
                    gestor.jogaPecaEspecial(coluna - 1);
                    break;
                case 3:
                    System.out.println("Nr. de creditos para voltar atras:");
                    int nrBacks = leInteiro();
                    if(!gestor.voltarAtras(nrBacks)) {
                        System.out.println("Numero de creditos disponivel insuficiente");
                    }
                    break;
                case 4:
                    gestor.escolheOpMiniJogo();
                    break;
                case 0:
                    gestor.saveEstadoJogoFicheiro("jogo.bin");
                    sair = true;
                    break;
            }
        }
    }

    private void iuMiniJogoC() {
        System.out.println(gestor.getMiniJogo().getPergunta());
        System.out.println("Resultado:");
        String resposta = String.valueOf(leInteiro());
        gestor.resolveCalculo(resposta);
    }

    private void iuMiniJogoP() {
        System.out.println(gestor.getMiniJogo().getPergunta());
        System.out.println("Digite as palavras apresentadas no menor tempo possível. Pressione a tecla [enter] quando terminar.");
        String resposta = sc.nextLine();
        gestor.digitaPalavras(resposta);
    }

    private void iuTerminaJogo() {
        mostraTabuleiro();
        System.out.println("\nParabéns " + gestor.getJogadorAtual().getNome() + " !!!\nÉs o grande vencedor.\n");
        System.out.println("Deseja gravar o jogo? [Sim/Nao]");
        String grava = sc.nextLine().toUpperCase();
        if(grava.equals("SIM")) {
            System.out.println("Nome do ficheiro:");
            String nomeFich = sc.nextLine();
            gestor.saveReplayJogo(nomeFich);
        }
        System.out.println("""
                        --+---+---+---+---+--
                        0 - Sair 
                        1 - Voltar a Jogar
                        --+---+---+---+---+--
                        Escolha uma opcao:""");
        switch (leInteiro(0,1)){
            case 0:
                sair = true;
                break;
            case 1:
                gestor.novaTentativa();
                break;
        }

    }

    private void mostraTabuleiro() {
        for (int i = 0; i < gestor.getTabuleiro().length; i++) {
            System.out.print("|");
            for (int j = 0; j < gestor.getTabuleiro()[0].length; j++) {
                System.out.print(gestor.getTabuleiro()[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private void mostraInfoJogadores() {
        int jog = 0;
        for (Jogador jogador : gestor.getJogadores()) {
            System.out.println("Jogador" + (jog + 1) + " -> " + jogador.toString());
            jog++;
        }
    }

    private void mostraReplay(Jogo jogo) {
        int jog = 0;
        for (Jogador jogador : jogo.getJogadores()) {
            System.out.println("Jogador" + (jog + 1) + " -> " + jogador.toString());
            jog++;
        }
        System.out.println();
        for (int i = 0; i < jogo.getTabuleiro().length; i++) {
            System.out.print("|");
            for (int j = 0; j < jogo.getTabuleiro()[0].length; j++) {
                System.out.print(jogo.getTabuleiro()[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("JOGA " + (jogo.getJogadorAtual().getNome()).toUpperCase() + "\n");
    }

    private int leInteiro(int min, int max) {
        int opcao;
        do {
            opcao = leInteiro();
            if (opcao < min || opcao > max) {
                System.out.println("Insira uma opcao valida.");
            }
        } while (opcao < min || opcao > max);
        return opcao;
    }

    private int leInteiro() {
        int opcao = -1;
        do {
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insira uma opcao valida.");
            }
        } while (opcao == -1);
        return opcao;
    }
}
