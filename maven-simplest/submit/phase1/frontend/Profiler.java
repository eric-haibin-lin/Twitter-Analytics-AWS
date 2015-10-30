package io.vertx.example.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric Haibin Lin on 29/10/15.
 */
public class Profiler {

  private Map<String, Long> map;

  public Profiler() {
    this.map = new HashMap<>();
  }

  /**
   * register a starting point of an event
   * @param name the name of the event
   */
  public void start(String name){
    long currentTime = System.currentTimeMillis();
    map.put(name, currentTime);
  }

  /**
   * signal the ending point of an event
   * @param name the name of the event
   * @return the duration of the event
   */
  public long end(String name){
    if (map.containsKey(name)){
      Long prev = map.get(name);
      long result = System.currentTimeMillis() - prev;
      map.put(name, result);
      print(name);
      return result;
    } else {
      return -1;
    }
  }

  /**
   * print the duration of event
   * @param name the name of the event
   */
  public void print(String name){
    System.out.println("[Profiler]\t" + name + " took " + map.get(name) + " ms");
  }

  /**
   * print the duration of all events
   */
  public void printAll(){
    for (Map.Entry<String, Long> entry : map.entrySet()) {
      System.out.println("[Profiler]\t" + entry.getKey() + " took " + entry.getValue() + " ms");
    }
  }

}
