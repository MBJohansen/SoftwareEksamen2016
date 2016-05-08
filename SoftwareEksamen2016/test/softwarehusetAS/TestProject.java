package softwarehusetAS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class TestProject {

	@After
	public void doReset() {
		Platform.reset();
	}
	
	//
	@Test
	public void constructorTest() {
		Employee employeeManager = new Employee(null, "INIT", null);

     	Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
        List<Activity> activitiesList  = new ArrayList();
		
		Activity activity1 = new Activity(start, end, "Intern Assignment", "A1d");
		Activity activity2 = new Activity(start, end, "Intern Assignment", "A2d");
		
		activitiesList.add(activity1);
		activitiesList.add(activity2);

		Employee Intern = new Employee(activitiesList, "INTR", Platform.getProject("Project1"));
	}
	
	//
	@Test
	public void constructorTestProjectNotExisting() {
		Employee employeeManager = new Employee(null, "INIT", null);

     	Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
        List<Activity> activitiesList  = new ArrayList();
		
		Activity activity1 = new Activity(start, end, "Intern Assignment", "A1d");
		Activity activity2 = new Activity(start, end, "Intern Assignment", "A2d");
		
		activitiesList.add(activity1);
		activitiesList.add(activity2);

		Employee Intern = new Employee(activitiesList, "INTR", Platform.getProject("Project2"));
	}
	
	//
	@Test
	public void testGetAvailable() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);

		employeeManager.makeManager("Project1");

		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AABB", null);
		Employee employee4 = new Employee(null, "ABBB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		employeeList.add(employee3);
		employeeList.add(employee4);
		employee4.setAvailable(false);

		for (int i = 1; i <= 20; i++) {

			employeeManager.createActivity(start, end, "Do something" + i, "TODO" + i);
			employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO" + i);

		}

		assertEquals(20, employee2.viewActivities().size());
		assertEquals(20, employee3.viewActivities().size());
		assertEquals(0, employee4.viewActivities().size());

		assertEquals(1, Platform.getAvailableEmployees().size());

	}
	
	//Test make manager when already manager
	@Test
	public void testAlreadyManager() {

		Employee employeeManager = new Employee(null, "INIT", null);

     	Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		assertEquals(null,employeeManager.getProjectInChargeOf());
		
		employeeManager.makeManager("Project1");
		
		assertTrue(employeeManager.isProjectManager());
		
		employeeManager.makeManager("Project2");
	}
	
	// Tests the finishing of a project
	@Test
	public void testEndProject() {
		Employee employee = new Employee(null, "INIT", null);

		employee.makeManager("Project1");

		assertTrue(employee.getProjectInChargeOf().isActive());

		employee.endProject();

		assertFalse(Platform.getProject("Project1").isActive());
	}
	
	//Deleting a project
	@Test
	public void testDeleteProject() {
		Employee employee = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "INI2", null);

		employee.makeManager("Project1");
		employee2.makeManager("Project 2");
		
		assertEquals(2,Platform.getProjects().size());
		
		Platform.editProjects(employee.getProjectInChargeOf());
		
		assertEquals(1,Platform.getProjects().size());
	}
	
	//Editing start and end dates for an activity
	@Test
	public void testEditActvityDates() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);

		Date newStart = new Date(2014 - 1900, 5, 5);
		Date newEnd = new Date(2016 - 1900, 12, 7);
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertTrue(employee2.Edit(newStart,newEnd,"TODO"));
		
		assertFalse(employee2.Edit(newStart, newEnd, "TODO2"));
	}
	
	//Test the amount of time left on an activity
	@Test
	public void testActiveTime() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2016 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertEquals(5137,employee2.getActiveTime("TODO"));
		
		assertEquals(0,employee2.getActiveTime("TODO2"));
	}
	
	//
	@Test
	public void testSetAvailability() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employee2.setAvailable(true);
		
		employee2.setAvailable(false);
		
		//Setting available again to test all possibilities of the method
		employee2.setAvailable(true);

		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
		
		//And again
		employee2.setAvailable(false);
		
		employee2.setAvailable(true);
	}
	
	//Adding vacation in the past
	@Test
	public void testVacationFail2() {
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016-1900,2,12);
		Date end = new Date(2016-1900, 4,10);
		
		assertFalse(employee.addVacation(start, end));
		
		assertFalse(employee.onVacation());
	}
	
	//Testing on vacation
	@Test
	public void testOnVacation() {
		
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016-1900,6,9);
		Date end = new Date(2016-1900, 10,15);
		
		assertTrue(employee.addVacation(start, end));
		
		Date newStart = new Date(2016-1900,3,9);
		Date newEnd = new Date(2016-1900,8,2);
		
		//Assigning new dates to the vacation to actually test if onVacation works
		//This shouldn't be legal but it is necessary to test it
		employee.Edit(newStart, newEnd, "VAC");
		
		assertTrue(employee.onVacation());
	}
	
	//Giving an employee a list of activities
	@Test
	public void testSetActivities() {

		Employee employeeManager = new Employee(null, "INIT", null);

     	Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee Intern = new Employee(null, "INTR", Platform.getProject("Project1"));
        
		List<Activity> activitiesList  = new ArrayList();
		
		Activity activity1 = new Activity(start, end, "Intern Assignment", "A1d");
		Activity activity2 = new Activity(start, end, "Intern Assignment", "A2d");
		
		activitiesList.add(activity1);
		activitiesList.add(activity2);

		Intern.setActivity(activitiesList);
	}
	
	//Giving an employee a list of activities
	@Test
	public void testSetActivitiesNull() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Employee Intern = new Employee(null, "INTR", Platform.getProject("Project1"));
        
		List<Activity> activitiesList  = null;
		
		Intern.setActivity(activitiesList);
	}
	
	//End week without having finished any activities
	@Test
	public void testEndOfWeek() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2017 - 1900, 5, 2);
		Date end = new Date(2017 - 1900, 5, 5);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO1"));
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employeeManager);
		
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1"));
		
		assertEquals(1,employeeManager.viewActivities().size());
		
		Platform.endOfWeek();
		
		assertEquals(1,employeeManager.viewActivities().size());
	}
	
	//
	@Test
	public void testViewEmployeeActivities() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> projectEmployee = new ArrayList();
		
		projectEmployee.add(employee2);
		
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO1"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(projectEmployee, "TODO1"));
		
		assertEquals(1,employeeManager.getProjectInChargeOf().viewEmployeeActivities("AAAB").size());
	}
	
	//
	@Test
	public void testViewEmployeeActivitiesFail() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AABB", null);
		List<Employee> projectEmployee = new ArrayList();
		
		projectEmployee.add(employee2);
		projectEmployee.add(employee3);
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO1"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(projectEmployee, "TODO1"));
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO2"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(projectEmployee, "TODO2"));
		assertEquals(null, employeeManager.getProjectInChargeOf().viewEmployeeActivities("AAAC"));
		
	}
	
	// Testing viewing an activity that exists, and the ID is correct
	@Test
	public void testViewActivityExists() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);

		employeeManager.makeManager("Project1");

		Date start = new Date(2016 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);

		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);

		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		assertEquals("Start: Mon May 02 00:00:00 CEST 2016 End: Fri Dec 02 00:00:00 CET 2016 Description: Do something",
				employeeManager.getProjectInChargeOf().viewActivity("TODO"));

	}

	// Testing viewing an activity that does not exist.
	@Test
	public void testViewActivityDoesNotExist() {

		Employee employeeManager = new Employee(null, "INIT", null);

		employeeManager.makeManager("Project1");

		Date start = new Date(2016 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);

		assertEquals("Activity does not exist", employeeManager.getProjectInChargeOf().viewActivity("Unassigned"));

	}

	// Testing viewing an activity that does not exist, when an activity exists.
	@Test
	public void testViewActivityWrongID() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);

		employeeManager.makeManager("Project1");

		Date start = new Date(2016 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);

		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);

		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		assertEquals("Activity does not exist", employeeManager.getProjectInChargeOf().viewActivity("Unassigned"));

	}
	
	//
	@Test
	public void testGetEmployees() {
		Employee employee1 = new Employee(null, "AAAA", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		Employee employee4 = new Employee(null, "AAAD", null);
		Employee employee5 = new Employee(null, "AAAE", null);
		Employee employee6 = new Employee(null, "AAAF", null);
		Employee employee7 = new Employee(null, "AAAG", null);
		
		assertEquals(7,Platform.getEmployees().size());
	}
	
	//
	@Test
	public void testGetEmployeesStringList() {
		Employee employee1 = new Employee(null, "AAAA", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		Employee employee4 = new Employee(null, "AAAD", null);
		Employee employee5 = new Employee(null, "AAAE", null);
		Employee employee6 = new Employee(null, "AAAF", null);
		Employee employee7 = new Employee(null, "AAAG", null);
		
		List<String> employeeIDs = new ArrayList<String>();
		employeeIDs.add("AAAB");
		employeeIDs.add("AAAC");
		employeeIDs.add("AAAF");
		employeeIDs.add("AAAZ");
		
		//AAAZ does not exist
		assertEquals(3,Platform.getEmployees(employeeIDs).size());
	}
	
	//
	@Test
	public void testActivityToString() {
		Employee employeeManager = new Employee(null,"INIT",null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employeeManager);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
		
		
		assertEquals("TODO",employeeManager.viewActivities().get(0).toString());
		
	}
	
	
	//Use Case 1
	
	//Non-manager failing to create an activity
	@Test
	public void testEmployeeCreateActivity() {
		Employee employee = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		assertFalse(employee.createActivity(start, end, "Do something", "TODO1"));
	}
	
	//Manager creating an activity and assigning it
	@Test
	public void testManagerCreateAndAssignActivity() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employee2.isFree());
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
		
		//Trying to create an activity that doesn't exist
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO2"));
		
		assertFalse(employee2.isFree());
	}
	
	//Manager failing to create an activity due to wrong dates
	@Test
	public void testManagerCreateActivityFail() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date end = new Date(2014 - 1900, 4, 2);
		Date start = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertFalse(employeeManager.createActivity(start, end, "Do something", "TODO"));
	}
	
	//Assigning an activity to several employees
	@Test
	public void testManagerAssignActivitySeveral() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		employeeList.add(employee3);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	
	//Manager failing to assign due to too many activities
	@Test
	public void testManagerAssignActivityFail() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		for (int i = 1; i <= 20; i++) {
			assertTrue(employeeManager.createActivity(start, end, "Do something" + i, "TODO" + i));
			assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO" + i));
		}
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertFalse(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	
	//Manager fail to assign due to sickness
	@Test
	public void testManagerAssignActivityFail2() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);

		assertTrue(employee2.isAvailable());

		employee2.setAvailable(false);
		
		assertFalse(employee2.isAvailable());
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		// Is sick
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	
	//Tests for the creation of a status report is under use case 5
	
	
	//Use Case 2
	
	//Tests the specifying of hours used on an activity
	@Test
	public void testSpecifyHours() {
		Employee employee = new Employee(null, "INIT", null);

		Date start = new Date(2016 - 1900, 5, 2);
		Date end = new Date(2016 - 1900, 5, 5);

		Activity activity = new Activity(start, end, "Do something", "TODO1");
		employee.addActivity(activity);
		employee.addHours(5, "TODO1");
		
		assertEquals(5,Math.round((employee.getHours())));
		
	}
	
	//Tests the specifying of hours on an activity the person isn't assigned to
	@Test
	public void testSpecifyHoursNotAssignedToActivity() {
		Employee employee = new Employee(null, "INIT", null);
		
		employee.addHours(5, "TODO1");
	}
	
	//Tests the specifying of hours on an activity the person isn't assigned to
	@Test
	public void testSpecifyHoursNotAssignedToAnotherActivity() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2017 - 1900, 5, 2);
		Date end = new Date(2017 - 1900, 5, 5);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO1"));
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1"));
		
		employee2.addHours(5, "TODO2");
	}
	
	//Fails due to wrong amount of hours
	@Test
	public void testSpecifyHoursFail() {
		Employee employee = new Employee(null, "INIT", null);

		Date start = new Date(2016 - 1900, 5, 2);
		Date end = new Date(2016 - 1900, 5, 5);

		Activity activity = new Activity(start, end, "Do something", "TODO1");
		employee.addActivity(activity);

		employee.addHours(5.123, "TODO1");
		
		assertEquals(0,Math.round(employee.getHours()));
	}
	
	//Tests the finishing of an activity
	@Test
	public void testEndActivity() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "INIT", null);
		
		Date start = new Date(2016 - 1900, 5, 2);
		Date end = new Date(2016 - 1900, 5, 5);
		
		employeeManager.makeManager("Project1");
		
		assertTrue(employeeManager.createActivity(start, end, "Do something1", "TODO1"));
		assertTrue(employeeManager.createActivity(start, end, "Do something2", "TODO2"));
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO2"));
		
		assertEquals(2, employee2.viewActivities().size());

		assertEquals(2, employee2.viewActiveActivities().size());
		
		employee2.endActivity();
		
		assertEquals(1, employee2.viewActiveActivities().size());
		
	}
	
	
	//Use Case 3
	
	//Searching for help
	@Test
	public void testSearchHelp() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		
		Date start = new Date(2016 - 1900, 5, 2);
		Date end = new Date(2016 - 1900, 5, 5);
		
		employeeManager.makeManager("Project1");
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertTrue(employee2.searchHelp("TODO"));
	}
	
	//Fails because of no available employees
	@Test
	public void testSearchHelpNoneAvailable() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		Date start = new Date(2016 - 1900, 5, 2);
		Date end = new Date(2016 - 1900, 5, 5);
		
		employeeManager.makeManager("Project1");
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employeeManager);
		
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertFalse(employeeManager.searchHelp("TODO"));
	}
	
	//Searching for help to an activity with no time left
	@Test
	public void testSearchHelpOutofTime() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		
		Date start = new Date(2016 - 1900, 2, 2);
		Date end = new Date(2018 - 1900, 2, 4);
		
		employeeManager.makeManager("Project1");
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		employee2.Edit(start, new Date(2016-1900,2,4), "TODO");
		assertFalse(employee2.searchHelp("TODO"));
	}
	
	//Searching for help to an activity not assigned to the employee
		@Test
		public void testSearchHelpNoActivity() {
			Employee employeeManager = new Employee(null, "INIT", null);
			Employee employee2 = new Employee(null, "AAAB", null);
			Employee employee3 = new Employee(null, "AAAC", null);
			
			Date start = new Date(2016 - 1900, 2, 2);
			Date end = new Date(2018 - 1900, 2, 4);
			
			employeeManager.makeManager("Project1");
			
			employeeManager.createActivity(start, end, "Do something", "TODO");
			
			List<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(employee2);
			
			employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
			
			assertFalse(employee2.searchHelp("WRONG"));
		}
	
	
	//Use Case 4
	
	//Adding a vacation-activity
	@Test
	public void testVacationSuccess (){
		
		// Testing that the employee can go on vacation
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016-1900, 10, 10);
		Date end = new Date(2016-1900, 10, 12);
		
		assertTrue(employee.addVacation(start, end));
		
		assertEquals(1, employee.viewActiveActivities().size());
		
		//Not on vacation, as the date is in the future
		assertFalse(employee.onVacation());
	}
	
	//Typing wrong dates on a vacation-activity
	@Test
	public void testVacationFail() {
		//Testing that the employee cannot enter dates in the wrong order
		
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016-1900,10,12);
		Date end = new Date(2016-1900, 10,10);
		
		assertFalse(employee.addVacation(start, end));
		
		assertFalse(employee.onVacation());
	}
	
	@Test
	public void testVacation(){
		
		// Testing that the employee can go on vacation
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016-1900, 10, 10);
		Date end = new Date(2016-1900, 10, 12);
		
	Activity activity = new Activity(start, end, "Do something", "TODO1");
		
		employee.addActivity(activity);
		
		assertTrue(employee.addVacation(start, end));
		
		assertEquals(2, employee.viewActiveActivities().size());
		
		assertFalse(employee.onVacation());
		
		Date start2 = new Date(2020-1900, 02-1, 02);
		Date end2 = new Date(2020-1900, 03-1, 02);
		
		employee.Edit(start2, end2, "VAC");
		
		assertFalse(employee.onVacation());
		
		Date start3 = new Date(2014-1900, 02-1, 02);
		Date end3 = new Date(2014-1900, 03-1, 02);
		
		employee.Edit(start3, end3, "VAC");
		
		assertFalse(employee.onVacation());

		Date start4 = new Date(2020-1900, 02-1, 02);
		Date end4 = new Date(2014-1900, 03-1, 02);
		
		employee.Edit(start4, end4, "VAC");
		
		assertFalse(employee.onVacation());
		
		Date start5 = new Date(2014-1900, 02-1, 02);
		Date end5 = new Date(2020-1900, 03-1, 03);
		
		employee.Edit(start5, end5, "VAC");
	
		assertTrue(employee.onVacation());
	}
	//Use Case 5
	
	//Tests the creation of a status report
	@Test
	public void testCreateStatusReport() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		Employee employee2 = new Employee(null, "AAAB", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
		
		assertEquals(0,employeeManager.getProjectInChargeOf().createReport().size());
		
		employee2.endActivity();
		
		assertEquals(1,employeeManager.getProjectInChargeOf().createReport().size());
		Platform.endOfWeek();
	}
	
	
	//Use Case 7
	//Employee views used hours on an activity
	@Test
	public void testViewHours() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertEquals(0,(int)employee2.viewActivities().get(0).getHours());
		
		employee2.addHours(2,"TODO");
		
		assertEquals(2,(int)employee2.viewActivities().get(0).getHours());
	}
	
	
	//Use Case 8
	
	//Tests the creation of a project and assigning a projectmanager
	@Test
	public void testMakeManager() {
		Employee employee = new Employee(null, "INIT", null);

		assertFalse(employee.isProjectManager());
		
		assertEquals(0, Platform.getProjects().size());

		employee.makeManager("Project1");

		assertEquals(1, Platform.getProjects().size());
		
		assertTrue(employee.isProjectManager());
	}
	
	//Test for creating an activity is done under Use Case 1
	
	//Manager searching for available employees
	@Test
	public void testSearchAvailableEmployees() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		Employee employee4 = new Employee(null, "AAAD", null);
		Employee employee5 = new Employee(null, "AAAE", null);
		Employee employee6 = new Employee(null, "AAAF", null);
		Employee employee7 = new Employee(null, "AAAG", null);
		
		employeeManager.makeManager("Project1");
		
		//employee3 has no activities
		
		//employee2 and 4 are unavailable
		employee2.setAvailable(false);
		employee4.setAvailable(false);
		
		Date start1 = new Date(2014 - 1900, 4, 2);
		Date end1 = new Date(2015 - 1900, 6, 2);
		
		Date start2 = new Date(2014 - 1900, 5, 2);
		Date end2 = new Date(2015 - 1900, 11, 2);
		
		Date start3 = new Date(2014 - 1900, 8, 2);
		Date end3 = new Date(2015 - 1900, 12, 2);
		
		employeeManager.createActivity(start1, end1, "First", "TODO1");
		employeeManager.createActivity(start2, end2, "Second", "TODO2");
		employeeManager.createActivity(start3, end3, "Third", "TODO3");
		
		//employee5 is the first to be done
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee5);
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1");
		
		//employee6 is done later
		employeeList.remove(employee5);
		employeeList.add(employee6);
		employeeList.add(employee7);
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO2");
		
		//employee7 is the latest to be done
		employeeList.remove(employee6);
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO3");
		
		assertEquals(4,Platform.getSuitableEmployees(4).size());
		
		for(int i = 0; i < Platform.getSuitableEmployees(4).size(); i++) {
			employeeManager.getProjectInChargeOf().addEmployee(Platform.getSuitableEmployees(4).get(i));
		}
		
		assertEquals(4,employeeManager.getProjectInChargeOf().getEmployees().size());
	}
	
	//
	@Test
	public void testNotEnoughSuitableEmployees() {
		assertEquals(null,Platform.getSuitableEmployees(5));
	}
	
	//
	@Test
	public void testMakeProjectNotNull() {
		
		List<Employee> projectEmployee = new ArrayList();
		
		List<Activity> projectActivities = new ArrayList();
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		Employee employee = new Employee(null, "INIT", null);
		
		Activity activity1 = new Activity(start, end, "Intern Assignment", "A1d");
		
		projectActivities.add(activity1);
		
		projectEmployee.add(employee);
		
	    Project project1 = new Project(projectEmployee, projectActivities, "Project 1");	
		
	}
	
	@Test
	public void testSuitbleEmployeesWithTooManyActivities() {
			Employee employeeManager = new Employee(null, "INIT", null);
			Employee employee2 = new Employee(null, "AAAB", null);
			Employee employee3 = new Employee(null, "AAAC", null);
		
			employeeManager.makeManager("Project1");
			
			Date start = new Date(2016-1900, 6-1, 10);
			Date end = new Date(2016-1900, 6-1, 12);
			
			List<Employee> employee = new ArrayList();
			
			employee.add(employeeManager);
		
			for (int i = 1; i <= 20; i++) {
				assertTrue(employeeManager.createActivity(start, end, "Do something" + i, "TODO" + i));
				assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employee, "TODO" + i));
			}

			List<Employee> employeeList2 = new ArrayList();
			
			employeeList2.add(employee2);
			employeeList2.add(employee3);
		
			assertTrue(employeeManager.createActivity(start, end, "Test", "Test1"));
			assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList2, "Test1"));
			
			employee3.setAvailable(false);
			
			assertEquals(1,Platform.getSuitableEmployees(1).size());

			assertEquals(0,Platform.getSuitableEmployees(0).size());
	}
		
	
	@Test
	public void testMoreSuitablesThanNecessary() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		Employee employee4 = new Employee(null, "AAAD", null);
		
		employeeManager.makeManager("Project1");
		
		Platform.getSuitableEmployees(0);
		
		assertEquals(2,Platform.getSuitableEmployees(2).size());
	}
	
	//
	@Test
	public void testGoDeeper() {
		
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		Employee employee3 = new Employee(null, "AAAC", null);
		Employee employee4 = new Employee(null, "AAAD", null);
		
		
		employeeManager.makeManager("Project1");
		
		Date start1 = new Date(2014 - 1900, 4, 2);
		Date end1 = new Date(2017 - 1900, 6, 2);
		
		Date start2 = new Date(2014 - 1900, 5, 2);
		Date end2 = new Date(2017 - 1900, 11, 2);
		
		Date start3 = new Date(2014 - 1900, 8, 2);
		Date end3 = new Date(2017 - 1900, 12, 2);
		
		employeeManager.createActivity(start1, end1, "First", "TODO1");
		employeeManager.createActivity(start2, end2, "Second", "TODO2");
		employeeManager.createActivity(start3, end3, "Third", "TODO3");
		
		//employee5 is the first to be done
		List<Employee> employeeList = new ArrayList();
		employeeList.add(employeeManager);
		employeeList.add(employee2);
		employeeList.add(employee3);
		employeeList.add(employee4);
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO2");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO3");
		
		
		assertEquals(4,Platform.getSuitableEmployees(4).size());
		
	}
	
	
	
	
	
}
