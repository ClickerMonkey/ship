package com.wmd.server.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author Sunger
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
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
	TestText.class,
	TestUnit.class,
	TestSquareRootParser.class,
	TestSymbol.class
})
	/**
	 * Runs all the entity tests.
	 */
public class AllEntityTests 
{
	//Empty
}
