package vehicleftl.visualizer.interactiveelements.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultiKeyMap<K, V> {
  private final Map<K, MultiKeyMap<K, V>> myInternalMap;
  private final Map<K, V> myBaseMap;

  public MultiKeyMap() {
    myInternalMap = new HashMap<>();
    myBaseMap = new HashMap<>();
  }

  public void put(V v, K... k) {
    if (k.length == 1) {
      myBaseMap.put(k[0], v);
      return;
    }
    myInternalMap.putIfAbsent(k[0],new MultiKeyMap<>());
    myInternalMap.get(k[0]).put(v, Arrays.copyOfRange(k, 1, k.length));
  }

  public V get(K... k) {
    if (k.length == 1) {
      return myBaseMap.get(k[0]);
    }
    if (myInternalMap.get(k[0]) == null) { return null; }
    return myInternalMap.get(k[0]).get(Arrays.copyOfRange(k, 1, k.length));
  }

//  @Override
//  public String toString() {
//    StringBuilder ret = new StringBuilder();
//    for (Map<J, Map<L,V>> map : myInternalMap.values()) {
//      ret.append(map.toString()).append("\n");
//    }
//    return ret.toString();
//  }

  public static void main(String[] args) {
    MultiKeyMap<String, String> map = new MultiKeyMap<>();
    map.put("abcd", "a", "b", "c", "d");
    System.out.println("map.get(\"a\", \"b\", \"c\", \"d\") = " + map.get("a", "b", "c", "d"));
    System.out.println("map.get(\"a\", \"c\", \"b\", \"d\") = " + map.get("a", "c", "b", "d"));
    map.put("bcad", "a", "b", "c", "d");
    System.out.println("map.get(\"a\", \"b\", \"c\", \"d\") = " + map.get("a", "b", "c", "d"));
    map.put("lkdjaf;lskdjf", "a", "b", "a", "a");
    System.out.println("map.get(\"a\", \"b\", \"a\", \"a\") = " + map.get("a", "b", "a", "a"));
  }
}
