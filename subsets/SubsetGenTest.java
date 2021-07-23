import java.util.ArrayList;

/**
   This program demonstrates the permutation generator.
*/
public class SubsetGenTest
{
   public static void main(String[] args)   {

      // SubsetGen generator = new SubsetGen();
      testGray();

    //  ArrayList<String> answer = new ArrayList<String>();
    //  answer.add("");
    //  answer.add("a");
    //  answer.add("b");
    //  answer.add("ab");
    //  answer.add("c");
    //  answer.add("ac");
    //  answer.add("bc");
    //  answer.add("abc");
    //
    //  SubsetGen generator = new SubsetGen();
    //  ArrayList<String> subsets = generator.getSubsets("abc");
    //   boolean match = true;
    //   int i = 0;
		// while ((match == true)&&(i<8))   {
    //      if (!answer.get(i).equals(subsets.get(i)))
    //         match = false;
    //      i++;
    //   }
    //   if (match == true)
    //      System.out.println("Congratulations all the strings match." );
    //   else
    //      System.out.println("your string at position  " + i  + " should be -" + answer.get(i-1)+ "- you returned -" + subsets.get(i-1)+"-");
   }

   public static void testGray(){
     ArrayList<String> answer = new ArrayList<String>();
     answer.add("000");
     answer.add("001");
     answer.add("011");
     answer.add("010");
     answer.add("110");
     answer.add("111");
     answer.add("101");
     answer.add("100");

     SubsetGen gen = new SubsetGen();
     ArrayList<String> subsets = gen.getGrayCode(3);
     boolean match = true;
     int i = 0;
     while ((match == true)&&(i<8)){
          if (!answer.get(i).equals(subsets.get(i)))
             match = false;
          i++;
        }
       if (match == true)
          System.out.println("Congratulations all the strings match in Gray Code." );
       else
          System.out.println("your string at position  " + i  + " should be -" + answer.get(i-1)+ "- you returned -" + subsets.get(i-1)+"-");

   }
}
