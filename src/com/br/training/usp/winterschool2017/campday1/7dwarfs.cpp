#include <bits/stdc++.h>
using namespace std;

int main() {
    
    long long int packages = 0, n = 0;
    cin>>packages;
    vector<long long int> pack;
    
    for(int i = 0; i < packages; i++) {
        cin>>n;
        pack.push_back(n);
    }
    
    vector<long long int> dp(7);
    vector<long long int> aux(7);
    
    for(int i = 0; i < pack.size(); i++) {
        //Copy current dp values
        for(int j = 0; j < dp.size(); j++) {
            aux[j] = dp[j];
        }
        
        for(int j = 0; j < dp.size(); j++) {
            long long int sum = aux[j] + pack[i];
            long long int mod = sum % 7;
            
            if (dp[mod] < sum) {
                dp[mod] = sum;
            }
        }
    }
    
    cout<<dp[0]<<endl;
    
    return 0;
}
