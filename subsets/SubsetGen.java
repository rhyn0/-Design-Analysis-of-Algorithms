import java.util.ArrayList;
import java.lang.String;

//	This class generates subsets of a set represented as a string in Java

public class SubsetGen   {

	public SubsetGen() {}

   public ArrayList<String> getSubsets(String word)   {
      ArrayList<String> subsets = new ArrayList<String>();

      //   Put your algorithm here
      ArrayList<String> ret = new ArrayList<String>();
			ArrayList<String> temp = new ArrayList<String>();
			String shortString = new String();

			if (word.length() > 0){
				shortString = word.substring(0, word.length() - 1); //stores substring without last letter
				temp = getSubsets(shortString);
				for (int i = 0; i < temp.size(); ++i) {
					ret.add(temp.get(i));
				}
				for (int i = 0; i < temp.size(); ++i) {
					ret.add(temp.get(i) + word.substring(word.length() - 1));
				}
				return ret;  //return subsets after generating
			}
			else {
				ret.add("");
				return ret;  //return subsets after empty case
			}
   }

	 public ArrayList<String> getGrayCode(int n)   {
   		//   Put your algorithm here
 			ArrayList<String> ret = new ArrayList<String>(); //array to be returned
			ArrayList<String> temp = new ArrayList<String>(); //temporary storage for each recursive call
			if (n > 1){ 																		//base case is n == 1, handled by else statement
				temp = getGrayCode(n - 1);
				for (int i = 0; i < temp.size(); ++i){ 			//prepend 0, traversing list increasing
					ret.add("0" + temp.get(i));
				}
				for (int i = temp.size() - 1; i >= 0; --i){ //prepend 1, traversing list decreasing
					ret.add("1" + temp.get(i));
				}
				return ret;
			}
			else{
				ret.add("0");
				ret.add("1");
				return ret;
			}
    	//return the ArrayList of Gray codes;
	 }
}
