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
	
	@Test
	public void constructorTest() {
		
		Employee employeeManager = new Employee(null, "INIT", null);

     	Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
        List<Activity> activitiesList  = new ArrayList();
		
		Activity activity1 = new Activity(start, end, "Intern Assignment", "Fix the cables");
		
		activitiesList.add(activity1);
		
		Employee Intern = new Employee(activitiesList, "INTR", Platform.getProject("Project1"));
		
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
	
	//
	@Test
	public void testEditActvityDates() {
		Employee employeeManager = new Employee(null, "INIT", null);
		Employee employee2 = new Employee(null, "AAAB", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		
		
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
		
		employeeManager.createActivity(start, end, "Do something1", "TODO1");
		employeeManager.createActivity(start, end, "Do something2", "TODO2");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO1");
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO2");
		
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
		Date end = new Date(2016 - 1900, 2, 4);
		
		employeeManager.makeManager("Project1");
		
		employeeManager.createActivity(start, end, "Do something", "TODO");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
		
		assertFalse(employee2.searchHelp("TODO"));
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
		assertEquals(2,Platform.getSuitableEmployees(2).size());

		for(int i = 0; i < Platform.getSuitableEmployees(2).size(); i++) {
			employeeManager.getProjectInChargeOf().addEmployee(Platform.getSuitableEmployees(2).get(i));
		}
		
		assertEquals(2,employeeManager.getProjectInChargeOf().getEmployees().size());
	}
	

	
	
	// // Tests editing the used time on an activity
	// @Test
	// public void testEditActivityUsedTime() {
	// Employee employee = new Employee(null, "INIT", null);
	//
	// }
	//
	// // Tests editing the estimated time of an activity
	// @Test
	// public void testEditActivityEstimatedTime() {
	// Employee employee = new Employee(null, "INIT", null);
	//
	// }
	//
	// @Test
	// public void testViewActivity() {
	// Employee employee = new Employee(null, "INIT", null);
	//
	// }
	//
	// @Test
	// public void test() {
	// Employee employee = new Employee(null, "INIT", null);
	//
	// }
	//
	// @Test
	// public void test() {
	// Employee employee = new Employee(null, "INIT", null);
	//
	// }
}
