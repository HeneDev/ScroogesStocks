import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.nio.file.Paths;

public class Main
{
  public static void main(String args[]) throws ParseException
  {
    boolean doContinue = true;
    List<Data> listOfData = new ArrayList<>();
    ReadFromFile(listOfData);

    while (doContinue)
    {
      Scanner answerReader = new Scanner(System.in); //Create Scanner object
      Scanner inputReader = new Scanner(System.in);

      System.out.println("Would you like to: ");
      System.out.println("(1) See longest bullish trend within a given range?");
      System.out.println("(2) See which given date range had highest trading volume or the most significant stock price change within a day?");
      System.out.println("(3) See which given date range had the best opening price compared to 5 days simple moving average?");
      System.out.println("(4) Exit");

      System.out.print(">> ");
      String desiredAnswer = answerReader.nextLine(); //Read user input

      switch (desiredAnswer)
      {
        case "1" -> {

          System.out.println("Please input in format mm/dd/yyyy");
          while(true)
          {
            System.out.println("Please input starting date");
            System.out.print(">> ");
            String startDate = inputReader.nextLine();

            System.out.println("Please input ending date");
            System.out.print(">> ");
            String endDate = inputReader.nextLine();

            if (startDate.matches("\\d{2}/\\d{2}/\\d{4}") && endDate.matches("\\d{2}/\\d{2}/\\d{4}")) //Regex to make sure input is in mm/dd/yyyy format
            {
              BullishTrend(startDate, endDate, listOfData);
              System.out.println();
              break;
            }
            else
            {
              System.out.println("Please input correct date format (mm/dd/yyyy)");
            }
          }
        }
        case "2" -> {
          System.out.println("Please input in format mm/dd/yyyy");
          while(true)
          {
            System.out.println("Please input starting date");
            System.out.print(">> ");
            String startDate = inputReader.nextLine();

            System.out.println("Please input ending date");
            System.out.print(">> ");
            String endDate = inputReader.nextLine();

            if (startDate.matches("\\d{2}/\\d{2}/\\d{4}") && endDate.matches("\\d{2}/\\d{2}/\\d{4}")) //Regex to make sure input is in mm/dd/yyyy format
            {
              HighestTradingVolume(startDate, endDate, listOfData);
              System.out.println();
              break;
            }
            else
            {
              System.out.println("Please input correct date format (mm/dd/yyyy)");
            }
          }
        }
        case "3" -> {
          System.out.println("Please input in format mm/dd/yyyy");
          while(true)
          {
            System.out.println("Please input starting date");
            System.out.print(">> ");
            String startDate = inputReader.nextLine();

            System.out.println("Please input ending date");
            System.out.print(">> ");
            String endDate = inputReader.nextLine();

            if (startDate.matches("\\d{2}/\\d{2}/\\d{4}") && endDate.matches("\\d{2}/\\d{2}/\\d{4}")) //Regex to make sure input is in mm/dd/yyyy format
            {
              SimpleMovingAverage(startDate, endDate, listOfData);
              System.out.println();
              break;
            }
            else
            {
              System.out.println("Please input correct date format (mm/dd/yyyy)");
            }
          }
        }
        case "4" -> {
          System.out.println("Goodbye!");
          doContinue = false;
        }
      }
    }
  }

  //Function to read from given file once
  public static void ReadFromFile(List<Data> listOfData)
  {
    // 0 = Date
    // 1 = Close/Last
    // 2 = Volume
    // 3 = Open
    // 4 = High
    // 5 = Low
    try (Scanner fileReader = new Scanner(Paths.get("src\\HistoricalQuotes.csv")))
    {
      String nextRow = fileReader.nextLine(); //Skip the first line, as it only contains the data labels
      while (fileReader.hasNextLine())
      {
        nextRow = fileReader.nextLine(); //Starting point of reading data
        List<String> allValues = Arrays.asList(nextRow.split(",")); //Split values
        String dateValue = allValues.get(0);
        String closeLastValue = allValues.get(1);
        String volumeValue = allValues.get(2);
        String openValue = allValues.get(3);
        String highValue = allValues.get(4);
        String lowValue = allValues.get(5);
        Data tempObject = new Data(dateValue, closeLastValue, volumeValue, openValue, highValue, lowValue); //Make new temporary object of Data
        listOfData.add(tempObject); //Add Data-Object to list
      }
    } catch (Exception e)
    {
      System.out.println("Error occurred: " + e.getMessage());
    }
  }

