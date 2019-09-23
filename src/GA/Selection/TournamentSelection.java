package GA.Selection;

public class TournamentSelection implements SelectionStrategy {
	@Override
	public int[][] Selection(double[] Fit, int PopSize, int[] BS, int[][] Pop, int N) {
		//Ωı±Í»¸—°‘Ò
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
