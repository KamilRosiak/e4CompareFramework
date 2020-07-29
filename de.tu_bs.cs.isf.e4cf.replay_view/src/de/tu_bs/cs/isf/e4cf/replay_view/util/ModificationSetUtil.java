package de.tu_bs.cs.isf.e4cf.replay_view.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import FeatureDiagramModificationSet.Modification;

public class ModificationSetUtil {
 
	
	public static String toDateString(long timestamp) {
		Timestamp ts = new Timestamp(timestamp);
		Date d = new Date(ts.getTime());
		String datePattern = "dd.MM.yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		return sdf.format(d);
	}
	
	public static int compare(Modification mod1, Modification mod2) {
		int result = Long.compare(mod1.getTimeStamp(), mod2.getTimeStamp());
		if (result == 0) {
			return Long.compare(mod1.getPrecisionTime(), mod2.getPrecisionTime());
		} 
		return result;
	}
}
