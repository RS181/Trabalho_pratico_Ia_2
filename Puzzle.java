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
    //TODO X representa o bot
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

    //!calcula o valor da funcao utilidade de uma string de tamnaho 4 que representa um segmento de  linha/coluna/diagonal
    int Utility_aux(String s){
        int nr_X =0;
        int nr_O =0;

        for (int i = 0 ; i < s.length();i++){
            if(s.charAt(i) == 'X')
                nr_X++;
            else if (s.charAt(i) == 'O')
                nr_O++;
        }

        
        if (nr_O == 3 && nr_X == 0) return -50;
        else if (nr_O == 2 && nr_X == 0) return -10;
        else if (nr_O == 1 && nr_X == 0) return -1;
        else if (nr_X == 3 && nr_O == 0) return 50;
        else if (nr_X == 2 && nr_O == 0) return 10;
        else if (nr_X == 1 && nr_O == 0) return 1;

 
        return 0;
    }
    //! retorna a coluna j sob a forma de string
    String get_coluna(int col){
        String ans = "";
        for (int i = 0 ; i < state.length ;i++)
            ans += state[i][col];
        return ans;

    }
    
    //!funcao que calcula a utilidade de um dado puzzle, e atualiza o valor da mesma
    //TODO testar a funcao utilidade (fazer varios testes)

    void Calculate_Utility(){
        //variavel que guarda o valor da funcao utilidade
        int u = 0;
        //? calculo da linhas
        for (int i = 0 ; i < state.length ;i++){
            //? Transformamos s numa string
            StringBuilder s = new StringBuilder();
            s.append(state[i]);
            System.out.println("Linha " + i + " = " + s);
            for (int j = 0 ; j < state[0].length;j++){
                if ( j == 4)
                    break;
                // pegamos em todos os segmentos de 4 slots
                String aux = s.substring(j, j+4);

                int cur_utility = Utility_aux(aux);
                u += cur_utility;
                System.out.println("substring da linha  "+ i +" e colunas [" + j + "," + (j+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
            }
        }
        System.out.println( );
        //? calculo das colunas
        for (int j = 0; j < state[0].length;j++ ){
            String s = get_coluna(j);
            System.out.println("Coluna " + j + " = " + s);
            for (int i = 0 ; i < state.length ;i++){
                if (i == 3)
                    break;
                String aux = " " + state[i][j] + state[i+1][j] + state[i+2][j] + state[i+3][j]; 
                int cur_utility = Utility_aux(aux);
                u += cur_utility;
                System.out.println("substring da coluna "+ j +" e linhas [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
            }
        }

        //? calculo das diagonais
        Diagonais d = new Diagonais(this);
        //Dp1 
        String aux = ""+ d.DP1[0] + d.DP1[1] +d.DP1[2] +d.DP1[3];
        int cur_utility = Utility_aux(aux);
        u += cur_utility;
        System.out.println("DP1 = " + aux + " | tem utilidade = " + cur_utility);

        //DP2
        StringBuilder s = new StringBuilder();
        s.append(d.DP2); 
        System.out.println("DP2 = " + s);
        for (int i = 0 ; i < d.DP2.length ;i++){
            if (i == 2)
                break;
            aux = "" +d.DP2[i] + d.DP2[i+1] + d.DP2[i+2] + d.DP2[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;

            System.out.println("substring de DP2 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }
        //DP3
        s = new StringBuilder();
        s.append(d.DP3);
        System.out.println("DP3 = " + s);
        for (int i = 0 ; i < d.DP3.length ;i++){
            if (i == 3)
                break;
            aux = "" + d.DP3[i] + d.DP3[i+1] + d.DP3[i+2] + d.DP3[i+3]; 
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DP3 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }

        //DP4
        s = new StringBuilder();
        s.append(d.DP4);
        System.out.println("DP4 = " + s);
        for (int i = 0 ; i < d.DP4.length;i++){
            if (i == 3)
                break;
            aux = "" + d.DP4[i] + d.DP4[i+1] +d.DP4[i+2] +d.DP4[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DP4 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }

        //DP5 
        s = new StringBuilder();
        s.append(d.DP5);
        System.out.println("DP5 = " + s);
        for (int i = 0 ; i < d.DP5.length ;i++){
            if (i==2)
                break;
            aux = "" + d.DP5[i] + d.DP5[i+1] + d.DP5[i+2]+ d.DP5[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DP5 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }
        
        //DP6
        aux = "" + d.DP6[0] + d.DP6[1] +d.DP6[2] +d.DP6[3] ;
        cur_utility = Utility_aux(aux);
        System.out.println("DP6 = " + aux + " | tem utilidade = " +cur_utility );
        u += cur_utility;
        
        //DS1
        aux = "" + d.DS1[0] +d.DS1[1] +d.DS1[2] +d.DS1[3];
        cur_utility = Utility_aux(aux);
        System.out.println("DS1 = " + aux + " | tem utilidade = " + cur_utility);
        u += cur_utility;

        //DS2 
        s = new StringBuilder();
        s.append(d.DS2);
        System.out.println("DS2 = " + s);
        for (int i = 0; i < d.DS2.length ;i++){
            if (i == 2)
                break;
            aux = "" + d.DS2[i] + d.DS2[i+1] +d.DS2[i+2] +d.DS2[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DS2 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);

        }

        //DS3 
        s = new StringBuilder();
        s.append(d.DS3);
        System.out.println("DS3 = " + s);
        for (int i = 0 ; i < d.DS3.length ;i++){
            if ( i== 3)
                break;
            aux = "" + d.DS3[i] + d.DS3[i+1] + d.DS3[i+2] + d.DS3[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DS3 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }

        //DS4
        s = new StringBuilder();
        s.append(d.DS4);
        System.out.println("DS4 = " + s);
        for (int i = 0 ; i < d.DS4.length ; i++){
            if ( i == 3)
                break;
            aux = "" + d.DS4[i] + d.DS4[i+1] + d.DS4[i+2] + d.DS4[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DS4 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }

        //DS5
        s = new StringBuilder();
        s.append(d.DS5);
        System.out.println("DS5 = " +s);
        for (int i = 0 ; i < d.DS5.length ;i++){
            if ( i == 2)
                break;
            aux = "" + d.DS5[i] + d.DS5[i+1] +d.DS5[i+2] +d.DS5[i+3];
            cur_utility = Utility_aux(aux);
            u += cur_utility;
            System.out.println("substring de DS5 de [" + i + "," + (i+3)  + "]" + " : " +aux + " | tem utilidade = "+ cur_utility);
        }

        //DS6
        aux = "" + d.DS6[0] + d.DS6[1] +d.DS6[2] +d.DS6[3] ;
        cur_utility = Utility_aux(aux);
        u += cur_utility;
        System.out.println("DS6 = " + aux + " | tem utilidade = " + cur_utility );


        Utility = u;
        System.out.println(Utility);
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
