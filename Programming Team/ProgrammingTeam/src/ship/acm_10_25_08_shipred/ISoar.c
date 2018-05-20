#include <stdio.h>

double min(double a, double b)
{
	return (a < b ? a : b);
}

double max(double a, double b)
{
	return (a > b ? a : b);
}

int intersects(int *b, double left, double right)
{
	return (right >= b[0] && left <= b[1]);
}

int* add(int *b, double left, double right)
{
	if (!intersects(b, left, right))
		return NULL;
	b[0] = min(b[0], left);
	b[1] = max(b[1], right);
	return b;
}

int main()
{
	int **bounds = (**int)malloc(sizeof(int) * 2000);
	int i;
	for (i = 0; i < 1000; i++)
		bounds[i] = (*int)malloc(sizeof(int) * 2);
	int count = 0;

	double length, left, right;

	scanf("%lf", &length);
	while (length >= 0.0)
	{
		scanf("%lf", &left);
		scanf("%lf", &right);
		while (left <= right)
		{
				
		}

		scanf("%lf", &length);
	}

}
