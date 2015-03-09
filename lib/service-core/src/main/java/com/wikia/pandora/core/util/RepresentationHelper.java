package com.wikia.pandora.core.util;

import com.theoryinpractise.halbuilder.api.Representation;

public class RepresentationHelper {

  public static void withLinkAndTitle(Representation representation, String rel,
                                      String href, String title) {
    representation.withLink(rel,
                            href,
                            null,
                            title,
                            null,
                            null);
  }
}
