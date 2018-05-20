package game.mario;

public interface Behavior
{

    public void update(float deltatime);
    
    public void doAction();
    
    public void endBehavior();
    
}
