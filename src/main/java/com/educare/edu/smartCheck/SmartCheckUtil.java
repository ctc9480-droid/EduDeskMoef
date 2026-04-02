/**
 */
package com.educare.edu.smartCheck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SmartCheckUtil {

	/**
	 * 최종결석수 계산
	 * @param absentCnt
	 * @param lateCnt
	 * @param leaveCnt
	 * @return
	 */
	public static int calcFinalAttendCnt(int absentCnt, int lateCnt, int leaveCnt) {
		// 지각과 조퇴의 합계를 계산
	    int combinedLateAndLeave = lateCnt + leaveCnt;

	    // 지각과 조퇴의 합계에 따라 추가되는 결석 수 계산
	    int additionalAbsences = combinedLateAndLeave / 3;

	    // 최종 결석 수 계산
	    int finalAbsentCnt = absentCnt + additionalAbsences;

	    return finalAbsentCnt;
	}

	public static double calcRatio(long sourcep, long source2p, long precisionp) {
		long precision = precisionp;
		long source = sourcep;
		long source2 = source2p;
		
		// 예외 처리: 음수 값이 들어올 경우 0으로 처리
        if (source < 0) source = 0;
        if (source2 < 0) source2 = 0;
        if (precision < 0) precision = 0;

        // 예외 처리: 원본 값이 0인 경우 비율은 0%
        if (source == 0) return 0.0;

        // 비율 계산
        double ratio = ((double) source2 / source) * 100;

        // 비율이 음수가 되지 않도록 처리 (이 상황에서는 필요 없지만, 일관성을 위해 추가)
        if (ratio < 0) ratio = 0.0;

        // 소수점 자리수 설정 (long을 int로 변환)
        BigDecimal bd = new BigDecimal(ratio).setScale((int) precision, RoundingMode.HALF_UP);
        ratio = bd.doubleValue();

        return ratio;
	}

	public static int calcAttMin(Date startDe, Date endDe, Date startDe2, Date endDe2) {
		 // 예외 처리: Date 파라미터가 null인 경우
	    if (startDe == null || endDe == null || startDe2 == null || endDe2 == null) {
	        return 0;
	    }

	    // 예외 처리: 교육 시작 시간이 교육 종료 시간보다 늦거나
	    // 출근 시간이 퇴근 시간보다 늦은 경우
	    if (startDe.after(endDe) || startDe2.after(endDe2)) {
	        return 0;
	    }

	    // 출근 시간이 교육 종료 시간보다 늦거나 퇴근 시간이 교육 시작 시간보다 빠른 경우
	    if (startDe2.after(endDe) || endDe2.before(startDe)) {
	        // 출석 인정 시간이 없음
	        return 0;
	    }

	    // 출석 인정 시간의 시작과 종료 시간 계산
	    Date actualStart = startDe2.after(startDe) ? startDe2 : startDe;
	    Date actualEnd = endDe2.before(endDe) ? endDe2 : endDe;

	    // 출석 인정 시간의 차이를 밀리초 단위로 계산
	    long diffInMillies = actualEnd.getTime() - actualStart.getTime();

	    // 밀리초를 분 단위로 변환
	    int diffInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffInMillies);

	    return diffInMinutes;
	}

	 public static long calcInterDate(Date eduBeginDe, Date eduEndDe, Date attBeginDe, Date attEndDe) {
	        // Convert Date objects to Calendar objects for easier manipulation
	        Calendar eduBegin = Calendar.getInstance();
	        Calendar eduEnd = Calendar.getInstance();
	        Calendar attBegin = Calendar.getInstance();
	        Calendar attEnd = Calendar.getInstance();
	        
	        eduBegin.setTime(eduBeginDe);
	        eduEnd.setTime(eduEndDe);
	        attBegin.setTime(attBeginDe);
	        attEnd.setTime(attEndDe);

	        // Find the latest start time and earliest end time of the overlap
	        Calendar overlapBegin = (eduBegin.after(attBegin)) ? eduBegin : attBegin;
	        Calendar overlapEnd = (eduEnd.before(attEnd)) ? eduEnd : attEnd;

	        // Check if there is an overlap
	        if (overlapBegin.after(overlapEnd)) {
	            return 0; // No overlap, return 0 seconds
	        }

	        // Calculate the overlap duration in milliseconds
	        long overlapMillis = overlapEnd.getTimeInMillis() - overlapBegin.getTimeInMillis();
	        
	        // Convert milliseconds to seconds
	        long overlapSeconds = (overlapMillis / 1000);
	        
	        return overlapSeconds; // Return the overlap duration in seconds
	    }

}

