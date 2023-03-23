import java.util.*;
public class AlphaBeta {
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

    public static Node AlphaBeta(Node Initial, int MaxDepth){
        Node best = max_value(Initial, MaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return best;
    }
    
    public static Node max_value(Node cur, int MaxDepth, int alpha, int beta){
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full()){
            cur.Utility = cur.current.Calculate_Utility();
            return cur;
        } 
        Node best = null;
        int v = Integer.MIN_VALUE;
        ArrayList<Node> Sucessors = Sucessores(cur);
    
        for (int i = 0; i < Sucessors.size(); i++){
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            int minValue = min_value(filho, MaxDepth-1, alpha, beta);
    
            if (minValue > v){
                v = minValue;
                best = filho;
            }
            //? Momento da poda (retorna o melhor)
            if (v >= beta){
                return best;
            }
            alpha = Math.max(alpha, v);
        }
    
        return best;
    }
    
    public static int min_value(Node cur, int MaxDepth, int alpha, int beta){
        if (cur.current.Is_Terminal() ||  MaxDepth == 0 ||  cur.current.Full()){
            cur.Utility = cur.current.Calculate_Utility();
            return cur.Utility;
        }
    
        int v = Integer.MAX_VALUE;
        ArrayList<Node> Sucessors = Sucessores(cur);
    
        for (int i = 0; i < Sucessors.size(); i++){
            Puzzle next = Sucessors.get(i).current;
            Node filho = new Node(next);
            v = Math.min(v, max_value(filho, MaxDepth-1, alpha, beta).current.Calculate_Utility());
    
            if (v <= alpha){
                return v;
            }
            beta = Math.min(beta, v);
        }
    
        return v;
    }
    
}
