package GA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.text.Position;

import GA.Selection.RouletteWheelSelection;
import GA.Selection.Selection;
import GA.Selection.SelectionStrategy;
import GA.Selection.TournamentSelection;

public class GATSP {

	int MaxGen;
	int PopSize;
	String dataName;
	SelectionStrategy strategy;
	public GATSP(int MaxGen, int PopSize , String dataName , SelectionStrategy strategy) {
		this.MaxGen = MaxGen;
		this.PopSize = PopSize;
		this.dataName = dataName;
		this.strategy = strategy;
	}
	
	public void StartEC() {
		long startTime = System.currentTimeMillis();
		Data data;
		try {
			data = new Data("./ALL_tsp/" + dataName);
		} catch (IOException e) {
			System.out.println("�ļ���ȡʧ�ܣ��Ѿ��˳���");
			return;
		}
		double[][] xy = data.distance;
		// System.out.print(xy.length);
		int N = xy.length;

		double[][] DM = data.distance;
		int[][] Pop = new int[PopSize][N];
		double[] Trace = new double[MaxGen];
		Pop nowPop = new Pop();
		double bs = 1e10;
		int[] BS = new int[N];

		// ���ɳ�ʼ��Ⱥ
		for (int p = 0; p < PopSize; p++) {
			for (int j = 0; j < N; j++) {
				Pop[p][j] = j + 1;
			}
			// ������ɳ�ʼ����
			for (int k = 0; k < N; k++) {
				Random rand = new Random();
				int r = rand.nextInt(N);
				int tmp;
				tmp = Pop[p][r];
				Pop[p][r] = Pop[p][k];
				Pop[p][k] = tmp;
			}
		}

		// �������
		for (int gen = 0; gen < MaxGen; gen++) {
			// ������Ⱥ��Ӧ��
			double[] Fit = new double[PopSize];
			int[] indiv = new int[N];

			for (int p = 0; p < PopSize; p++) {
				// ȡһ������
				for (int j = 0; j < N; j++) {
					indiv[j] = Pop[p][j];
				}
				Fit[p] = nowPop.fit(indiv, data);
			}

			// �������Ÿ����Լ����Ÿ������Ӧ��
			double[] SortAfterFit = new double[PopSize];// ����һ����Ӧ������
			for (int i = 0; i < PopSize; i++) {
				SortAfterFit[i] = Fit[i];
			}
			double[] BestS = nowPop.Max(Fit);
			double tmpbs = 1 / BestS[0]; // ��ǰ�����Ž⣨���Ÿ������Ӧ�ȣ�
			if (tmpbs < bs) {
				bs = tmpbs;
				int BestIndex = (int) BestS[1];
				for (int i = 0; i < N; i++) {
					BS[i] = Pop[BestIndex][i]; // ���Ÿ���
				}
			}
			Trace[gen] = bs;
			int[][] newPop = strategy.Selection(Fit, PopSize, BS, Pop, data.citycount);
			//�Ѿ���Ϊʹ�ò��Խ���ѡ��
			//int[][] newPop = Selection.fitness_proportional(Fit, PopSize, BS, Pop, data.citycount); // ѡ�����̶�ѡ��
			// int[][] newPop = Selection.tournament(Fit, PopSize, Pop, BS, data.citycount);
			// //ѡ�ý�����ѡ��
			/*
			 * ���²����Ƶ�Section�������̶�ѡ�񷽷��� //���̶�ѡ�� double[] cusFit0 =
			 * Cusume(Fit);//���鳤�ȱ�ΪN+1���������׸�Ԫ��0.0 double sumFit = Sum(Fit); //��һ�� double[]
			 * cusFit = new double[cusFit0.length]; for (int i = 0; i < cusFit.length; i++)
			 * { cusFit[i] =cusFit0[i] / sumFit; }
			 * 
			 * //������� int[][] newPop = new int[PopSize][N]; for (int q = 0;q < N; q++) {
			 * newPop[0][q] = BS[q]; } for (int p = 1; p < PopSize; p++) { double rand =
			 * Math.random(); for (int r = 0; r < PopSize; r++) { if (rand > cusFit[r] &&
			 * rand <= cusFit[r+1]) { for (int q = 0;q < N; q++) { newPop[p][q] = Pop[r][q];
			 * } } } }
			 */
			// �Ŷ�����
			for (int p = 0; p < PopSize; p++) {
				double R = Math.random();

				int[] S = new int[N];
				for (int i = 0; i < N; i++) {
					S[i] = newPop[p][i];
				}

				int[] S0 = new int[N];
				if (R < 0.33) {
					S0 = Sharking.Swap(S);
				} else if (R > 0.67) {
					S0 = Sharking.Insert(S);
				} else {
					S0 = Sharking.Flip(S);
				}

				for (int i = 0; i < N; i++) {
					newPop[p][i] = S0[i];
				}
			}

			// ������Ⱥ
			for (int p = 0; p < PopSize; p++) {
				for (int i = 0; i < N; i++) {
					Pop[p][i] = newPop[p][i];
				}
			}
			System.out.println("��ǰ����" + gen + "�ε��������·������Ϊ:" + bs);
		} // �������

		long endTime = System.currentTimeMillis();
		// ������
		System.out.println("����" + MaxGen + "�ε��������·������Ϊ:" + bs);
		System.out.println("������ʱ " + (double) (endTime - startTime) / 1000 + "��.");
		double bs0 = 2579;
		System.out.println("�����Ž�����Ϊ" + (bs - bs0) / bs0 * 100 + "%.");
		/*
		 * for (int i = 0; i < MaxGen; i++) { System.out.println(i+1+"  "+Trace[i]); }
		 */

		System.out.println("��Ӧ�����·��Ϊ");
		for (int i = 0; i < N; i++) {
			System.out.print(BS[i] + "->");
		}
		System.out.print(BS[0]);

	}

	public static void main(String[] args) throws IOException {
		
		new GATSP(10000, 200, "a280", new RouletteWheelSelection()).StartEC();
	}
}