package org.greatlogic.gxttestbed.client;

import java.util.TreeMap;
import com.sencha.gxt.data.shared.ListStore;

public class Cache {
//--------------------------------------------------------------------------------------------------
private static Cache             _currentCache;

private TreeMap<String, PetType> _petTypeByShortDescMap;
private ListStore<PetType>       _petTypeStore;
//--------------------------------------------------------------------------------------------------
public static Cache getCurrentCache() {
  if (_currentCache == null) {
    _currentCache = new Cache();
  }
  return _currentCache;
}
//--------------------------------------------------------------------------------------------------
private Cache() {
  // prevent instantiation from other classes
}
//--------------------------------------------------------------------------------------------------
public PetType findPetTypeUsingShortDesc(final CharSequence petTypeShortDesc) {
  if (_petTypeByShortDescMap == null) {
    _petTypeByShortDescMap = new TreeMap<>();
    for (int petTypeIndex = 0; petTypeIndex < _petTypeStore.size(); ++petTypeIndex) {
      final PetType petType = _petTypeStore.get(petTypeIndex);
      _petTypeByShortDescMap.put(petType.getPetTypeShortDesc(), petType);
    }
  }
  return _petTypeByShortDescMap.get(petTypeShortDesc);
}
//--------------------------------------------------------------------------------------------------
public ListStore<PetType> getPetTypeStore() {
  if (_petTypeStore == null) {
    _petTypeStore = new ListStore<PetType>(PetType.getModelKeyProvider());
  }
  return _petTypeStore;
}
//--------------------------------------------------------------------------------------------------
}