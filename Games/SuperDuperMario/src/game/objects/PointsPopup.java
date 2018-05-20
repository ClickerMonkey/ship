package game.objects;

import static game.GameConstants.*;
import net.philsprojects.game.IDraw;
import net.philsprojects.game.IName;
import net.philsprojects.game.IUpdate;
import net.philsprojects.game.Text;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;

/**
 * The effect that happens when Mario hits anything that gives him points. It appears then after a certain
 *      amount of seconds disappears.
 *     
 * @author Philip Diffenderfer
 */
public class PointsPopup implements IDraw, IUpdate, IName
{
    private static final Text _print = new Text(TILESHEET_TEXTURE, "0123456789xUPC", 0, 244, 32, 37, 2, 12, 14);
    
    private String _points;
    private float _time = 0f;
    private float _x = 0f;
    private float _y = 0f;
    private Color _shade = Color.white();
    
    public PointsPopup(BoundingBox bounds, String points)
    {
	_x = bounds.getCenterX(); // - (points.length() / 2f * _print.getCharWidth());
	_y = bounds.getCenterY();
	_points = points;
    }
    
    public void draw()
    {
	_print.draw(_points, (int)_x, (int)_y, true, _shade);
    }
    
    public void update(float deltatime)
    {
	_y += POINTPOPUP_RISESPEED * deltatime;
	//_shade.setA(1f - _time / POINTPOPUP_LIFETIME);
	_time += deltatime;
    }

    public boolean isVisible()
    {
	return true;
    }

    public void setVisible(boolean visible)
    {
    }

    public boolean isEnabled()
    {
	return (_time < POINTPOPUP_LIFETIME);
    }

    public String getName()
    {
	return "";
    }
    
}
