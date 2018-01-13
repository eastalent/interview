class Solution {
    public int findRotateSteps(String ring, String key) {
        char[] r = ring.toCharArray();
        char[] k = key.toCharArray();
        int nk = key.length();
        int nr = ring.length();
        int[][] dp = new int[nk][nr];
        for(int i = 0; i < nk; i++){
            Arrays.fill(dp[i], -1);
        }
        for(int i = 0; i < nk; i++){
            for(int j = 0; j < nr; j++){
                if(i == 0 && k[i] == r[j]){
                    if(nr - j < j){
                        dp[i][j] = nr - j + 1;
                    }
                    else{
                        dp[i][j] = j + 1;
                    }
                }
                else if(k[i] == r[j]){
                    for(int m = 0; m < nr; m++){
                        if(dp[i-1][m] == -1)
                            continue;
                        if(j >= m){
                            if(j - m < m + nr - j){
                                if(dp[i][j] == -1){
                                    dp[i][j] = j-m+1 + dp[i-1][m];
                                }
                                else{
                                    dp[i][j] = Math.min(dp[i][j], j - m + 1 + dp[i-1][m]);
                                }
                            }
                            else{
                                if(dp[i][j] == -1){
                                    dp[i][j] = m + nr - j + 1 + dp[i-1][m];
                                }
                                else{
                                    dp[i][j] = Math.min(dp[i][j], m + nr - j + 1 + dp[i-1][m]);
                                }
                            }
                        }
                        else{
                            if(m - j < j + nr - m){
                                if(dp[i][j] == -1){
                                    dp[i][j] = m - j+1 + dp[i-1][m];
                                }
                                else{
                                    dp[i][j] = Math.min(dp[i][j], m - j + 1 + dp[i-1][m]);
                                }
                            }
                            else{
                                if(dp[i][j] == -1){
                                    dp[i][j] = j + nr - m+1 + dp[i-1][m];
                                }
                                else{
                                    dp[i][j] = Math.min(dp[i][j], j + nr - m + 1 + dp[i-1][m]);
                                }
                            }
                        }
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for(int i = 0; i < nr; i++){
            if(dp[nk-1][i] != -1){
                res = Math.min(res, dp[nk-1][i]);
            }
        }
        return res;
    }
}
