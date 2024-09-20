
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The FormatChecker class evaluates the format of one or more input files, and declares the file as valid or invalid based on the formatting specifications
 * in the file.
 *
 * @author Diego Dominguez
 * @version 1.0 CS221 Fall 2024
 */
class FormatChecker{
      /**
   * Main method carries out functionality to read the arguements given by the user and call the openFile method for every arguement,
   * if no file is given it will print out usage message
   *
   * @param args file names
   */

    public static void main(String[] args){
        // Check to see that a file was supplied, if not provide usage message
        if (args.length < 1) {
            System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
        }
        // loop through args and call open file method for each one
        for (int i = 0; i < args.length; i++) {
            String fileName = args[i];
            System.out.println("File: " + fileName);
            openFile(fileName);
            System.out.println();
        }
    }

    /**
   * openFile takes in a String filename, tries to open the file and loops through each row while keeping count on which row its on for future valid or invalid
   * references. calls ReadFormattingDimensions method for the first row read, and rowCounter for all other rows. Lastly it will print valid if the formatting
   * row count is the same as the number of times it looped through it and if the variable isRowValid is true. Otherwise it will print invalid and the reaasoning for it
   *
   * @param filename string, name of file to open
   * @throws FileNotFoundException if given file is now found
   * @throws NumberFormatExcpetion if value can't be converted to double
   * @throws Exception, when there are more rows than the specification says
   */
    public static void openFile(String filename){
        try (Scanner scnr = new Scanner(new File(filename));){
            // Counter for rows
            int rowCount = 0;
            double[] formatingNums = new double[2];
            boolean isRowValid = true;
            while (scnr.hasNext()) {
                String row =  scnr.nextLine();
                // Scanner rowScanner = new Scanner(row);
                if (rowCount == 0){
                    formatingNums = ReadFormattingDimensions(row, formatingNums);
                } else {
                    if (isRowValid) {
                        isRowValid = rowCounter(row, formatingNums[1]);
                    }
                }
                // ADD TO ROW COUNTER SINCE ITS AT THE END 
                rowCount++;
            }
            if ((rowCount - 1) == formatingNums[0] && isRowValid) {
                System.out.println("VALID");
            } else{
                // System.out.println("INVALID");
                if (rowCount != formatingNums[0]) {
                    throw new Exception("Row count is " + (rowCount - 1) + " when it should be " + (int)formatingNums[0]);
                }
                }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            System.out.println("INVALID");
        } catch (NumberFormatException e){
            System.out.println(e.toString());
            System.out.println("INVALID");
        } catch (Exception e){
            System.out.println(e.toString());
            System.out.println("INVALID");
        }
    }

    /**
   * ReadFormattingDimensions takes in a String row (will be for first row), to determine if the row contains the correct amount of 
   * integers for the formatting specificiations and makes sure it can successfully be converted to a double and then adds the formatting 
   * specification to the array formatDimensions
   *
   * @param row string, single row of a given file
   * @param formatDimensions empty array, will carry the dimensions for the matrix
   * @return array, returns formatDimensions with values inputted
   * @throws Exception, when the formatting specifications length is larger than 2
   */
    public static double[] ReadFormattingDimensions(String row, double[] formatDimensions) throws Exception{
        // Converting String to array
        String[] str = row.split(" ");
        // IF FIRST ROW HAS MORE THAN TWO DIGITS
        if (str.length > 2) {
            throw new Exception("Formatting Specificiations is to long");
            // System.out.println("INVALID");
        }
        // LOOPS through string that was converted to array and converts each element to integers and adds to formatDimensions array
        for (int i = 0; i < str.length; i++) {
            // Convert to Integer and remove any white space from the string if there is some
            // double doubleNum = str[i].replace(oldChar, newChar)
            int num = Integer.parseInt(str[i].replaceAll("\\s", ""));
            formatDimensions[i] = num;
        }
        return formatDimensions;
    }

     /**
   * rowCounter converts the string row to an array so it can loop through each element and try to convert it
   * to a double and makes sure the array length is the same as the formatting column specifications
   *
   * @param row string, single row of a given file
   * @param colCount number of columns a file should have
   * @return boolean, true if row was valid based on logic else false
   * @throws Exception, when the loop finds a row that has more columns than it should have
   */
    public static boolean rowCounter(String row, double colCount) throws Exception{
        boolean isRowValid = false;
        String[] str = row.split(" ");
        // Make sure I can convert the element of each arry to a double
        for (int i = 0; i < str.length; i++) {
            Double.parseDouble(str[i].replaceAll("\\s", ""));
        }
        if ((double)str.length == colCount) {
            isRowValid = true;
        } else {
            throw new Exception("The following row:  " + row + ", has " + str.length + " columns, when it should have " + (int)colCount);
        }
        return isRowValid;
    }
}
