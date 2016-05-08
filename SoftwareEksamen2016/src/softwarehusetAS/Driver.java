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
	}
	
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
				input=sc.next();
				emp.makeManager(input);
				doOption(mainMenu());
				break;
			case 3: // change user
				logIn();
				doOption(mainMenu());
				break;
			case 11: //Create activity
				if(emp.isProjectManager()){
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
					System.out.println("Please enter a description for the activity");
					String desc = sc.next();
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
			default:
				System.out.println("Please enter a valid option");
				doOption(mainMenu());
				break;
		}
		
		
		
		
	}

}
