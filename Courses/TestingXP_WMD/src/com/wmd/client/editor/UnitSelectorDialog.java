package com.wmd.client.editor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.wmd.client.service.GetUnitsService;
import com.wmd.client.service.GetUnitsServiceAsync;
import com.wmd.client.service.SaveUnitService;
import com.wmd.client.service.SaveUnitServiceAsync;

/**
 * A dialog box for creating a list of units.
 * 
 * @author Philip Diffenderfer, Eric Abruzzese
 *
 */
public class UnitSelectorDialog extends DialogBox
{
	
	// The service for returning all units in the database.
	private static final GetUnitsServiceAsync serviceGetUnits = 
		GWT.create(GetUnitsService.class);
	
	// The service for adding a new unit to the database.
	private static final SaveUnitServiceAsync serviceSaveUnit = 
		GWT.create(SaveUnitService.class);
	
	
	private final SuggestBox suggestBox;
	private final MultiWordSuggestOracle oracle;
	
	private final ListBox availableUnits;
	private final ListBox selectedUnits;

	private final ListBox correctUnit;

	private final Button addToSelected;
	private final Button removeFromSelected;
	
	private final FlexTable container = new FlexTable();

	private final UnitSelectorListener listener;
	
	private final Button saveButton;
	
	/**
	 * Initializes a new UnitSelectorDialog given an initial list of units.
	 *  
	 * @param units The initial list of selected units.
	 */
	public UnitSelectorDialog(UnitSelectorListener listener,  ArrayList<String> units) 
	{
		this(listener);
		
		// Add the given units to the selected list.
		for (String unit : units) 
		{
			addSelectedUnit(unit);
		}
	}
	
