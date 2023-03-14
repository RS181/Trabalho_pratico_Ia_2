
import java.util.*;
public class Main{


    public static void main(String[] args) {
        Scanner stdin = new Scanner (System.in);
        
        //?Parte de carregar uma dada configuracao 
        String aux ="";
        for (int i = 0 ; i < 6 ;i++ )
            aux += stdin.nextLine();
        Puzzle p = new Puzzle();
        p.upload_board(aux);

        System.out.println(p);
        System.out.println(Arrays.toString(p.valid));

        System.out.println(p.Is_Terminal());

        
    }
}