package GA;

public class Selection {

	public static double[] Cusume(double[] A) {
        //适应于轮盘赌的累加器
        double[] cus = new double[A.length+1];
        
        cus[0]  = 0.0;
        for (int i = 0; i < A.length; i++) {
            cus[i+1] = cus[i] + A[i];
        }
        return cus;
    }
	
	public static double Sum(double[] Arr) {
        //求和
        double sum = 0.0;
        for (int i = 0;i <Arr.length;i++) {
            sum += Arr[i];
        }
        return sum;
    }
	
	public static int[][] fitness_proportional(double[] Fit, int PopSize, int[] BS, int[][] Pop) {
		//轮盘赌选择
		int N = Data.XY().length;
        double[] cusFit0 = Cusume(Fit);//数组长度变为N+1，补充了首个元素0.0
        double sumFit = Sum(Fit);
            //归一化
        double[] cusFit = new double[cusFit0.length];
        for (int i = 0; i < cusFit.length; i++) {
            cusFit[i] =cusFit0[i] / sumFit;
        }
        
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
	
	public static int[][] tournament(double[] Fit, int PopSize, int[][] Pop, int[] BS) {
		//锦标赛选择
		int N = Data.XY().length;
		int n = PopSize / 10;
		int[][] p = new int[PopSize][N];
		for (int i = 0; i < PopSize; i++) {
			for (int j = 0; j < N; j++) {
				p[i][j] = Pop[i][j];
			}
		}
		int[][] newPop = new int[PopSize][N];
		int[] flag = new int[PopSize];
		for (int q = 0;q < N; q++) {
            newPop[0][q] = BS[q];
        }
		for (int i = 1; i < PopSize; i++) {
			int[] num = new int[n];
			for (int j = 0; j < n; j++) {
				int rand = (int) (Math.random() * PopSize);
				if (flag[rand] == 0) {
					flag[rand] = 1;
					num[j] = rand;
				} else {
					for (int k = rand; k < PopSize; k++) {
						if (flag[k] == 0) {
							flag[k] = 1;
							num[j] = k;
							break;
						}
					}
				}
			}
			double max = 0;
			int k = -1;
			for (int j = 0; j < n; j++) {
				if (Fit[num[j]] > max) {
					max = Fit[num[j]];
					k = j;
				}
			}
			for (int j = 0; j < N; j++) {
				newPop[i][j] = Pop[num[k]][j];
			}
			for (int j = 0; j < n; j++) {
				if (j != k) {
					flag[num[j]] = 0;
				}
			}
		}
		return newPop;
	}
}
