// DO NOT ADD NEW METHODS OR NEW DATA FIELDS!

package PJ3;

class Cashier {

   // cashier id and current customer which is served by this cashier 
   private int cashierID;
   private Customer serveCustomer;

   // start time and end time of current (free or busy) interval
   private int startClockTime;
   private int endClockTime;

   // for keeping statistical data
   private int totalFreeTime;
   private int totalBusyTime;
   private int totalCustomers;

   // Constructor
   Cashier()
   {
	// add statements
	this.cashierID = 0;
	this.startClockTime = 0;
	this.endClockTime = 0;
	this.totalFreeTime = 0;
	this.totalBusyTime = 0;
	this.totalCustomers = 0;
   }


   // Constructor with cashier id
   Cashier(int cashierId)
   {
	// add statements
	this.cashierID = cashierId;
	this.startClockTime = 0;
	this.endClockTime = 0;
	this.totalFreeTime = 0;
	this.totalBusyTime = 0;
	this.totalCustomers = 0;
  	
   }

   // accessor methods

   int getCashierID() 
   {
	return cashierID;
   }

   Customer getCurrentCustomer() 
   {
	// add statements
	return serveCustomer;
   }

   // Transition from free interval to busy interval
   void freeToBusy (Customer serveCustomer, int currentTime)
   {
  	// goal  : switch from free interval to busy interval
  	//         i.e. end free interval, start busy interval 
        //              to serve a new customer
        //
  	// steps : update totalFreeTime
  	// 	   set startClockTime, endClockTime, serveCustomer, 
  	// 	   update totalCustomers

	// add statements
    totalFreeTime += currentTime - startClockTime;
	//BUSY = 1;
	this.serveCustomer = serveCustomer;
	startClockTime = currentTime;
	endClockTime = startClockTime + serveCustomer.getServiceTime();
	totalCustomers++;
   }

   // Transition from busy interval to free interval
   Customer busyToFree ()
   {
  	// goal  : switch from busy interval to free interval
  	//         i.e. end busy interval to return served customer, 
        //              start free interval 
   	// 
  	// steps     : update totalBusyTime 
  	// 	       set startClockTime 
  	//             return serveCustomer

	// add statements
    totalBusyTime += endClockTime - startClockTime;
	//FREE = 0;
	startClockTime = endClockTime;
	//   this.currentCustomer = currentCustomer;
	//   return currentCustomer;
	Customer tempCustomer = serveCustomer;
	this.serveCustomer = null;
	
	return tempCustomer;
   }

   // Return end busy clock time, use in priority queue
   int getEndBusyClockTime() 
   {
	// add statements
	return endClockTime;
   }

   // For free interval at the end of simulation, 
   // update totalFreeTime 
   void setEndFreeClockTime (int endsimulationtime)
   {
  	// for free interval at the end of simulation:
  	// set endClockTime, update totalFreeTime

	// add statements
	   totalFreeTime += endClockTime - startClockTime;
   }

   // For busy interval at the end of simulation, 
   // update totalBusyTime 
   void setEndBusyClockTime (int endsimulationtime)
   {
  	// for busy interval at the end of simulation:
  	// set endClockTime, update totalBusyTime

	// add statements
	totalBusyTime += endClockTime - startClockTime;
   }

   // functions for printing statistics :
   void printStatistics () 
   {
  	// print cashier statistics, see project statement

  	System.out.println("\t\tCashier ID             : "+cashierID);
  	System.out.println("\t\tTotal free time        : "+totalFreeTime);
  	System.out.println("\t\tTotal busy time        : "+totalBusyTime);
  	System.out.println("\t\tTotal # of customers   : "+totalCustomers);
  	if (totalCustomers > 0)
  	    System.out.format("\t\tAverage checkout time  : %.2f%n\n",(totalBusyTime*1.0)/totalCustomers);
   }

   public String toString()
   {
	return "CashierID="+cashierID+":startClockTime="+startClockTime+
               ":endClockTime="+endClockTime+">>serveCustomer:"+serveCustomer;
   }

   public static void main(String[] args) {
        // quick check
        Customer mycustomer = new Customer(1,15,5);
	Cashier mycashier = new Cashier(5);
        mycashier.freeToBusy (mycustomer, 12);
        System.out.println(mycashier);

   }

};

