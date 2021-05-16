package vehicleftl.visualizer.interactiveelements.util;

import java.util.HashMap;
import java.util.Map;

public class TwoKeyMap<K, J, V> {
  private final Map<K, Map<J, V>> myInternalMap;

  public TwoKeyMap() {
    myInternalMap = new HashMap<>();
  }

  public void put(K k, J j, V v) {
    myInternalMap.putIfAbsent(k,new HashMap<>());
    myInternalMap.get(k).put(j, v);
  }

  public V get(K k, J j) {
    return (myInternalMap.get(k) == null)? null : myInternalMap.get(k).get(j);
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    for (Map<J,V> map : myInternalMap.values()) {
      ret.append(map.toString()).append("\n");
    }
    return ret.toString();
  }
}
