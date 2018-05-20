#include <stdio.h>

int solve(int N, int T1, int T2, int T3)
{
	int r1 = T1 - T2;
	if (r1 < 0)
		r1 += N;
	int r2 = T3 - T2;
	if (r2 < 0)
		r2 += N;
	return 6 * N - 1 - r1 - r2;
}

int main()
{
	char happy = 1;
	do
	{
		int N, T1, T2, T3;
		scanf("%d", &N);
		scanf("%d", &T1);
		scanf("%d", &T2);
		scanf("%d", &T3);
		happy = (N + T1 + T2 + T3 > 0);
		if (happy) 
			printf("%d\n", solve(N, T1, T2, T3));
	} while (happy);
}
