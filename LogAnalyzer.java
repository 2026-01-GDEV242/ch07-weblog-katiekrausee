import java.util.ArrayList;
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author   Katie Krause
 * @version    2026.04.05
 */

public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // Keep a list of all log entries for counting
    private ArrayList<LogEntry> entries;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
        entries = new ArrayList<>();
        readData();
    }

    /**
     * Constructor that takes filname (7.12)
     */
    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
        entries = new ArrayList<>();
        readData();
    }
    /**
     * Read all data into entries ArrayList
     */
    private void readData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            entries.add(entry);
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Return total number of accesses (7.14)
     */
    public int numberOfAccesses()
    {
        return entries.size();
    }

    /**
     * Which hour has the most accesses? (7.15)
     */
    public int busiestHour()
    {
        int max = 0;
        int busiest = 0;
        for(int i = 0; i < hourCounts.length; i++) {
            if(hourCounts[i] > max) {
                max = hourCounts[i];
                busiest = i;
            }
        }
        return busiest;
    }

    /**
     * Which hour has the fewest accesses? (7.16)
     */
    public int quietestHour()
    {
        int min = hourCounts[0];
        int quietest = 0;
        for(int i = 1; i < hourCounts.length; i++) {
            if(hourCounts[i] < min) {
                min = hourCounts[i];
                quietest = i;
            }
        }
        return quietest;
    }

    /**
     * Which consecutive 2-hour period is busiest? (7.18)
     */
    public int busiestTwoHour()
    {
        int max = 0;
        int bestHour = 0;
        for(int i = 0; i < 24; i++) {
            int next = (i + 1) % 24; // wraps around midnight
            int total = hourCounts[i] + hourCounts[next];
            if(total > max) {
                max = total;
                bestHour = i;
            }
        }
        return bestHour;
    }

    /**
     * Print the hourly counts
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
