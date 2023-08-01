////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class ArrayBean {

  /*
   * primitive types
   */
  private byte[] byteArray;
  private short[] shortArray;
  private int[] intArray;
  private long[] longArray;
  private float[] floatArray;
  private double[] doubleArray;
  private boolean[] booleanArray;
  private char[] charArray;

  /*
   * wrapper types
   */
  private Byte[] bytes;
  private Short[] shorts;
  private Integer[] integers;
  private Long[] longs;
  private Float[] floats;
  private Double[] doubles;
  private Boolean[] booleans;
  private Character[] characters;

  /*
   * custom types
   */
  private String[] strings;
  private Person[] persons;

  public ArrayBean() {
  }

  public byte[] getByteArray() {
    return this.byteArray;
  }

  public short[] getShortArray() {
    return this.shortArray;
  }

  public int[] getIntArray() {
    return this.intArray;
  }

  public long[] getLongArray() {
    return this.longArray;
  }

  public float[] getFloatArray() {
    return this.floatArray;
  }

  public double[] getDoubleArray() {
    return this.doubleArray;
  }

  public boolean[] getBooleanArray() {
    return this.booleanArray;
  }

  public char[] getCharArray() {
    return this.charArray;
  }

  public Byte[] getBytes() {
    return this.bytes;
  }

  public Short[] getShorts() {
    return this.shorts;
  }

  public Integer[] getIntegers() {
    return this.integers;
  }

  public Long[] getLongs() {
    return this.longs;
  }

  public Float[] getFloats() {
    return this.floats;
  }

  public Double[] getDoubles() {
    return this.doubles;
  }

  public Boolean[] getBooleans() {
    return this.booleans;
  }

  public Character[] getCharacters() {
    return this.characters;
  }

  public String[] getStrings() {
    return this.strings;
  }

  public Person[] getPersons() {
    return this.persons;
  }

  public void setByteArray(final byte[] byteArray) {
    this.byteArray = byteArray;
  }

  public void setShortArray(final short[] shortArray) {
    this.shortArray = shortArray;
  }

  public void setIntArray(final int[] intArray) {
    this.intArray = intArray;
  }

  public void setLongArray(final long[] longArray) {
    this.longArray = longArray;
  }

  public void setFloatArray(final float[] floatArray) {
    this.floatArray = floatArray;
  }

  public void setDoubleArray(final double[] doubleArray) {
    this.doubleArray = doubleArray;
  }

  public void setBooleanArray(final boolean[] booleanArray) {
    this.booleanArray = booleanArray;
  }

  public void setCharArray(final char[] charArray) {
    this.charArray = charArray;
  }

  public void setBytes(final Byte[] bytes) {
    this.bytes = bytes;
  }

  public void setShorts(final Short[] shorts) {
    this.shorts = shorts;
  }

  public void setIntegers(final Integer[] integers) {
    this.integers = integers;
  }

  public void setLongs(final Long[] longs) {
    this.longs = longs;
  }

  public void setFloats(final Float[] floats) {
    this.floats = floats;
  }

  public void setDoubles(final Double[] doubles) {
    this.doubles = doubles;
  }

  public void setBooleans(final Boolean[] booleans) {
    this.booleans = booleans;
  }

  public void setCharacters(final Character[] characters) {
    this.characters = characters;
  }

  public void setStrings(final String[] strings) {
    this.strings = strings;
  }

  public void setPersons(final Person[] persons) {
    this.persons = persons;
  }
}
