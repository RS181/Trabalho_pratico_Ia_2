
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

    public static void main(String[] args) {
        Scanner stdin = new Scanner (System.in);
        
        //?Parte de carregar uma dada configuracao 
        String aux ="";
        for (int i = 0 ; i < 6 ;i++ )
            aux += stdin.nextLine();
        Puzzle p = new Puzzle();
        p.upload_board(aux);
        // System.out.println(p);
        // System.out.println(Arrays.toString(p.valid));
        // Play_with_2_players(p);        
        p.Calculate_Utility();
       

        
    }
}