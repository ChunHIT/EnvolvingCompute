package GA;

import java.util.Random;

public class Sharking {
    
    public static int[] Swap(int[] S) {
        //交换操作
        
        Random rand = new Random();
        int I = rand.nextInt(S.length);
        int J = rand.nextInt(S.length);
        
        int tmp = S[I];
        S[I] = S[J];
        S[J] = tmp;
        
        return S;
    }
    
    public static int[] Flip(int[] S) {
        //翻转操作
        
        int[] S0 = new int [S.length];
        
        Random rand = new Random();
        int tmpI = rand.nextInt(S.length);
        int tmpJ = tmpI;
        while(tmpI==tmpJ) {
            tmpJ = rand.nextInt(S.length);
        }
        int I = Math.min(tmpI, tmpJ);
        int J = Math.max(tmpI, tmpJ);
    
        for (int i = 0; i < S0.length;i++) {
            if (i >= I && i <= J) {
                S0[i] = S[I+J-i];
            }else {
                S0[i] = S[i];
            }
        }
        return S0;
    }
    
    public static int[] Insert(int[] S) {
        //插入操作
        
        int[] S0 = new int [S.length];
        
        Random rand = new Random();
        int tmpI = rand.nextInt(S.length);
        int tmpJ = tmpI;
        while(tmpI==tmpJ) {
            tmpJ = rand.nextInt(S.length);
        }
        int I = Math.min(tmpI, tmpJ);
        int J = Math.max(tmpI, tmpJ);
        
        for (int i = 0; i < S0.length;i++) {
            if (i >= I && i < J) {
                S0[i] = S[i+1];
            }else if(i==J){
                S0[i] = S[I];
            }else{
                S0[i] = S[i];
            }
        }
        return S0;
    }
}