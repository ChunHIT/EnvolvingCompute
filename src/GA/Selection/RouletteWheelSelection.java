package GA.Selection;

public class RouletteWheelSelection implements SelectionStrategy{
	
	private double[] Cusume(double[] A) {
        //��Ӧ�����̶ĵ��ۼ���
        double[] cus = new double[A.length+1];
        
        cus[0]  = 0.0;
        for (int i = 0; i < A.length; i++) {
            cus[i+1] = cus[i] + A[i];
        }
        return cus;
    }
    
    private double Sum(double[] Arr) {
        //���
        double sum = 0.0;
        for (int i = 0;i <Arr.length;i++) {
            sum += Arr[i];
        }
        return sum;
    }
    
	@Override
	public int[][] Selection(double[] Fit, int PopSize, int[] BS, int[][] Pop, int N) {
		//���̶�ѡ��
        double[] cusFit0 = Cusume(Fit);//���鳤�ȱ�ΪN+1���������׸�Ԫ��0.0
        double sumFit = Sum(Fit);
            //��һ��
        double[] cusFit = new double[cusFit0.length];
        for (int i = 0; i < cusFit.length; i++) {
            cusFit[i] =cusFit0[i] / sumFit;
        }
        
        //�������
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
