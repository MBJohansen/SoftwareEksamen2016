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
	private boolean available=true;
	private double hoursWorked=0;
	
	public Employee(List<Activity> activities, String ID, Project currentProject){
		
		if(activities==null){
			activities = new ArrayList();
			List<Activity> activities2 = new ArrayList();
			this.allActivities=activities2;
		}
		else{
			List<Activity> activities2 = new ArrayList();
			for(int i=0;i<activities.size();i++){
				allActivities.add(activities.get(i));
			}
		}
		
		this.ID=ID;
		this.activities=activities;
		
		this.currentProject=currentProject;
		Platform.editEmployee(this);

	}
	
	public boolean onVacation (){
		for(int i=0;i<activities.size();i++){
			if(activities.get(i).getID().equals("VAC")){
				Date currentDate = new Date();
				if(currentDate.after(activities.get(i).getStartDate()) && currentDate.before(activities.get(i).getEndDate())){
					return true;
				}
				
			}
		}
			
		return false;
	}
	
	public void makeManager(String projectName){
		isProjectManager=true;
		Project newProject = new Project(null, null,projectName);
		currentProject=newProject;
		Platform.editProjects(newProject);
	}
	
	public boolean isProjectManager() {
		return isProjectManager;
	}
	
	public Project getProjectInChargeOf(){
		if(isProjectManager){
			return currentProject;
		}
		return null;
	}
	
	public void setActivity(List<Activity> activities){
		this.activities=activities;
		this.allActivities=activities;
		if(activities.equals(null)){
			
		}else{
		free=false;
		currentActivity=activities.get(0);
		}
	}
	
	public void addVacation(Date start, Date end){
		createActivity(start,end,"Vacation","VAC");
		
	}
	
	public void addHours(double hours,Activity activity){
		hoursWorked=hoursWorked+hours;
		activity.addHours(hours);
		
	}
	
	public void addActivity(Activity activity){
		if(activities.size()==0){
			currentActivity=activity;
		}
		activities.add(activity);
		allActivities.add(activity);
		free=false;
	}
	
	
	public void SearchHelp(Activity activity){
		Platform.update();
		Platform.getAvailableEmployees().get(0).addActivity(activity);
	}
	
	/*public void RegisterTime(Activity activity, Date date, Boolean pastEvent){
		
	}*/
	
	public boolean Edit(Date start, Date end, Activity activity){
		for(int i =0; i<allActivities.size(); i++){
			if(allActivities.get(i).equals(activity)){
				allActivities.get(i).setStartDate(start);
				allActivities.get(i).setEndDate(end);
				return true;
			}
		}
		return false;
	}
	
	public List<Activity> viewActivities(){
		return allActivities;
	}
	
	public List<Activity> viewActiveActivities(){
		return activities;
	}
	
	public void endOfWeek (){
		for(int i=0;i<allActivities.size();i++){
			if(allActivities.get(i).getFinished()){
				allActivities.remove(i);
			}
		}
	}
	
	public long getActiveTime(String activityID){
		for(int i = 0; i<allActivities.size();i++){
			if(allActivities.get(i).getID().equals(activityID)){
				return (allActivities.get(i).getEndDate().getTime()-allActivities.get(i).getStartDate().getTime())/3600000;
			}
		}
		return 0;
	}
	
	public String toString(){
		return ID;
	}
	
	public void endActivity(){
		currentActivity.setFinished();
		activities.remove(0);
		if(activities.size()>0){
		currentActivity = activities.get(0);
		}else{
			free=true;
			Platform.update();
		}
	}
	
	public boolean isFree(){
		return free;
	}
	
	public void endProject(){
		getProjectInChargeOf().setFinished();
		this.isProjectManager=false;
	}
	
	public boolean createActivity(Date startDate, Date endDate, String description, String activityID){
		

		if(isProjectManager){
			for(int i=0;i<currentProject.getActivities().size();i++){
				if(currentProject.getActivities().get(i).getID().equals(activityID)){
					return false;
				}
			}
			
			currentProject.createActivity(startDate, endDate, description, activityID);
			return true;
		}
		return false;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean avail){
		this.available=avail;
	}
}
