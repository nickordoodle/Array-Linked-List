package all;


//QUESTIONS:

/*Are helper/getter methods included in Big Oh notation?
 * What should I set the unused avail array indexes equal to?
 * Does the first to last "sequence" mean to start with the front and go to the last Item with a next value of -1
 * For addFront method, when we add the String name, does that add the object along with it or whatever is added into all array turns into an object?
 * What does " time must not depend on the length of the all array" exactly mean?
 * 
 * 
 * 
 * How can I find the "next available slot" without using a loop since it will affect runtime?
 * Should I initialize all values of the constructor to make all slots open?
 * Does the addFront method also do the same thing as "updateFront" would?
 * In what ways can I update/change numAvail appropriately?
 * 
 */

//REMINDERS:
/*
 * Don't forget to set/update the next values when working with the arrays
 * HOW TO IMPLEMENT NUMAVAIL: Whenever a method is called, if it involves deleting, then numAvail should be that index that was
 * deleted.
 * Can we change the class Item to add another field?
 * Should I initialize all values of the available array in the constructor? Because aren't they all initially available?
 * 
 * Null pointer exception line 109?
 */

class Item {
	String name;
	int next;
	public Item(String name, int next) {
		this.name = name;
		this.next = next;
	}
	public String toString() {
		return "(" + name + "," + next + ")";
	}
}

public class ArrayLL {

	private Item[] all;
	private int numItems;
	private int front;
	private int[] avail;
	private int numAvail;

	// Constructor, initializes all data fields, to represent 
	// an empty Item array linked list of length maxItems
	public ArrayLL(int maxItems) {
		
		all = new Item[maxItems];//sets max length these arrays can ever hold
		avail = new int[maxItems];
		
		for (int i = 0; i < all.length; i++){
			
			all[i] = null;
			
		}
		
		for(int i = 0; i < avail.length; i++){
			
			avail[i] = i;//sets the array to make all spots initially available
			
		}
		
		numItems = 0;//We do not start with any values, so we start with 0
		front = -1;//By default, the front is not entered yet, so we give it a starting value of -1
		numAvail = avail.length - 1;//Will use this field like a divider
		

	}

	

	// Adds a name to the front of this array linked list, in worst case O(1) time,
	// and returns true.
	// Returns false if the array is full, in O(1) time
	public boolean addFront(String name) {
		
		if(front != -1){//if there are items in the list already, do this
			
			int oldFront = front;
			
			if(numAvail >= 0){
				
				int openIndex = avail[numAvail];
				Item itemName = new Item(name, oldFront);//creates Item out of the parameter and establishes its pointer
				all[openIndex] = itemName;//adds string to open spot
				front = openIndex;//updates front
				avail[numAvail] = -1;//makes sure the index is not open anymore
				
				if(numAvail - 1 >= -1){//adjusts numAvail value
					numAvail--;
					
				}
				
				numItems++;//we have one more item
				
				return true;
			}
			
		}
		else{//case if nothing is in the list?" yet
			
			if(numAvail >= 0){
				int openIndex = avail[numAvail];
				Item itemName = new Item(name, -1);
				all[openIndex] = itemName;//adds string to open spot
				avail[numAvail] = -1;//makes sure the index is not open anymore
				front = openIndex;
				
				if(numAvail - 1 >= -1){//adjusts numAvail value
					numAvail--;
					
				}
				numItems++;//we have one more item
				
				return true;
			}
			
		}
		
	
		return false;

	}

	// Deletes the name that is at the front this array linked list, in worst case O(1) time,
	// and returns the deleted name
	// Returns null if the list is empty, in O(1) time
	public String deleteFront() {

		// COMPLETE THIS METHOD1
		
		if(numItems < 1){//Since -1 is being used when there are no more available spots
			return null;
			
		}
		
		Item deletedItem = all[front];
		int deletedIndex = front;
		
		if(numAvail + 1 <= avail.length - 1){//checks if we can fit one more slot in avail array
			avail[numAvail + 1] = deletedIndex;
			numAvail++;
			
		}
		
		avail[numAvail] = deletedIndex;//need to get new available index
		if(all[front].next != -1){
			front = all[front].next;//sets new front equal to the front's pointer from before
								//so if front = 5, and the pointer goes to index 2, front will then be equal to 2
		}
		else{
			front = -1;
		}
		all[deletedIndex].next = -1;//must make pointer -1 since it is now "deleted"
		numItems--;
		
		return deletedItem.name;
		
	}

