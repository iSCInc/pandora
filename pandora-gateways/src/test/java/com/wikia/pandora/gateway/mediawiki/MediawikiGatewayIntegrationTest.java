package com.wikia.pandora.gateway.mediawiki;

import com.wikia.mwapi.MWApi;
import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.domain.CategoryStat;
import com.wikia.mwapi.domain.Image;
import com.wikia.mwapi.domain.Page;
import com.wikia.mwapi.domain.Revision;
import com.wikia.mwapi.fluent.WikiaChoose;
import com.wikia.pandora.core.test.IntegrationTest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Category(IntegrationTest.class)
public class MediawikiGatewayIntegrationTest {

  public static final String STARGATE = "stargate";
  MediawikiGateway gateway;

  @Before
  public void setupMediawikiGateway() {
    HttpClient httpClient = HttpClientBuilder.create().build();
    gateway = new MediawikiGateway(httpClient);
  }


  @Test
  public void testQueryBuilder() throws Exception {
    WikiaChoose wikiaChoose = gateway.queryBuilder();
    MWApi mwApi = (MWApi) wikiaChoose;

    assertNotNull(wikiaChoose);
    assertNotNull(mwApi);
  }

  @Test
  public void testGetArticleByTitle() throws Exception {
    ApiResponse apiResponse = gateway.getArticleByTitle(STARGATE, "Levannan musket");
    int pageId = apiResponse.getQuery().getFirstPage().getPageId();
    assertEquals(44966, pageId);
  }

  @Test
  public void testGetArticleWithContentByTitle() throws Exception {
    ApiResponse apiResponse = gateway.getArticleWithContentByTitle(STARGATE, "Neeva Casol");
    String content = apiResponse.getQuery().getFirstPage().getFirstRevision().getContent();
    assertNotNull(content);
  }

  @Test
  public void testGetCategoryByName() throws Exception {
    ApiResponse apiResponse = gateway.getCategoryByName(STARGATE, "One-shot_Atlantis_characters");
    int ns = apiResponse.getQuery().getFirstPage().getNs();
    assertEquals(14, ns);
  }

  @Test
  public void testGetArticlesFromWikia() throws Exception {
    ApiResponse apiResponse = gateway.getArticlesFromWikia(STARGATE);
    int allPagesCount = apiResponse.getQuery().getAllPages().size();
    String apFrom = apiResponse.getQueryContinue().getAllpages().getApfrom();
    assertEquals(10, allPagesCount);
    assertNotNull(apFrom);
  }

  @Test
  public void testGetArticleCategories() throws Exception {
    ApiResponse apiResponse = gateway.getArticleCategories(STARGATE, "merlin");
    List<com.wikia.mwapi.domain.Category>
        categories =
        apiResponse.getQuery().getFirstPage().getCategories();

    String[]
        categoryTitleArray = categories.stream()
        .map(com.wikia.mwapi.domain.Category::getTitle)
        .toArray(String[]::new);

    String[]
        expectedCategoryArray = new String[]{
        "Category:Ancients",
        "Category:Ascended beings",
        "Category:Deceased characters",
        "Category:One-shot Atlantis characters",
        "Category:Recurring SG-1 characters",
        "Category:Scientists"};

    assertArrayEquals(expectedCategoryArray, categoryTitleArray);
  }

  @Test
  public void testGetArticleImages() throws Exception {
    ApiResponse apiResponse = gateway.getArticleImages(STARGATE, "merlin");
    List<Image> images = apiResponse.getQuery().getFirstPage().getImages();

    String[] imagesArray = images.stream()
        .map(Image::getTitle)
        .toArray(String[]::new);

    String[] expectedImagesArray = new String[]{
        "File:Gate Logo.png",
        "File:Merlin'sRepository.jpg",
        "File:Merlin.jpg",
        "File:Merlinstasis.jpg",
        "File:Moros.jpg"};

    assertArrayEquals(expectedImagesArray, imagesArray);
  }

  @Test
  public void testGetArticleRevisions() throws Exception {
    ApiResponse apiResponse = gateway.getArticleRevisions(STARGATE, "Culling");
    int revisionCount = apiResponse.getQuery().getFirstPage().getRevisions().size();
    assertEquals(10, revisionCount);
  }

  @Test
  public void testGetArticleContributors() throws Exception {
    ApiResponse apiResponse = gateway.getArticleContributors(STARGATE, "Culling");
    String warningMessage = apiResponse.getWarnings().getQuery().getMessage();
    //not supported at the moment. Needs Mediawiki 1.23. Currently 1.19
    assertEquals("Unrecognized value for parameter 'prop': contributors", warningMessage);
  }

  @Test
  public void testGetCategoryArticles() throws Exception {
    ApiResponse apiResponse = gateway.getCategoryArticles(STARGATE, "Scientists", 5, "");
    List<Page> categoryMembers = apiResponse.getQuery().getCategorymembers();
    String[] categoryMembersTitles = categoryMembers.stream()
        .map(Page::getTitle)
        .toArray(String[]::new);
    String[] expectedCategoryMembers = new String[]{
        "Scientist",
        "Abrams",
        "Amelius",
        "Amuro",
        "An"
    };

    assertArrayEquals(expectedCategoryMembers, categoryMembersTitles);
  }

  @Test
  public void testGetCategoriesFromWikia() throws Exception {
    ApiResponse apiResponse = gateway.getCategoriesFromWikia(STARGATE, 5, "");
    List<CategoryStat> categoryList = apiResponse.getQuery().getAllCategories();
    String[] categoryArray = categoryList.stream()
        .map(CategoryStat::getCategoryName)
        .toArray(String[]::new);
    String[] expectedArray = new String[]
        {
            "\"Indeed\"",
            "*",
            "11",
            "1979 births",
            "302 images"
        };

    assertArrayEquals(expectedArray, categoryArray);
  }

  @Test
  public void testGetRevisionById() throws Exception {
    ApiResponse apiResponse = gateway.getRevisionById(STARGATE, 242260L);
    Revision revision = apiResponse.getQuery().getFirstPage().getFirstRevision();
    assertEquals("Colonelsam", revision.getUser());
    assertEquals(234819, revision.getParentId());
    assertEquals(4342, apiResponse.getQuery().getFirstPage().getLength());
    assertNull(revision.getContent());
  }

  @Test
  public void testGetRevisionByIdWithContent() throws Exception {
    ApiResponse apiResponse = gateway.getRevisionByIdWithContent(STARGATE, 273440L);
    String content = apiResponse.getQuery().getFirstPage().getFirstRevision().getContent();
    String expectedContent =
        "{{Infobox Character\n" +
        "|name=Ryder\n" +
        "|image=\n" +
        "|race=[[Tau'ri]]\n" +
        "|home planet=[[Earth]]\n" +
        "|gender=\n" +
        "|born=\n" +
        "|died=\n" +
        "|rank=[[Specialist]]\n" +
        "|allegiances=*[[Stargate Command]]\n" +
        "**[[SG-19]]\n" +
        "|hideo=yes\n" +
        "|appearances=\n" +
        "|actor=\n" +
        "}}\n" +
        "'''Ryder''' was a [[Covert operations]] [[Specialist]] assigned to [[SG-19]] during the time that there were only twenty [[SG team]]s. {{Cite|RPG|Roleplaying Game}}\n"
        +
        "\n" +
        "{{char-stub}}\n" +
        "[[Category:Americans]]\n" +
        "[[Category:Mentioned-only SG-1 characters]]\n" +
        "[[Category:SG-19 members]]\n" +
        "[[Category:Stargate SG-1 RPG individuals]]";

    assertEquals(expectedContent, content);
  }
}
