import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
		public long int_time;
		public String time;
		//public int year;
		//public int month;
		public int date;
		public int hour;
		public int min;
		public int second;
		public int time_period;
		
		public Time(long t){
			int_time=t;
			time=getStartTimeString();
			//year=get_year();
			//month=get_month();
			date=get_date();
			hour=get_hour();
			min=get_min();
			second=get_second();
			time_period=get_time_period();

		}
		//public static int time_intervel=time_interval();
		

	    public String getStartTimeString() {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd, HH:mm:ss");
	        Calendar start = Calendar.getInstance();
	        start.setTimeInMillis(int_time);

	        return sdf.format(int_time);
	    }
/*		
		public int get_year(){
			char year_char[]=new char[4];
			time.getChars(0, 4, year_char, 0);
			String year_str=String.valueOf(year_char);
			year=Integer.parseInt(year_str);
			return year;		
		}
		public int get_month(){
			char month_char[]=new char[2];
			time.getChars(5, 7, month_char, 0);
			String month_str=String.valueOf(month_char);
			month=Integer.parseInt(month_str);
			return month;
		}
*/
		public int get_date(){
			char date_char[]=new char[2];
			time.getChars(0, 2, date_char, 0);
			String date_str=String.valueOf(date_char);
			date=Integer.parseInt(date_str);
			return date;
		}
		public int get_hour(){
			char hour_char[]=new char[2];
			time.getChars(4, 6, hour_char, 0);
			String hour_str=String.valueOf(hour_char);
			hour=Integer.parseInt(hour_str);
			return hour;
		}
		public int get_min(){
			char min_char[]=new char[2];
			time.getChars(7,9,min_char,0);
			String min_str=String.valueOf(min_char);
			min=Integer.parseInt(min_str);
			return min;
		}
		public int get_second(){
			char second_char[]=new char[2];
			time.getChars(10, 12, second_char, 0);
			String second_str=String.valueOf(second_char);
			second=Integer.parseInt(second_str);
			return second;		
		}
		public int get_time_period(){
			if (hour>=23 && hour<2)
				return 1;
			else if (hour>=2 && hour<5)
				return 2;
			else if (hour>=5 && hour<8)
				return 3;
			else if (hour>=8 && hour<11)
				return 4;
			else if (hour>=11 && hour<14)
				return 5;
			else if (hour>=14 && hour<17)
				return 6;
			else if (hour>=17 && hour<20)
				return 7;
			else 
				return 8;					
		}
		
		public static void main(){
			System.out.println("a");
			long cur = 1405305067756L;
			Time t= new Time(cur);
			System.out.println(t.date);
			System.out.println(t.hour);
			System.out.println(t.min);
			System.out.println(t.second);
			
			
			
		}
	}

