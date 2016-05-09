package softwarehusetAS;

import java.util.ArrayList;
import java.util.List;

public abstract class Platform {

	private static List<Employee> employees = new ArrayList<Employee>();
	private static List<Employee> availableEmployees = new ArrayList<Employee>();
	private static List<Project> projects = new ArrayList<Project>();

	

	public static void endOfWeek() {
		for (int i = 0; i < employees.size(); i++) {
			employees.get(i).endOfWeek();
		}
	}

	public static List<Employee> getSuitableEmployees(int n) {
		if (n > getAvailableEmployees().size()) {
			System.out.println("Not enough employees to get " + n + " suitable ones");
			return null;
		}
		if (n == 0) {
			return new ArrayList();
		}
		int numberIter = n;
		List<Employee> finalList = getFreeEmployees();
		List<Integer> skips = new ArrayList();

		if (finalList.size() > n) {
			for (int i = 0; i < finalList.size() - n; i++) {
				finalList.remove(finalList.size() - 1);
			}
			return finalList;
		}
		while (numberIter > 0 && finalList.size() < n) {
			long firstEnd = 0;
			long latestEndForEmp = 0;
			skips.add(1);
			boolean skip = false;

			for (int i = 0; i < employees.size(); i++) {
				latestEndForEmp = 0;
				for (int k = 0; k < skips.size(); k++) {
					if (skips.get(k) == i) {
						skip = true;
					}
				}
				if (!skip) {
					if (employees.get(i).viewActivities().size() < 20 && employees.get(i).isAvailable()) {

						for (int j = 0; j < employees.get(i).viewActiveActivities().size(); j++) {

							if (employees.get(i).viewActiveActivities().get(j).getEndDate()
									.getTime() > latestEndForEmp) {
								latestEndForEmp = employees.get(i).viewActiveActivities().get(j).getEndDate().getTime();
							}
						}
						if (firstEnd == 0 || latestEndForEmp < firstEnd) {
							firstEnd = latestEndForEmp;
							skips.set(skips.size() - 1, i);
						}
					}

				} else {
					skip = false;
				}

			}
			finalList.add(employees.get(skips.get(skips.size() - 1)));

			numberIter--;
		}
		

		return finalList;
	}

	public static List<Employee> getEmployees() {
		return employees;
	}

	public static List<Employee> getEmployees(List<String> s) {
		List<Employee> emp = new ArrayList();
		List<String> notFound = new ArrayList();
		boolean found = false;

		for (int i = 0; i < s.size(); i++) {
			found = false;
			for (int j = 0; j < employees.size(); j++) {
				if (s.get(i).equals(employees.get(j).getID())) {
					emp.add(employees.get(j));
					found = true;
				}
			}
			if (!found) {
				notFound.add(s.get(i));
			}
		}
		for (int i = 0; i < notFound.size(); i++) {
			System.out.println("Employee " + notFound.get(i) + " could not be found");
		}

		return emp;
	}

	public static List<Employee> getAvailableEmployees() {
		List<Employee> outputList = new ArrayList<Employee>();

		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).isAvailable() && employees.get(i).viewActivities().size() < 20) {
				outputList.add(employees.get(i));
			}
		}

		return outputList;
	}

	public static List<Employee> getFreeEmployees() {
		return availableEmployees;
	}

	public static List<Project> getProjects() {
		return projects;
	}

	public static boolean editEmployee(String employeeID) {
		Boolean found = false;
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getID().equals(employeeID)) {
				// employees.remove(i);
				found = true;
			}
		}
		return found;
	}

	public static void addEmployee(Employee emp) {
		employees.add(emp);
	}

	public static void editProjects(Project project) {
		Boolean found = false;
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).equals(project)) {
				projects.remove(i);
				found = true;
			}
		}
		if (found == false) {
			projects.add(project);
		}
	}

	public static void update() {
		availableEmployees.clear();
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).isFree()) {
				availableEmployees.add(employees.get(i));
			}
		}
	}

	public static void reset() {
		employees = new ArrayList<Employee>();
		availableEmployees = new ArrayList<Employee>();
		projects = new ArrayList<Project>();
	}

	public static Project getProject(String projectName) {
		for (Project project : projects) {
			if (projectName.equals(project.getName())) {
				return project;
			}
		}
		return null;
	}
}
