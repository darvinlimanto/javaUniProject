/*
 * Name: Darvin Limanto
 * ID : 19515566
 * Campus: Parramatta South
 * Class: Friday 9 a.m -  11a.m  
 * Tutor Name: Janagan Sivagnanasundaram
 */

import java.util.Comparator;

/*
 * The following code has been modified from: https://www.geeksforgeeks.org/arrays-sort-in-java-with-examples/
 * This class function is to sort the array records by descending order based on demerit points.
 * It will be called with Arrays.sort method
 */
public class Sort_19515566 implements Comparator<Driver_19515566> 
{ 
    public int compare(Driver_19515566 a, Driver_19515566 b) 
    { 
        return b.getDemerit() - a.getDemerit(); 
    } 
}

/*
 * End of Code
 */