  public static void BullishTrend(String startDate, String endDate, List<Data> listOfData) throws ParseException
  {
    int maxDaysIncreased = 0; // Number of times the stock value rose
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //Format date to month / day / year
    Date begin = formatter.parse(startDate);
    Date end = formatter.parse(endDate);

    Double closeLastCompareValue = 0.0; //Make an variable to compare closeLast value to

    //Loop list of Objectives from end to beginning
    for (int i = listOfData.size(); i-- > 0; )
    {
      if (isDateInBetweenIncludingEndPoints(begin, end, listOfData.get(i).getStockDate()))
      {
        if (listOfData.get(i).getCloseLastAsValue() > closeLastCompareValue)
        {
          maxDaysIncreased++;
        } else
        {
          maxDaysIncreased = 0; //If price doesnt increase, reset days
        }
        closeLastCompareValue = listOfData.get(i).getCloseLastAsValue();
      }
    }
    System.out.println("In Apple stock historical data the Close/Last price increased " + maxDaysIncreased + " days in a row between " + startDate + " and " + endDate);

    //See if object is between startDate and endDate

    //If yes and getCloseLastValue is higher than closeLastCompareValue then increase maxDaysIncreased
  }

  public static void HighestTradingVolume(String startDate, String endDate, List<Data> listOfData) throws ParseException
  {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //Format date to month / day / year
    Date begin = formatter.parse(startDate);
    Date end = formatter.parse(endDate);
    Double changedPrice = 0.0;
    List<VolumeList> valueList = new ArrayList<>();

    for (int i = listOfData.size(); i-- > 0; ) //Loop values from the end of the list to the beginning
    {
      if (isDateInBetweenIncludingEndPoints(begin, end, listOfData.get(i).getStockDate()))
      {
        //Calculate price change in a day: High - low
        changedPrice = listOfData.get(i).getHighAsValue() - listOfData.get(i).getLowAsValue();
        if (changedPrice < 0)
        {
          changedPrice *= -1.0;
        }

        VolumeList tempObject = new VolumeList(listOfData.get(i).getVolumeAsValue(), changedPrice, listOfData.get(i).getStockDate());
        valueList.add(tempObject); //Add temporary object to list of Objects. To be printed later.
        //If price is < 0, then multiply by -1 if > 0 keep the number

        //Save the date and volume

        //add to list

        //Order list by volume and price change
      }
    }
    valueList.sort(Comparator.comparing(VolumeList::getVolumeNumber) //First compares volumes, then changed prices
        .thenComparing(VolumeList::getChangedPrice));

    for (VolumeList object : valueList)
    {
      System.out.println(object);
    }
  }

  public static void SimpleMovingAverage(String startDate, String endDate, List<Data> listOfData) throws ParseException
  {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //Format date to month / day / year
    Date begin = formatter.parse(startDate);
    Date end = formatter.parse(endDate);

    List <SimpleMovingAverage> listOfSMA = new ArrayList<>();
    double totalClosingPrice = 0.0;
    int dividingValue = 0; //How many days we loop through
    for (int i = listOfData.size(); i-- > 0; ) //Loop values from the end of the list to the beginning
    {
      if (isDateInBetweenIncludingEndPoints(begin, end, listOfData.get(i).getStockDate()))
      {
        totalClosingPrice += listOfData.get(i).getCloseLastAsValue();
        dividingValue++;
      }

      //Calculate Simple Moving Average for given range

      //Compare it to the 5 days given

      //See which days had the best opening price

      //Calculate percentage difference of actual starting price and calculated price
    }

    Double simpleMovingAverage5 = totalClosingPrice / dividingValue;

    for (int i = listOfData.size(); i-- > 0;)
    {
      if (isDateInBetweenIncludingEndPoints(begin, end, listOfData.get(i).getStockDate()))
      {
        Double differencePercentage = (simpleMovingAverage5 / listOfData.get(i).getOpenAsValue()) * 100; //Calculate difference percentage
        differencePercentage = differencePercentage - 100;

        SimpleMovingAverage tempObject = new SimpleMovingAverage(listOfData.get(i).getStockDate(), differencePercentage);
        listOfSMA.add(tempObject);
      }
    }

    listOfSMA.sort(Comparator.comparing(SimpleMovingAverage::getPriceChangePercentage)); //Sort list by price change percentage

    for (SimpleMovingAverage object : listOfSMA)
    {
      System.out.println(object);
    }
  }

  //Helper function to compare dates
  public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
    return !(date.before(min) || date.after(max));
  }
}
