package org.github.fscheffer.arras.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LightboxIT extends ArrasTestCase {

    @BeforeMethod
    void before() {
        open("/LightboxDemo");
    }

    @Test
    void testBasic() {

        openLightbox(By.linkText("Bridge over river"));

        assertUrlAttribute("#cboxLoadedContent > img", "src",
            "/arras/assets/meta/b9626ecd/photos/landscape/bridge-over-river.jpg");

        closeLightbox();
    }

    @Test
    void testGroup() {

        // group example
        openLightbox(By.linkText("Rocky cliffs"));

        assertUrlAttribute("#cboxLoadedContent > img", "src",
            "/arras/assets/meta/d45fddff/photos/landscape/pointarena_rockycliffs.jpg");

        click(By.cssSelector("#cboxLoadedContent > img"));

        assertUrlAttribute("#cboxLoadedContent > img", "src",
            "/arras/assets/meta/3aad432/photos/landscape/san-joaquin-river-view.jpg");

        click(By.cssSelector("#cboxLoadedContent > img"));

        assertUrlAttribute("#cboxLoadedContent > img", "src",
            "/arras/assets/meta/dedf595e/photos/landscape/man_point-arena-stornetta.jpg");

        click(By.cssSelector("#cboxLoadedContent > img"));

        assertUrlAttribute("#cboxLoadedContent > img", "src",
            "/arras/assets/meta/d45fddff/photos/landscape/pointarena_rockycliffs.jpg");

        closeLightbox();
    }

    @Test
    void testFixedSize() {

        // fixed size
        openLightbox(By.linkText("San Joaquin River (Fixed size)"));

        WebElement wrapper = element(By.cssSelector("#cboxWrapper"));

        Assert.assertEquals(wrapper.getCssValue("height"), "700px");
        Assert.assertEquals(wrapper.getCssValue("width"), "500px");

        closeLightbox();
    }

    @Test
    void testInlined() {

        // inline
        openLightbox(By.linkText("Show Kafka!"));

        assertTextPresent(By.cssSelector("#cboxLoadedContent h3"), "Kafka");

        closeLightbox();
    }

    @Test
    void testZoneUpdate() {

        openLightbox(By.linkText("Some ajax event"));

        assertTextPresent(By.cssSelector("#cboxLoadedContent h3"), "Updated zone at");

        closeLightbox();
    }

    private void openLightbox(By by) {

        click(by);

        waitUntilVisible(By.cssSelector("#cboxLoadedContent"));
    }

    private void closeLightbox() {

        click(By.cssSelector("#cboxClose"));

        waitUntilInvisible(By.cssSelector("#cboxOverlay"));
    }

    void assertUrlAttribute(String cssSelector, String attribute, String expected) {

        String attr = attr(By.cssSelector(cssSelector), attribute);
        // ignore base url
        Assert.assertTrue(attr.endsWith(expected));
    }

}