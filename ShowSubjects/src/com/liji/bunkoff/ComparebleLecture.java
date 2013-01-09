package com.liji.bunkoff;

import java.util.Comparator;

public class ComparebleLecture implements Comparator<Lecture> {

	@Override
	public int compare(Lecture lhs, Lecture rhs) {
		int result=(lhs.getHour()*60+lhs.getMinute())-(rhs.getHour()*60+rhs.getMinute());
		return result;
	}

}
