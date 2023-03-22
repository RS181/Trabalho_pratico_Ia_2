import java.util.*;

import javax.print.DocFlavor.STRING;
class Diagonais {
    //! ver folha 2 que indica qual diagonal e qual
    
    //?Diagonais da esqueda para direita
    char[] DP1 = new char[4];
    char[] DP2 = new char[5];
    char[] DP3 = new char[6];
    char[] DP4 = new char[6];
    char[] DP5 = new char[5];
    char[] DP6 = new char[4];

    //?Diagonais da direita para a esquerda
    char[] DS1 = new char[4];
    char[] DS2 = new char[5];
    char[] DS3 = new char[6];
    char[] DS4 = new char[6];
    char[] DS5 = new char[5];
    char[] DS6 = new char[4];


    //!Dado  uma matriz 6x7 (colocamos cada diagonal especifica no respetivo array acima)

    //✅ significa que testei
    Diagonais (Puzzle p){
        char[][] m = p.state;
        
        int pos = 0;

        //DP1 ✅
        int col = 0;
        for(int lin = 2 ; lin <=5 ;lin++){
            DP1[pos] = m[lin][col];
            col++;
            pos++;
        }
        
        //DP2✅
        pos = 0;
        col = 0;
        for (int lin = 1; lin <=5 ; lin++){
            DP2[pos] = m[lin][col];
            col++;
            pos++;
        }
        
        //DP3✅
        pos = 0;
        col = 0;
        for (int lin = 0; lin <=5 ;lin++){
            DP3[pos] = m[lin][col];
            col++;
            pos++;
        }

        //DP4✅
        pos = 0;
        col = 1;
        for (int lin = 0 ; lin <=5 ;lin++){
            DP4[pos] = m[lin][col];
            col++;
            pos++;
        }

        //DP5✅
        pos = 0;
        col = 2;
        for (int lin = 0 ; lin <=4 ;lin++){
            DP5[pos] = m[lin][col];
            col++;
            pos++;
        }

        //DP6✅
        pos = 0;
        col = 3;
        for (int lin = 0; lin <=3 ;lin++){
            DP6[pos] = m[lin][col];
            col++;
            pos++;
        }

        //DS1✅
        pos = 0;
        col = 3;
        for (int lin = 0 ;lin <=3 ;lin++){
            DS1[pos] = m[lin][col];
            col--;
            pos++;
        }

        //DS2✅
        pos = 0;
        col = 4;
        for (int lin = 0 ; lin <=4 ;lin++){
            DS2[pos] = m[lin][col];
            col--;
            pos++;
        }

        //DS3✅
        pos = 0;
        col = 5;
        for (int lin = 0 ;lin <=5 ;lin++){
            DS3[pos] = m[lin][col];
            col--;
            pos++;
        }

        //DS4✅
        pos = 0;
        col = 6;
        for (int lin = 0 ; lin <=5 ;lin++){
            DS4[pos] = m[lin][col];
            col--;
            pos++;
        }

        //DS5✅
        pos = 0;
        col = 6;
        for (int lin = 1 ; lin <=5 ;lin++){
            DS5[pos] = m[lin][col];
            col--;
            pos++;
        }

        //DS6✅
        pos = 0;
        col = 6;
        for (int lin = 2 ; lin <=5 ;lin++){
            DS6[pos] = m[lin][col];
            col--;
            pos++;
        }
    }
    int Utility_aux_d(String s){
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
        
        //todo testar condicao abaixo !!!!!!!!!!!!!!!!!
        else if (nr_O == 4) return -512;
        else if (nr_X == 4) return 512;

 
        return 0;
    }




    //verifica se alguma das diagonais contem "XXXX" ou "OOOO"
    boolean check_final(){
        return String.valueOf(DS1).contains("XXXX") || String.valueOf(DS1).contains("OOOO") ||
        String.valueOf(DS2).contains("XXXX") || String.valueOf(DS2).contains("OOOO") ||
        String.valueOf(DS3).contains("XXXX") || String.valueOf(DS3).contains("OOOO") ||
        String.valueOf(DS4).contains("XXXX") || String.valueOf(DS4).contains("OOOO") ||
        String.valueOf(DS5).contains("XXXX") || String.valueOf(DS5).contains("OOOO") ||
        String.valueOf(DS6).contains("XXXX") || String.valueOf(DS6).contains("OOOO") ||
        String.valueOf(DP1).contains("XXXX") || String.valueOf(DP1).contains("OOOO") ||
        String.valueOf(DP2).contains("XXXX") || String.valueOf(DP2).contains("OOOO") ||
        String.valueOf(DP3).contains("XXXX") || String.valueOf(DP3).contains("OOOO") ||
        String.valueOf(DP4).contains("XXXX") || String.valueOf(DP4).contains("OOOO") ||
        String.valueOf(DP5).contains("XXXX") || String.valueOf(DP5).contains("OOOO") ||
        String.valueOf(DP6).contains("XXXX") || String.valueOf(DP6).contains("OOOO") ;
    }

    @Override
    public String toString() {
        String ans = "";
        ans += "DP1 :" + String.valueOf(DP1) +"\n";
        ans += "DP2 :" + String.valueOf(DP2) +"\n";
        ans += "DP3 :" + String.valueOf(DP3) +"\n";
        ans += "DP4 :" + String.valueOf(DP4) +"\n";
        ans += "DP5 :" + String.valueOf(DP5) +"\n";
        ans += "DP6 :" + String.valueOf(DP6) +"\n";
        ans += "\n";
        ans += "DS1 :" + String.valueOf(DS1) +"\n";
        ans += "DS2 :" + String.valueOf(DS2) +"\n";
        ans += "DS3 :" + String.valueOf(DS3) +"\n";
        ans += "DS4 :" + String.valueOf(DS4) +"\n";
        ans += "DS5 :" + String.valueOf(DS5) +"\n";
        ans += "DS6 :" + String.valueOf(DS6) +"\n";
        
        return ans;
    }
    
}
