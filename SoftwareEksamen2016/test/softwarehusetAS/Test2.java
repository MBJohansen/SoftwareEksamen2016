//package softwarehusetAS;
//
//import static org.junit.Assert.assertEquals;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import java.util.Date;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.After;
//import org.junit.Test;
////
//public class Test2 {
//
//	@After
//	public void doReset() {
//		Platform.reset();
//	}
//	
//	
////	@Test
////	public void testVacation(){
////		
////		// Testing that the employee can go on vacation
////		Employee employee = new Employee(null, "INIT", null);
////		
////		Date start = new Date(2016-1900, 10, 10);
////		Date end = new Date(2016-1900, 10, 12);
////		
////	Activity activity = new Activity(start, end, "Do something", "TODO1");
////		
////		employee.addActivity(activity);
////		
////		assertTrue(employee.addVacation(start, end));
////		
////		assertEquals(2, employee.viewActiveActivities().size());
////		
////		assertFalse(employee.onVacation());
////		
////		Date start2 = new Date(2020-1900, 02-1, 02);
////		Date end2 = new Date(2020-1900, 03-1, 02);
////		
////		employee.Edit(start2, end2, "VAC");
////		
////		assertFalse(employee.onVacation());
////		
////		Date start3 = new Date(2014-1900, 02-1, 02);
////		Date end3 = new Date(2014-1900, 03-1, 02);
////		
////		employee.Edit(start3, end3, "VAC");
////		
////		assertFalse(employee.onVacation());
////
////		Date start4 = new Date(2020-1900, 02-1, 02);
////		Date end4 = new Date(2014-1900, 03-1, 02);
////		
////		employee.Edit(start4, end4, "VAC");
////		
////		assertFalse(employee.onVacation());
////		
////		Date start5 = new Date(2014-1900, 02-1, 02);
////		Date end5 = new Date(2020-1900, 03-1, 03);
////		
////		employee.Edit(start5, end5, "VAC");
////	
////		assertTrue(employee.onVacation());
////	}
//	
//	//Typing wrong dates on a vacation-activity
//	
//	
//}
///*	@Test
//	public void testingStuff2() {
//
//		Employee employeeManager = new Employee(null, "INIT", null);
//
//		Date start = new Date(2014 - 1900, 4, 2);
//		Date end = new Date(2016 - 1900, 11, 2);
//
//		employeeManager.makeManager("Project1");
//
//		Employee employee2 = new Employee(null, "AAAB", null);
//		Employee employee3 = new Employee(null, "AABB", null);
//		Employee employee4 = new Employee(null, "ABBB", null);
//		List<Employee> employeeList = new ArrayList<Employee>();
//		employeeList.add(employee2);
//		employeeList.add(employee3);
//		employeeList.add(employee4);
//		employee4.setAvailable(false);
//
//		for (int i = 1; i <= 20; i++) {
//
//			employeeManager.createActivity(start, end, "Do something" + i, "TODO" + i);
//			employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO" + i);
//
//		}
//
//		assertEquals(20, employee2.viewActivities().size());
//		assertEquals(20, employee3.viewActivities().size());
//		assertEquals(0, employee4.viewActivities().size());
//
//		assertEquals(1, Platform.getAvailableEmployees().size());
//
//	}
//}
///*	
//	@Test
//	public void testDeleteProject() {
//		Employee employee = new Employee(null, "INIT", null);
//		Employee employee2 = new Employee(null, "INI2", null);
//
//		employee.makeManager("Project1");
//		employee2.makeManager("Project 2");
//		
//		assertEquals(2,Platform.getProjects().size());
//		
//		Platform.editProjects(employee.getProjectInChargeOf());
//		
//		assertEquals(1,Platform.getProjects().size());
//	}
//}
//	
//	
//	
//	
///*	
//	
//	
//
//	// Testing viewing an activity that exists, and the ID is correct
///*	@Test
//	public void testViewActivityExists() {
//		Employee employeeManager = new Employee(null, "INIT", null);
//		Employee employee2 = new Employee(null, "AAAB", null);
//
//		employeeManager.makeManager("Project1");
//
//		Date start = new Date(2016 - 1900, 4, 2);
//		Date end = new Date(2016 - 1900, 11, 2);
//
//		List<Employee> employeeList = new ArrayList<Employee>();
//		employeeList.add(employee2);
//
//		employeeManager.createActivity(start, end, "Do something", "TODO");
//		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
//		assertEquals("Start: Mon May 02 00:00:00 CEST 2016 End: Fri Dec 02 00:00:00 CET 2016 Description: Do something",
//				employeeManager.getProjectInChargeOf().viewActivity("TODO"));
//
//	}
//
//	// Testing viewing an activity that does not exist.
//	@Test
//	public void testViewActivityDoesNotExist() {
//
//		Employee employeeManager = new Employee(null, "INIT", null);
//
//		employeeManager.makeManager("Project1");
//
//		Date start = new Date(2016 - 1900, 4, 2);
//		Date end = new Date(2016 - 1900, 11, 2);
//
//		assertEquals("Activity does not exist", employeeManager.getProjectInChargeOf().viewActivity("Unassigned"));
//
//	}
//
//	// Testing viewing an activity that does not exist, when an activity exists.
//	@Test
//	public void testViewActivityWrongID() {
//		Employee employeeManager = new Employee(null, "INIT", null);
//		Employee employee2 = new Employee(null, "AAAB", null);
//
//		employeeManager.makeManager("Project1");
//
//		Date start = new Date(2016 - 1900, 4, 2);
//		Date end = new Date(2016 - 1900, 11, 2);
//
//		List<Employee> employeeList = new ArrayList<Employee>();
//		employeeList.add(employee2);
//
//		employeeManager.createActivity(start, end, "Do something", "TODO");
//		employeeManager.getProjectInChargeOf().assignActivity(employeeList, "TODO");
//		assertEquals("Activity does not exist", employeeManager.getProjectInChargeOf().viewActivity("Unassigned"));
//
//	}
//
//}*/
//// @Test
//// public void testVacationSuccess (){
////
////
//// // Testing that the employee can go on vacation
//// Employee Reinhardt = new Employee(null, "REIN", null);
////
//// Date start = new Date(2016, 10, 10);
//// Date end = new Date(2016, 10, 12);
////
//// assertTrue(Reinhardt.addVacation(start, end));
////
//// assertEquals(1, Reinhardt.viewActiveActivities().size());
////
//// //Not on vacation, as the date is in the future
//// assertFalse(Reinhardt.onVacation());
////
//// }
////
//// @Test
//// public void testVacationFail() {
////
//// //Testing that the employee cannot enter dates in the wrong order
////
//// Employee Reinhardt = new Employee(null, "REIN", null);
////
//// Date start = new Date(2016,10,12);
//// Date end = new Date(2016, 10,10);
////
//// assertFalse(Reinhardt.addVacation(start, end));
////
////
////
//// }
////
////
////
//// // Tests the creation of a project and assigning a projectmanager
//// @Test
//// public void testMakeManager() {
//// Employee employee = new Employee(null, "INIT", null);
////
//// assertFalse(employee.isProjectManager());
////
//// assertEquals(0, Platform.getProjects().size());
////
//// employee.makeManager("Project1");
////
//// assertEquals(1, Platform.getProjects().size());
////
//// assertTrue(employee.isProjectManager());
//// }
////
//// // Tests the finishing of a project
//// @Test
//// public void testEndProject() {
//// Employee employee = new Employee(null, "INIT", null);
////
//// employee.makeManager("Project1");
////
//// assertTrue(employee.getProjectInChargeOf().isActive());
////
//// employee.endProject();
////
//// assertFalse(Platform.getProject("Project1").isActive());
//// }
////
//// // Tests the creation of an activity and the projectmanager assigning an
//// // activity
//// @Test
//// public void testAssignActivity() {
////
//// Employee employee = new Employee(null, "INIT", null);
////
//// assertTrue(employee.isFree());
//// Date start = new Date(2016, 5, 2);
//// Date end = new Date(2016, 5, 5);
////
//// Activity activity = new Activity(start, end, "Do something", "TODO1");
////
//// employee.addActivity(activity);
////
//// assertFalse(employee.isFree());
////
//// }
////
//// // Tests the finishing of an activity
//// @Test
//// public void testEndActivity() {
////
//// Employee employee = new Employee(null, "INIT", null);
////
//// Date start = new Date(2016, 5, 2);
//// Date end = new Date(2016, 5, 5);
////
//// Activity activity = new Activity(start, end, "Do something", "TODO1");
////
//// employee.addActivity(activity);
////
//// assertEquals(1, employee.viewActivities().size());
////
//// employee.endActivity();
////
//// }
////
//// @Test
//// public void useCase1() {
////
//// Employee employeeManager = new Employee(null, "INIT", null);
////
//// employeeManager.makeManager("Project1");
////
//// Employee employee2 = new Employee(null, "INIT", null);
////
//// Date start = new Date(2014 - 1900, 4, 2);
//// Date end = new Date(2016 - 1900, 11, 2);
////
//// // Testing that a non-manager cannot create the activity
//// assertFalse(employee2.createActivity(start, end, "Do something", "TODO1"));
////
//// // Testing that you cannot allocate more than 20 activities to a worker
//// List<Employee> employeeList = new ArrayList<Employee>();
//// employeeList.add(employee2);
////
//// for (int i = 1; i <= 20; i++) {
//// assertTrue(employeeManager.createActivity(start, end, "Do something" + i,
//// "TODO" + i));
//// assertTrue(employeeManager.getProjectInChargeOf().assignActivity(employeeList,
//// "TODO" + i));
//// }
////
//// assertFalse(employeeManager.createActivity(start, end, "Do something20",
//// "TODO20"));
//// assertTrue(employeeManager.createActivity(start, end, "Do something21",
//// "TODO21"));
//// // Too many activities
//// assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList,
//// "TODO21"));
////
//// // Creating report
//// employee2.endActivity();
//// employeeManager.getProjectInChargeOf().createReport();
////
//// // Testing Sickness / Vacation status
//// Employee employee3 = new Employee(null, "INIT", null);
//// List<Employee> employeeList2 = new ArrayList<Employee>();
//// employeeList.add(employee3);
////
//// assertTrue(employee3.isAvailable());
////
//// employee3.setAvailable(false);
////
//// assertFalse(employee3.isAvailable());
////
//// assertTrue(employeeManager.createActivity(start, end, "Do something22",
//// "TODO22"));
//// // Is sick/on vacation
//// assertFalse(employeeManager.getProjectInChargeOf().assignActivity(employeeList,
//// "TODO22"));
////
//// }
////
//// }