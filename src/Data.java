import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data
{
  private String stockDate = "01/01/1970";
  private String closeLast = "0";
  private String volume = "0";
  private String open = "0";
  private String high = "0";
  private String low = "0";

  public Data(String stockDate, String closeLast, String volume, String open, String high, String low)
  {
    this.stockDate = stockDate;
    this.closeLast = closeLast;
    this.volume = volume;
    this.open = open;
    this.high = high;
    this.low = low;
  }

  public Date getStockDate() throws ParseException
  {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    return formatter.parse(this.stockDate);
  }

  public void setStockDate(String stockDate)
  {
    this.stockDate = stockDate;
  }

  public String getCloseLast()
  {
    return closeLast;
  }

  public void setCloseLast(String closeLast)
  {
    this.closeLast = closeLast;
  }

  public String getVolume()
  {
    return volume;
  }

  public void setVolume(String volume)
  {
    this.volume = volume;
  }

  public String getOpen()
  {
    return open;
  }

  public void setOpen(String open)
  {
    this.open = open;
  }

  public String getHigh()
  {
    return high;
  }

  public void setHigh(String high)
  {
    this.high = high;
  }

  public String getLow()
  {
    return low;
  }

  public void setLow(String low)
  {
    this.low = low;
  }

  public Double getCloseLastAsValue()
  {
    String doubleString = this.closeLast.replace("$", "");

    return Double.parseDouble(doubleString);
  }

  public int getVolumeAsValue()
  {
    String parsedString = this.volume.replace(" ", "");
    return Integer.parseInt(parsedString);
  }

  public Double getOpenAsValue()
  {
    String doubleString = this.open.replace("$", "");

    return Double.parseDouble(doubleString);
  }

  public Double getHighAsValue()
  {
    String doubleString = this.high.replace("$", "");

    return Double.parseDouble(doubleString);
  }

  public Double getLowAsValue()
  {
    String doubleString = this.low.replace("$", "");

    return Double.parseDouble(doubleString);
  }
}

