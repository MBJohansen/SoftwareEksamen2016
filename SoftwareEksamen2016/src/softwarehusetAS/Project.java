package softwarehusetAS;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Project{
	
	String name;
	List<Employee> projectEmployee;
	List<Activity> projectActivities;
	Date endDate;
	Date startDate;
	boolean isActive;
	
	public Project(List<Employee> projectEmployee, List<Activity> projectActivities, String name){
		if(projectActivities==null){
			projectActivities = new ArrayList();
		}
		if(projectEmployee==null){
			projectEmployee = new ArrayList();
		}
		this.projectEmployee=projectEmployee;
		this.projectActivities=projectActivities;
		this.name=name;
		isActive=true;
	}
	
	public List <Activity> getActivities(){
		return projectActivities;
	}
	
	public String getName() {
		return name;
	}
	
	public void createActivity(Date start, Date end, String description, String id){	
		Activity newActivity = new Activity(start, end, description, id);
		projectActivities.add(newActivity);
		
	}
	
	public void editActivity(Date newEndTime, Activity activity){
		newEndTime.setYear(newEndTime.getYear());
		activity.editEndDate(newEndTime);		
	}
	
	public boolean firstDateAfterSecond(Date first, Date second){
		if(first.getYear()>second.getYear()){
			return true;
		}
		else if(first.getYear()==second.getYear()){
			if(first.getMonth()>second.getMonth()){
				return true;
			}
			else if(first.getMonth()==second.getMonth()){
				if(first.getDate()>second.getDate()){
					return true;
				}
				else if(first.getDate()==second.getDate()){
					if(first.getHours()>second.getHours()){
						return true;
					}
					else if(first.getHours()==second.getHours()){
						if(first.getMinutes()>second.getMinutes()){
							return true;
						}
						else if(first.getMinutes()==first.getMinutes()){
							if(first.getSeconds()>second.getSeconds()){
								return true;
							}
						}
					}
				}
			}
			
		}
		
		
		
		return false;
	}
	
	public boolean assignActivity(List<Employee> employees, String activityID){	
		boolean success=false;
		int goodEmployees=0;
		Activity activity=null;
		boolean found = false;
		for(int j=0; j<projectActivities.size(); j++){
			if(projectActivities.get(j).getID().equals(activityID)){
				activity=projectActivities.get(j);
				found=true;
			}
		}
		if(!found){
			return found;
		}
		
		Date currentDate = new Date ();
		
		if(firstDateAfterSecond(currentDate,activity.getEndDate())){
			return false;
		}
		
		for(int i=0; i<employees.size(); i++){
			if(!(employees.get(i).viewActivities().size()>=20)&&employees.get(i).isAvailable()){
				employees.get(i).addActivity(activity);
				goodEmployees++;
				projectEmployee.add(employees.get(i));
			}else{
				System.out.println(employees.get(i)+" could not be assigned");
			}
		}
		
		if(goodEmployees==employees.size()){
			success=true;
		}	
		return success;
	}
	
	public List<Activity> createReport(){
		List<Activity> finishedActivities = new ArrayList();
		System.out.println("Description: " + " Finished: ");
		for(int i=0; i<projectActivities.size(); i++){
			System.out.println(projectActivities.get(i).getDescription()+" " + projectActivities.get(i).getFinished());
		if(projectActivities.get(i).getFinished()){
			finishedActivities.add(projectActivities.get(i));
		}
		}
		return finishedActivities;
	}
	
	public List<Activity> viewEmployeeActivities(Employee e){
		return e.viewActivities();
	}
	
	public String viewActivity(Activity activity){
		return "Start: "+activity.getStartDate()+ " End: "+ activity.getEndDate()+ " Description: "+ activity.getDescription(); 
	}
	
	public void createProject(List<Activity> activities){
		projectActivities=activities;
	}
	
	public void addEmployee(Employee employee){
		projectEmployee.add(employee);
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setFinished(){
		isActive=false;
	}
	
	public List<Employee> getEmployees() {
		return projectEmployee;
	}
	
}
