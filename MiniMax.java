
import java.util.*;
public class MiniMax {
    private Node initial;
    private int MaxDepth;
    private Node bestNode;
    static int qtd_nos = 1;

    MiniMax(Node i, int MD) {
        initial = i;
        MaxDepth = MD;
        qtd_nos = 1;
        bestNode = MiniMax(initial, MaxDepth);
    }

    public Node getBestNode() {
        return bestNode;
    }

    // TODO testar esta funcao
    public static ArrayList<Node> Sucessores(Node n) {
        ArrayList<Node> suc = new ArrayList<>();
        Puzzle aux = n.current;
        // ? So gera sucessores se o no atual nao for
        if (!(n.current.Is_Terminal())) {
            for (int i = 0; i < 7; i++) {
                // Se a coluna atual for valida para jogar criamos sucesores
                if (aux.valid[i] != -1) {
                    // faz o movimento sem alterar o puzzle original
                    Puzzle temp = aux.Result(aux, i);

                    // criamos um novo no
                    suc.add(new Node(temp));
                }
            }
        }

        qtd_nos += suc.size();

        return suc;
    }
    public static Node MiniMax(Node Initial, int MaxDepth) {
        ArrayList<Node> Sucessors = Sucessores(Initial);
        
        //! guarda  escolha do MAX do no Inicial  , fazendo um procura com limite de profundidade maximo MaxDepth
        Node best = new Node();
        //?Melhor valor atual de utilidade
        int cur_best = Integer.MIN_VALUE;
        //*Explicacao do ciclo abaixo */
        //*A ideia e fazer o algoritmo Minimax para cada sucessor do no passado a funcao (Initial)
        //* ate chegar a uma das condicoes de paragem
        //* em cada iteracao do ciclo guardamos vamos verificando se o no que calculamos 
        //* atualmente tem valor de utilidade maior do que nos temos guardado , e atualizamos 
        //* best (no que representa a escolha MAX)
        for (int i = 0 ; i < Sucessors.size() ;i++){
            int ans = Math.min(Integer.MAX_VALUE,min_value(Sucessors.get(i),MaxDepth-1));
            
            if (ans > cur_best){
                cur_best = ans; 
                best = Sucessors.get(i);
            }
        }
        return best;
    }
    // *Estamos no turno Max(computador a jogar) e queremos o maximo do turno Min
    public static int max_value(Node cur, int MaxDepth) {
        // ?Verifica se (o no atual e um no terminal ou a profundidade maxima foi
        // ?atingida ou temos o tabuleiro cheio
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full()) {
            return cur.current.Calculate_Utility();
        }
        // !No que contem a maior utilidade (proximo movimento do Computador)
        int v = Integer.MIN_VALUE;
        // Sucessores do no atual
        ArrayList<Node> Sucessors = Sucessores(cur);
        for (int i = 0; i < Sucessors.size(); i++) {
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            v = Math.max(v, min_value(filho, MaxDepth - 1));
        }
        return v;
    }

    // *Estamos no turno Min e queremos o minimo do turno Max
    public static int min_value(Node cur, int MaxDepth) {
        // ?Verifica se (o no atual e um no terminal ou a profundidade maxima foi
        // ?atingida ou temos o tabuleiro cheio
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full()) {
            return cur.current.Calculate_Utility();
        }
        int v = Integer.MAX_VALUE;
        // Sucessores do no atual
        ArrayList<Node> Sucessors = Sucessores(cur);
        for (int i = 0; i < Sucessors.size(); i++) {
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            // ?Para evitar visitar estados que ja foram visitados
            v = Math.min(v, max_value(filho, MaxDepth - 1));
        }
        return v;
    }
}
