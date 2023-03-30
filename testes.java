// esta classe apenas serve para fazer testes
import java.util.*;
public class testes {
    public static Node_MCTS select(ArrayList<Node_MCTS> filhos){
        // guarda o no com maior UCB1
        Node_MCTS ans = new Node_MCTS();
        
        for (int i = 0 ; i < filhos.size() ; i++){
            Node_MCTS aux = filhos.get(i);
            if (aux.getUCB1() > ans.getUCB1())
                ans = aux;
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner stdin = new Scanner (System.in);
        String aux = "";
        for (int i = 0 ; i < 6 ;i++ )
            aux += stdin.nextLine();
        Puzzle p = new Puzzle();
        p.upload_board(aux);

     
        ArrayList<Node_MCTS> filhos = new ArrayList<>();


        Node_MCTS n1 = new Node_MCTS(p);
        Node_MCTS n2 = new Node_MCTS(p);
        n1.setUCB1(1);
        n2.setUCB1(3);
        filhos.add (n1);
        filhos.add (n2);

        Node_MCTS result  =select(filhos);
        result.setValue(42);
        System.out.println(result);
        System.out.println(n2);

        // new MCTS(t, 20);
        // ArrayList<Node_MCTS> filhos = t.getfilhos();
        // for (Node_MCTS n : filhos){
            // System.out.println(n.getPuzzle());
        // }
        
    }
}
