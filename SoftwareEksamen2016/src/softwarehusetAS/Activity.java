package softwarehusetAS;

import java.util.Date;

public class Activity {
	private Date startDate;
	private Date endDate;
	private String description;
	private Boolean finished=false;
	private String activityID;
	private double hoursUsed;
	
	
	
	public Activity(Date startDate, Date endDate, String description, String activityID){
		this.startDate=startDate;
		this.endDate=endDate;
		this.description=description;
		this.activityID=activityID;
		hoursUsed=0;
	}
	
	public void addHours(double inHours){
		hoursUsed=hoursUsed+inHours;
	}

	public double getHours(){
		return hoursUsed;
	}
	
	public Date getStartDate(){
	return startDate;
	}
	
	public void setStartDate(Date newStartDate){
	this.startDate=newStartDate;
	}
	
	public Date getEndDate(){
	return endDate;
	}
	
	public void setEndDate(Date newEndDate){
	this.endDate=newEndDate;
	}
	
	public void setFinished(){
		finished=true;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Boolean getFinished(){
		return finished; 
	}
	
	public String getID(){
		return activityID;
	}
	public String toString(){
		return activityID;
	}

}
