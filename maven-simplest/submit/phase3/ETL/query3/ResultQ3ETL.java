import java.math.BigInteger;
import java.util.Comparator;


public class ResultQ3ETL implements Comparator<ResultQ3ETL> {
    private int score;
	private String tid;
	private String content;
	
	public ResultQ3ETL(int score, String tid, String content){
        this.score = score;
		this.tid = tid;
		this.content = content;
	}

    public ResultQ3ETL() {}

    public int getScore() {
        return score;
    }

	public int compare(ResultQ3ETL o1, ResultQ3ETL o2) {
        if (o1.score < o2.score) {
            return 1;
        } else if (o1.score > o2.score) {
            return -1;
        } else {
            if (o1.score > 0) {
		        return new BigInteger(o1.tid).compareTo(new BigInteger(o2.tid));
            } else {
                return new BigInteger(o2.tid).compareTo(new BigInteger(o1.tid));
            }
        }
	}

  public String toString(){
    return content;
  }

}
