import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateValidation {
    public static boolean validate_date(String str, String format) {
        SimpleDateFormat form = new SimpleDateFormat(format);
        form.setLenient(false);

        try {
            Date date = form.parse(str);
            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }

    public static boolean validate_calendar(int year, int month, int day) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);

            calendar.set(year, month - 1, day);

            Date date = calendar.getTime();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
