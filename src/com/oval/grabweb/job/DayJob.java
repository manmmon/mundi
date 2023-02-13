package com.oval.grabweb.job;

import com.oval.util.DateUtil;

public class DayJob extends AbstractJob{
	@Override
	protected String getBeginDate() {
		return DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
	}
	@Override
	protected String getEndDate() {
		return DateUtil.getLastDay("yyyy-MM-dd");
	}
}
