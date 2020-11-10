/*
 * Name: Darvin Limanto
 * ID : 19515566
 * Campus: Parramatta South
 * Class: Friday 9 a.m -  11a.m  
 * Tutor Name: Janagan Sivagnanasundaram
 */

import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu_19515566 {
	static Scanner kb = new Scanner (System.in);
		
		public static void main(String[] args) {
			// menuItems to display menus for the program.
			String[] menuItems = { "Display Drivers", "Import Infringement File", "Generate Suspension Report", "Save Driver Records", 
														"Display Fined Drivers","Exit Program"};
			int choice;										// User's choice for menu
			final int minMenu = 1;							// Min menu option
			final int maxMenu = menuItems.length;			// Max menu option
			int count = 0;									// Count lines on Driver.txt file
			int obj = 0;									// Hold value for each driver object on DriverArray		
			int demerit = 0;								// Hold demerit Points from tokens to int
			int excess;										// Excess Speed value
			int location = 0;								// Location of the searched files
			int infringements = 0;							// Count Total infringements 
			int suspended = 0;								// Count Total suspended licence
			int index;				  	    				// Hold penalty index option
			int newDemerit;				  	    			// Penalty for Demerit Points
			float newFine;				  	    			// Penalty for Fines 
			float totalFine= 0.0f;							// Count Total Fines
			String filename; 								// Filename location
			String str;										// Read each line of text file
			String [] tokens;								// Help breaking files from comma
			Scanner inputFile;								// Hold scanner value
			String option;									// Hold option choice (Yes/ No)
			boolean newLicenceStatus;						// Penalty for licence status 
			boolean exitChoice = true;						// Hold option for users to exit the program (false = don't exit)
			boolean change = false;							// Hold change value from the driver records (false = records have been saved)
	    
			/*
			 * Attempt to open file "Driver.txt"
			 * Validate filename if it's available 
			 * If Driver.txt file does not exist, notify user and ask for new file location
			 * Open file from the new location.
			 */
			filename = "Driver.txt";
			do {
	 			inputFile = openFile (filename);
				filename = validate (inputFile, filename);		
	 		} while (inputFile == null);
	 		
			/*
			 *  Read file first time (inputFile)
			 *  Use count to count how many lines in the text file.
			 *  Create arrays called "driverArray" based on the lines count to store Driver object.
			 *  Close inputFile 
			 */	
			while (inputFile.hasNext()) {
				str = inputFile.nextLine();
				count++;		
			}
			inputFile.close();	
			Driver_19515566[] driverArray = new Driver_19515566 [count];
			
			/*
			 * 	Read the same file second time 
			 * 	Split the text on each line for the commas by using tokens.
			 * 	Each token represent a data value with different type
			 * 	There are 9 tokens in each line of the "Driver.txt" file.  				   
			 * 	Convert the value of tokens[7] which is demerit point into integer value.
			 * 	Fill each driver object with appropriate data from the text file.
			 */	
			inputFile = openFile (filename);	
			try { 
				while (inputFile.hasNext()) {
					str = inputFile.nextLine();
					tokens = str.split(",");

					/*
					 * The following code is modified from: 
					 * https://www.geeksforgeeks.org/string-to-integer-in-java-parseint/
					 * This Integer.parseInt will allow us to convert number from strings to integer data type.
					 */
					demerit = Integer.parseInt(tokens[7]);
					// end of code				
					driverArray[obj] = new Driver_19515566(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], demerit, tokens[8], tokens[6]);			
					obj++;
				}
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println ("Wrong Text File data");
			}
			inputFile.close();
			
			 /*
		   * Display menus and let user choose an option
		   * If option 5 is chosen, users will exit the program. 
		   * "Display Drivers", "Import Infringement File", "Generate Suspension Report", "Save Driver Records", "Exit Program" 	
		   */
		  do {
			  displayMenu(menuItems);
			  choice = getMenuChoice(minMenu, maxMenu);
			  System.out.println("You chose option " + choice);      
	      
	      // If option 1 is chosen, display driver array from memory. 
	      if (choice == 1) { 
			  				displayDriver (driverArray);
			  				kb.nextLine();
			  				
			  				System.out.println ("\n\nDo you wish to sort driver record by demerit points (descending) ? (Yes/ No)");
			  				option = kb.nextLine();
			 		
			  				if (option.equalsIgnoreCase("Yes")) {
			  					Arrays.sort(driverArray, new Sort_19515566());
			  				}
			  				else {
			  					System.out.println ("You don't wish to sort driver records");
			  				}	
	      }
	      
	      /*
	       *  If option 2 is chosen, import infringement files. 
	       *  Apply punishments to the driver by checking their licence number
	       *  Generate on screen report for infringement files that displays
	       *  Total Applied Infringements, Suspended Drivers, and  Total Fines 
	       */
	      if (choice == 2) { 
	      	kb.nextLine();
	      	System.out.println ("Infringement File Location: ");
		  	  filename = kb.nextLine();
					
		  	  do {
						inputFile = openFile (filename);	
						filename = validate (inputFile, filename);		
				 	} while (inputFile == null);
					
					try { 
						while (inputFile.hasNext()) {
							str = inputFile.nextLine();
							tokens = str.split(",");

							excess = Integer.parseInt(tokens[3]);
						  location = searchArray(driverArray, tokens[1]);
							
			  	    if (location >= 0) {	
			  	    	SpeedingPenalty[] penalty = new SpeedingPenalty [1];
			  	    	
				  	    index = driverArray[location].applyPenalty(excess);				  	    
				  	    newDemerit = penalty[0].getDemerit(index);
				  	    newFine = penalty[0].getFine(index);
				  	    newLicenceStatus = penalty[0].getLicenceSuspension(index);
				  	       
				  	    /*
				  	     *  Apply Punishments for the related driver by:
				  	     *  Adding Demerit Points,
				  	     *  Suspend Driver Licence Status. This is achieved if automatic suspension is applied or 
				  	     *  current demerit points more than 13.
				  	     *  Set Fines for the driver.
				  	     *  Records of fined driver can be displayed on option 5 from the menu. 
				  	     */
				  	    newDemerit = driverArray[location].addDemerit(newDemerit);
				  	    driverArray[location].setDemerit(newDemerit);
				  	    System.out.println ("New Demerit: " + driverArray[location].getDemerit());

				  	    if (newLicenceStatus == true) {
				  	    	driverArray[location].setLicenceStatus("Suspended");
				  	    	suspended++;
				  	    }
				  	    else if (driverArray[location].getDemerit() > 13 ) {
				  	    	driverArray[location].setLicenceStatus("Suspended");
				  	    	suspended++;
				  	    	 //There have been a change in records. 
				  	    }
				  	    change = true;
				  	    
				  	    driverArray[location].setFine(newFine);
				  	    infringements++;
				  	    totalFine += newFine;
			  	    } 
			  	    else {
			  	      System.out.println("\n\nYour Driver cannot be found in the database.");
			  	      System.out.println("Driver Licence might not be NSW Licence.");
			  	    }    
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println ("Wrong Text File data");
					}
					displayReport(infringements, totalFine, suspended);		
	  	   }
	      inputFile.close();
	      
	      // If option 3 is chosen, display every driver information that have their licence suspended
	      if (choice == 3) {
	      	displaySuspendedDriver(driverArray);
	 			 	} 
	     
	      /*
	       *  If option 4 is chosen, save any changes into the same file.
	       *  Give false into change. This means changes have been saved.
	       */
	      if (choice == 4) {
	      	change = false;
	      	saveRecords(driverArray);  	
	      }
	      
	      // If option 5 is chosen, display every driver information that got a fine records
	      if (choice == 5) {
	      	displayFinedDriver(driverArray); 	
	      }
	      
	      /*
	       *  If option 6 is chosen, check if there is any changes onto the records
	       *  If there is changes that haven't been saved, ask the user if user wish to continue to exit the program
	       *  If user say yes, user can exit the program. 
	       *  Otherwise, user will be prompted back into main menu
	       */
	      if (choice == 6) {
	      	kb.nextLine(); //Consume Newline
	      	
	      	if (change == true) {
	      		System.out.println("Driver's record data have been changed");
	      	  System.out.println("Do you wish to continue to exit the program without saving ? (Yes / No)");
			      option = kb.nextLine();    
						if (option.equalsIgnoreCase("Yes")) {
							System.out.println("You chose to exit the program.");
						   exitChoice = true;    
						}     
					  else {
					     exitChoice = false;     		
					  }	
	      	}
	      	else {
	      		System.out.println("There are no changes in the record.");
	      		System.out.println("You chose to exit the program.");
	      	}			       
	      }    
	    } while (choice != maxMenu  || exitChoice == false);
	   	 
		}
		 	
						
		/*
		 * The following java method have been modified from: 
		 * Gaddis. T. (2016). Starting out with Java : from control structures through objects (6th Ed.). p.715-717. Boston :Pearson. 
		 * In this method, we try to open a text file. 
		 * If it doesn't work, catch the exception (File Not Found) and give null value to scan
		 */ 	
		public static Scanner openFile(String filename) {
	 	 	Scanner scan;
	 	 	try {
	 	 		File myFile = new File(filename);
	 	 	 	scan = new Scanner(myFile);
	 	 	} 
	 	 	catch (FileNotFoundException e) {
	 	 		scan = null;
	 	 	}
	 	 	return scan;
		}
		
		/*
		 * The idea of this method is to find the file that is needed.
		 * @return filename is needed, so user will have the right file processed second time (line 67)
		 * This means user doesn't need to specify the filename again. Filename is based on the first choice (line 41)
		 */
		public static String validate (Scanner inputFile, String filename) {
			if (inputFile == null) {
				System.out.println ("File does not exist");
				System.out.println ("Locate the file: ");
				filename = kb.nextLine();
				inputFile = openFile(filename);			
			}
			return filename;
		}
	 	/*
	 	 * end of codes
	 	 */
	 	
		/*
		 * This following method has been modified from:
		 * Week 9 Lecture Code (SearchSortDemo1.java)
		 * 
	   * Search an object array Driver_19515566
	   * @param array - Array to be searched
	   * @param lookingFor - Search Filter 
	   * @return the location of the integer in the array. 
	   * 	If it is not found, it will return -1
	   */
	  public static int searchArray(Driver_19515566[] array, String lookingFor) {
	    int i = 0;                
	    int foundAt = -1;        
	    boolean found = false;    
	    
	    while (!found && i < array.length) {
	      if (array[i].getLicenceNumber().equals(lookingFor) ) {
	        found = true;
	        foundAt = i;
	      }
	      i++;
	    }
	    return foundAt;
	  }
	  /*
	   * end of code
	   */
	  
	  /*
	   * This method is used to display infringement reports summary.
	   * It is called when option 2 is chosen.
	   * @param infringements - total applied infringements
	   * @param totalFine - total fined drivers
	   * @param suspended - total suspended drivers
	   */
	  public static void displayReport(int infringements, float totalFine, int suspended) {
	  	System.out.println("\n==================================================================================================");
			System.out.println("\nInfringements Summary Report");
			System.out.println("Total Number of Infringements: " + infringements);
			System.out.println("Total Revenue: " + totalFine);
			System.out.println("Total Suspended Licence: " + suspended);
			System.out.println("\n==================================================================================================");	
	  }
	  	 
		/*
	   * Display all information on array Driver (option 1 from menu)
	   * @param array - Array to be displayed
	   */
	  public static void displayDriver(Driver_19515566 [] array) {
	  	String bar = " | ";
	  	
	  	System.out.println("\nThe Drivers List : ");
  		System.out.print("\n\tLicence Number" + bar + "First Name" + bar + "Last Name" + bar +
  		  	"Suburb" + bar + "Demerit Points" + bar + "Licence Status");
  		System.out.println("\n==================================================================================================");
	  	try {
			  for (int i = 0; i < array.length; i++) {
			  	System.out.print("\nDriver " + (i+1) + ": " + "\t" );
			  	System.out.print(array[i].toString());
			  }
			}
		  catch (NullPointerException e) {
		  	System.out.println ("Can't read driverArray. Wrong Text files data");
		  }
	  }
	  
		/*
	   * Display suspended Driver from the array Driver (option 3 from menu)
	   * @param array - Array to be displayed
	   */
	  public static void displaySuspendedDriver(Driver_19515566 [] array) {
	  	String bar = " | ";
	  	
	  	System.out.println("\nThe Suspended Drivers List : ");
  		System.out.print("\n\tLicence Number" + bar + "First Name" + bar + "Last Name" + bar + "Address" + bar
  		  	+ "Suburb" + bar + "Demerit Points");
  		System.out.println("\n==================================================================================================");
	  	try {
			  for (int i = 0; i < array.length; i++) {
			  	if (array[i].getLicenceStatus().equals("Suspended")) {
			  	System.out.print("\n" + array[i].suspended());
			  	}
			  }
			}
		  catch (NullPointerException e) {
		  	System.out.println ("Can't read driverArray. Wrong Text files data");
		  }
	  }
	  
	  /*
	   * This is an additional menu option
	   * Display Fined Driver from the array Driver (option 5 from menu)
	   * @param array - Array to be displayed
	   */ 
	  public static void displayFinedDriver(Driver_19515566 [] array) {
	  	String bar = " | ";
	  	
	  	System.out.println("\nThe Fined Drivers List : ");
  		System.out.print("\nLicence Number" + bar + "First Name" + bar + "Last Name" + bar + "Demerit Points" + bar + "Fines");
  		System.out.println("\n==================================================================================================");
	  	try {
			  for (int i = 0; i < array.length; i++) {
			  	if (array[i].getFine() > 0) {
				  	System.out.print("\n" + array[i].fined());
			  	}
			  }
			}
		  catch (NullPointerException e) {
		  	System.out.println ("Can't read driverArray. Wrong Text files data");
		  }
	  }
	  
	  /*
	   * This is a method to save driver records into text file
	   * @param array - array to be saved
	   * Text file saved will have "," within each field (same as original file) 
	   */
	  
	  public static void saveRecords(Driver_19515566 [] array) {
	   	try {
      	PrintWriter outFile = new PrintWriter("Driver.txt");
      	for (int i = 0; i < array.length; i++) {  
          String lNumber = array[i].getLicenceNumber();
          String lClass = array[i].getLicenceClass();
          String fName = array[i].getFirstName();
          String lName = array[i].getLastName();
          String address = array[i].getAddress();
          String suburb = array[i].getSuburb();
          String postcode = array[i].getPostcode();
          String demerit = Integer.toString(array[i].getDemerit());
          String lStatus = array[i].getLicenceStatus();
          
          outFile.println(lNumber + "," + lClass +  "," + fName + "," + lName +  "," + address + "," + suburb +  "," 
          		+ postcode + "," + demerit + "," + lStatus);
        }
        outFile.close();
      } 
	   	catch (IOException e) {
      		System.out.println ("Error with File");
      	}
	  }
		
		/*
		 * Method to display menu to the program.
		 * Menus are defined in the string arrays.
		 * In the project, 5 option menus are needed.
		 */
	  public static void displayMenu(String[] menu) {
	    System.out.println("\n\nEnter your menu option");
	    for (int i = 0; i < menu.length; i++) {
	      System.out.println((i + 1) + ". " + menu[i]);
	    }
	  }
	  
	  /*
	   * Method to ask user's choice for the menu
	   * If user chose invalid option, notify user and ask user to choose another option
	   */
	  public static int getMenuChoice(int min, int max) {
	    int input;
	    do {
	      input = kb.nextInt();
	      //kb.nextLine(); //help to minimize code?
	      if (input < min || input > max) {
	      	System.out.println("\nYou chose an invalid option.");
	        System.out.println("Please choose a menu option from " + min + " to " + max);
	      }
	    } while (input < min || input > max);
	    return input;
	  } 
	}

