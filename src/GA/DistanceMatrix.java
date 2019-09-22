package GA;

public class DistanceMatrix {
    public static double[][] DistMatrix(double[][] xy){
        //¼ÆËã¾àÀë¾ØÕó
        int N = xy.length;
        double[][] DM = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                DM[i][j] = Math.hypot(xy[i][0] - xy[j][0],xy[i][1] - xy[j][1]);
            }
        }
        return DM;
    }
}