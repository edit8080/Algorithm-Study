/*
* 1. 피보나치 수열은 [F(n+1), [1 1  [F(n)
		    F(n)] =  1 0]  F(n-1)] 와 같이 선형대수 형태로 표현할 수 있다.
*                                n
* 2. 이를 일반화하면 [F(n+1), [1 1  [1
*                    F(n)] = 1 0]  0] 과 같이 표현할 수 있다.
*
* 3. 따라서 행렬의 n제곱을 (n/2 제곱)*(n/2 제곱) 으로 분할하여 계산한다.
*
* 주의) 피보나치를 선형대수를 통해 행렬의 곱으로 변환하는 것에 유의한다.
*
* 시간복잡도 = O(lg n) (n: n번째 피보나치 수)
*/

#include <iostream>
#include <vector>

#define MOD 1000000007

using namespace std;

typedef vector<vector<long long>> matrix_vector;
matrix_vector initial = { {1,1},{1,0} };

matrix_vector multiply_matrix(matrix_vector a, matrix_vector b) {
	matrix_vector ret(2, vector<long long>(2));

	ret[0][0] = (a[0][0] * b[0][0] + a[0][1] * b[1][0]) % MOD;
	ret[0][1] = (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % MOD;
	ret[1][0] = (a[1][0] * b[0][0] + a[1][1] * b[1][0]) % MOD;
	ret[1][1] = (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % MOD;

	return ret;
}

matrix_vector fibo_matrix(long long n) {
	if (n == 1)
		return initial;

	matrix_vector divide = fibo_matrix(n / 2);

	if (n % 2 == 1)
		return multiply_matrix(initial, multiply_matrix(divide, divide));
	else
		return multiply_matrix(divide, divide);
}

int main() {
	long long n;
	cin >> n;

	if (n == 0)
		cout << 0;
	else {
		matrix_vector matrix = fibo_matrix(n);
		cout << matrix[1][0];
	}
}
