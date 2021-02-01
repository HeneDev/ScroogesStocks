import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleMovingAverage
{
  private Date currentDate;
  private Double priceChangePercentage = 0.0;

  public SimpleMovingAverage(Date currentDate, Double priceChangePercentage)
  {
    this.currentDate = currentDate;
    this.priceChangePercentage = priceChangePercentage;
  }

  public String getCurrentDate()
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    return dateFormat.format(this.currentDate);
  }

  public void setCurrentDate(Date currentDate)
  {
    this.currentDate = currentDate;
  }

  public Double getPriceChangePercentage()
  {
    return priceChangePercentage;
  }

  public void setPriceChangePercentage(Double priceChangePercentage)
  {
    this.priceChangePercentage = priceChangePercentage;
  }

  @Override
  public String toString()
  {
    DecimalFormat formatDecimals = new DecimalFormat("#.##");

    return "Date: " + this.getCurrentDate() + " Price change percentage: " + formatDecimals.format(this.priceChangePercentage) + "%";
  }
}
