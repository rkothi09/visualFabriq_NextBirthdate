package NextBirthDayDatenew.NextBirthDayDatenew;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class NextBirthdayCalculator 
{
    public static String getTimeUntilNextBirthday(String dateOfBirth, String unit) {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextBirthday = LocalDate.parse(dateOfBirth).withYear(currentDate.getYear());

        // If the birthday has already occurred this year, add 1 year to get the next birthday
        if (nextBirthday.isBefore(currentDate) || nextBirthday.isEqual(currentDate)) {
            nextBirthday = nextBirthday.plusYears(1);
        }

        // Calculate the period between the current date and the next birthday
        Period period = Period.between(currentDate, nextBirthday);

        // Calculate the time remaining based on the specified unit
        long timeRemaining;
        switch (unit) {
            case "hour":
                timeRemaining = ChronoUnit.HOURS.between(currentDate.atStartOfDay(), nextBirthday.atStartOfDay());
                return timeRemaining + " hours left";
            case "day":
                timeRemaining = ChronoUnit.DAYS.between(currentDate, nextBirthday);
                return timeRemaining + " days left";
            case "week":
                timeRemaining = ChronoUnit.WEEKS.between(currentDate, nextBirthday);
                return timeRemaining + " weeks left";
            case "month":
                timeRemaining = ChronoUnit.MONTHS.between(currentDate, nextBirthday);
                return timeRemaining + " months left";
            default:
                return "Invalid unit";
        }
    }

    public static void main(String[] args) {
        String dateOfBirth = "1990-10-30";
        String unit = "hour";
        String hoursRemaining = getTimeUntilNextBirthday(dateOfBirth, "hour");
        System.out.println(hoursRemaining);
        String daysRemaining = getTimeUntilNextBirthday(dateOfBirth, "day");
        System.out.println(daysRemaining);
        String weeksRemaining = getTimeUntilNextBirthday(dateOfBirth, "week");
        System.out.println(weeksRemaining);
        String monthsRemaining = getTimeUntilNextBirthday(dateOfBirth, "month");
        System.out.println(monthsRemaining);
        
    }
}


