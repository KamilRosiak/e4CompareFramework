#compile with: pyinstaller --onefile --hidden-import="sklearn.neighbors._typedefs" --hidden-import="sklearn.utils._weight_vector" --hidden-import="sklearn.neighbors._quad_tree" clustering.py

from sklearn.cluster import AgglomerativeClustering
import numpy as np
import sys


def perform_clustering(threshold, distances):
    clustering = AgglomerativeClustering(n_clusters=None, affinity='precomputed', linkage='complete',
                                         distance_threshold=threshold)
    clustering.fit(distances)
    return clustering.labels_


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
