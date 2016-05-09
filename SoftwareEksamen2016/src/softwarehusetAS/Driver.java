package softwarehusetAS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Driver {

	static String loggedID;
	static Employee emp;
	public static void main(String[] args) {
		
		Employee employeeManager = new Employee(null, "MAN", null);
		Employee employee2 = new Employee(null, "RAND", null);
		
		Date start = new Date(2016 - 1900, 2, 2);
		Date end = new Date(2016 - 1900, 7, 5);
		
		employeeManager.makeManager("Project1");
		
		employeeManager.createActivity(start, end, "Do something1", "TODO1");
		employeeManager.createActivity(start, end, "Do something2", "TODO2");
		
		employeeManager.getProjectInChargeOf().endDate=new Date(2017-1900,5-1,11);
		
		
		logIn();
		
		doOption(mainMenu());
		
		
	}
	
	public static void logIn(){
		String input;
		loggedID="";
		boolean loggedIn=false;
		System.out.println("Enter your user ID: ");
		Scanner sc = new Scanner (System.in);
		input=sc.next();
		boolean digits=false;
		for(int i=0;i<input.length();i++){
			if(Character.isDigit(input.charAt(i))){
				digits=true;
			}
		}
		
		if(input.length()>4||digits){
			System.out.println("Please enter a valid ID");
			logIn();
			
		}else{
		
		for(int i=0;i<Platform.getEmployees().size();i++){
			if(input.equals(Platform.getEmployees().get(i).getID())){
				System.out.println("Welcome back " + input);
				loggedID=input;
				loggedIn=true;
				emp=Platform.getEmployees().get(i);
			}
		}
		if(!loggedIn){
			System.out.println("We cannot match your ID to any existing one, so we have created a new profile for you.");
			emp = new Employee(null,input,null);
			loggedID=input;
		}
	}}
	
	public static int mainMenu(){

		
		
		System.out.println("Welcome " + loggedID + " please use the numbers shown to navigate the program.");
		System.out.println("0. Exit");
		System.out.println("1. Add hours");
		System.out.println("2. Become project manager");
		System.out.println("3. Change user");
		System.out.println("4. End activity");
		System.out.println("5. Add vacation");
		System.out.println("6. Search help");
		System.out.println("7. End week");
		System.out.println("8. View activities");
		System.out.println("9. Get time assigned to activity");
		System.out.println("10. Set availability");


		
		if(emp.isProjectManager()){
			System.out.println("11. Create activity");
			System.out.println("12. Assign activity");
			System.out.println("13. Create report");
			System.out.println("14. Change project end date");
			System.out.println("15. Find suitable employees");
			System.out.println("16. Set employee's availabiity");
			System.out.println("17. View work hours used on activity");


		}
		Scanner sc = new Scanner (System.in);
		String input;
		
		
		input=sc.next();
		int choice=0;
		boolean faultyInput=false;
		for(int i =0;i<input.length();i++){
			if(!Character.isDigit(input.charAt(i))){
				faultyInput=true;
			}
		}
		if(!faultyInput){
			choice=Integer.parseInt(input);
		}else{
			System.out.println("Please enter a valid option");
			mainMenu();
		}
		
		
		return choice;
	}
	public static boolean checkInt( String s){
		boolean isInt=true;
		
		for(int i=0;i<s.length();i++){
			if(!Character.isDigit(s.charAt(i))){
				isInt=false;
			}
		}
		
		return isInt;
	}
	
	public static boolean checkDouble( String s){
		int numDots=0;
		boolean isInt=true;
		
		for(int i=0;i<s.length();i++){
			if(!Character.isDigit(s.charAt(i))&&s.charAt(i)!='.'){
				isInt=false;
			}
			if(s.charAt(i)=='.'){ // should only be one
				numDots++;
			}
		}
		if(numDots>1){return !isInt;}
		return isInt;
	}
	
	
	public static void doOption(int in){
		Scanner sc = new Scanner(System.in);
		double choice=0;
		String input;
		switch(in){
			case 0:
			break;
			case 1: // Addhours
				System.out.println("How many hours have you worked: ");
				input=sc.next();
				if(checkDouble(input)){
					choice=Double.parseDouble(input);
				}else{
					System.out.println("Please enter hours by half hour increments");
					doOption(mainMenu());
					break;
				}
				
				System.out.println("Please enter the activity ID");
				input=sc.next();
				emp.addHours(choice, input);
				doOption(mainMenu());
				break;
			case 2: // Become project manager
				System.out.println("Please enter the name of the project");
				String projName=sc.next();
				System.out.println("Please enter the end date of the project");
				input=sc.next();
				Date ended;
				if(checkInt(input)&&input.length()==8){
					ended = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
					if(ended.after(new Date())){
						emp.makeManager(projName);
						emp.getProjectInChargeOf().endDate=ended;
						System.out.println(projName+" has been created");
					}else{
						System.out.println("The date you entered was before today. Please try again with a valid date");
						}
				}else{
					System.out.println("You must follow the format YYYYMMDD as described. Please try again with a valid date");
				}
				doOption(mainMenu());
				break;
			case 3: // change user
				logIn();
				doOption(mainMenu());
				break;
			case 11: //Create activity
				if(emp.isProjectManager()){
					if(emp.getProjectInChargeOf().endDate.after(new Date())){
					boolean success=true;
					Date start=null;
					Date end=null;
					System.out.println("Please enter a start date for the activity in the format: YYYYMMDD");
					input=sc.next();
					if(checkInt(input)&&input.length()==8){
					start = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
					}else{success=false;}
					System.out.println("Please enter an end date for the activity in the format: YYYYMMDD");
					input=sc.next();
					if(checkInt(input)&&input.length()==8){
						end = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
					}else{success=false;}
					System.out.println("Please enter a description for the activity");
					sc.useDelimiter(System.getProperty("line.separator"));

					String desc=sc.next();
					
					System.out.println("Please enter an ID for the activity");
					String ID=sc.next();
					
					if(success){
						if(emp.createActivity(start, end, desc, ID)){
						System.out.println("The activity " + ID + " has been created");
						}
						else{System.out.println("You have entered invalid dates. Please try again");}
					}else{
						System.out.println("You have entered invalid dates. Please try again");
					}
					doOption(mainMenu());
				}else{
					System.out.println("There must be more time assigned to the project before you can create new activities");
					doOption(mainMenu());
					}
				}
				else{
					System.out.println("Please enter a valid option");
					doOption(mainMenu());

				}
				break;
			case 12: // Assign activity
				if(emp.isProjectManager()){
					List<String> employeeList = new ArrayList();
					System.out.println("Please enter the ID of the activity");
					String activityID=sc.next();
					System.out.println("Please enter the ID of the employees you want working on the activity. Seperate each input with a break and end with 'q'");
					String next=sc.next();
					while(!next.equals("q")){
					employeeList.add(next);
					next=sc.next();
					}
					emp.getProjectInChargeOf().assignActivity(Platform.getEmployees(employeeList), activityID);
					if(emp.getProjectInChargeOf().getActivities().size()==0 || emp.getProjectInChargeOf().viewActivity(activityID).equals("Activity does not exist")){
						System.out.println("You do not have managing access to " + activityID);
					}
					doOption(mainMenu());// s
					
				}else{
						System.out.println("Please enter a valid option");
						doOption(mainMenu());

					}
				break;
			case 4: // End activity
				if(emp.viewActiveActivities().size()>0){
				emp.endActivity();
				if(emp.viewActiveActivities().size()>0){
				System.out.println("You have ended your current activity and should now work on "+ emp.viewActiveActivities().get(0));
				}else{
					System.out.println("You have ended your current activity and you are now free");
				}
				
				}
				else{
					System.out.println("You must have an active activity to end one");
					
				}
				doOption(mainMenu());
				break;
			case 5: // Add vacation
				boolean success=true;
				Date start=null;
				Date end=null;
				System.out.println("Please enter a start date for the activity in the format: YYYYMMDD");
				input=sc.next();
				if(checkInt(input)){
				start = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
				}else{success=false;}
				System.out.println("Please enter an end date for the activity in the format: YYYYMMDD");
				input=sc.next();
				if(checkInt(input)){
					end = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
				}else{success=false;}
				if(success){
					if(!emp.addVacation(start, end)){
						System.out.println("Please enter valid dates");
					}
				}else{
					System.out.println("Please enter valid dates");
				}
				doOption(mainMenu());
				
				break;
			case 6: // Search for help
				System.out.println("Enter the ID for the activity you need help with");
				input=sc.next();
				if(emp.searchHelp(input)){
					System.out.println("A coworker will help shortly");
				}else{
					System.out.println("You are not able to receive help at this moment");
				}
				doOption(mainMenu());
				break;
			case 7: // End week
				emp.endOfWeek();
				int numActiv=20-emp.viewActivities().size();
				System.out.println("You have registered your finished activities for the week and can start "+numActiv + " new ones");
				doOption(mainMenu());
				break;
			case 8: // View activities
				for(int i=0;i<emp.viewActivities().size();i++){
					System.out.println(emp.viewActivities().get(i));
				}
				doOption(mainMenu());
				break;
			case 9: // Get time assigned for activity
				System.out.println("Please enter the ID of the activity you want to look at");
				input=sc.next();
				if(emp.getActiveTime(input)==0){
					System.out.println("The activity you referred to cannot be found in your activities to do. Please try again.");
				}else{
					System.out.println("There are " + emp.getActiveTime(input) + " hours assigned to the activity " + input);
				}
				doOption(mainMenu());
				break;
			case 10: // Set availability
				System.out.println("Are you going to be unavailable? (Y/N)");
				input=sc.next();
				if(input.equals("Y")){
				emp.setAvailable(false);
				}else if(input.equals("N")){
					emp.setAvailable(true);
				}else{
					System.out.println("Please enter a valid option");
				}
				doOption(mainMenu());
				break;
			case 13:
				if(emp.isProjectManager()){
					if(new Date().getDay()!=5){
						System.out.println("Please note that today is not a friday");
					}
					emp.getProjectInChargeOf().createReport();
					doOption(mainMenu());
				}else{
						System.out.println("Please enter a valid option");
						doOption(mainMenu());

					}
				
				break;
			case 14:
				if(emp.isProjectManager()){
					
					System.out.println("Please enter a new end date in the format YYYYMMDD");
					input=sc.next();
					if(checkInt(input)&&input.length()==8){
						ended = new Date(Integer.parseInt(input.substring(0, 4))-1900,Integer.parseInt(input.substring(4,6))-1,Integer.parseInt(input.substring(6,8)));
						if(ended.after(new Date())){
							emp.getProjectInChargeOf().endDate=ended;
							System.out.println("The end date has been changed to "+ended);
						}else{
							System.out.println("You cannot change your end date to a date before today");
						}
					}else{
						System.out.println("Please enter a valid date in the format YYYYMMDD");
					}
					doOption(mainMenu());
				}else{
						System.out.println("Please enter a valid option");
						doOption(mainMenu());

					}
				
				break;
			case 15:
				if(emp.isProjectManager()){
				System.out.println("How man employees are you looking for");
				input=sc.next();
				if(checkInt(input)){
					List<Employee> printer=Platform.getSuitableEmployees(Integer.parseInt(input));
					if(printer!=null){
						if(Integer.parseInt(input)==0){
							System.out.println("You didn't look for any employees");}
						System.out.println("The ID's of the most suitable employees are "+printer);
					}
					
					doOption(mainMenu());

				}
				
				}else{
					System.out.println("Please enter a valid option");
					doOption(mainMenu());

				}
				
				break;
			case 16:
					if(emp.isProjectManager()){
						System.out.println("Please enter the ID of the employee you want to mark unavailable/available");
						input=sc.next();
						List<String> listOfEmp=new ArrayList();
						listOfEmp.add(input);
						if(Platform.getEmployees(listOfEmp).size()>0){
						System.out.println("Will he be unavailable? (Y/N)");
						input=sc.next();
						if(input.equals("Y")){
							Platform.getEmployees(listOfEmp).get(0).setAvailable(false);
						}else if(input.equals("N")){
							Platform.getEmployees(listOfEmp).get(0).setAvailable(true);
						}else{
							System.out.println("Please enter a valid option");
						}}
						
						
						doOption(mainMenu());
					}else{
							System.out.println("Please enter a valid option");
							doOption(mainMenu());

						}
					
					break;
			case 17:
				if(emp.isProjectManager()){
					System.out.println("Please enter the activity ID");
					input=sc.next();
					boolean found=false;
					
					for(Activity a : emp.getProjectInChargeOf().getActivities()){
						if(a.getID().equals(input)){
							System.out.println(a.getHours()+" hours have been recorded for this activity");
							found=true;
						}
					}
					if(!found){
						System.out.println("The activity could not be found");
					}
					
				}else{
						System.out.println("Please enter a valid option");
						doOption(mainMenu());

					}
				
				break;
			default:
				System.out.println("Please enter a valid option");
				doOption(mainMenu());
				break;
		}
		
		
		
		
	}

}
