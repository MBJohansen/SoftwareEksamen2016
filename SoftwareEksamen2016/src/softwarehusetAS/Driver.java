package softwarehusetAS;

import java.util.Date;
import java.util.Scanner;

public class Driver {

	static String loggedID;
	static Employee emp;
	public static void main(String[] args) {
		
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
		
		if(emp.isProjectManager()){
			System.out.println("4. Create activity");
			System.out.println("5. Assign activity");

			
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
		boolean isInt=true;
		
		for(int i=0;i<s.length();i++){
			if(!Character.isDigit(s.charAt(i))&&s.charAt(i)!='.'){
				isInt=false;
			}
		}
		
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
			case 4:
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
					String desc = sc.nextLine();
					System.out.println("Please enter an ID for the activity");
					String ID=sc.next();
					
					if(success){
						emp.createActivity(start, end, desc, ID);
						System.out.println("The activity " + ID + " has been created");

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
			case 5:
				break;
			case 6:
				break;
			default:
				break;
		}
		
		
		
		
	}

}
