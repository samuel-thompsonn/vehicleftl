package vehicleftl.visualizer.interactiveelements.util;

import java.util.HashMap;
import java.util.Map;

public class ThreeKeyMap<K, J, L, V> {
  private final Map<K, Map<J, Map<L, V>>> myInternalMap;

  public ThreeKeyMap() {
    myInternalMap = new HashMap<>();
  }

  public void put(K k, J j, L l, V v) {
    myInternalMap.putIfAbsent(k,new HashMap<>());
    myInternalMap.get(k).putIfAbsent(j,new HashMap<>());
    myInternalMap.get(k).get(j).put(l, v);
  }

  public V get(K k, J j, L l) {
    if (myInternalMap.get(k) == null) { return null; }
    return (myInternalMap.get(k).get(j) == null)? null : myInternalMap.get(k).get(j).get(l);
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    for (Map<J, Map<L,V>> map : myInternalMap.values()) {
      ret.append(map.toString()).append("\n");
    }
    return ret.toString();
  }
}
