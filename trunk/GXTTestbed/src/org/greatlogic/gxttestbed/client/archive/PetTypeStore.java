package org.greatlogic.gxttestbed.client.archive;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;

public class PetTypeStore extends ListStore<PetType> {
//--------------------------------------------------------------------------------------------------
private final TreeMap<Integer, PetType> _petTypeByIdMap;
private final TreeMap<String, PetType>  _petTypeByShortDescMap;
//--------------------------------------------------------------------------------------------------
public PetTypeStore(final ModelKeyProvider<? super PetType> keyProvider) {
  super(keyProvider);
  _petTypeByIdMap = new TreeMap<Integer, PetType>();
  _petTypeByShortDescMap = new TreeMap<String, PetType>();
}
//--------------------------------------------------------------------------------------------------
@Override
public void add(final int index, final PetType petType) {
  super.add(index, petType);
  addPetType(petType);
}
//--------------------------------------------------------------------------------------------------
@Override
public void add(final PetType petType) {
  super.add(petType);
  addPetType(petType);
}
//--------------------------------------------------------------------------------------------------
@Override
public boolean addAll(final Collection<? extends PetType> petTypes) {
  for (final PetType petType : petTypes) {
    addPetType(petType);
  }
  return super.addAll(petTypes);
}
//--------------------------------------------------------------------------------------------------
@Override
public boolean addAll(final int index, final Collection<? extends PetType> petTypes) {
  for (final PetType petType : petTypes) {
    addPetType(petType);
  }
  return super.addAll(index, petTypes);
}
//--------------------------------------------------------------------------------------------------
private void addPetType(final PetType petType) {
  _petTypeByIdMap.put(petType.getPetTypeId(), petType);
  _petTypeByShortDescMap.put(petType.getPetTypeShortDesc(), petType);
}
//--------------------------------------------------------------------------------------------------
@Override
public void clear() {
  super.clear();
  _petTypeByIdMap.clear();
  _petTypeByShortDescMap.clear();
}
//--------------------------------------------------------------------------------------------------
@Override
public PetType remove(final int index) {
  removePetType(get(index));
  return super.remove(index);
}
//--------------------------------------------------------------------------------------------------
@Override
public boolean remove(final PetType petType) {
  removePetType(petType);
  return super.remove(petType);
}
//--------------------------------------------------------------------------------------------------
private void removePetType(final PetType petType) {
  _petTypeByIdMap.remove(petType.getPetTypeId());
  _petTypeByShortDescMap.remove(petType.getPetTypeShortDesc());
}
//--------------------------------------------------------------------------------------------------
@Override
public void replaceAll(final List<? extends PetType> petTypes) {
  _petTypeByIdMap.clear();
  _petTypeByShortDescMap.clear();
  for (final PetType petType : petTypes) {
    addPetType(petType);
  }
  super.replaceAll(petTypes);
}
//--------------------------------------------------------------------------------------------------
@Override
public void update(final PetType petType) {
  removePetType(_petTypeByIdMap.get(petType.getPetTypeId()));
  addPetType(petType);
  super.update(petType);
}
//--------------------------------------------------------------------------------------------------
public PetType findUsingId(final int petTypeId) {
  return _petTypeByIdMap.get(petTypeId);
}
//--------------------------------------------------------------------------------------------------
public PetType findUsingShortDesc(final String shortDesc) {
  return _petTypeByShortDescMap.get(shortDesc);
}
//--------------------------------------------------------------------------------------------------
}