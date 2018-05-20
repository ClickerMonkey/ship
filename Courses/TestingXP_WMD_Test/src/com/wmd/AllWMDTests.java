package com.wmd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.wmd.client.entity.TestEntitySerializability;
import com.wmd.client.msg.TestMsgSerializability;
import com.wmd.client.msg.TestUserAsigmentMsg;
import com.wmd.client.msg.TestUserMsg;
import com.wmd.server.db.DatabaseTest;
import com.wmd.server.db.TestAdmin;
import com.wmd.server.db.TestAdminList;
import com.wmd.server.db.TestAssignment;
import com.wmd.server.db.TestAssignmentList;
import com.wmd.server.db.TestDatabase;
import com.wmd.server.db.TestInstructor;
import com.wmd.server.db.TestProblem;
import com.wmd.server.db.TestProblemList;
import com.wmd.server.db.TestProblemStatus;
import com.wmd.server.db.TestStudent;
import com.wmd.server.db.TestStudentList;
import com.wmd.server.db.TestUnit;
import com.wmd.server.db.TestUnitList;
import com.wmd.server.entity.TestAnswer;
import com.wmd.server.entity.TestDecimal;
import com.wmd.server.entity.TestEntityContainer;
import com.wmd.server.entity.TestEntityParser;
import com.wmd.server.entity.TestExponent;
import com.wmd.server.entity.TestFraction;
import com.wmd.server.entity.TestInteger;
import com.wmd.server.entity.TestNewline;
import com.wmd.server.entity.TestQuestion;
import com.wmd.server.entity.TestSquareRootParser;
import com.wmd.server.entity.TestSymbol;
import com.wmd.server.entity.TestText;
import com.wmd.server.service.TestAddAssignmentServiceImpl;
import com.wmd.server.service.TestAddProblemToAssignmentServiceImpl;
import com.wmd.server.service.TestCopyProblemServiceImpl;
import com.wmd.server.service.TestDeleteAssignmentService;
import com.wmd.server.service.TestDeleteUserService;
import com.wmd.server.service.TestGetAllUserAssignmentStatusServiceImpl;
import com.wmd.server.service.TestGetAllUsersService;
import com.wmd.server.service.TestGetAssignmentProblemService;
import com.wmd.server.service.TestGetAssignments;
import com.wmd.server.service.TestGetProblemStamentServiceImpl;
import com.wmd.server.service.TestGetProblemStatusService;
import com.wmd.server.service.TestGetUnitsService;
import com.wmd.server.service.TestGetUserAssignmentStatusServiceImpl;
import com.wmd.server.service.TestGetUserService;
import com.wmd.server.service.TestGetUsersByPeriodServiceImpl;
import com.wmd.server.service.TestSaveProblemServiceImpl;
import com.wmd.server.service.TestSaveUnitService;
import com.wmd.server.service.TestSaveUpdateUserService;
import com.wmd.server.service.TestSwapPoblemNumbersServiceImpl;
import com.wmd.server.service.TestUpdateProblemStatusService;
import com.wmd.server.service.TestUpdateProblemStatusServiceImpl;
import com.wmd.server.service.TestUpdateUserPasswordServiceImpl;
import com.wmd.server.service.TestUserSignInServiceImpl;
import com.wmd.util.TestXMLUtil;


/**
 * @author Merlin
 * 
 *         Created: March 28, 20010
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{   
	//com.wmd.client.entity
	TestEntitySerializability.class,
	
	//com.wmd.client.msg
	TestMsgSerializability.class,
	TestUserAsigmentMsg.class,
	TestUserMsg.class,
	
	//com.wmd.server.db
	DatabaseTest.class,
	TestAdmin.class,
	TestAdminList.class,
	TestAssignment.class,
	TestAssignmentList.class,
	TestDatabase.class,
	TestInstructor.class,
	TestProblem.class,
	TestProblemList.class,
	TestProblemStatus.class,
	TestStudent.class,
	TestStudentList.class,
	TestUnit.class,
	TestUnitList.class,
	
	//com.wmd.server.entity
	TestAnswer.class,
	TestDecimal.class,
	TestEntityContainer.class,
	TestEntityParser.class,
	TestExponent.class,
	TestFraction.class,
	TestInteger.class,
	TestNewline.class,
	TestProblem.class,
	TestQuestion.class,
	TestSquareRootParser.class,
	TestSymbol.class,
	TestText.class,
	TestUnit.class,
	
	//com.wmd.server.service
	TestAddAssignmentServiceImpl.class,
	TestAddProblemToAssignmentServiceImpl.class,
	TestCopyProblemServiceImpl.class,
	TestDeleteAssignmentService.class,
	TestDeleteUserService.class,
	TestGetAllUserAssignmentStatusServiceImpl.class,
	TestGetAllUsersService.class,
	TestGetAssignmentProblemService.class,
	TestGetAssignments.class,
	TestGetProblemStamentServiceImpl.class,
	TestGetProblemStatusService.class,
	TestGetUnitsService.class,
	TestGetUserAssignmentStatusServiceImpl.class,
	TestGetUsersByPeriodServiceImpl.class,
	TestGetUserService.class,
	TestSaveProblemServiceImpl.class,
	TestSaveUnitService.class,
	TestSaveUpdateUserService.class,
	TestSwapPoblemNumbersServiceImpl.class,
	TestUpdateProblemStatusServiceImpl.class,
	TestUpdateUserPasswordServiceImpl.class,
	TestUpdateProblemStatusService.class,
	TestUserSignInServiceImpl.class,

	//com.wmd.util
	TestEntityParser.class,
	TestXMLUtil.class
	
})
public class AllWMDTests
{
	//Empty
}