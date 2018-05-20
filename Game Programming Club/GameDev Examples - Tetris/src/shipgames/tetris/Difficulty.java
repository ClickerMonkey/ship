package shipgames.tetris;

public enum Difficulty
{
	Easy   (0.015f, 1.0f, 0.40f),
	Medium (0.030f, 1.5f, 0.35f),
	Hard   (0.045f, 2.0f, 0.30f);
	
	private float increase = 0f;
	private float bonus = 0f;
	private float initialSpeed = 0f;
	
	private Difficulty(float increase, float bonus, float initialSpeed) 
	{
		this.increase = increase;
		this.bonus = bonus;
		this.initialSpeed = initialSpeed;
	}
	
	public float getIncrease()
	{
		return increase;
	}
	
	public float getBonus()
	{
		return bonus;
	}
	
	public float getInitialSpeed()
	{
		return initialSpeed;
	}
	
	public float getInterval(int level)
	{
		return (initialSpeed - (increase * level));
	}
	
	public static Difficulty next(Difficulty d)
	{
		switch (d)
		{
		case Easy: return Medium;
		case Medium: return Hard;
		case Hard: return Easy;
		}
		return Easy;
	}
	
}
