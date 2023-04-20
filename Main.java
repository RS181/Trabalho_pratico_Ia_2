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
            //! -----------------------------//
            if (p.draw ==true)
                System.out.println("Empate");
            //! -----------------------------//
            else if(p.turn.equals("O's turn"))
                System.out.println("X Ganhou");
            else if (p.turn.equals("X's turn"))
                System.out.println("O Ganhou");
        }
    }


    //Supondo que X sao as jogadas do computador
    public static void Play_with_Computer(Puzzle p, int Dificulty,String AI){
        Scanner stdin = new Scanner (System.in);
        //! TEMPO
        long starTime ,endTime,duration;
        //!

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
                //Sugestao da professora (verificar se algum sucessor do no atual e final, se sim retornar esse no )
                Node aux2 = aux.Succesor_is_Final();
                
                //!TEMPO 
                starTime = System.currentTimeMillis();
                //!
                
                
                if (aux2 != null){
                    p = aux.Succesor_is_Final().current;
                }
                //Sugestao da professora
                else {
                    int nr_nos = -1;
                    Node ans;
                    if (AI.equals("minimax")){
                        MiniMax m = new MiniMax(aux, Dificulty);
                        // ans = (new MiniMax(aux, Dificulty)).getBestNode();
                        ans = m.getBestNode();
                        nr_nos = m.qtd_nos;
                    }
                    else {
                        AlphaBeta b = new AlphaBeta(aux,Dificulty);
                        // ans = (new AlphaBeta(aux, Dificulty)).getBestNode();
                        ans = b.getBestNode();
                        nr_nos = b.qtd_nos;
                    }
                    p = ans.current;
                    System.out.println("Jogada feita por computador");
                
                    //! TEMPO
                    endTime = System.currentTimeMillis();
                    duration = endTime - starTime;

                    System.out.println("Time taken: " + duration  + "milliseconds");
                    System.out.println("Numero de nos " + nr_nos);

                }

            }
        }

        System.out.println("estado final do tabuleiro");
        System.out.println(p);
        if (p.Is_Terminal()){
            //! -----------------------------//
            if (p.draw ==true)
               System.out.println("Empate");
            //! -----------------------------//
            else if(p.turn.equals("O's turn"))
                System.out.println("X Ganhou");
            else if (p.turn.equals("X's turn"))
                System.out.println("O Ganhou");
        }
    }

    //! Implementar com MCTS

    public static void Play_with_Computer_MCTS(Puzzle p, int nr_times){
        Scanner stdin = new Scanner(System.in);
        while (!(p.Is_Terminal()) && !(p.Full())){
            System.out.println(p);
            System.out.println("It is now " + p.turn);
            //!escolhe o  jogador 
            if (p.turn .equals("O's turn")){
                System.out.println("Make a move by choosing your coordinates to play (0 to 6) ");
                p.Choose_col(stdin.nextInt());
            }
            //escolhe o bot 

            else {
                Node_MCTS aux = new Node_MCTS(p);
                //Sugestao da professora (verificar se algum sucessor do no atual e final, se sim retornar esse no )
                Node_MCTS aux2 = aux.Succesor_is_Final();
                if (aux2 != null){
                    p = aux.Succesor_is_Final().getPuzzle();
                }
                else {
                    //Sugestao da professora    
                    Node_MCTS ans = (new MCTS(aux,nr_times)).getBest_child();
                    p = ans.getPuzzle();
                    System.out.println("Jogada feita por computador");
                }
            }

        }
            System.out.println("estado final do tabuleiro");
            System.out.println(p);
            if (p.Is_Terminal()){
                //! -----------------------------//
                if (p.draw ==true)
                   System.out.println("Empate");
                //! -----------------------------//
                else if(p.turn.equals("O's turn"))
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
       
        System.out.println("Indique contra qual algoritmo quer jogar\nminimax   alphabeta     montecarlo");
        String Ai = stdin.nextLine();
        if (Ai.equals("minimax") ||Ai.equals("alphabeta")){
            System.out.println("Indique a profundide maxima (< 8 senao demora muito)");
            int dificuldade = stdin.nextInt();
            // stdin.nextLine();
            Play_with_Computer(p,dificuldade,Ai);     
        
        }
        else if (Ai.equals("montecarlo")){
            System.out.println("Indique o numero de vezes que devemos aplicar o algoritmo MCTS");
            int nr_times = stdin.nextInt();
            Play_with_Computer_MCTS(p, nr_times);
        }

    }
}