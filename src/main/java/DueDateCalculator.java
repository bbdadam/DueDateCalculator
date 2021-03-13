import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {
    public final int WORKING_HOUR_START = 9;
    public final int WORKING_HOUR_END = 17;

    public LocalDateTime calculateDueDate(LocalDateTime submitDate, LocalTime turnaroundTime){

        if (null == submitDate || null == turnaroundTime){
            throw new IllegalArgumentException();
        }
        submitDate = adjustSubmitDate(submitDate);
        LocalDateTime dueDate = submitDate;

        for (int i = 0; i < turnaroundTime.getHour(); i++){
            dueDate = dueDate.plusHours(1);
            if (WORKING_HOUR_END <= dueDate.getHour()){
                dueDate = forwardDateWithNumOfDays(1, dueDate);
            }
            if (DayOfWeek.SATURDAY == dueDate.getDayOfWeek()){
                dueDate = forwardDateWithNumOfDays(2, dueDate);
            }
        }

        return dueDate;
    }

    public LocalDateTime forwardDateWithNumOfDays(int numOfDays, LocalDateTime date){
        date = date.plusDays(numOfDays);
        date = date.withHour(WORKING_HOUR_START);
        return date;
    }

    public LocalDateTime adjustSubmitDate(LocalDateTime submitDate){

        if (WORKING_HOUR_START > submitDate.getHour()){
            submitDate = forwardDateWithNumOfDays(0, submitDate);
            submitDate = submitDate.withMinute(0);
        } else if (WORKING_HOUR_END <= submitDate.getHour()){
            submitDate = forwardDateWithNumOfDays(1, submitDate);
            submitDate = submitDate.withMinute(0);
        }

        if (notWorkingDay(submitDate.getDayOfWeek())){
            int numOfDays = (submitDate.getDayOfWeek() == DayOfWeek.SUNDAY) ? 1:2;
            submitDate = forwardDateWithNumOfDays(numOfDays, submitDate);
            submitDate = submitDate.withMinute(0);
        }

        return submitDate;
    }

    public boolean notWorkingDay(DayOfWeek day){
        return (day == DayOfWeek.SATURDAY) || (day == DayOfWeek.SUNDAY);
    }


}
