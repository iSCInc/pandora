package com.wikia.discussionservice.enums;

/**
 * ResponseGroup represent the different depth and
 * details of a requested resource. Modeled after
 * Amazon's own Response Groups
 * @see <a href="http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CHAP_ResponseGroupsList.html">
 *        ResponseGroups</a>
 */
public enum ResponseGroup {
  SMALL,
  MEDIUM,
  LARGE,
  FULL;
  
  public static ResponseGroup getResponseGroup(String name) {
    ResponseGroup responseGroup = null;
    if (name.equalsIgnoreCase(SMALL.name())) {
      responseGroup = SMALL;
    } else if (name.equalsIgnoreCase(MEDIUM.name())) {
      responseGroup = MEDIUM;
    } else if (name.equalsIgnoreCase(LARGE.name())) {
      responseGroup = LARGE;
    } else if (name.equalsIgnoreCase(FULL.name())) {
      responseGroup = FULL;
    }
    return responseGroup;
  }
  
  public String toString() {
    return this.name();
  }
}
