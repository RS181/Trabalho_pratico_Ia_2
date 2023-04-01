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
    //escolhe uma acao random para o puzzle(dentro das possiveis acoes)
    public static int Random_Available_Action(Puzzle p){
        //Guarda as colunas validas para jogar
        ArrayList<Integer> Moves = new ArrayList<>();
        int[] valid_moves = p.valid;

        //percorre os movimentos validos do puzzle atual
        // e adiciona as colunas validas para jogo
        for (int i = 0 ; i < valid_moves.length;i++){
            if (valid_moves[i] != -1)
                Moves.add(i);
        }

        //Parte de gerar uma acao random

        Random gerador = new Random();
        
        // System.out.println(Moves);
        int aux = gerador.nextInt(Moves.size());
        int col = Moves.get(aux);
        return col;
    }
    public static double roollout(Node_MCTS current){
        //Aqui so nos interessa trabalhar com o puzzle
        //porque queremos chegar a um puzzle final, para retornar o valor
        // do rollout (0,0.5,1)

        //Copiamos o puzzle atual para nao fazer alteracoes sobre o mesmo
        Puzzle cur = current.getPuzzle().copy();
        System.out.println("Puzzle Inicial");
        System.out.println(cur);
        while( !(cur.Is_Terminal())){
            //geramos uma acao aleatoria
            int Random_action = Random_Available_Action(cur);
            //geramos um novo puzzle com essa acao aleatoria
            cur = cur.Result(cur, Random_action);
            System.out.println("Puzzle resultante escolhendo a coluna " + Random_action);
            System.out.println(cur);
        }
        //valor que retornamos da funcao rollout
        double return_value = -1;
        if (cur.Is_Terminal()){
            //! -----------------------------//
            if (cur.draw ==true){
               System.out.println("Empate");
                return_value = 0.5;
            }
            //! -----------------------------//
            else if(cur.turn.equals("O's turn")){
                System.out.println("X Ganhou");
                return_value = 1;

            }
            else if (cur.turn.equals("X's turn")){
                System.out.println("O Ganhou");
                return_value = 0;
            }
        }
        
        return return_value;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner (System.in);
        String aux = "";
        for (int i = 0 ; i < 6 ;i++ )
            aux += stdin.nextLine();
        Puzzle p = new Puzzle();
        p.upload_board(aux);
        Node_MCTS n1 = new Node_MCTS(p);
        // ArrayList<Node_MCTS> f = new ArrayList<>();
        // f.add(n1);
        // System.out.println(n1);
        new MCTS(n1, 100);

        // n1.setUCB1(1);
        // Node_MCTS n2 = new Node_MCTS(p);
        // n2.setUCB1(2);
        // Node_MCTS n3 = new Node_MCTS(p);
        // n3.setUCB1(3);
        // ArrayList<Node_MCTS> f = new ArrayList<>();
        // System.out.println(f.size());
        // f.add(n1);
        // f.add(n2);
        // f.add(n3);
        // System.out.println(f);
        // Collections.reverse(f);
        // System.out.println(f);

        // System.out.println(roollout(n1));
        // System.out.println(p.Is_Terminal());

        // System.out.println(Random_Available_Action(p));

        // ArrayList<Node_MCTS> filhos = new ArrayList<>();


        // Node_MCTS n2 = new Node_MCTS(p);
        // n1.setUCB1(1);
        // n2.setUCB1(3);
        // filhos.add (n1);
        // filhos.add (n2);
        // Node_MCTS result  =select(filhos);
        // result.setValue(42);
        // System.out.println(result);
        // System.out.println(n2);

        // new MCTS(t, 20);
        // ArrayList<Node_MCTS> filhos = t.getfilhos();
        // for (Node_MCTS n : filhos){
            // System.out.println(n.getPuzzle());
        // }
        
    }
}
