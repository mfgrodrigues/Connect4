package pt.isec.a2019112924.tp.jogo.iu.texto;

import pt.isec.a2019112924.tp.jogo.logica.Gestor;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorVirtual;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class IU {
    private Gestor gestor;
    private boolean sair = false;
    private int tam = 0;
    private boolean continua = false;
    private Scanner sc = new Scanner(System.in);
    private boolean replay;

    public IU(Gestor gestor) {
        this.gestor = gestor;
    }

    public void corre() {

        while (!sair) {
            Situacao situacao = gestor.getSituacaoAtual();
            System.out.println();
            mostraLog();
            System.out.println();
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
                } else if (opcao == 2) {
                    String nome;
                    for (int i = 0; i < 2; i++) {
                        boolean adicionado = false;
                        do {
                            System.out.println("Jogador " + (i + 1) + "\nDigite o seu username: ");
                             nome = sc.nextLine();
                            if (gestor.adicionaJogador(nome)) {
                                adicionado = true;
                                System.out.println("Bem-vindo");
                            }
                        }while(!adicionado);
                    }
                    gestor.iniciaJogo();
                } else if (opcao == 3) {
                    gestor.iniciaJogo();
                } else if (opcao == 0) {
                    sair = true;
                }
                break;
            case 2:
                if(gestor.loadEstadoJogoFicheiro("jogo.bin")){
                System.out.println("Jogo carregado com sucesso");
                }else{
                    System.out.println("Nao ha jogos guardados");
                }
                break;
            case 3:
                mostraFicheiros();
                while(!continua){
                    System.out.println("Indique o nome do ficheiro: ");
                    String nomeFich = sc.nextLine();
                    if(gestor.loadReplayJogo(nomeFich)){
                        continua = true;
                    }
                    else{
                        System.out.println("O ficheiro nao existe");
                    }
                }
                replay = true;
                while(gestor.getStackJogoSize() > 0){
                    gestor.avancaReplay();
                    mostraReplay();
                    System.out.println("Pressione [ENTER] para avancar");
                    sc.nextLine();
                }
                break;
            case 0:
                sair = true;
                break;

        }
    }

    private void iuAguardaJogada() {
        mostraTabuleiro();
        System.out.println();
        mostraInfoJogadores();
        System.out.println();
        System.out.println("\n--+---+---+---+---+--");
        System.out.println("JOGA " + (gestor.getJogadorAtual().getNome()).toUpperCase() + "\n");
        if (gestor.getJogadorAtual() instanceof JogadorVirtual) {
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
                        System.out.println("Nao e possivel voltar atras");
                    }
                    break;
                case 4:
                    gestor.escolheOpMiniJogo();
                    break;
                case 0:
                    if(gestor.saveEstadoJogoFicheiro("jogo.bin")){
                        System.out.println("Jogo gravado com sucesso");
                    }
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
        System.out.println("Digite as palavras apresentadas no menor tempo poss??vel. Pressione a tecla [enter] quando terminar.");
        String resposta = sc.nextLine();
        gestor.digitaPalavras(resposta);
    }

    private void iuTerminaJogo() {
        if(!replay) {
            mostraTabuleiro();
            System.out.println("\nParab??ns " + gestor.getJogadorAtual().getNome() + " !!!\n??s o grande vencedor.\n");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            if (gestor.saveReplayJogo("Jogo" + dtf.format(LocalDateTime.now()))) {
                System.out.println("Jogo gravado com sucesso");
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
        else {
            replay = false;
            gestor.novaTentativa();
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

    private void mostraReplay() {
        mostraInfoJogadores();
        mostraTabuleiro();
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

    private void mostraLog(){
        for(int i = tam; i < gestor.getLog().size(); i++){
            System.out.println("[" + gestor.getLog().get(i) + "]");
        }
        tam = gestor.getLog().size();
    }

    private void mostraFicheiros(){
        if(gestor.reuneFicheiros().length == 0){
            System.out.println("Nao existem jogos gravados.");
            continua = true;
        }
        for(String nomeFich: gestor.reuneFicheiros()) {
            System.out.println(nomeFich);
        }
        continua = false;
    }
}
