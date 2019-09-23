package GA.Selection;

public class RouletteWheelSelection implements SelectionStrategy{
	
	private double[] Cusume(double[] A) {
        //适应于轮盘赌的累加器
        double[] cus = new double[A.length+1];
        
        cus[0]  = 0.0;
        for (int i = 0; i < A.length; i++) {
            cus[i+1] = cus[i] + A[i];
        }
        return cus;
    }
    
    private double Sum(double[] Arr) {
        //求和
        double sum = 0.0;
        for (int i = 0;i <Arr.length;i++) {
            sum += Arr[i];
        }
        return sum;
    }
    
	@Override
	public int[][] Selection(double[] Fit, int PopSize, int[] BS, int[][] Pop, int N) {
		//轮盘赌选择
        double[] cusFit0 = Cusume(Fit);//数组长度变为N+1，补充了首个元素0.0
        double sumFit = Sum(Fit);
            //归一化
        double[] cusFit = new double[cusFit0.length];
        for (int i = 0; i < cusFit.length; i++) {
            cusFit[i] =cusFit0[i] / sumFit;
        }
        
        //交叉操作
        int[][] newPop = new int[PopSize][N];
        for (int q = 0;q < N; q++) {
             newPop[0][q] = BS[q];
         }
        for (int p = 1; p < PopSize; p++) {
            double rand = Math.random();
            for (int r = 0; r < PopSize; r++) {
                if (rand > cusFit[r] && rand <= cusFit[r+1]) {
                    for (int q = 0;q < N; q++) {
                        newPop[p][q] = Pop[r][q];
                    }
                }
            }
        }
        return newPop;
	}
}
