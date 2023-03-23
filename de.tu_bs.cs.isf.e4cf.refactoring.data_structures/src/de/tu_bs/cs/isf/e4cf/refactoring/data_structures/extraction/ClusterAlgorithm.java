package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction;

public class ClusterAlgorithm {
	
	private void cluster(double[][] distanceMatrix) {
		int[] clusterMap = new int[distanceMatrix.length];

		for (int i = 0; i < distanceMatrix.length; i++) {
			clusterMap[i] = 1;
		}

		do {
			double lowestDistance = Double.MAX_VALUE;
			int lowestIndexI = 0;
			int lowestIndexJ = 0;

			for (int x = 0; x < distanceMatrix.length; x++) {
				for (int y = x; y < distanceMatrix[0].length; y++) {

					if (distanceMatrix[x][y] < lowestDistance && x != y) {
						lowestDistance = distanceMatrix[x][y];
						lowestIndexI = x;
						lowestIndexJ = y;
					}
				}
			}

			double[][] updatedDistanceMatrix = new double[distanceMatrix.length][distanceMatrix[0].length];
			int nodeISize = clusterMap[lowestIndexI];
			int nodeJSize = clusterMap[lowestIndexJ];

			for (int i = 0; i < distanceMatrix.length; i++) {
				for (int j = i; j < distanceMatrix[0].length; j++) {
					if (i == j) {
						updatedDistanceMatrix[i][j] = 0;
					} else if (i == lowestIndexI || j == lowestIndexJ) {
						if (i == lowestIndexI && j == lowestIndexJ) {
							updatedDistanceMatrix[i][j] = 0;
						} else if (i == lowestIndexI) {
							// WPGMA: updatedDistanceMatrix[i][j] = (distanceMatrix[i][j] +
							// distanceMatrix[j][lowestIndexJ])/ (2);
							updatedDistanceMatrix[i][j] = (nodeISize * distanceMatrix[i][j]
									+ nodeJSize * distanceMatrix[j][lowestIndexJ]) / (nodeISize + nodeJSize);
						} else {
							// WPGMA: updatedDistanceMatrix[i][j] = (distanceMatrix[i][j] +
							// distanceMatrix[i][lowestIndexI])/2;
							updatedDistanceMatrix[i][j] = (nodeISize * distanceMatrix[i][j]
									+ nodeJSize * distanceMatrix[i][lowestIndexI]) / ((nodeISize + nodeJSize));
						}
					} else if (i == lowestIndexJ || j == lowestIndexI) {

						if (i == lowestIndexJ && j == lowestIndexI) {
							updatedDistanceMatrix[i][j] = 0;
						} else if (i == lowestIndexJ) {
							// WPGMA: updatedDistanceMatrix[i][j] = (distanceMatrix[i][j] +
							// distanceMatrix[lowestIndexI][j])/2;
							updatedDistanceMatrix[i][j] = (nodeISize * distanceMatrix[i][j]
									+ nodeJSize * distanceMatrix[lowestIndexI][j]) / (nodeISize + nodeJSize);
						} else {
							// WPGMA: updatedDistanceMatrix[i][j] = (distanceMatrix[i][j] +
							// distanceMatrix[lowestIndexJ][i])/2;
							updatedDistanceMatrix[i][j] = (nodeISize * distanceMatrix[i][j]
									+ nodeJSize * distanceMatrix[lowestIndexJ][i]) / (nodeISize + nodeJSize);
						}
					} else {
						updatedDistanceMatrix[i][j] = distanceMatrix[i][j];
					}
					updatedDistanceMatrix[j][i] = updatedDistanceMatrix[i][j];
				}
			}

			double[][] reducedDistanceMatrix = new double[updatedDistanceMatrix.length
					- 1][updatedDistanceMatrix[0].length - 1];
			int k = 0;
			for (int i = 0; i < updatedDistanceMatrix.length - 1; i++, k++) {
				if (i == lowestIndexJ) {
					k++;
				}
				System.arraycopy(updatedDistanceMatrix[k], 0, reducedDistanceMatrix[i], 0, lowestIndexJ);
				System.arraycopy(updatedDistanceMatrix[k], lowestIndexJ + 1, reducedDistanceMatrix[i], lowestIndexJ,
						updatedDistanceMatrix[k].length - 1 - lowestIndexJ);
			}

			int[] newClusterMap = new int[reducedDistanceMatrix.length];

			int r = 0;
			for (int i = 0; i < newClusterMap.length; i++, r++) {

				if (i == lowestIndexI) {
					newClusterMap[i] = clusterMap[r] + clusterMap[lowestIndexJ];
				} else if (i == lowestIndexJ) {
					r++;
					newClusterMap[i] = clusterMap[r];
				} else {
					newClusterMap[i] = clusterMap[r];
				}
			}

			clusterMap = newClusterMap;
			distanceMatrix = reducedDistanceMatrix;
		} while (distanceMatrix.length != 1);
	}
}
