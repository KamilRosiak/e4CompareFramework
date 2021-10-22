#compile with: pyinstaller --onefile clustering_scipy.py

from scipy.cluster.hierarchy import complete, fcluster
from scipy.spatial.distance import squareform
import numpy as np
import sys


def perform_clustering(threshold, distances):
    distances = squareform(distances)
    z = complete(distances)
    clusters = fcluster(z, t=threshold, criterion='distance')   
    return clusters


if __name__ == '__main__':

    while True:
        val = input()        
        val = val.split()

        distances = np.array(eval(val[0]))
        threshold = float(val[1]) + 0.00000001
        labels = perform_clustering(threshold, distances)

        for i in range(labels.size):
            print(labels[i], end="")
            if i != labels.size - 1:
                print(",", end="")
        print(f"\n")
