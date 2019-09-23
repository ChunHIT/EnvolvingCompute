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
			System.out.println("文件读取失败，已经退出！");
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

		// 生成初始种群
		for (int p = 0; p < PopSize; p++) {
			for (int j = 0; j < N; j++) {
				Pop[p][j] = j + 1;
			}
			// 随机生成初始个体
			for (int k = 0; k < N; k++) {
				Random rand = new Random();
				int r = rand.nextInt(N);
				int tmp;
				tmp = Pop[p][r];
				Pop[p][r] = Pop[p][k];
				Pop[p][k] = tmp;
			}
		}

		// 进入迭代
		for (int gen = 0; gen < MaxGen; gen++) {
			// 计算种群适应度
			double[] Fit = new double[PopSize];
			int[] indiv = new int[N];

			for (int p = 0; p < PopSize; p++) {
				// 取一个个体
				for (int j = 0; j < N; j++) {
					indiv[j] = Pop[p][j];
				}
				Fit[p] = nowPop.fit(indiv, data);
			}

			// 更新最优个体以及最优个体的适应度
			double[] SortAfterFit = new double[PopSize];// 拷贝一份适应度数组
			for (int i = 0; i < PopSize; i++) {
				SortAfterFit[i] = Fit[i];
			}
			double[] BestS = nowPop.Max(Fit);
			double tmpbs = 1 / BestS[0]; // 当前代最优解（最优个体的适应度）
			if (tmpbs < bs) {
				bs = tmpbs;
				int BestIndex = (int) BestS[1];
				for (int i = 0; i < N; i++) {
					BS[i] = Pop[BestIndex][i]; // 最优个体
				}
			}
			Trace[gen] = bs;
			int[][] newPop = strategy.Selection(Fit, PopSize, BS, Pop, data.citycount);
			//已经改为使用策略进行选择
			//int[][] newPop = Selection.fitness_proportional(Fit, PopSize, BS, Pop, data.citycount); // 选用轮盘赌选择
			// int[][] newPop = Selection.tournament(Fit, PopSize, Pop, BS, data.citycount);
			// //选用锦标赛选择
			/*
			 * 以下部分移到Section类中轮盘赌选择方法了 //轮盘赌选择 double[] cusFit0 =
			 * Cusume(Fit);//数组长度变为N+1，补充了首个元素0.0 double sumFit = Sum(Fit); //归一化 double[]
			 * cusFit = new double[cusFit0.length]; for (int i = 0; i < cusFit.length; i++)
			 * { cusFit[i] =cusFit0[i] / sumFit; }
			 * 
			 * //交叉操作 int[][] newPop = new int[PopSize][N]; for (int q = 0;q < N; q++) {
			 * newPop[0][q] = BS[q]; } for (int p = 1; p < PopSize; p++) { double rand =
			 * Math.random(); for (int r = 0; r < PopSize; r++) { if (rand > cusFit[r] &&
			 * rand <= cusFit[r+1]) { for (int q = 0;q < N; q++) { newPop[p][q] = Pop[r][q];
			 * } } } }
			 */
			// 扰动操作
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

			// 更新种群
			for (int p = 0; p < PopSize; p++) {
				for (int i = 0; i < N; i++) {
					Pop[p][i] = newPop[p][i];
				}
			}
			System.out.println("当前经过" + gen + "次迭代，最短路径长度为:" + bs);
		} // 结果迭代

		long endTime = System.currentTimeMillis();
		// 结果输出
		System.out.println("经过" + MaxGen + "次迭代，最短路径长度为:" + bs);
		System.out.println("程序用时 " + (double) (endTime - startTime) / 1000 + "秒.");
		double bs0 = 2579;
		System.out.println("与最优解的误差为" + (bs - bs0) / bs0 * 100 + "%.");
		/*
		 * for (int i = 0; i < MaxGen; i++) { System.out.println(i+1+"  "+Trace[i]); }
		 */

		System.out.println("相应的最短路径为");
		for (int i = 0; i < N; i++) {
			System.out.print(BS[i] + "->");
		}
		System.out.print(BS[0]);

	}

	public static void main(String[] args) throws IOException {
		
		new GATSP(10000, 200, "a280", new RouletteWheelSelection()).StartEC();
	}
}