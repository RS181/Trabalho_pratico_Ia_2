import java.util.*;

//todo FAZER UM METODO QUE VERIFICA SE UMA DADO PUZZLE E O ESTADO FINAL E QUEM GANHOU
//!Classe que representa um jogo dos 4 em linha
class Puzzle {
    char[][] state;// estado do jogo
    int[] valid;// indica a linha mais "funda" ,em que e possivel jogar , de cada coluna
    String turn; // diz quem e que vai jogar

    int Utility;//Guarda o valor da funcao utilidade

    Puzzle() {
        state = new char[6][7];
        turn = "undefined";
        valid = new int[7];
    }

    void upload_board(String s) {
        int pos = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[i][j] = s.charAt(pos);
                pos++;
            }
        }
        // ? Atualiza turn para indicar quem e o proximo a jogar e a posicao mais acima que podemos jogar a cada coluna
        To_Move();
        Actions();
    }

    // !Dado um estado do jogo atualiza turn para conter o jogador que vais jogar a
    //! seguir
    void To_Move() {
        int nr_x = 0;
        int nr_o = 0;
        // ?joga aquele que tiver menos pecas no tabuleiro (ou no caso de empate joga o
        // X)
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 'X')
                    nr_x++;
                else if (state[i][j] == 'O')
                    nr_o++;
            }
        }
        // *proximo jogador e O
        if (nr_x > nr_o)
            turn = "O's turn";
        // *proximo jogador e X
        else if (nr_o > nr_x)
            turn = "X's turn";
        // *proximo jogador e X
        else if (nr_x == nr_o)
            turn = "X's turn";
    }

    // !Indica e atualiza numero de movimentos legais
    // * em cada coluna vai indicar qual a linha mais baixa em que pode colocar uma
    // peca
    // * se tiver -1 quer dizer que essa coluna esta completa
    // * [1,2,3,4,5,6,7]
    void Actions() {
        int[] ans = { -1, -1, -1, -1, -1, -1, -1 };
        int pos = 0;
        // ? para tal percorremos as colunas
        for (int j = 0; j < state[0].length; j++) {
            for (int i = 0; i < state.length; i++) {
                if (state[i][j] == '-')
                    ans[pos] = i;
            }
            pos++;// *proxima coluna
        }
        valid = ans;
        // System.out.println("Movimentos Possiveis = " + Arrays.toString(valid));
    }

    //!Indica a coluna em que queromos jogar uma peca
    //TODO a Bola representa o bot
    void Choose_col(int col){
        //?Posicao invalida
        if ( col >= 7 || col < 0){
            System.out.println("coluna =" + col);
            System.out.println("Coluna invalida");
            return;
        }
        //? Se ainda houver espaco numa coluna
        if ( valid[col] != -1){
            if (turn.equals("X's turn")){
                //colocamos a peca
                state[valid[col]][col] = 'X';
                //atualizamos o proximo a jogar
                turn = "O's turn";
                //atualizamos,para cada coluna , a linha mais acima, em que podemos jogar uma peca
                Actions();   
            }

            else if (turn.equals("O's turn")){
                //colocamos a peca
                state[valid[col]][col] = 'O';
                //atualizamos o proximo a jogar
                turn = "X's turn";
                //atualizamos,para cada coluna , a linha mais acima, em que podemos jogar uma peca
                Actions();                   
            }
        }
        else 
            System.out.println("Coluna (" + col + ") encontra-se cheia");

    }

    //! retorna o estado em que um puzzle ficaria (sem alterar estado original) 
    //! colocando a peca na coluna col
    Puzzle Result (Puzzle p , int col){
        Puzzle ans = p.copy();
        ans.Choose_col(col);
        return ans;
    }


    //!Copia toda a informacao do puzzle auxiliar para outro puzzle
    Puzzle copy(){
        Puzzle p = new Puzzle();
        p.valid = valid;
        p.turn = turn;
        p.Utility = Utility;
        for (int i = 0 ; i < state.length ; i++){
            for (int j = 0 ; j < state[0].length ; j++){
                p.state[i][j] = state[i][j];
            }
        }
        return p;
    }

    //!Verifica se ainda exitem espacos por jogar
    boolean is_valid(){
        int count = 0;
        for (int i = 0 ; i < valid.length ;i++){
            if (valid[i] == -1)
                count++;
        }
        //?Se nao existir mais espacos por preencher
        if (count == 7)
            return false;
        return true;
    }
    //? Indica se alguma linha contem a substring com 4 X's ou 4 O's
    boolean Line_complete(String s){
        return (s.contains("XXXX") || s.contains("OOOO"));
    }
    //? Indica se alguma coluna contem a substring com 4 X's ou 4 O's
    boolean col_complete(String s){
        return (s.contains("XXXX") || s.contains("OOOO"));
    }

    //? verifica se um tabuleiro esta cheio
    boolean Full(){
        for (int i = 0 ; i < valid.length; i++){
            if (valid[i] != -1)
                return false;
        }
        return true;
    }



    //! Verfica se o jogo acabou ou nao (PARECE BEM MAS TER CUIDADO)
    boolean Is_Terminal(){ 
        //nao tem mais espaco para fazer jogadas
        if (Full()){
            System.out.println("Tabuleiro cheio");
            return true;
        }
        //?Verifica se algum dos jogadores ganhou (Horizontal)
        
        for (int i = 0 ; i < state.length ;i++){
            String aux = String.valueOf(state[i]);
            if (Line_complete(aux) == true){
                System.out.println("linha " + i + " tem um vencedor");
                return true;
            }
        }
        //?Verfica se algum dos jogadores ganhou (Vertical)
        for (int j = 0 ; j < state[0].length ; j++){
            String aux = "";
            for (int i = 0 ; i < state.length ; i++){
                aux += state[i][j];
            }
            if (col_complete(aux) == true){
                System.out.println("Coluna " + j + " tem um vencedor");
                return true;
            }
        }
        //? Verifica se o estado do jogo atual tem algum vencedor(diagonal)
        Diagonais d = new Diagonais(this);
        if (d.check_final() == true){
            System.out.println("Temos vencedor nas diagonais");
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        String ans = "";
        for (int i = 0; i < state.length; i++) {
            ans += String.valueOf(state[i]) + "\n";
        }
        ans += "0123456\n";
        return ans;
    }

}