	/**
	 * Instantiates a new UnitSelectorDialog.
	 */
	public UnitSelectorDialog(UnitSelectorListener listener)
	{
		this.listener = listener;
		this.initialize();
		
		oracle = new MultiWordSuggestOracle();
		
		correctUnit = new ListBox();
		correctUnit.setWidth("100%");
		correctUnit.addItem("Please select at least one unit.");
		
		suggestBox = new SuggestBox(oracle);
		suggestBox.setWidth("98%");
		suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event)
			{
				handleSuggestion(event.getSelectedItem());
			}
		});
		suggestBox.getTextBox().addKeyUpHandler(new KeyUpHandler(){
			@Override
			public void onKeyUp(KeyUpEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					handleNewUnit(suggestBox.getText());
				}
			}
		});
		
		availableUnits = new ListBox(true);
		availableUnits.setVisibleItemCount(10);
		availableUnits.setWidth("100%");
		
		selectedUnits = new ListBox(true);
		selectedUnits.setVisibleItemCount(10);
		selectedUnits.setWidth("100%");
		
		addToSelected = new Button();
		addToSelected.setHTML("<h3>&raquo;</h3>");
		addToSelected.setWidth("100%");
		addToSelected.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				addSelected();
			}
		});
		
		removeFromSelected = new Button();
		removeFromSelected.setHTML("<h3>&laquo;</h3>");
		removeFromSelected.setWidth("100%");
		removeFromSelected.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				removeSelected();
			}
		});
		
		saveButton = new Button();
		saveButton.setText("Save Unit Selection");
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				saveUnits();
			}
		});
		
		// Call the service to populate the units in the dialog
		serviceGetUnits.getUnits(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught)
			{
			}
			@Override
			public void onSuccess(List<String> result)
			{
				populate(result);
			}
		});
		
		this.layoutElements();
	}
	
	/**
	 * Initializes the common attributes of the dialogbox for use 
	 * with the overloaded constructors.
	 */
	private void initialize()
	{
		// Lock the screen with "glass" and turn on animation
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);

		// Set the dialog box content to the container table
		this.container.setWidth("500px");
		this.container.setHeight("350px");
		this.setWidget(this.container);
		this.setHTML("<h2>Unit Selector</h2>");

		// Center the dialogbox on the screen and display it
		this.center();
	}
	
	/**
	 * Initializes the elements in the dialog box.
	 */
	private void layoutElements()
	{
		container.setWidget(0, 0, new HTML("Please select your relevant units below. You may begin typing in the box below to see suggested " +
											"units for your query (for example, type &quot;m&quot; for suggestions like &quot;millimeter&quot;, " +
											"&quot;meter&quot;, etc.). Use the list of units below to add units to your &quot;selected units&quot; " +
											"box below.<br/><br/><hr/><br/>"));
		container.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		container.setWidget(1, 0, suggestBox);
		container.getFlexCellFormatter().setColSpan(1, 0, 3);

		container.setWidget(2, 0, new HTML("<h2>Available Units</h2>"));
		container.setWidget(3, 0, availableUnits);
		container.getFlexCellFormatter().setWidth(2, 0, "240px");

		container.getFlexCellFormatter().setWidth(2, 1, "20px");
		
		FlexTable listControl = new FlexTable();
		listControl.setWidth("100%");
		listControl.setWidget(0, 0, addToSelected);
		listControl.setWidget(1, 0, removeFromSelected);
		
		container.setWidget(3, 1, listControl);
		
		container.setWidget(2, 2, new HTML("<h2>Selected Units</h2>"));
		container.setWidget(3, 2, selectedUnits);
		container.getFlexCellFormatter().setWidth(2, 2, "240px");
		
		container.setWidget(4, 0, new HTML("Correct answer:"));
		container.getFlexCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		container.setWidget(4, 1, correctUnit);
		container.getFlexCellFormatter().setColSpan(4, 1, 2);

		container.setWidget(5, 0, saveButton);
		
		Button cancelButton = new Button();
		cancelButton.setText("Cancel");
		cancelButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event)
			{
				hide();
			}
			
		});
		container.setWidget(5, 2, cancelButton);
		container.getFlexCellFormatter().setHorizontalAlignment(5, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		
	}

	/**
	 * Populates the suggestbox and availableUnits with a set of units.
	 * 
	 * @param units The list of units to populate with.
	 */
	private void populate(List<String> units)
	{
		if (units == null) 
		{
			return;
		}
		
		oracle.clear();
		oracle.addAll(units);

		availableUnits.clear();
		for (String unit : units)
		{
			availableUnits.addItem(unit);
		}
	}
	
	/**
	 * Adds a unit to the selected units and correct unit list.
	 * 
	 * @param unit The unit to add.
	 */
	public void addSelectedUnit(String unit)
	{
		selectedUnits.addItem(unit);
		correctUnit.addItem(unit);
	}
	
	/**
	 * Removes the unit at the given index from the selected unit list and
	 * the correct unit.
	 * 
	 * @param index The index of the unit
	 */
	public void removeSelectedUnit(int index)
	{
		selectedUnits.removeItem(index);
		correctUnit.removeItem(index + 1);
	}
	
	/**
	 * Handles adding a unit that existed in the available list.
	 * 
	 * @param suggest The suggestion from the SuggestBox.
	 */
	private void handleSuggestion(Suggestion suggest)
	{
		// Get the unit suggested.
		String add = suggest.getReplacementString();
		
		// Get the list of current units
		ArrayList<String> units = getItems(selectedUnits);
		
		// If the unit doesn't exist then add it
		if (!units.contains(add)) 
		{
			addSelectedUnit(add);
		}
		
		// Clear the text.
		suggestBox.setText("");
	}
	
	/**
	 * Handles adding a new unit that doesn't exist in the available list.
	 * 
	 * @param unit The unit to add.
	 */
	private void handleNewUnit(final String unit)
	{
		// If the unit is blank ignore call.
		if (unit.trim().length() == 0) 
		{
			return;
		}
		
		// Get the list of current units
		ArrayList<String> units = getItems(selectedUnits);
		
		// If the unit is not in the list...
		if (!units.contains(unit))
		{
			
			// Get the list of available items.
			ArrayList<String> available = getItems(availableUnits);
			
			// If the list doesn't contain the unit...
			if (!available.contains(unit)) 
			{

				String prompt = "The unit '" + unit + "' doesn't exist in the " +
						"database, would you like to permanently save it in the " +
						"database?";
			
				// Confirm saving to the database.
				if (Window.confirm(prompt))
				{
					
					// Call the save unit service.
					serviceSaveUnit.saveUnit(unit, new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught)
						{
							Window.alert(caught.getMessage());
						}
						@Override
						public void onSuccess(Boolean result)
						{
							// Error adding to the database.
							if (!result) {
								Window.alert("Error adding to the database, " +
										"adding to the list anyway.");
							}
							// Add it to the list no matter.
							availableUnits.addItem(unit);
							addSelectedUnit(unit);
						}
					});
				}
				else 
				{
					// They don't want to save it but they still want it in 
					// the list of options.
					addSelectedUnit(unit);
				}
			} 
			else
			{
				addSelectedUnit(unit);
			}
		}

		// Clear the text.
		suggestBox.setText("");
	}
	
	/**
	 * Saves the units by checking this dialog has valid unit data and if it
	 * does the listener will be notified and this dialog will be closed.
	 */
	private void saveUnits()
	{
		// Get the list of selected units
		ArrayList<String> units = getSelectedUnits();
		
		// If the unit list is empty then don't proceed.
		if (units == null || units.size() == 0)
		{
			Window.alert("No units selected");
			return;
		}
		
		// Get the correct answer selected.
		String correct = getCorrect();
		
		// Check if they selected a correct answer
		if (correct == null) 
		{
			Window.alert("You must select the correct unit");
			return;
		}
		
		// Notify the listener of a succesful unit selection.
		listener.unitsSelected(units, correct);
		
		// Close this dialog box
		this.hide();
	}
	
	/**
	 * Adds the selected items from the availableUnits ListBox to the
	 * selectedUnits ListBox.
	 */
	private void addSelected() 
	{
		// Build a list of selected units
		ArrayList<Integer> selected = takeSelected(availableUnits);
		// Get the list of current units
		ArrayList<String> units = getItems(selectedUnits);

		// Add in each item thats selected into the selectedUnits list only if
		// it already doesn't exist in the list.
		for (Integer index : selected) 
		{
			String current = availableUnits.getItemText(index);
			if (!units.contains(current)) 
			{
				addSelectedUnit(current);
			}
		}
	}
	
	/**
	 * Removes the items selected in the selectedUnits ListBox.
	 */
	private void removeSelected()
	{
		// Build a list of selected units. The array returned has indices 
		// ordered in ascending.
		ArrayList<Integer> selected = takeSelected(selectedUnits);
		
		// Go backwards and remove all selected items
		for (int i = selected.size() - 1; i >= 0; i--)
		{
			removeSelectedUnit(selected.get(i));
		}
	}
	
	/**
	 * Given a ListBox this will return a list of all items selected as well as
	 * unselecting them in the process.
	 * 
	 * @param list The ListBox to take the selected items from.
	 * @return The ArrayList built.
	 */
	private ArrayList<Integer> takeSelected(ListBox list)
	{
		ArrayList<Integer> selected = new ArrayList<Integer>();
		int count = list.getItemCount();
		for (int i = 0; i < count; i++)
		{
			if (list.isItemSelected(i)) 
			{
				selected.add(i);
				list.setItemSelected(i, false);
			}
		}
		return selected;
	}
	
	/**
	 * Given a ListBox this returns a list of the item strings in the list.
	 * 
	 * @param list The ListBox to get the items from.
	 * @return The ArrayList built.
	 */
	private ArrayList<String> getItems(ListBox list)
	{
		ArrayList<String> items = new ArrayList<String>();
		int count = list.getItemCount();
		for (int i = 0; i < count; i++) 
		{
			items.add(list.getItemText(i));
		}
		return items;
	}
	
	/**
	 * @return The list of selected units.
	 */
	public ArrayList<String> getSelectedUnits()
	{
		return getItems(selectedUnits);
	}
	
	/**
	 * @return The string selected in the correct list box.
	 */
	public String getCorrect()
	{
		int index = correctUnit.getSelectedIndex();
		return (index == 0 ? null : correctUnit.getItemText(index));
	}
	
}
