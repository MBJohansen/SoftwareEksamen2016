package softwarehusetAS;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.activity.ActivityCompletedException;

public class Employee {
	private boolean free = true;
	private Platform platform;
	private List<Activity> activities;
	private List<Activity> allActivities;
	private Activity currentActivity;
	private String ID;
	private Project currentProject;
	private boolean isProjectManager = false;
	private boolean available = true;
	private double hoursWorked = 0;

	public Employee(List<Activity> activities, String ID, Project currentProject) {
		free = true;
		if (activities == null) {
			activities = new ArrayList();
			List<Activity> activities2 = new ArrayList();
			this.allActivities = activities2;
		} else {
			List<Activity> activities2 = new ArrayList();
			this.allActivities = activities2;

			for (int i = 0; i < activities.size(); i++) {
				allActivities.add(activities.get(i));
			}
		}

		this.ID = ID;
		this.activities = activities;

		this.currentProject = currentProject;
		if (!Platform.editEmployee(this.getID())) {
			Platform.addEmployee(this);
		}

	}

	public String getID() {
		return ID;
	}

	public boolean onVacation() {
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).getID().equals("VAC")) {
				Date currentDate = new Date();
				if (currentDate.after(activities.get(i).getStartDate())
						&& currentDate.before(activities.get(i).getEndDate())) {
					return true;
				}

			}
		}

		return false;
	}

	public void makeManager(String projectName) {

		if (isProjectManager) {
			System.out.println(this.ID + " is already a project manager for " + this.currentProject.name);
		} else {
			isProjectManager = true;
			Project newProject = new Project(null, null, projectName);
			currentProject = newProject;
			Platform.editProjects(newProject);
		}
	}

	public boolean isProjectManager() {
		return isProjectManager;
	}

	public Project getProjectInChargeOf() {
		if (isProjectManager) {
			return currentProject;
		}
		return null;
	}

	public void setActivity(List<Activity> activities) {
		if (activities != null) {
			this.activities = activities;
			this.allActivities = activities;
			free = false;
			currentActivity = activities.get(0);
		}
	}

	public boolean addVacation(Date start, Date end) {
		if (start.before(new Date())) {
			return false;
		} else {

			if (createActivity(start, end, "Vacation", "VAC")) {
				return true;
			}
			return false;
		}
	}

	public void addHours(double hours, String ID) {
		boolean activityFound = false;
		for (int i = 0; i < allActivities.size(); i++) {
			if (allActivities.get(i).getID().equals(ID)) {
				activityFound = true;
				double hourCheck = (hours * 10) / 5;
				if (hourCheck == Math.ceil(hourCheck)) {
					hoursWorked = hoursWorked + hours;
					allActivities.get(i).addHours(hours);
				} else {
					System.out.println("Please limit the hour logging to half hour increments");
				}
			}
		}
		if (!activityFound) {
			System.out.println("You are not assigned the indicated activity");
		}

	}

	public void addActivity(Activity activity) {
		if (activities.size() == 0) {
			currentActivity = activity;
		}
		activities.add(activity);
		allActivities.add(activity);
		free = false;
		Platform.update();
	}

	public boolean searchHelp(String ID) {
		Platform.update();

		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).getID().equals(ID)) {
				if (activities.get(i).getEndDate().before(new Date())) {
					return false;
				}
				if (Platform.getFreeEmployees().size() > 0) {
					Platform.getFreeEmployees().get(0).addActivity(activities.get(i));
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean Edit(Date start, Date end, String ID) {
		for (int i = 0; i < allActivities.size(); i++) {
			if (allActivities.get(i).getID().equals(ID)) {
				allActivities.get(i).setStartDate(start);
				allActivities.get(i).setEndDate(end);
				return true;
			}
		}
		return false;
	}

	public double getHours() {
		return hoursWorked;
	}

	public List<Activity> viewActivities() {
		return allActivities;
	}

	public List<Activity> viewActiveActivities() {
		return activities;
	}

	public void endOfWeek() {
		for (int i = 0; i < allActivities.size(); i++) {
			if (allActivities.get(i).getFinished()) {
				allActivities.remove(i);
			}
		}
	}

	public long getActiveTime(String activityID) {
		for (int i = 0; i < allActivities.size(); i++) {
			if (allActivities.get(i).getID().equals(activityID)) {
				return (allActivities.get(i).getEndDate().getTime() - allActivities.get(i).getStartDate().getTime())
						/ 3600000;
			}
		}
		return 0;
	}

	public String toString() {
		return ID;
	}

	public void endActivity() {
		currentActivity.setFinished();
		activities.remove(0);
		if (activities.size() > 0) {
			currentActivity = activities.get(0);
		} else {
			free = true;
			Platform.update();
		}
	}

	public boolean isFree() {
		return free;
	}

	public void endProject() {
		getProjectInChargeOf().setFinished();
		this.isProjectManager = false;
	}

	public boolean createActivity(Date startDate, Date endDate, String description, String activityID) {
		if (startDate.after(endDate)) {
			return false;
		}

		if (isProjectManager) {
			for (int i = 0; i < currentProject.getActivities().size(); i++) {
				if (currentProject.getActivities().get(i).getID().equals(activityID)) {
					Platform.update();

					return false;
				}
			}

			currentProject.createActivity(startDate, endDate, description, activityID);
			Platform.update();
			return true;
		}
		Platform.update();
		if (activityID.equals("VAC")) {
			addActivity(new Activity(startDate, endDate, description, activityID));
			return true;
		}
		return false;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean avail) {
		if (free && !avail) {
			free = false;
		} else {
			if (avail && this.activities.size() == 0) {
				free = true;
			}
		}
		this.available = avail;
		Platform.update();
	}
}
