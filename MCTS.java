
import java.util.*;
public class MCTS {

    private Node_MCTS best_child;

    
    //Inicio do MCTS definindo o no raiz  co numero de iteracoes
    MCTS(Node_MCTS root,int times){
        monte_carlo_tree_search(root,times);
        
        Node_MCTS aux = root.getfilhos().get(0);
        

        for (Node_MCTS filho : root.getfilhos()){
            if (filho.getUCB1() > aux.getUCB1())
                aux = filho; 
        }    

        //guardamos o melhor no em best child
        best_child = aux;
        // System.out.println("Biggest Node UCB1 = " + aux );   
    }

    //obter o melhor filho (apos a aplicacao do algoritmo MCTS )
    public Node_MCTS getBest_child() {
        return best_child;
    }

    // gera os filhos de um no e guarda-os no 
    // em n.filhos(sendo n um Node_MCTS)
    //!So expande se o no atual nao for terminal
    public static void Expand(Node_MCTS n) {
        ArrayList<Node_MCTS> suc = new ArrayList<>();
        Puzzle aux = n.getPuzzle();
        // ? So gera sucessores se o no atual nao for um no terminal
        if (!(aux.Is_Terminal())) {
            for (int i = 0; i < 7; i++) {
                // Se a coluna atual for valida para jogar criamos sucesores
                if (aux.valid[i] != -1) {
                    // faz o movimento sem alterar o puzzle original
                    Puzzle temp = aux.Result(aux, i);

                    // criamos um novo no
                    suc.add(new Node_MCTS(temp));
                }
            }
        }
        n.setfilhos(suc);
        // if (suc.size() == 0)
            // System.out.println("No atual nao tem filhos possiveis " + n);
    }



    //Seleciona o proximo no com maior valor de 
    //UCB1 dos filhos de um dado no
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
    //*Atualiza o valor de UCB1 para todos os filhos do no atual
    //! Verificar esta funcao
    public static void Compute_UCB1_For_children(Node_MCTS current){
        ArrayList<Node_MCTS>  aux = current.getfilhos();
        for (Node_MCTS n : aux){
            //Atualizamos o numero de nos 
            //visitados pelo  no pai nos filhos
            n.setN(current.getn());
            
            if (n.getn() !=0){
                //atualizamos o valor do UCB1 do no filho  atual
                n.setUCB1(n.calculate_UCB1());
            }
        }

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

        //Geramos um indice aleaotorio entre 0 e Move.size()
        int aux = gerador.nextInt(Moves.size());
        
        //escolhemos a coluna que corresponde ao indice aleatorio aux
        int col = Moves.get(aux);
        return col;
    }


    //*O VALOR RETORNADO PELO ROLLOUT E:
    //* -> 0 (por vitoria de oponente)
    //* -> 0.5 (por empate) */
    //* -> 1 (por vitoria de AI)
    //! No rollout os estados percorridos nao contam como visitados
    public static double roollout(Node_MCTS current){
        //Aqui so nos interessa trabalhar com o puzzle
        //porque queremos chegar a um puzzle final, para retornar o valor
        // do rollout (0,0.5,1)

        // System.out.println("Inicio do roolout do no : "  + current);
       
        //Copiamos o puzzle atual para nao fazer alteracoes sobre o mesmo
        // System.out.println(current);
        Puzzle cur = current.getPuzzle().copy();
        // System.out.println("Puzzle Inicial");
        // System.out.println(cur);
        while( !(cur.Is_Terminal())){
            //geramos uma acao aleatoria
            int Random_action = Random_Available_Action(cur);
            //geramos um novo puzzle com essa acao aleatoria
            cur = cur.Result(cur, Random_action);
            // System.out.println("Puzzle resultante escolhendo a coluna " + Random_action);
            // System.out.println(cur);
        }
        // System.out.println("Fim do roolout do no : "  + current);

        //valor que retornamos da funcao rollout
        double return_value = -1;
        if (cur.Is_Terminal()){
            //! -----------------------------//
            if (cur.draw ==true){
               //System.out.println("Empate");
               return_value = 0.5;
            }
            //! -----------------------------//
            else if(cur.turn.equals("O's turn")){
                // System.out.println("X Ganhou");
                return_value = 1;

            }
            else if (cur.turn.equals("X's turn")){
                // System.out.println("O Ganhou");
                return_value = 0;
            }
        }
        if (return_value == -1)
            System.out.println("ERRO NA LINHA 137");
        
        return return_value;
    }

