import java.util.*;
class Node {
    Puzzle current;//Puzzle atual
    int Utility;
    

    //* recebe um puzzle e um caminho 
    Node(Puzzle p){
        current = p.copy();
        // Utility = current.Calculate_Utility();
    }


    @Override
    public String toString() {
        return current.toString() + "\n Tem utilidade = " + current.Calculate_Utility() + "\n";
    }
    
    //Funcao auxiliar para favorecer vitorias mais rapidas
    Node  Succesor_is_Final(){
        Puzzle aux = current;
        // ? So gera sucessores se o no atual nao for
        for (int i = 0; i < 7; i++) {
            // Se a coluna atual for valida para jogar criamos sucesores
            if (aux.valid[i] != -1) {
                Puzzle temp = aux.Result(aux, i);
                if(temp.Is_Terminal())
                    return new Node(temp);
            }
        }
        return null;
    }    
}
