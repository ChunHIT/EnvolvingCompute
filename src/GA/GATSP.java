package GA;

import java.util.Random;

public class GATSP {
    
    
    
    public static void main(String[] args) {
        
        long startTime=System.currentTimeMillis();
        
        //�����б�
        //31����TSP���Ž�15377.711
        int MaxGen = 1000;
        int PopSize = 200;
        double[][] xy = Data.XY();
        int N = xy.length;
        
        double[][] DM = DistanceMatrix.DistMatrix(xy);
        int[][] Pop = new int[PopSize][N];
        double[] Trace = new double[MaxGen];
        Pop nowPop = new Pop();
        double bs = 1e10;
        int[] BS = new int[N];
        
        
        //���ɳ�ʼ��Ⱥ
        for (int p = 0; p < PopSize; p++) {
            for (int j = 0; j < N;j++) {
                Pop[p][j] = j + 1;
            }
            //������ɳ�ʼ����
            for (int k = 0; k < N;k++) {
                Random rand = new Random();
                int r = rand.nextInt(N);
                int tmp;
                tmp = Pop[p][r];
                Pop[p][r] = Pop[p][k];
                Pop[p][k] = tmp;
            }
        }
        
        //�������
        for (int gen = 0; gen < MaxGen;gen++) {
            // ������Ⱥ��Ӧ��
            double[] Fit = new double[PopSize];
            int[] indiv = new int[N];
            
            for (int p = 0; p < PopSize;p++) {
                //ȡһ������
                for (int j = 0; j < N;j++) {
                    indiv[j] = Pop[p][j];
                }
                Fit[p] = nowPop.fit(indiv);
            }
            
            //�������Ÿ����Լ����Ÿ������Ӧ��
            double[] SortAfterFit = new double[PopSize];//����һ����Ӧ������
            for (int i = 0; i < PopSize;i++) {
                SortAfterFit[i] = Fit[i];
            }
            double[] BestS = nowPop.Max(Fit);
            double tmpbs = 1/BestS[0];            //��ǰ�����Ž⣨���Ÿ������Ӧ�ȣ�
            if (tmpbs < bs) {
                bs = tmpbs;
                int BestIndex = (int)BestS[1];
                for (int i = 0; i < N; i++) {
                    BS[i] =Pop[BestIndex][i];            //���Ÿ���
                }
            }
            Trace[gen] = bs;
            
//            int[][] newPop = Selection.fitness_proportional(Fit, PopSize, BS, Pop);	//ѡ�����̶�ѡ��
            int[][] newPop = Selection.tournament(Fit, PopSize, Pop, BS);	//ѡ�ý�����ѡ��
            
            //�Ŷ�����
            for (int p = 0; p < PopSize; p++) {
                double R = Math.random();
                
                int[] S = new int[N];
                for (int i = 0; i < N; i++) {
                    S[i] = newPop[p][i];
                }
                
                int[] S0 = new int[N];
                if (R < 0.33) {
                    S0 = Sharking.Swap(S);
                }else if (R > 0.67) {
                    S0 = Sharking.Insert(S);
                }else {
                    S0 = Sharking.Flip(S);
                }
                
                for (int i = 0; i < N; i++) {
                    newPop[p][i] = S0[i];
                }
            }
            
            //������Ⱥ
            for (int p = 0; p < PopSize; p++) {
                for (int i = 0; i < N; i++) {
                    Pop[p][i] = newPop[p][i];
                }
            }
        }//�������
        
        long endTime=System.currentTimeMillis();
        //������
        System.out.println("����"+MaxGen+"�ε��������·������Ϊ:"+bs);
        System.out.println("������ʱ "+(double)(endTime - startTime)/1000+"��.");
        double bs0 = 15377.711;
        System.out.println("�����Ž�����Ϊ"+(bs-bs0)/bs0*100+"%.");
        for (int i = 0; i < MaxGen; i++) {
            System.out.println(i+1+"  "+Trace[i]);
        }
        
        System.out.println("��Ӧ�����·��Ϊ");
        for (int i = 0; i < N; i++) {
            System.out.print(BS[i]+"->");
        }
        System.out.print(BS[0]);
    }
}