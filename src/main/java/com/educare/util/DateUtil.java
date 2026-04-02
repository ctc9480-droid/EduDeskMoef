package com.educare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @Class Name : DateUtil.java
 * @author SI개발팀 박용주
 * @since 2019. 10. 8.
 * @version 1.0
 * @see
 * @Description 
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 10. 8.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class DateUtil {
	
	Calendar calendar;

	private static final String  LOCALE_LANGUAGE   = "ko"; 
	private static final String  LOCALE_COUNTRY    = "KR";
	private static final String  TIME_ZONE_ID       = "JST";
	
	public DateUtil(){
		Locale		locale		= null;
		TimeZone	timeZone	= null;

		locale = new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY);
		timeZone = TimeZone.getTimeZone(TIME_ZONE_ID);

		calendar = Calendar.getInstance(timeZone,locale);
	}
	
	public DateUtil(int year, int month){
		Locale		locale		= null;
		TimeZone	timeZone	= null;

		locale = new Locale(LOCALE_LANGUAGE,
				LOCALE_COUNTRY);
		timeZone = TimeZone.getTimeZone(TIME_ZONE_ID);

		calendar = Calendar.getInstance(timeZone,locale);

		calendar.clear(Calendar.YEAR);
		calendar.clear(Calendar.MONTH);
		calendar.clear(Calendar.DATE);
		calendar.set(year,month-1,1);
	}
	
	public DateUtil(Date date){
		Locale		locale		= null;
		TimeZone	timeZone	= null;

		locale = new Locale(LOCALE_LANGUAGE,
				LOCALE_COUNTRY);
		timeZone = TimeZone.getTimeZone(TIME_ZONE_ID);

		calendar = Calendar.getInstance(timeZone,locale);

		calendar.clear(Calendar.YEAR);
		calendar.clear(Calendar.MONTH);
		calendar.clear(Calendar.DATE);
		calendar.setTime(date);
	}
	
	public int getYear(){
    	return  calendar.get(Calendar.YEAR) ;
    }
	
	public int getMonth(){
        return ( calendar.get(Calendar.MONTH)+1 );
    }

    public int getDay(){
        return  calendar.get(Calendar.DAY_OF_MONTH) ;
    }

    public int getLastDay(){
    	return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ;
    }
    
    /**
 	 * 날짜에서 정해진만큼의 기간이 지난 후의 날짜를 반환한다.
 	 */
	public static String dayCalForStr(int y, int m, int d, int margin, String mode, String dtformat){
		int year = y;
		int month = m;
		int date = d;
		int addMargin = margin;

		Calendar cal = Calendar.getInstance();
		Date day;

		if(mode.equals("year")) {     // 년모드
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.YEAR, addMargin);
			day  = cal.getTime();
		}else if(mode.equals("month")) {   // 달모드
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.MONTH, addMargin);
			day  = cal.getTime();
		}else{        // 날짜모드(default)
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.DATE, addMargin);
			day  = cal.getTime();
		}
		if(dtformat == null){
			return null;
		}
  		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);
		String strDay = format.format(day);
		return strDay;
	 }
	
	/**
 	 * 날짜에서 정해진만큼의 기간이 지난 후의 날짜를 반환한다.
 	 */
	public static Date dayCalForStr(int y, int m, int d, int margin, String mode){
		int year = y;
		int month = m;
		int date = d;
		int addMargin = margin;

		Calendar cal = Calendar.getInstance();
		Date day;

		if(mode.equals("year")) {     // 년모드
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.YEAR, addMargin);
			day  = cal.getTime();
		}else if(mode.equals("month")) {   // 달모드
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.MONTH, addMargin);
			day  = cal.getTime();
		}else{        // 날짜모드(default)
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, date );
			cal.add(Calendar.DATE, addMargin);
			day  = cal.getTime();
		}
		return day;
	 }
	
	public static String getFirstDayOfWeek(int y, int m, int d, String dtformat) {
		if(dtformat == null){
			return null;
		}
		int year = y;
		int month = m;
		int date = d;

		int startDay = 0;

		Calendar cal = Calendar.getInstance();
		Date day;

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, date );
		startDay = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, -(startDay-1));

		day = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);

		String strDay = format.format(day);
		return strDay;
	}
	
	public static String getLastDayOfMonth(int y, int m, int d, String dtformat) {
		if(dtformat == null){
			return null;
		}
		
		int year = y;
		int month = m;
		int date = d;
		int lstDay = 0;
		
		Date day;
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date);
		lstDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, year);
		cal2.set(Calendar.MONTH, month - 1);
		cal2.set(Calendar.DATE, lstDay );
		day = cal2.getTime();
		
		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);

		String strDay = format.format(day);
		
		return strDay;
	}
	
	public static String getLastSaturdayOfCalendar(int y, int m, int d, String dtformat){
		if(dtformat == null){
			return null;
		}
		int year = y;
		int month = m;
		int date = d;
		
		Date day;
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date);
		
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayNum == 7){
			day = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);

			String strDay = format.format(day);
			return strDay;
		}else{
			cal.add(Calendar.DATE, 7 - dayNum);
			day = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);

			String strDay = format.format(day);
			return strDay;
		}
	}
	
	/**
	 * 넘겨준 year,month,day,time,minute값으로 디비에 저장할 Date함수를 넘겨주는 메소드
	 * @param int year 년도
	 * @param int month 월
	 * @param int day 일
	 * @param int time 시간
	 * @param int minute 분
	 * @return Date
	 * */
	public Date getDateInString(int year, int month, int day, int time, int minute){
		calendar.set(year, month-1, day, time, minute, 1);
		Date date = calendar.getTime();
		return date;
	}
	
	public static String getDate2Str(long getDate, String dtformat) {
		Date day = new Date(getDate);
		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);
		String strDay = format.format(day);
		return strDay;
	}
	
	public static String getDate2Str(Date getDate, String dtformat) {
		if(getDate == null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);
		String strDay = format.format(getDate);
		return strDay;
	}

	public static Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.MONTH, 4);
		//cal.set(Calendar.DAY_OF_MONTH, 3);
		return cal;
	}

	public static Date getStr2Date(String eduDt, String dtformat) {
		SimpleDateFormat format = new SimpleDateFormat(dtformat, Locale.KOREA);
		try {
			return format.parse(eduDt);
		} catch (ParseException e) {
			return null;
		}
	}

	public static long getDiffDayFromNow(Date target) {
		if(target==null){
			return 0;
		}
		Calendar getToday = Calendar.getInstance();
		getToday.setTime(new Date()); //금일 날짜
		
		Calendar cmpDate = Calendar.getInstance();
		cmpDate.setTime(target); //특정 일자
		
		long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
		long diffDays = diffSec / (24*60*60); //일자수 차이
		return diffDays;
	}
	
	public static int calDays(String startDay, String endDay){
		
		int sy = Integer.parseInt(startDay.substring(0, 4));
		int sm = Integer.parseInt(startDay.substring(4, 6));
		int sd = Integer.parseInt(startDay.substring(6, 8));
		
		int ey = Integer.parseInt(endDay.substring(0, 4));
		int em = Integer.parseInt(endDay.substring(4, 6));
		int ed = Integer.parseInt(endDay.substring(6, 8));
		
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		
		sCal.set(sy, sm - 1, sd);
		eCal.set(ey, em - 1, ed);
		
		long diffSec = (eCal.getTimeInMillis() - sCal.getTimeInMillis()) / 1000; 
		long diffDay = diffSec/(60 * 60 * 24);
		
		return (int) diffDay;
	}
	
	public static int getWeekNoByDate(Date date){

        // Calendar 객체 생성
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 요일을 가져오기 위해 Calendar에서 요일 필드를 가져옴
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

       return dayOfWeek;
	}
	/**
	 * 나이계산
	 * <pre>
	 * 
	 * </pre>
	 * @return
	 */
	public static int calculateAge(String birthDate) {
        try {
        	// 생년월일 문자열을 LocalDate로 변환
        	LocalDate birthDateObj = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        	/*
        	// 현재 날짜 구하기
        	LocalDate currentDate = LocalDate.now();
        	// 나이 계산
        	long age = ChronoUnit.YEARS.between(birthDateObj, currentDate);
        	return (int) age;
        	*/
            int koreanAge = LocalDate.now().getYear() - birthDateObj.getYear() + 1;
            return koreanAge >= 0 ? koreanAge : 0;
		} catch (NullPointerException e) {
			return 0;
		}
    }
	
	public static Date addMinutesToDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
	
	public static String calculateStrTm(long timeDifferenceMillis) throws ParseException {
	       
            long hoursDifference = timeDifferenceMillis / (60 * 60 * 1000);
            long minutesDifference = (timeDifferenceMillis / (60 * 1000)) % 60;

            String result = (hoursDifference + "시간 ");
            if(minutesDifference>0){
            	result += (minutesDifference+"분");
            }
            
            return result;
       
	}
	
	 public static String getYearForAge(int age) {
	        // 현재 날짜 구하기
	        Calendar calendar = Calendar.getInstance();

	        // 한국 나이로 20세에 해당하는 년도 계산
	        int birthYear = calendar.get(Calendar.YEAR) - age;
	        birthYear++;
	        // 생년월일을 설정
	        calendar.set(Calendar.YEAR, birthYear);

	        // 날짜를 yyyyMMdd 형식으로 포맷팅
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.KOREA);
	        Date birthDate = calendar.getTime();
	        String formattedBirthDate = sdf.format(birthDate);

	        return formattedBirthDate;
	    }

	// 두 Date 객체 사이의 시간 차이를 초 단위로 계산하는 함수
    public static long getSecondsDifference(Date a, Date b) {
        // 두 Date 객체의 밀리초 값을 가져옴
    	long diffInMillis = b.getTime() - a.getTime();

        // 밀리초를 초로 변환
        long diffInSeconds = diffInMillis / 1000;

        return diffInSeconds;
    }

	public static int calMin(Date startDe, Date endDe) {
		// 예외 처리: startDe 또는 endDe가 null인 경우
	    if (startDe == null || endDe == null) {
	        throw new IllegalArgumentException("Date parameters cannot be null");
	    }

	    // 시작 시간이 끝 시간보다 늦을 경우 예외 처리
	    if (startDe.after(endDe)) {
	        throw new IllegalArgumentException("Start date cannot be after end date");
	    }

	    // 두 시간의 차이를 밀리초 단위로 계산
	    long diffInMillies = endDe.getTime() - startDe.getTime();

	    // 밀리초를 분 단위로 변환
	    int diffInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffInMillies);

	    return diffInMinutes;
	}
	
	public static int calSec(Date startDe, Date endDe) {
	    // 예외 처리: startDe 또는 endDe가 null인 경우
	    if (startDe == null || endDe == null) {
	        throw new IllegalArgumentException("Date parameters cannot be null");
	    }

	    // 시작 시간이 끝 시간보다 늦을 경우 예외 처리
	    if (startDe.after(endDe)) {
	        throw new IllegalArgumentException("Start date cannot be after end date");
	    }

	    // 두 시간의 차이를 밀리초 단위로 계산
	    long diffInMillies = endDe.getTime() - startDe.getTime();

	    // 밀리초를 초 단위로 변환
	    int diffInSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMillies);

	    return diffInSeconds;
	}
	public static int calMil(Date startDe, Date endDe) {
        // 두 날짜의 차이를 밀리초로 계산
        long differenceInMilliseconds = endDe.getTime() - startDe.getTime();
        
        // int로 변환하여 반환 (int로 변환 시 범위 주의)
        return (int) differenceInMilliseconds;
    }
	
	public static int getOverlapDays(String offStart, String offEnd,
            String peakStart, String peakEnd) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate offStartDate = LocalDate.parse(offStart, formatter);
        LocalDate offEndDate = LocalDate.parse(offEnd, formatter);
        LocalDate peakStartDate = LocalDate.parse(peakStart, formatter);
        LocalDate peakEndDate = LocalDate.parse(peakEnd, formatter);

        // 겹치는 구간의 시작 = 두 기간 중 더 늦은 시작일
        LocalDate overlapStart = offStartDate.isAfter(peakStartDate) ? offStartDate : peakStartDate;
        // 겹치는 구간의 종료 = 두 기간 중 더 이른 종료일
        LocalDate overlapEnd = offEndDate.isBefore(peakEndDate) ? offEndDate : peakEndDate;

        if (overlapStart.isAfter(overlapEnd)) {
            // 겹치지 않음
            return 0;
        }

        // inclusive 계산을 위해 +1
        return (int) (ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1);
	}
}
