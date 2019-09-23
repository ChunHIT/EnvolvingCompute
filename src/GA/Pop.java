package GA;

public class Pop {
    
    public static double fit(int[] Ind, Data data) {
        //计算适应度函数
        double[][] DM = data.distance;
        double dist = 0.0;
        int s = Ind.length;
        
        for (int i0 = 0; i0 < s - 1; i0++) {
            dist += DM[Ind[i0] - 1][Ind[i0 + 1] - 1];
        }
        dist += DM[Ind[Ind.length - 1] - 1][Ind[0] - 1];
        return 1/dist;
    }
    
    public static double[] Max(double[] Fit) {
        //寻找最优个体及相应的位置
        double[] max = new double[2];
        double maxFit = 0.0;
        double maxIndex = -1;
        for (int i = 0; i < Fit.length; i++) {
            if (Fit[i] > maxFit) {
                maxFit = Fit[i];
                maxIndex = (double)i;
            }
        }
        max[0] = maxFit;
        max[1] = maxIndex;
        return max; 
    }
}