/**
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.openapitools.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.client.model.OuterEnum;
import org.openapitools.client.model.OuterEnumDefaultValue;
import org.openapitools.client.model.OuterEnumInteger;
import org.openapitools.client.model.OuterEnumIntegerDefaultValue;
import org.openapitools.jackson.nullable.JsonNullable;




public class EnumTest  {
  
public enum EnumStringEnum {

    UPPER(String.valueOf("UPPER")), LOWER(String.valueOf("lower")), EMPTY(String.valueOf(""));

    String value;

    EnumStringEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

  private EnumStringEnum enumString;

public enum EnumStringRequiredEnum {

    UPPER(String.valueOf("UPPER")), LOWER(String.valueOf("lower")), EMPTY(String.valueOf(""));

    String value;

    EnumStringRequiredEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

  private EnumStringRequiredEnum enumStringRequired;

public enum EnumIntegerEnum {

    NUMBER_1(Integer.valueOf(1)), NUMBER_MINUS_1(Integer.valueOf(-1));

    Integer value;

    EnumIntegerEnum (Integer v) {
        value = v;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

  private EnumIntegerEnum enumInteger;

public enum EnumNumberEnum {

    NUMBER_1_DOT_1(Double.valueOf(1.1)), NUMBER_MINUS_1_DOT_2(Double.valueOf(-1.2));

    Double value;

    EnumNumberEnum (Double v) {
        value = v;
    }

    public Double value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

  private EnumNumberEnum enumNumber;

  private OuterEnum outerEnum;

  private OuterEnumInteger outerEnumInteger;

  private OuterEnumDefaultValue outerEnumDefaultValue = OuterEnumDefaultValue.PLACED;

  private OuterEnumIntegerDefaultValue outerEnumIntegerDefaultValue = OuterEnumIntegerDefaultValue.NUMBER_0;

 /**
   * Get enumString
   * @return enumString
  **/
  public EnumStringEnum getEnumString() {
    return enumString;
  }

  /**
    * Set enumString
  **/
  public void setEnumString(EnumStringEnum enumString) {
    this.enumString = enumString;
  }

  public EnumTest enumString(EnumStringEnum enumString) {
    this.enumString = enumString;
    return this;
  }

 /**
   * Get enumStringRequired
   * @return enumStringRequired
  **/
  public EnumStringRequiredEnum getEnumStringRequired() {
    return enumStringRequired;
  }

  /**
    * Set enumStringRequired
  **/
  public void setEnumStringRequired(EnumStringRequiredEnum enumStringRequired) {
    this.enumStringRequired = enumStringRequired;
  }

  public EnumTest enumStringRequired(EnumStringRequiredEnum enumStringRequired) {
    this.enumStringRequired = enumStringRequired;
    return this;
  }

 /**
   * Get enumInteger
   * @return enumInteger
  **/
  public EnumIntegerEnum getEnumInteger() {
    return enumInteger;
  }

  /**
    * Set enumInteger
  **/
  public void setEnumInteger(EnumIntegerEnum enumInteger) {
    this.enumInteger = enumInteger;
  }

  public EnumTest enumInteger(EnumIntegerEnum enumInteger) {
    this.enumInteger = enumInteger;
    return this;
  }

 /**
   * Get enumNumber
   * @return enumNumber
  **/
  public EnumNumberEnum getEnumNumber() {
    return enumNumber;
  }

  /**
    * Set enumNumber
  **/
  public void setEnumNumber(EnumNumberEnum enumNumber) {
    this.enumNumber = enumNumber;
  }

  public EnumTest enumNumber(EnumNumberEnum enumNumber) {
    this.enumNumber = enumNumber;
    return this;
  }

 /**
   * Get outerEnum
   * @return outerEnum
  **/
  public OuterEnum getOuterEnum() {
    return outerEnum;
  }

  /**
    * Set outerEnum
  **/
  public void setOuterEnum(OuterEnum outerEnum) {
    this.outerEnum = outerEnum;
  }

  public EnumTest outerEnum(OuterEnum outerEnum) {
    this.outerEnum = outerEnum;
    return this;
  }

 /**
   * Get outerEnumInteger
   * @return outerEnumInteger
  **/
  public OuterEnumInteger getOuterEnumInteger() {
    return outerEnumInteger;
  }

  /**
    * Set outerEnumInteger
  **/
  public void setOuterEnumInteger(OuterEnumInteger outerEnumInteger) {
    this.outerEnumInteger = outerEnumInteger;
  }

  public EnumTest outerEnumInteger(OuterEnumInteger outerEnumInteger) {
    this.outerEnumInteger = outerEnumInteger;
    return this;
  }

 /**
   * Get outerEnumDefaultValue
   * @return outerEnumDefaultValue
  **/
  public OuterEnumDefaultValue getOuterEnumDefaultValue() {
    return outerEnumDefaultValue;
  }

  /**
    * Set outerEnumDefaultValue
  **/
  public void setOuterEnumDefaultValue(OuterEnumDefaultValue outerEnumDefaultValue) {
    this.outerEnumDefaultValue = outerEnumDefaultValue;
  }

  public EnumTest outerEnumDefaultValue(OuterEnumDefaultValue outerEnumDefaultValue) {
    this.outerEnumDefaultValue = outerEnumDefaultValue;
    return this;
  }

 /**
   * Get outerEnumIntegerDefaultValue
   * @return outerEnumIntegerDefaultValue
  **/
  public OuterEnumIntegerDefaultValue getOuterEnumIntegerDefaultValue() {
    return outerEnumIntegerDefaultValue;
  }

  /**
    * Set outerEnumIntegerDefaultValue
  **/
  public void setOuterEnumIntegerDefaultValue(OuterEnumIntegerDefaultValue outerEnumIntegerDefaultValue) {
    this.outerEnumIntegerDefaultValue = outerEnumIntegerDefaultValue;
  }

  public EnumTest outerEnumIntegerDefaultValue(OuterEnumIntegerDefaultValue outerEnumIntegerDefaultValue) {
    this.outerEnumIntegerDefaultValue = outerEnumIntegerDefaultValue;
    return this;
  }


  /**
    * Create a string representation of this pojo.
  **/
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EnumTest {\n");
    
    sb.append("    enumString: ").append(toIndentedString(enumString)).append("\n");
    sb.append("    enumStringRequired: ").append(toIndentedString(enumStringRequired)).append("\n");
    sb.append("    enumInteger: ").append(toIndentedString(enumInteger)).append("\n");
    sb.append("    enumNumber: ").append(toIndentedString(enumNumber)).append("\n");
    sb.append("    outerEnum: ").append(toIndentedString(outerEnum)).append("\n");
    sb.append("    outerEnumInteger: ").append(toIndentedString(outerEnumInteger)).append("\n");
    sb.append("    outerEnumDefaultValue: ").append(toIndentedString(outerEnumDefaultValue)).append("\n");
    sb.append("    outerEnumIntegerDefaultValue: ").append(toIndentedString(outerEnumIntegerDefaultValue)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

