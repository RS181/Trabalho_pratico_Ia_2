
import java.util.*;
public class MCTS {

    private Node_MCTS best_child;

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
    }


    //Inicio do MCTS definindo o no raiz  co numero de iteracoes
    MCTS(Node_MCTS root,int times){
        monte_carlo_tree_search(root,times);       

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
    public static void Compute_UCB1_For_children(Node_MCTS current){
        ArrayList<Node_MCTS>  aux = current.getfilhos();
        for (Node_MCTS n : aux){
            //atualizamos o valor do UCB1 do no atual
            n.setUCB1(n.calculate_UCB1());
        }

    }


    public static int Random_Available_Action(Puzzle p){


        return 0;
    }


    //*O VALOR RETORNADO PELO ROLLOUT E:
    //* -> 0 (por vitoria de oponente)
    //* -> 0.5 (por empate) */
    //* -> 1 (por vitoria de AI)
    //! No rollout os estados percorridos nao contam como visitados
    public static double roollout(Node_MCTS current){
        
        
        
        
        
        return 0;
    }

    //! IMPORTANTE
    //! NO BACKPROPAGATION DEVEMOS CALCULAR O UCB1 DE BAIXO PARA CIMA ? (EU ACHO QUE NAO)

    //todo Testar a ver se guarda os valores corretos para os nos (value,n,N)
    //todo (e verificar se guarda a "arvore" , ou seja , se guarda os valores corretos 
    //todo para cada no)

    //! DUVIDA ,  se em algum momento o algoritmo encontrar um no final , o que e que o mesmo 
    //!faz



    public static void monte_carlo_tree_search(Node_MCTS root,int times){
        
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
                //atualiza o valor de UCB1 para os nos filhos
                Compute_UCB1_For_children(current);
                //escolhemos o no filho que tem maior UCB1
                current = select(current.getfilhos());
                //adicionamos ao "caminho" atual
                visited.add(current);
            }

            //Chegamos a um no que e uma folha ou que e final
            
            //todo Confirmar daqui para baixo

            //Adicionamos os nos a "arvore" , ou seja ,
            //geramos os filhos do no current e colocamos-os 
            // nos filhos de current 
            Expand(current);

            //adicionamos ao "caminho" atual
            visited.add(current);


            
     





            times--;
            break;

        }
    }





}
