package PJ3;

import java.util.*;
import java.io.*;

// You may add new functions or data fields in this class 
// You may modify any functions or data members here
// You must use Customer, Cashier and CheckoutArea classes
// to implement SuperMart simulator

class SuperMart {
	
  Random rand = new Random();

  // input parameters
  private int numCashiers, customerQLimit;
  private int chancesOfArrival, maxServiceTime;
  private int simulationTime, dataSource;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;

  // internal data
  private int counter;	             // customer ID counter
  private CheckoutArea checkoutarea; // checkout area object
  private Scanner dataFile;	     // get customer data from file
  private Random dataRandom;	     // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int serviceTime;

  // initialize data fields
  private SuperMart()
  {
	// add statements
  	numCashiers = 0;
	customerQLimit = 0;
	simulationTime = 0;
	dataSource = 0;
	chancesOfArrival = 0;
	maxServiceTime = 0;
	numGoaway = 0;
	numServed = 0;
	totalWaitingTime = 0;
	counter = 1;
	checkoutarea = null;
	dataFile = null;
	anyNewArrival = false;
	serviceTime = 0;
  }

  private void setupParameters()
  {
	// read input parameters from user
	// setup dataFile or dataRandom
	// add statements
	  Scanner input = new Scanner(System.in);
	  System.out.println("Enter simulation time:");
	  
      do{
    	  
      simulationTime = input.nextInt();
      } while(simulationTime < 1 || simulationTime > 10000);System.out.println("Enter maximum duration of transaction:");
      
      do{
    	  
      maxServiceTime = input.nextInt();
      } while(maxServiceTime < 1 || maxServiceTime > 500);System.out.println("Enter chances (0% < & <= 100%:) of a new customer:");
      
      do{
    	  
      chancesOfArrival = input.nextInt();
      } while (chancesOfArrival < 1 || chancesOfArrival > 100);
 
      System.out.println("Enter the number of cashiers:");
      
      do{
    	  
    	  numCashiers = input.nextInt();
      } while (numCashiers < 1 || numCashiers > 10);
 
      System.out.println(" Enter customer waiting queue limit: ");
      
      do{
    	  
    	  customerQLimit = input.nextInt();
      } while(customerQLimit < 1 || customerQLimit > 50);
 
      System.out.println(" Enter 1/0 to get data from file/random:");
      
      do{
    	  
      dataSource = input.nextInt();
      } while(dataSource < 0 || dataSource > 1);
 
      if (dataSource == 1){
 
              System.out.println("Enter filename:");
              //this asks the user to enter a filename               
              String file;
              Scanner scanner = new Scanner(System.in);
              file = scanner.nextLine();
              //checks if the filename by the user exists
                    try{
                            dataFile = new Scanner(new File(file));
                    }
                    catch (FileNotFoundException e){
                            System.out.println("Error opening the file:" + file);
                    }      
                    scanner.close();
      }
      input.close();
  }

  // Use by step 1 in doSimulation()
  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and serviceTime
	// add statements
	if (dataSource == 1){
		  
          int data1 = dataFile.nextInt();
          int data2 = dataFile.nextInt();
 
          anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
          serviceTime= (data2%maxServiceTime)+1;
          
    } else {
          anyNewArrival = ((rand.nextInt(100)+1) <= chancesOfArrival);
          serviceTime = rand.nextInt(maxServiceTime)+1;  
    }
	  
  }

  private void doSimulation()
  {
	// add statements

	// Initialize CheckoutArea
	checkoutarea = new CheckoutArea(numCashiers, customerQLimit);
	  
	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {
  			System.out.println("Time "+currentTime);

    		// Step 1: any new customer enters the checkout area?
    		getCustomerData();

    		if (anyNewArrival) {
      		    // Step 1.1: setup customer data
      		    // Step 1.2: check customer waiting queue too long?
    			counter++;
				Customer newCustomer = new Customer(counter, serviceTime, currentTime);
				System.out.println("\tCustomer id #"+counter+" arrives with checkout time "+newCustomer.getServiceTime() + " units");
				
				if(checkoutarea.isCustomerQTooLong()){
					System.out.println("\tCustomer Q is too long: Customer id "+ counter +" leaves the queue");
					numGoaway++;
				}
				else{
					checkoutarea.insertCustomerQ(newCustomer);
					System.out.println("\tCustomer id "+ counter +" waits in the customer queue");
				}
 
    		} else {
      		    System.out.println("\tNo new customer!");
    		}

    		// Step 2: free busy cashiers, add to free cashierQ
    		for(int i = 0; i < checkoutarea.sizeBusyCashierQ(); i++){
				//Peek and check if it is still busy, if not remove and add it to free, otherwise do nothing
				Cashier newCashier = checkoutarea.peekBusyCashierQ();
				if(newCashier.getEndBusyClockTime() <= currentTime) {
					Customer newCustomer;
					newCashier = checkoutarea.removeBusyCashierQ();
					newCustomer = newCashier.busyToFree();	                              
					System.out.println("\tCustomer #:" + newCustomer.getCustomerID() + " is done.");
					checkoutarea.insertFreeCashierQ(newCashier);
					System.out.println("\tCashier #:" + newCashier.getCashierID() + " is free.");
				}
 
			}
 
    		
    		// Step 3: get free cashiers to serve waiting customers 
  	} // end simulation loop

  	// clean-up
  }

  private void printStatistics()
  {
	// add statements into this method!
	// print out simulation results
	// see the given example in README file
    // you need to display all free and busy gas pumps
	System.out.println("   End of simulation report");
    System.out.println("         # total arrival customers:" + --counter);
	System.out.println("         # customers gone-away    :" + numGoaway);
    System.out.println("         # customers served       :" + numServed);
	System.out.println("         *** Current Cashier Info.");
	System.out.println("         # waiting customers      :" + checkoutarea.numWaitingCustomers());
	System.out.println("         # busy cashiers          :" + checkoutarea.numBusyCashiers());
	System.out.println("         # free cashiers          :" + checkoutarea.numFreeCashiers());
	System.out.println("         Total waiting line       :" + totalWaitingTime);}
  }


  // *** main method to run simulation ****
  public static void main(String[] args) {
   	SuperMart runSuperMart=new SuperMart();
   	runSuperMart.setupParameters();
   	runSuperMart.doSimulation();
   	runSuperMart.printStatistics();
  }

}
