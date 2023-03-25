
// 24/03/2023
import java.util.*;

//TODO Mostrar implementacao a professora
//! TENHO DUVIDAS NA PARTE DE ATUALIZACAO DO ALPHA E BETA
public class AlphaBeta {
    private Node initial;
    private int MaxDepth;
    private Node bestNode;

    AlphaBeta(Node i, int MD) {
        initial = i;
        MaxDepth = MD;
        bestNode = AlphaBeta(initial, MaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Node getBestNode() {
        return bestNode;
    }

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
        return suc;
    }

    // TODO testar a funcao  
    //TODO PEDIR A PROFESSORA PARA DAR UMA VISTA DE OLHOS
    public static Node AlphaBeta(Node Initial, int MaxDepth, int alpha, int beta) {
        ArrayList<Node> Sucessors = Sucessores(Initial);
        // ! guarda escolha do MAX do no Inicial , fazendo um procura com limite de
        // profundidade maximo MaxDepth
        Node best = new Node();
        // ?Melhor valor atual de utilidade
        int cur_best = Integer.MIN_VALUE;
        for (int i = 0; i < Sucessors.size(); i++) {

            int ans = Math.min(Integer.MAX_VALUE, min_value(Sucessors.get(i), MaxDepth - 1, alpha, beta));

            if (ans > cur_best) {
                cur_best = ans;
                best = Sucessors.get(i);
            }
            // ! Aqui so atualizamos o valor de alpha porque
            // ! estamos no no raiz e estamos a atualizar a sua melhor
            // ! escolha de os seus descendentes (primeiras jogadas do MIN)
            alpha = Math.max(alpha, cur_best);
            if (cur_best >= beta)
                break;
        }

        return best;
    }

    public static int max_value(Node cur, int MaxDepth, int alpha, int beta) {
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full()) {
            return cur.current.Calculate_Utility();
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Node> Sucessors = Sucessores(cur);

        for (int i = 0; i < Sucessors.size(); i++) {
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            v = Math.max(v, min_value(filho, MaxDepth - 1, alpha, beta));

            // ? Momento da poda (retorna o melhor)
            if (v >= beta) {
                return v;
            }
            // ? atualizamos o valo de alpha
            alpha = Math.max(alpha, v);

        }

        return v;
    }

    public static int min_value(Node cur, int MaxDepth, int alpha, int beta) {
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full()) {
            cur.Utility = cur.current.Calculate_Utility();
            return cur.Utility;
        }

        int v = Integer.MAX_VALUE;
        ArrayList<Node> Sucessors = Sucessores(cur);

        for (int i = 0; i < Sucessors.size(); i++) {
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            v = Math.min(v, max_value(filho, MaxDepth - 1, alpha, beta));
            // ? Momento da poda (retorna o melhor)
            if (v <= alpha) {
                return v;
            }

            // ? valor beta e apenas atualizado na funcao min_value
            beta = Math.min(beta, v);
        }

        return v;
    }

}
