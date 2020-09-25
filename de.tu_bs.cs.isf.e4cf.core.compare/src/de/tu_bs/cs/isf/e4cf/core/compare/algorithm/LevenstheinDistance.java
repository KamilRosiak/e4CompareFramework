package de.tu_bs.cs.isf.e4cf.core.compare.algorithm;

public class LevenstheinDistance {

	private static int minimum(int a, int b, int c) {                            
		return Math.min(Math.min(a, b), c);                                      
    }                                                                            
                                                                                 
    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    }
    
    public static int computeDamerauLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++) {
        	for (int j = 1; j <= rhs.length(); j++) {
        		int cost = lhs.charAt(i - 1) == rhs.charAt(j - 1) ? 0 : 1;
        		distance[i][j] = minimum(                                        
        				distance[i - 1][j] + 1,                                  
        				distance[i][j - 1] + 1,                                  
        				distance[i - 1][j - 1] + cost);
        		
        		if (i > 1 && j > 1 && lhs.charAt(i-2) == rhs.charAt(j-1) && lhs.charAt(i-1) == rhs.charAt(j-2)) {
        			distance[i][j] = Math.min(
        					distance[i-2][j-2] + cost,
        					distance[i][j]);
        		}
        	}
        }
        return distance[lhs.length()][rhs.length()];                           
    }
}
