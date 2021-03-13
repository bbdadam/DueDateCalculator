import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DueDateCalculatorTest {
    DueDateCalculator calculator = new DueDateCalculator();
    DateTimeFormatter formatterSumit = DateTimeFormatter.ofPattern("yyyy.MM.dd:HH.mm");
    DateTimeFormatter formatterTurnaround = DateTimeFormatter.ofPattern("HH.mm");

    @Test
    void testDueDateIsNextDay(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.10:16.00", formatterSumit);
        LocalTime turnaroundTime = LocalTime.parse("02.00", formatterTurnaround);
        LocalDateTime dueDate = calculator.calculateDueDate(submitDate, turnaroundTime);

        assert(dueDate.getDayOfMonth() > submitDate.getDayOfMonth());

    }

    @Test
    void testSkipWeekends(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.12:16.00", formatterSumit);
        LocalTime turnaroundTime = LocalTime.parse("02.00", formatterTurnaround);
        LocalDateTime dueDate = calculator.calculateDueDate(submitDate, turnaroundTime);
        assert(dueDate.getDayOfWeek() != DayOfWeek.SATURDAY && dueDate.getDayOfWeek() != DayOfWeek.SUNDAY);
    }

    @Test
    void testNotWorkingDayPositive(){
        LocalDateTime date = LocalDateTime.parse("2021.03.13:16.00", formatterSumit);

        assert(calculator.notWorkingDay(date.getDayOfWeek()));
    }

    @Test
    void testNotWorkingDayNegative(){
        LocalDateTime date = LocalDateTime.parse("2021.03.12:16.00", formatterSumit);

        assert(!calculator.notWorkingDay(date.getDayOfWeek()));
    }

    @Test
    void testAdjustSubmitDateToNextDay(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.10:18.00", formatterSumit);
        submitDate = calculator.adjustSubmitDate(submitDate);

        assertEquals(DayOfWeek.THURSDAY, submitDate.getDayOfWeek());

    }

    @Test
    void testAdjustSubmitDateSkippingWeekends(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.13:18.00", formatterSumit);
        submitDate = calculator.adjustSubmitDate(submitDate);

        assertEquals(DayOfWeek.MONDAY, submitDate.getDayOfWeek());
    }

    @Test
    void testAdjustSubmitDateToNextDayWorkingStart(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.13:18.00", formatterSumit);
        submitDate = calculator.adjustSubmitDate(submitDate);

        assertEquals(calculator.WORKING_HOUR_START, submitDate.getHour());
    }

    @Test
    void testForwardDateWithNumOfDaysToNextDay(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.11:18.00", formatterSumit);
        submitDate = calculator.forwardDateWithNumOfDays(1,submitDate);

        assertEquals(DayOfWeek.FRIDAY, submitDate.getDayOfWeek());
    }

    @Test
    void testForwardDateWithNumOfDaysToNextDaysWorkStart(){
        LocalDateTime submitDate = LocalDateTime.parse("2021.03.11:18.00", formatterSumit);
        submitDate = calculator.forwardDateWithNumOfDays(1,submitDate);

        assertEquals(calculator.WORKING_HOUR_START, submitDate.getHour());
    }
}
