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

	// Tests the creation of a project and assigning a projectmanager
	@Test
	public void testMakeManager() {
		Employee employee = new Employee(null, "INIT", null);

		assertFalse(employee.isProjectManager());

		assertEquals(0, Platform.getProjects().size());

		employee.makeManager("Project1");

		assertEquals(1, Platform.getProjects().size());

		assertTrue(employee.isProjectManager());
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

	// Tests the creation of an activity and the projectmanager assigning an
	// activity
	@Test
	public void testAssignActivity() {

		Employee employee = new Employee(null, "INIT", null);

		assertTrue(employee.isFree());
		Date start = new Date(2016, 5, 2);
		Date end = new Date(2016, 5, 5);

		Activity activity = new Activity(start, end, "Do something", "TODO1");

		employee.addActivity(activity);

		assertFalse(employee.isFree());

	}

	// Tests the finishing of an activity
	@Test
	public void testEndActivity() {

		Employee employee = new Employee(null, "INIT", null);

		Date start = new Date(2016, 5, 2);
		Date end = new Date(2016, 5, 5);

		Activity activity = new Activity(start, end, "Do something", "TODO1");

		employee.addActivity(activity);

		assertEquals(1, employee.viewActivities().size());

		employee.endActivity();

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
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	
	//Manager failing to create an activity do to wrong dates
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
	public void testManagerAssignActivity() {
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
	
	//Too many activities
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
	
	//Unavailable
	@Test
	public void testManagerFailAssignActivity2() {
		Employee employeeManager = new Employee(null, "INIT", null);

		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "INIT", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	
	//Status report
	@Test
	public void testCreateStatusReport() {
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		Employee employee2 = new Employee(null, "INIT", null);
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		assertTrue(employeeManager.createActivity(start, end, "Do something", "TODO"));
		assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
		
		employee2.endActivity();
		employeeManager.getProjectInChargeOf().createReport();
	}
	
	//Fail to assign do to vacation
	@Test
	public void testVacation() {
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
		// Is sick/on vacation
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO"));
	}
	

	
	@Test
	public void useCase1() {
		
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "INIT", null);
		
		Date start = new Date(2014 - 1900, 4, 2);
		Date end = new Date(2016 - 1900, 11, 2);
		
		// Testing that a non-manager cannot create the activity
		assertFalse(employee2.createActivity(start, end, "Do something", "TODO1"));

		// Testing that you cannot allocate more than 20 activities to a worker
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);

		for (int i = 1; i <= 20; i++) {
			assertTrue(employeeManager.createActivity(start, end, "Do something" + i, "TODO" + i));
			assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO" + i));
		}

		assertFalse(employeeManager.createActivity(start, end, "Do something20", "TODO20"));
		assertTrue(employeeManager.createActivity(start, end, "Do something21", "TODO21"));
		// Too many activities
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO21"));

		// Creating report
		employee2.endActivity();
		employeeManager.getProjectInChargeOf().createReport();

		// Testing Sickness / Vacation status
		Employee employee3 = new Employee(null, "INIT", null);
		List<Employee> employeeList2 = new ArrayList<Employee>();
		employeeList.add(employee3);

		assertTrue(employee3.isAvailable());

		employee3.setAvailable(false);

		assertFalse(employee3.isAvailable());

		assertTrue(employeeManager.createActivity(start, end, "Do something22", "TODO22"));
		// Is sick/on vacation
		//assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO22"));

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
