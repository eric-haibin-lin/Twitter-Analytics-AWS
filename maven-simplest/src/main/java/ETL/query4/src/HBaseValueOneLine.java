public class HBaseValueOneLine implements Comparable<HBaseValueOneLine> {
	String Date = "";
	Integer Count = 0;
	String UserIds = "";
	String SourceText = "";

	@Override
	public String toString() {
		return Date + ":" + Count.toString() + ":" + UserIds + ":" + SourceText;
	}

	@Override
	public int compareTo(HBaseValueOneLine o) {
		/* Each day should be sorted by count in a descending order */
		int compare = Integer.compare(o.getCount(), Count);
		/*
		 * If counts are the same, the lines should be sorted by date in an
		 * ascending order
		 */
		if (compare == 0) {
			compare = Date.compareTo(o.getDate());
		}
		return compare;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public String getUserIds() {
		return UserIds;
	}

	public void setUserIds(String userIds) {
		UserIds = userIds;
	}

	public String getSourceText() {
		return SourceText;
	}

	public void setSourceText(String sourceText) {
		SourceText = sourceText;
	}
}
