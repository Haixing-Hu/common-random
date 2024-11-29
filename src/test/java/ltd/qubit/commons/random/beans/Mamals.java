////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.List;

public class Mamals {

  private Mammal mamal;
  private MammalImpl mamalImpl;
  private List<Mammal> mamalList;
  private List<MammalImpl> mamalImplList;

  public Mammal getMamal() {
    return mamal;
  }

  public void setMamal(final Mammal mamal) {
    this.mamal = mamal;
  }

  public MammalImpl getMamalImpl() {
    return mamalImpl;
  }

  public void setMamalImpl(final MammalImpl mamalImpl) {
    this.mamalImpl = mamalImpl;
  }

  public List<Mammal> getMamalList() {
    return mamalList;
  }

  public void setMamalList(final List<Mammal> mamalList) {
    this.mamalList = mamalList;
  }

  public List<MammalImpl> getMamalImplList() {
    return mamalImplList;
  }

  public void setMamalImplList(final List<MammalImpl> mamalImplList) {
    this.mamalImplList = mamalImplList;
  }
}
