package corp.skaj.foretagskvitton.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class ReceiptScanner {
    private List<String> listOfStrings;

    public String getDate(List<String> listOfStrings) {
        for (int i = 0; i < listOfStrings.size(); i++) {
            String currentString = listOfStrings.get(i);

            if (correctFirstNum(currentString.substring(0, 4)) && correctLength(currentString)) {
                return currentString;
            }
        }
        return "2017-04-28";
        //return Calendar.getInstance().getTime().toString();
    }

    /**
     *
     * @param date
     * @return
     */
    // Checks that the string starts with the current year in ex. 17 or 2017.
    // TODO - Check how simple Dateformat class works? Necessery with other solutions for date?
    private boolean correctFirstNum(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        String year = "2017";
       //String year = dateFormat.format(cal).toString();
        return date.substring(0, 2).equals(year.substring(0, 4)) || date.equals(year);
    }

    /**
     *
     * @param date
     * @return
     */
    // Checks that the size is in correct swedish format, either 170218 or 2017-05-03.
    private boolean correctLength(String date) {
        return date.length() <= 10 && date.length() >= 6;
    }

    /**
     *
     * @param listOfStrings
     * @return
     */
    private List<Double> findAllDoubles(List<String> listOfStrings) {
        List<Double> listOfDoubles = new ArrayList<>();
        for (int i = 0; i < listOfStrings.size(); i++) {
            String s = listOfStrings.get(i).replace("," , ".");
            if (s.contains(".")) {
                if(isDouble(s)) {
                    listOfDoubles.add(Double.parseDouble(s));
                }
            } else {
                double totalCost = 0;
                for (int j = 0; j < listOfStrings.size(); j++) {
                    if (checkForText()) {
                        totalCost = checkBeforeAndAfter(i);
                        listOfDoubles.add(totalCost);
                    }
                }
            }
        }
        return listOfDoubles;
    }

    /**
     *
     * @param s
     * @return <code>true</code> if s is a double
     * <code>false</code> otherwise
     */
    public boolean isDouble (String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     *
     * @param s
     * @return <code>true</code> if s is a interger
     * <code>false</code> otherwise
     */
    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     *
     * @return <code>true</code> if kr or sek is found
     * <code>false</code> otherwise
     */

    //här borde vi snarare returnera rätt index för att kunna använda i metoden nedan???
    private boolean checkForText () {
        for (int i = 0; i < listOfStrings.size(); i++) {
            if (listOfStrings.get(i).toLowerCase().equals("kr")
                        || listOfStrings.get(i).toLowerCase().equals("sek")
                            || listOfStrings.get(i).toLowerCase().equals("total")
                                || listOfStrings.get(i).toLowerCase().equals("totalt")) {
                return true;
            }
        }
        return false;
    }

    public double checkBeforeAndAfter (int index) {
        double totalCostBefore = 0.0;
        double totalCostAfter = 0.0;
        double totalCost;
        String temp;
        if (isInt(listOfStrings.get(index - 1)) || isDouble(listOfStrings.get(index - 1))) {
            temp = listOfStrings.get(index - 1);
            totalCostBefore = Double.parseDouble(temp);

        } if (isInt(listOfStrings.get(index + 1)) || isDouble(listOfStrings.get(index + 1))) {
            temp = listOfStrings.get(index + 1);
            totalCostAfter = Double.parseDouble(temp);
        }
        if (totalCostBefore > totalCostAfter) {
            totalCost = totalCostBefore;
        } else {
            totalCost = totalCostAfter;
        }

        return totalCost;
    }

    public String getTotalCost(List<String> listOfStrings) {
        this.listOfStrings = listOfStrings;
        List<Double> listOfDoubles = findAllDoubles(listOfStrings);

        // TODO fast fix so this wont crash if no double was found, need to be fixed
        try {
            return String.valueOf(Collections.max(listOfDoubles));
        } catch (Exception e) {
            return null;
        }

        }

    public void getProducts(List<String> listOfStrings) {

    }

    public void getCardNumber() {
    }

}