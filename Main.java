// 23/03/2023 10:18
//! Minimax aparente funcionar
import java.util.*;

public class Main{
    public static void Play_with_2_players(Puzzle p){
        Scanner stdin = new Scanner (System.in);
        if (p.turn .equals("X's turn")){
            System.out.println("player 1 uses X");
        }
        else  if (p.turn .equals("O's turn"))
            System.out.println("player 1 uses O");
        else {
            System.out.println("Decide who starts first (click X or O to decide)");
            String ans = stdin.next();
            if (ans.equals("X"))
                p.turn = "X's turn";
            else if (ans.equals("O"))
                p.turn = "O's turn";
            else{
                System.out.println("Invalid input");
                return;
            } 
        }
        //Enquanto nao e terminal e nao esta completamente cheio (quebra o ciclo se algum destes falhar)
        while(!(p.Is_Terminal()) && !(p.Full())){
            System.out.println(p);
            System.out.println("It is now " + p.turn);
            System.out.println("Make a move by choosing your coordinates to play (0 to 6).");
            p.Choose_col(stdin.nextInt());
        }

        System.out.println("estado final do tabuleiro");
        System.out.println(p);
        if (p.Is_Terminal()){
            if(p.turn.equals("O's turn"))
                System.out.println("X Ganhou");
            else if (p.turn.equals("X's turn"))
                System.out.println("O Ganhou");
        }
    }


    //Supondo que X sao as jogadas do computador
    public static void Play_with_Computer(Puzzle p, int Dificulty,String AI){
        Scanner stdin = new Scanner (System.in);

        //Enquanto nao e terminal e nao esta completamente cheio (quebra o ciclo se algum destes falhar)
        while(!(p.Is_Terminal()) && !(p.Full())){
            System.out.println(p);
            System.out.println("It is now " + p.turn);
            //!escolhe o jogador
            if (p.turn .equals("O's turn")){ 
                System.out.println("Make a move by choosing your coordinates to play (0 to 6).");
                p.Choose_col(stdin.nextInt());
            }
            //escolhe o bot
            else{
                Node aux = new Node(p);
                //Sugestao da professora
                Node aux2 = aux.Succesor_is_Final();
                if (aux2 != null){
                    p = aux.Succesor_is_Final().current;
                }
                //Sugestao da professora
                else {
                    Node ans;
                    if (AI.equals("minimax"))
                        ans = (new MiniMax(aux, Dificulty)).getBestNode();
                    else 
                        ans = (new AlphaBeta(aux, Dificulty)).getBestNode();
                    p = ans.current;
                    System.out.println("Jogada feita por computador");
                }
            }
        }

        System.out.println("estado final do tabuleiro");
        System.out.println(p);
        if (p.Is_Terminal()){
            if(p.turn.equals("O's turn"))
                System.out.println("X Ganhou");
            else if (p.turn.equals("X's turn"))
                System.out.println("O Ganhou");
        }
    }


    public static void main(String[] args) {
        Scanner stdin = new Scanner (System.in);
        System.out.println("Indique um tabuleiro");
        //?Parte de carregar uma dada configuracao 
        String aux ="";
        for (int i = 0 ; i < 6 ;i++ )
            aux += stdin.nextLine();
        Puzzle p = new Puzzle();
        p.upload_board(aux);
        // System.out.println(p);
        // System.out.println(Arrays.toString(p.valid));
        // Play_with_2_players(p);        
        System.out.println("Indique a dificuldade (< 8 senao demora muito)");
        int dificuldade = stdin.nextInt();
        stdin.nextLine();
        System.out.println("Indique contra qual algoritmo quer jogar\nminimax   alphabeta     montecarlo");
        String Ai = stdin.nextLine();
        if (Ai.equals("minimax") ||Ai.equals("alphabeta"))
            Play_with_Computer(p,dificuldade,Ai);     
        else if (Ai.equals("montecarlo")){
            System.out.println("Ainda por implementar");
        }

    }
}