    //! IMPORTANTE
    //! NO BACKPROPAGATION DEVEMOS CALCULAR O UCB1 DE BAIXO PARA CIMA ? (EU ACHO QUE NAO)

    //todo Testar a ver se guarda os valores corretos para os nos (value,n,N)
    //todo (e verificar se guarda a "arvore" , ou seja , se guarda os valores corretos 
    //todo para cada no)

    //! DUVIDA ,  se em algum momento o algoritmo encontrar um no final , o que e que o mesmo 
    //!faz



    public static void monte_carlo_tree_search(Node_MCTS root,int times){
        int condition = root.getn();
        
        while (times != 0){
            //estrutura de dados que guarda o "caminho" atual
            //que mais tarde vai permitir atualizar os
            //valores com Backpropagate
            ArrayList<Node_MCTS> visited = new ArrayList<>();
            //adicionamos o no raiz
            visited.add(root);
            Node_MCTS current = root;
            //enquanto o no nao e uma folha e  o puzzle correspondente nao e final
            //todo confirmar este ciclo abaixo
            while (!(current.isLeaf()) && !(current.getPuzzle().Is_Terminal())){
                //atualiza o valor de UCB1 para os nos filhos de current
                // System.out.println("DEBUG");
                Compute_UCB1_For_children(current);
                //escolhemos o no filho que tem maior UCB1
                current = select(current.getfilhos());
                // se o filho atual nao tiver 
                if (current.getfilhos() == null)
                    current.setfilhos(new ArrayList<>());
                
                //adicionamos ao "caminho" atual
                visited.add(current);
            }
            //Chegamos a um no que e uma folha ou que e final
            
            //todo Confirmar daqui ate proximo todo !!!!!!!!

            //todo CONFIRMAR SE ESTOU A "TRATAR BEM" DO NO RAIZ
            // se o no nao for novo , ou for o no root
            if (current.getn() != 0 ){
                // System.out.println("Entrei aqui");
           
                //Adicionamos os nos a "arvore" , ou seja ,
                //geramos os filhos do no current e colocamos-os 
                // nos filhos de current 
                Expand(current);
                // System.out.println("------------------------------------------");
                // for (Node_MCTS n : current.getfilhos())
                    // System.out.println(n);
                // System.out.println("------------------------------------------");

                //caso o no atual nao seja final
                if (current.getfilhos().size() != 0 ){
                    // current = primeiro filho do current 
                    current = current.getfilhos().get(0);
                    //adicionamos a visited
                    visited.add(current);
                }
            }
    

            //todo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
            // System.out.println("Caminho ate agora");
            // for (Node_MCTS n : visited){
            //    atualiza as estatiscas dos nos da arvore visitados
                // System.out.println(n);
            // }
            // System.out.println("    FIM ");


                //Fazemos o rollout do novo no 
            // e guardamos o valor na variavel value
            //! TEM CASOS EM QUE CURRENT E NULL
            double value = roollout(current);       

            //Falta so fazer a Backpropagation dos valores

            //Fazemos reverse a lista de visitados 
            // para percorrer desde do ultimo no ate raiz
            Collections.reverse(visited);

            //TODO confirmar ate ao proximo todo
            //Fazemos a backprogation aos elementos de visited
            for (Node_MCTS n : visited){
                //atualiza as estatiscas dos nos da arvore visitados
                n.setn(n.getn() +1);
                n.setValue(value+n.getValue());
            }
            //todo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            times--;
        }

        //TODO Falta escolher o melhor no filho e colocar-lo em bestnode 
        //TODO para depois usar no algoritmo 
        // System.out.println("Estado final depois do MCTS");
        // for (Node_MCTS n : root.getfilhos()){
            // System.out.println("Filho " +n.getfilhos());
            // System.out.println(n);
        // }
        // System.out.println(root.getn());

    }





}
