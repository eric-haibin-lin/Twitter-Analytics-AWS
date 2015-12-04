import java.math.BigInteger;
import java.util.Comparator;


public class Result implements Comparator<Result> {
	private String tid;
	private String content;
	
	public Result(String tid, String content){
		this.tid = tid;
		this.content = content;
	}

  public Result(){}

	public int compare(Result o1, Result o2) {
		return new BigInteger(o1.tid).compareTo(new BigInteger(o2.tid));
	}

  public String toString(){
    return tid + ":" + content + "\\n";
  }

}
