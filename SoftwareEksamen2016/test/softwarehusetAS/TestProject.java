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
	public void doReset(){
		Platform.reset();
	}
	
	// Tests the creation of a project and assigning a projectmanager
	@Test
	public void testMakeManager() {
		Employee employee = new Employee(null, "INIT", null);
		
		assertFalse(employee.isProjectManager());
		
		assertEquals(0,Platform.getProjects().size());
		
		employee.makeManager("Project1");
		
		assertEquals(1,Platform.getProjects().size());
		
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
	
	// Tests the creation of an activity and the projectmanager assigning an activity
	@Test
	public void testAssignActivity() {
		
		Employee employee = new Employee(null, "INIT", null);
		
		assertTrue(employee.isFree());
		Date start = new Date(2016, 5, 2);
		Date end = new Date(2016, 5, 5);
		
		Activity activity = new Activity(start,end,"Do something","TODO1");
		
		employee.addActivity(activity);
		
		assertFalse(employee.isFree());
		
	}
	
	// Tests the finishing of an activity
	@Test
	public void testEndActivity() {
		
		Employee employee = new Employee(null, "INIT", null);
		
		Date start = new Date(2016, 5, 2);
		Date end = new Date(2016, 5, 5);
		
		Activity activity = new Activity(start,end,"Do something","TODO1");
		
		employee.addActivity(activity);
		
		assertEquals(1,employee.viewActivities().size());
		
		employee.endActivity();
		
		
	}
	
	@Test
	public void useCase1() {
		
		Employee employeeManager = new Employee(null, "INIT", null);
		
		employeeManager.makeManager("Project1");
		
		Employee employee2 = new Employee(null, "INIT", null);
		
		Date start = new Date(2014-1900, 4, 2);
		Date end = new Date(2016-1900,11,2);
		
		
		assertFalse(employee2.createActivity(start,end,"Do something","TODO1"));
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employee2);
		
		for(int i = 1; i<= 20; i++) {
			assertTrue(employeeManager.createActivity(start,end,"Do something" + i,"TODO" + i));
			assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList,"TODO"+i));
		}
		
		assertFalse(employeeManager.createActivity(start,end,"Do something20","TODO20"));
		
		assertTrue(employeeManager.createActivity(start,end,"Do something21","TODO21"));
		//Too many activities
		assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList,"TODO21"));
		
		
	}
	
	
	
	
	
	
	
	
	
//	// Tests editing the used time on an activity
//	@Test
//	public void testEditActivityUsedTime() {
//		Employee employee = new Employee(null, "INIT", null);
//		
//	}
//	
//	// Tests editing the estimated time of an activity
//	@Test
//	public void testEditActivityEstimatedTime() {
//		Employee employee = new Employee(null, "INIT", null);
//		
//	}
//	
//	@Test
//	public void testViewActivity() {
//		Employee employee = new Employee(null, "INIT", null);
//		
//	}
//	
//	@Test
//	public void test() {
//		Employee employee = new Employee(null, "INIT", null);
//		
//	}
//	
//	@Test
//	public void test() {
//		Employee employee = new Employee(null, "INIT", null);
//		
//	}
}
