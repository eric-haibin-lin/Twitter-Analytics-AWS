package io.vertx.example;

import java.math.BigInteger;
import java.util.Comparator;


public class ResultQ3 implements Comparator<ResultQ3> {
    private int score;
	private String tid;
	private String content;
	
	public ResultQ3(int score, String tid, String content){
        this.score = score;
		this.tid = tid;
		this.content = content;
	}

    public ResultQ3() {}

    public int getScore() {
        return score;
    }

	public int compare(ResultQ3 o1, ResultQ3 o2) {
        if (o1.score < o2.score) {
            return 1;
        } else if (o1.score > o2.score) {
            return -1;
        } else {
		    return new BigInteger(o1.tid).compareTo(new BigInteger(o2.tid));
        }
	}

  public String toString(){
    return score + "," + tid + "," + content + "\n";
  }

}
