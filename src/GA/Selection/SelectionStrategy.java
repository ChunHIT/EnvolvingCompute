package GA.Selection;

public interface SelectionStrategy {
	int[][] Selection(double[] Fit, int PopSize, int[] BS, int[][] Pop, int N);
}
