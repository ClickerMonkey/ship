#include <stdio.h>

int main()
{
	double cal, fat, carb, protein;
	double calLow, calHigh, fatLow, fatHigh, carbLow, carbHigh, proteinLow, proteinHigh;
	while(1==1)
	{
		scanf("%lf", &cal);
		scanf("%lf", &fat);
		scanf("%lf", &carb);
		scanf("%lf", &protein);
		if(cal==0.0 && fat==0.0 && carb==0.0 && protein==0.0)
			break;

		fatLow = fat-.5;
		fatHigh = fat+.4;
	
		carbLow = carb-.5;
		carbHigh = carb+.4;
	
		proteinLow = protein-.5;
		proteinHigh = protein+.4;
		
		if(fatLow<0)
			fatLow=0;
		if(carbLow<0)
			carbLow=0;
		if(proteinLow<0)
			proteinLow=0;

		calLow = fatLow*9 + carbLow*4 + proteinLow*4;
		calHigh = fatHigh*9 + carbHigh*4 + proteinHigh*4;

		if(cal-0.5 > calHigh && cal+0.4 < calHigh)
			printf("yes\n");
		else
			printf("no\n");
	}
return 0;
} 
