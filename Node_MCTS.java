//* no utilizado para o Monte Carlo Tree Search
import java.util.*;
class Node_MCTS {
    //valor do no
    private double value;
    //numero de visitas do no
    private int n;
    //numero de visitas do no Pai
    private int N;
    //valor de Upper Confidence Bounds for Trees para este no
    private double UCB1;
    //puzzle correspondente
    private Puzzle current;
    //lista dos filhos do no atual
    private ArrayList<Node_MCTS> filhos;

    //Construtor que usei para o metodo select(so e usado nesse metodo)
    Node_MCTS(){
        UCB1 = 0;
    }
    
    //Inicializa os nos com valores deffault
    Node_MCTS(Puzzle p){    
        value = 0;
        n =0;
        N = 0;
        UCB1 = Double.MAX_VALUE;
        current = p;
        filhos = new ArrayList<>();
    }
    
    //verifica se o no atual e um no folha 
    //(basta verificar se filhos.size() == 0)
    public boolean isLeaf(){
        return filhos.size() == 0;
    }
    //calcula o UCB1 
    //supondo que tem toda a informacao necessaria para tal
    //! C = 1 (escolhi esse valor para a constante)
    
    public double calculate_UCB1(){
        double aux = Math.sqrt(2*Math.log(N)/n);
        return (value/n)  + 0.5 * aux;
    }

    @Override
    public String toString() {
        return "{UCB1 : "+UCB1 + ", valor : " +  value + ", numero de visitas : "+ n  + ", numero de visitas do no pai : " +N +"}" + "\n"+current;
    }


    //Funcao auxiliar para favorecer vitorias mais rapidas
    Node_MCTS  Succesor_is_Final(){
        Puzzle aux = current;
        // ? So gera sucessores se o no atual nao for
        for (int i = 0; i < 7; i++) {
            // Se a coluna atual for valida para jogar criamos sucesores
            if (aux.valid[i] != -1) {
                Puzzle temp = aux.Result(aux, i);
                if(temp.Is_Terminal())
                    return new Node_MCTS(temp);
            }
        }
        return null;
    }   


    //getters e setters
    public int getN() {
        return N;
    }
    public int getn(){
        return n;
    }
    public double getValue(){
        return value;
    }
    public double getUCB1(){
        return UCB1;
    }
    public Puzzle getPuzzle(){
        return current;
    }
    public ArrayList<Node_MCTS> getfilhos(){
        return filhos;
    }
    public void setN(int N){
        this.N = N;
    }
    public void setn(int n){
        this.n = n;
    }
    public void setValue(double value){
        this.value = value;
    }
    public void setUCB1(double UCB1){
        this.UCB1 = UCB1;
    }
    public void setfilhos(ArrayList<Node_MCTS> filhos){
        this.filhos = filhos;
    }
    
}
