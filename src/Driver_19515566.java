/*
 * Name: Darvin Limanto
 * ID : 19515566
 * Campus: Parramatta South
 * Class: Friday 9 a.m -  11a.m  
 * Tutor Name: Janagan Sivagnanasundaram
 */

import java.util.Scanner;

public class Driver_19515566 {
	private String licenceNumber;			// Hold Driver's Licence Number
	private String licenceClass;			// Hold Driver's Licence Class
	private String firstName;				// Hold Driver's First Name
	private String lastName;				// Hold Driver's Last name
	private String driverAddress;			// Hold Driver's Address
	private String driverSuburb;			// Hold Driver's Suburb
	private int demeritPoints; 				// Hold Driver's demerit points
	private String licenceStatus;			// Hold Driver's Licence Status
	private float driverFines;				// Hold Driver's Fines
	private String postcode;				// Hold Driver's postcode
	
	/**
	  * Constructor 
	  * @param licNumber - The new driver Licence Number.
	  * @param licClass - The new driver Licence Class
	  * @param fName - The new driver First Name
	  * @param lName - The new driver Last name
	  * @param address - The new driver Address
	  * @param suburb - The new driver Suburb
	  * @param demerit - The new driver demerit points
	  * @param licStatus - TThe new driver Licence Status
	  * @param pcode - The new driver postcode
	  */
	
	public Driver_19515566(String licNumber, String licClass, String fName, String lName, String address, String suburb, int demerit, String licStatus, String pcode) {
		licenceNumber = licNumber;
		licenceClass = licClass;
		firstName = fName;
		lastName = lName;
		driverAddress = address;
		driverSuburb = suburb;
		demeritPoints = demerit;
		licenceStatus = licStatus;
		postcode = pcode;
	}
	

	/*
	 * Mutators method
	 */
	public void setSuburb (String suburb) {
		driverSuburb = suburb;
	}
	
	public void setDemerit (int demerit) {
		demeritPoints = demerit;
	}
	
	public void setLicenceStatus (String licStatus) {
		licenceStatus = licStatus;
	}
	
	public void setFine (float fine) {
		driverFines = fine;
	}
	
	/*
	 * Accessor Method
	 */
	public String getLicenceNumber() {
		return licenceNumber;
	}
	
	public String getLicenceClass() {
		return licenceClass;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getAddress() {
		return driverAddress;
	}
	
	public String getSuburb() {
		return driverSuburb;
	}
	
	public int getDemerit() {
		return demeritPoints;
	}
	
	public String getLicenceStatus() {
		return licenceStatus;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public float getFine () {
		return driverFines;
	}
	
	// Create string method for information needed in displaying Drivers information.
  public String toString() {
  	String bar = " | ";
    String str = licenceNumber + " " + bar + firstName + " " + bar + lastName
    		+ " " + bar  + driverSuburb + " " + bar + demeritPoints
    		+ " " + bar + licenceStatus;  
    return str;
  }
  
  // Create string method for information needed in displaying suspended Drivers information.
  public String suspended() {
  	String bar = " | ";
    String str = licenceNumber  + bar + firstName + bar + lastName
    		+ bar + driverAddress + bar + driverSuburb + bar + demeritPoints; 
    return str;
  }
  
  // Create string method for information needed in fined Drivers information.
  public String fined() {
  	String bar = " | ";
    String str = licenceNumber  + bar + firstName + bar + lastName
    		+ bar + demeritPoints + bar + driverFines; 
    return str;
  }
  
  /*
   *  This method is created from Speeding Penalty class
   *  It will return an index 
   *  It shows which appropriate punishment to be applied from SpeedingPenalty class
   */
  public byte applyPenalty(int overSpeed) {
    return SpeedingPenalty.testing(overSpeed);
  }
  
  public int addDemerit (int newDemerit) {
  	demeritPoints += newDemerit;
  	return demeritPoints;
  }

 
}



