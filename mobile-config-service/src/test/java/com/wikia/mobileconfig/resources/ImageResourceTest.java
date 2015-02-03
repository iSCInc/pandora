package com.wikia.mobileconfig.resources;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.wikia.mobileconfig.service.ImageService;

import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link com.wikia.mobileconfig.resources.MobileConfigResource}.
 */
public class ImageResourceTest {

  private static final ImageService imageServiceMock = mock(
      ImageService.class
  );

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ImageResource(imageServiceMock)).build();

  @Test
  public void getMobileImage_notFound() throws IOException {
    when(imageServiceMock.getImage("NonExistentImage.png"))
        .thenReturn(null);

    try {
      resources.client()
        .resource("/images/NonExistentImage.png")
        .get(String.class);
      fail("Did not get expected exception when requesting invalid resource");
    } catch (Exception e) {
      assert(e instanceof UniformInterfaceException);
    }

    verify(imageServiceMock).getImage("NonExistentImage.png");
    reset(imageServiceMock);
  }

  @Test
  public void getMobileApplicationConfigSuccess() throws IOException {
    byte[] imgResponse = new byte[] {1};
    when(imageServiceMock.getImage("TestImage.png"))
        .thenReturn(imgResponse);

    byte[] response = resources.client()
      .resource("/images/TestImage.png")
      .get(byte[].class);

    assertThat(response).isEqualTo(imgResponse);

    verify(imageServiceMock).getImage("TestImage.png");
    reset(imageServiceMock);
  }
}
