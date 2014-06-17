package org.greatlogic.gxttestbed.client;

import com.sencha.gxt.data.shared.ModelKeyProvider;

public class PetType {
//--------------------------------------------------------------------------------------------------
private final String _petTypeDesc;
private final int    _petTypeId;
private final String _petTypeShortDesc;
//--------------------------------------------------------------------------------------------------
public static ModelKeyProvider<? super PetType> getModelKeyProvider() {
  return new ModelKeyProvider<PetType>() {
    @Override
    public String getKey(final PetType petType) {
      final int petTypeId = petType == null ? 0 : petType.getPetTypeId();
      return Integer.toString(petTypeId);
    }
  };
}
//--------------------------------------------------------------------------------------------------
public PetType(final int petTypeId, final String petTypeDesc, final String petTypeShortDesc) {
  _petTypeId = petTypeId;
  _petTypeDesc = petTypeDesc;
  _petTypeShortDesc = petTypeShortDesc;
}
//--------------------------------------------------------------------------------------------------
public String getPetTypeDesc() {
  return _petTypeDesc;
}
//--------------------------------------------------------------------------------------------------
public int getPetTypeId() {
  return _petTypeId;
}
//--------------------------------------------------------------------------------------------------
public String getPetTypeShortDesc() {
  return _petTypeShortDesc;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "Id:" + _petTypeId + " Desc:" + _petTypeDesc + " ShortDesc:" + _petTypeShortDesc;
}
//--------------------------------------------------------------------------------------------------
}