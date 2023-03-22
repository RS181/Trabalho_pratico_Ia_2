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

    
    
}
