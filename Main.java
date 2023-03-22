
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



    //TODO testar esta funcao
    public static ArrayList<Node> Sucessores(Node n){
        ArrayList<Node> suc = new ArrayList<>();
        Puzzle aux = n.current;
        //? So gera sucessores se o no atual nao for
        if (!(n.current.Is_Terminal())){
            for (int i = 0 ; i < 7 ;i++){
                //Se a coluna atual for valida para jogar criamos sucesores 
                if (aux.valid[i] != -1){
                    //faz o movimento sem alterar o puzzle original
                    Puzzle temp = aux.Result(aux,i);

                    //criamos um novo no 
                    suc.add(new Node(temp));
                }
            }
        }
        return suc;
    }

    //! MiniMax
    //!Todo Acabar metodo do minimax e testar-lo
    //!todo falta fazer a parte de retornar o melhor no
    public static Node MiniMax(Node Initial,int MaxDepth){
        Node best = max_value(Initial,MaxDepth);
        return best;
    }
    //*Estamos no turno Max(computador a jogar) e queremos o maximo do turno Min

    public static Node max_value(Node cur,int MaxDepth){
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full() ){
            cur.Utility = cur.current.Calculate_Utility();
            return cur;
        }
        //!No que contem a maior utilidade (proximo movimento do Computador)
        Node best = null;

        int v = Integer.MIN_VALUE;
            //Sucessores do no atual
            ArrayList<Node> Sucessors = Sucessores(cur);


            for (int i = 0 ; i < Sucessors.size(); i++){
                Puzzle next =Sucessors.get(i).current;
                Node filho = new Node(next);
                int minValue = min_value(filho, MaxDepth-1);
                //! se o valor atual for maior limite inferior(Integer.MIN_VALUE)
                //!
                if (minValue > v){
                    v = minValue;
                    best = filho;
                }            
            }
        return best;
    }

    //*Estamos no turno Min e queremos o minimo do turno Max
    public static int min_value(Node cur,int MaxDepth){
        //?Verifica se  (o no atual e um no terminal ou a profundidade maxima foi atingida ou temos o tabuleiro cheio
         
        if (cur.current.Is_Terminal() || MaxDepth == 0 || cur.current.Full() ){
            
            //? se esse for o caso calcula a utilidade do no atual e atualiza-a e retorna esse no
            cur.Utility = cur.current.Calculate_Utility();
            return cur.Utility;
        }


        int v = Integer.MAX_VALUE;

        //Sucessores do no atual
        ArrayList<Node> Sucessors = Sucessores(cur);
        for (int i = 0 ; i < Sucessors.size();i++){
            Puzzle next =Sucessors.get(i).current;
            Node filho = new Node(next);
            //?queremos o minimo dos successores
            v = Math.min(v,max_value(filho, MaxDepth-1).current.Calculate_Utility());
        }
        return v;
    }

    //Supondo que X sao as jogadas do computador
    public static void Play_with_Computer(Puzzle p, int Dificulty){
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
                Node ans = MiniMax(aux, Dificulty);
                p = ans.current;
                System.out.println("Jogada feita por computador");
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

        Play_with_Computer(p,dificuldade);        
    }
}