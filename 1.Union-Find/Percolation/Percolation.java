
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int[][] opended;
	private int[] idx;
	private int len;
	private int idxLen;
	private WeightedQuickUnionUF weightedQuickUnionUF;

	private boolean isValid(int row, int col) {
		if (row <= 0 || row > len || col <= 0 || col > len) {
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		} else {
			return true;
		}
	}

	private int xyTo1D(int row, int col) {
		return (row - 1) * len + col;
	}

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n is an illegal argument");
		}
		len = n;
		idxLen = (int) Math.pow(len, 2) + 2;
		opended = new int[len][len];
		idx = new int[idxLen];
		weightedQuickUnionUF = new WeightedQuickUnionUF(idxLen);

		for (int i = 0; i < idx.length; i++) {
			idx[i] = i;
		}
		for (int i = 0; i < len; i++) {
			weightedQuickUnionUF.union(i, i + 1);
			weightedQuickUnionUF.union(idxLen - 1, idxLen - i - 2);
		}
	}

	public void open(int row, int col) {
		try {
			isValid(row, col);
			opended[row - 1][col - 1] = 1;
			int cur_pos_in_idx = xyTo1D(row, col);
			int tmp_pos_in_idx = 0;

			try {
				isValid(row - 1, col);
				if (isOpen(row - 1, col)) {
					tmp_pos_in_idx = xyTo1D(row - 1, col);
					weightedQuickUnionUF.union(cur_pos_in_idx, tmp_pos_in_idx);
				}
			} catch (Exception e) {
			}
			try {
				isValid(row + 1, col);
				if (isOpen(row + 1, col)) {
					tmp_pos_in_idx = xyTo1D(row + 1, col);
					weightedQuickUnionUF.union(cur_pos_in_idx, tmp_pos_in_idx);
				}
			} catch (Exception e) {
			}
			try {
				isValid(row, col - 1);
				if (isOpen(row, col - 1)) {
					tmp_pos_in_idx = xyTo1D(row, col - 1);
					weightedQuickUnionUF.union(cur_pos_in_idx, tmp_pos_in_idx);
				}
			} catch (Exception e) {
			}
			try {
				isValid(row, col + 1);
				if (isOpen(row, col + 1)) {
					tmp_pos_in_idx = xyTo1D(row, col + 1);
					weightedQuickUnionUF.union(cur_pos_in_idx, tmp_pos_in_idx);
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
	}

	public boolean isOpen(int row, int col) {
		if (row <= 0 || row > len || col <= 0 || col > len) {
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
		if (opended[row - 1][col - 1] == 0)
			return false;
		return true;
	}

	public boolean isFull(int row, int col) {
		if (row <= 0 || row > len || col <= 0 || col > len) {
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
		if (isOpen(row, col)) {
			if (weightedQuickUnionUF.connected(0, xyTo1D(row, col))) {
				return true;
			}
		}
		return false;
	}

	public boolean percolates() {
		if (weightedQuickUnionUF.connected(0, idxLen - 1)) {
			if (len == 1 && !isOpen(1, 1))
				return false;
			return true;
		}
		return false;
	}
}
