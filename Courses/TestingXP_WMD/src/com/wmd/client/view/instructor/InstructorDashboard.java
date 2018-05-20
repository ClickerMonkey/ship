package com.wmd.client.view.instructor;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.msg.PeriodMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetAssignmentsService;
import com.wmd.client.service.GetAssignmentsServiceAsync;
import com.wmd.client.service.GetPeriodsService;
import com.wmd.client.service.GetPeriodsServiceAsync;
import com.wmd.client.view.View;
import com.wmd.client.widget.ApplicationMenuBar;
import com.wmd.client.widget.instructor.AssignmentReportWidget;
import com.wmd.client.widget.instructor.StudentAssignmentReportWidget;
import com.wmd.client.widget.instructor.StudentPeriodReportWidget;

/**
 * Creates instructor dashboard. Has an ApplicationMenuBar with Student,
 * Instructor, & Reports menus
 * 
 * @author Chris Koch and Paul Cheney 3/17/2010
 * 
 * 
 * Add the report viewing options to the menu and load the reports when
 * they are selected.
 * 
 * @author Kevin Rexroth and Sam Storino
 * 
 */
public class InstructorDashboard extends View
{
	// The user of this dashboard.
	private final UserMsg user;
	
	/**
	 * Initializes new InstructorDashboard
	 * @param user - user of this dashboard
	 */
	public InstructorDashboard(UserMsg user)
	{
		this.user = user;
		final ApplicationMenuBar amb = ApplicationMenuBar.getInstance();
		AbsolutePanel absolutePanel = new AbsolutePanel();
		// Clear all the items on the master bar, before adding any.
		// //Edited by Paul 4/7/2010. Changed to use the new clear method.
		amb.clearItemsNew();

		Command manageAssignmentsCommand = new Command()
		{
			public void execute()
			{
				try
				{
					ApplicationWrapper.getInstance().setWidget(
							new ManageAssignmentsWidget());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		Command createNewAsssignmentCommand = new Command()
		{
			public void execute()
			{
				ApplicationWrapper.getInstance().setWidget(
						new InstructorAssignmentView());
			}
		};
		Command assignAssignmentCommand = new Command()
		{
			public void execute()
			{
			}
		};
		
		//Lists every assignment that a particular student is taking and their grade
		//Example: Shows assignment1 with a grade and assignment2 with a grade for a
		//		   student named John Doe.
		Command viewByStudent = new Command()
		{
			public void execute()
			{
				/**
				 * Will open the viewAllAssignmentsForStudent widget
				 */

				//Get all of the periods and set them as the parameter for this report, rather than null
				
				final GetPeriodsServiceAsync getAllPeriods = GWT.create(GetPeriodsService.class);
				
				
				getAllPeriods.getPeriods(new AsyncCallback<List<PeriodMsg>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						//Do nothing						
					}

					@Override
					public void onSuccess(List<PeriodMsg> allPeriods)
					{
						ApplicationWrapper.getInstance().setWidget(
								new StudentAssignmentReportWidget(allPeriods));						
					}					
				});
			}
		};
		
		//Lists the grades for every student taking a particular assignment
		//Example: Shows the grade for John Doe, Jane Doe, Bob Smith,
		//		   and Fred Johnson for assignment1
		Command viewByAssignment = new Command()
		{
			public void execute()
			{
				/**
				 * Will open the AssignmentReportWidget widget
				 */
					
				final GetAssignmentsServiceAsync getAssignmentsService = GWT.create(GetAssignmentsService.class);
				
				getAssignmentsService.getAssignments(new AsyncCallback<List<AssignmentMsg>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						//Do nothing
					}
					
					public void onSuccess(List<AssignmentMsg> allAssignments)
					{
						ApplicationWrapper.getInstance().setWidget(
								new AssignmentReportWidget(allAssignments));
					}
				});
			}
		};
		
		//Shows the student grades for a certain period
		Command viewStudentGradesByPeriod = new Command()
		{
			public void execute()
			{
				/**
				 * Will open the viewStudentGrades widget
				 */
				
				ApplicationWrapper.getInstance().setWidget(
						new StudentPeriodReportWidget());
			}
		};
		
		Command studentManagementCommand = new Command()
		{
			public void execute()
			{
				ApplicationWrapper.getInstance().setWidget(
						new StudentManagementView());
			}
		};
		Command instructorManagementCommand = new Command()
		{
			public void execute()
			{
				ApplicationWrapper.getInstance().setWidget(
						new InstructorManagementView());
			}
		};
		
		MenuBar assignmentsMenu = new MenuBar();
		assignmentsMenu.addItem("Manage", true, manageAssignmentsCommand);
		assignmentsMenu
				.addItem("Create New", true, createNewAsssignmentCommand);
		assignmentsMenu.addItem("Assign", true, assignAssignmentCommand);
		MenuBar reportsMenu = new MenuBar();
		reportsMenu.addItem("View Assignment Grades By Student", true, viewByStudent);
		reportsMenu.addItem("View Student Grades By Assignment", true, viewByAssignment);
		reportsMenu.addItem("View Student Grades By Period", true, viewStudentGradesByPeriod);
		MenuBar userManagementMenu = new MenuBar();
		userManagementMenu.addItem("Students", true, studentManagementCommand);
		userManagementMenu.addItem("Instructors", true, instructorManagementCommand);
		amb.addItem("User Management", true, userManagementMenu);
		amb.addItem("Assignments", true, assignmentsMenu);
		amb.addItem("Reports", true, reportsMenu);
		Image banner = new Image("Resources/Images/MathRocketBanner.png");
		absolutePanel.add(banner);
		absolutePanel.add(amb);
		// Image pickSomething = new
		// Image("Resources/Images/picksomething.png");
		// absolutePanel.add(pickSomething);
		// use the super menu to add these to keep it compatible
		super.getLayout().setWidget(0, 0, absolutePanel);
	}
	/**
	 * @return The user of this dashboard.
	 */
	public UserMsg getUser()
	{
		return user;
	}
}
