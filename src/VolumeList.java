import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VolumeList
{
  private int volumeNumber = 0;
  private Double changedPrice = 0.0;
  private Date currentDate;

  public VolumeList(int volumeNumber, Double changedPrice, Date currentDate)
  {
    this.volumeNumber = volumeNumber;
    this.changedPrice = changedPrice;
    this.currentDate = currentDate;
  }

  public int getVolumeNumber()
  {
    return volumeNumber;
  }

  public void setVolumeNumber(int volumeNumber)
  {
    this.volumeNumber = volumeNumber;
  }

  public Double getChangedPrice()
  {
    return changedPrice;
  }

  public void setChangedPrice(Double changedPrice)
  {
    this.changedPrice = changedPrice;
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

  @Override
  public String toString()
  {
    DecimalFormat formatDecimals = new DecimalFormat("#.##");

    return "Date: " + this.getCurrentDate() + " Volume: " + this.volumeNumber + " Price changed: $" + formatDecimals.format(this.changedPrice);
  }
}
