package com.wmd.client.widget.instructor;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Newline;
import com.wmd.client.entity.Text;
import com.wmd.client.widget.EntityWidget;
import com.wmd.client.widget.FractionWidget;
import com.wmd.client.widget.IntegerWidget;
import com.wmd.client.widget.NewlineWidget;
import com.wmd.client.widget.TextWidget;

/**
 * This widget displays buttons for each possible answer type the instructor can
 * choose from. Each button adds an appropriate input field to the panel.
 * 
 * @author AJ Marx, Chris Koch
 * 
 */
public class ComposeWidget extends VerticalPanel
{

	private FlowPanel buttonPanel;
	private HorizontalPanel inputPanel;

	private Button addFractionButton;
	private Button addIntegerButton;
	private Button removeLastButton;
	private Button labelButton;
	private Button newlineButton;

	private ArrayList<EntityWidget<?>> widgets;
	private String title;

	/**
	 * creates empty panel
	 * 
	 * @param title
	 *            - The title of the widget
	 */
	public ComposeWidget(String title)
	{
		this.title = title;
		this.widgets = new ArrayList<EntityWidget<?>>();
		this.init();
	}

	/**
	 * @return Returns the entities
	 */
	public ArrayList<Entity> getEntities()
	{
		ArrayList<Entity> entities = new ArrayList<Entity>(widgets.size());
		for (EntityWidget<?> ew : widgets)
			entities.add(ew.getEntity());
		return entities;
	}

	private void init()
	{
		setStylePrimaryName("question-panel");

		buttonPanel = new FlowPanel();

		inputPanel = new HorizontalPanel();
		inputPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		inputPanel.setStylePrimaryName("answer-panel");

		addFractionButton = new Button("+fraction");
		addIntegerButton = new Button("+integer");
		labelButton = new Button("+text");
		newlineButton = new Button("+next line");
		removeLastButton = new Button("-remove last");

		buttonPanel.add(labelButton);
		buttonPanel.add(addIntegerButton);
		buttonPanel.add(addFractionButton);
		buttonPanel.add(newlineButton);
		buttonPanel.add(removeLastButton);

		removeLastButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				int count = inputPanel.getWidgetCount();
				if (count > 0)
				{
					inputPanel.remove(inputPanel.getWidgetCount() - 1);
					widgets.remove(widgets.size() - 1);
				}
			}
		});

		addFractionButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				addWidget(new FractionWidget());
			}
		});
		addIntegerButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				addWidget(new IntegerWidget());
			}
		});
		labelButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				addWidget(new TextWidget());
			}
		});
		newlineButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				addWidget(new NewlineWidget());
			}
		});

		this.setHorizontalAlignment(ALIGN_CENTER);
		this.add(new HTML("<h1>" + title + "</h1>"));
		this.add(buttonPanel);
		this.add(inputPanel);
		this.setSize("100%", "100%");
	}

	/**
	 * Adds a widget to the panel and the list.
	 * 
	 * @param widget
	 *            The widget to add.
	 */
	public void addWidget(EntityWidget<?> widget)
	{
		widgets.add(widget);
		inputPanel.add(widget);
		widget.requestFocus();
	}

	/**
	 * Sets the entities contained in this ComposeWidget. This will clear any
	 * existing widgets and add new ones.
	 * 
	 * @param entities
	 *            The entities to set in this widget.
	 */
	public void setEntities(ArrayList<Entity> entities)
	{
		widgets.clear();
		inputPanel.clear();

		for (Entity e : entities)
		{
			if (e instanceof Fraction)
			{
				addWidget(new FractionWidget((Fraction) e));
			} else if (e instanceof Integer)
			{
				addWidget(new FractionWidget((Fraction) e));
			} else if (e instanceof Text)
			{
				addWidget(new TextWidget((Text) e));
			} else if (e instanceof Newline)
			{
				addWidget(new NewlineWidget());
			}
		}
	}

}
