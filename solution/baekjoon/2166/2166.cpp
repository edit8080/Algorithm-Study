/*
* 1. 사선공식을 통해 입력받은 좌표에 대한 면적을 계산한다.
*
* 시간복잡도 = O(n)
*/

#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

int main() {
	int n;
	cin >> n;
	double area = 0;
	vector<double> x(n + 1);
	vector<double> y(n + 1);

	for (int i = 0; i < n; i++)
		cin >> x[i] >> y[i];

	x[n] = x[0];
	y[n] = y[0];

	for (int i = 0; i < n; i++)
		area += x[i] * y[i + 1];

	for (int i = 1; i <= n; i++)
		area -= x[i] * y[i - 1];

	area = round(abs(area / 2) * 10) / 10.0;

	printf("%.1lf", area);
}
