#include <iostream>
using namespace std;

int divideChocolate(long long int width, long long int height, long long int targetChocolate);

int main() {
    int tests;
    cin>>tests;
    
    for(int i = 0; i < tests; i++) {
        long long int width;
        cin>>width;
        long long int height;
        cin>>height;
        long long int targetChocolate;
        cin>>targetChocolate;
        
        cout<<divideChocolate(width, height, targetChocolate)<<endl;
    }
    
    return 0;
}

int divideChocolate(long long int width, long long int height, long long int targetChocolate) {
    if (targetChocolate == width * height) {
        return 0;
    }
    
    if (targetChocolate % width == 0 || targetChocolate % height == 0) {
        return 1;
    }
    
    if (width <= height) {
        for(double widthPrime = 1; widthPrime <= 1000000; widthPrime++) {
            if (widthPrime > width) {
                break;
            }
            
            double heightPrime1 = targetChocolate / widthPrime;
            double heightPrime2 = ((width * height) - targetChocolate) / widthPrime;
            
            if ((heightPrime1 <= height && heightPrime1 == (long long int) heightPrime1)
               || (heightPrime2 <= height && heightPrime2 == (long long int) heightPrime2)) {
                return 2;
            }
        }
    } else {
        for(double heightPrime = 1; heightPrime <= 1000000; heightPrime++) {
            if (heightPrime > height) {
                break;
            }
            
            double widthPrime1 = targetChocolate / heightPrime;
            double widthPrime2 = ((width * height) - targetChocolate) / heightPrime;
            
            if ((widthPrime1 <= width && widthPrime1 == (long) widthPrime1)
               || (widthPrime2 <= width && widthPrime2 == (long) widthPrime2)) {
                return 2;
            }
        }
    }
    
    return 3;
}