	// Deletes the given name from this array linked list, and returns true.
	// Returns false if the name is not in the list.
	// Note: If there are n active items in the list, then this method must run in
	// worst case O(n) time, i.e. time must not depend on the length of the all array
	// (since the array might include available space not filled by active items)
	// Also, avail array should be accessed/updated in O(1) time
	public boolean delete(String name) {
		
		int tempFront = front;
		int prev = -1;
	
		if(front != -1){
			while(tempFront != -1 || prev == -1){
			
				if(numItems == 1 && all[tempFront].name.equals(name)){
					
					//adjusts numAvail "divider" to the right per se
					if(numAvail + 1 <= avail.length - 1){
						all[front].next = all[tempFront].next;//changes pointer
						
						front = -1;
						avail[numAvail + 1] = tempFront;
						all[tempFront].next = -1;
						numAvail++;
						numItems--;
						
						return true;
					}
					
					
				}
				
				else if (all[tempFront].name.equals(name)){
					
					if(numAvail + 1 <= avail.length - 1){//adjusts numAvail "divider" to the right per se
						
						all[front].next = all[tempFront].next;//changes pointer
						if(all[front] == all[tempFront]){//must change front in this case
							
							front = all[front].next;
							
						}
						avail[numAvail + 1] = tempFront;
						all[tempFront].next = -1;
						numAvail++;
						numItems--;
						return true;
					}
					
					
					
				}
				else{
					prev = tempFront;
					tempFront = all[tempFront].next;
					
				}
				
			}
		}
	
		return false;	
	}

	// Checks if the given name is in this array linked list
	// Note: If there are n items in the list, then this method must run in
	// worst case O(n) time, i.e. time does not depend on the length of the all array.
	public boolean contains(String name) {

		// COMPLETE THIS METHOD
		
		int tempFront = front;
		
		if(numItems == 1 && all[tempFront].name.equals(name)){
			return true;
			
		}

		while(all[tempFront].next != -1){
			
			if (all[tempFront].name.equals(name)){
				
				return true;
				
			}
			else{
				tempFront = all[tempFront].next;
				
			}
			
		}
		
		return false;
		
		}

	// Prints the items in this array linked list in sequence from first to last,
	// in worst case O(n) time where n is the number of items in the linked list
	// The list should be printed in a single line, separated by commas
	// Example: earth,mercury,venus
	// Make sure there aren't any extra commas in your output.
	// If the list is empty, you may print either nothing, or an empty string
	public void printList() {

		// COMPLETE THIS METHOD
		int pointer = front;//sets pointer equal to the start or "front" of array
		if(numItems > 0){//makes sure there are items in list
			while(pointer != -1){
				
				if(all[pointer].next > -1){
					System.out.print(all[pointer].name + ",");
					pointer = all[pointer].next;//updates pointer to get the next value of each consecutative item until we reach end of
												//list where the pointer will equal -1
				}
				else{
					System.out.print(all[pointer].name);//accounts for extra comma at the end
					pointer = all[pointer].next;//need this to update pointer one last time so the loop will stop
				}
				
			}
		}
		else{
			System.out.println("");
			
		}
		
		
	}

	// Prints all the entries in the main array (including unused spaces)
	// You may fill in this method and use it for debugging
	// This method WILL NOT be graded
	public void printArray() {
		
		for(int i = 0; i < all.length; i++){
			
			System.out.print(all[i]);//prints all array
		}
		System.out.println("\n");
	
		for(int i = 0; i < avail.length; i++){
			
			System.out.print(avail[i]);//prints avail spots
			
		}
		System.out.println("\n");
	
		System.out.println("front: " + front);
		System.out.println("NumAvail: "  + numAvail);
		System.out.println("NumItems: " + numItems);
	}

	// Prints all the entries in the avail array that correspond to
	// available spaces in the main array
	// You may fill in this method and use it for debugging
	// This method WILL NOT be graded
	public void printAvailableSpots() {
		
		for(int i = 0; i < avail.length; i++){
			
			System.out.print(avail[i]);
			
		}
		
	}
}
