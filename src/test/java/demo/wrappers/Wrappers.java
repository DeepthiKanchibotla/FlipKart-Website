package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.Advice.Enter;

import java.sql.Wrapper;
import java.time.Duration;

public class Wrappers {
   /*
    * Write your selenium wrappers here
    */

   static RemoteWebDriver driver;


   public  Wrappers(ChromeDriver driver){
      this.driver=driver;
   }

   public static void search(String text) throws InterruptedException {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
      wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Search for Products')]")));
      WebElement searchBox = driver.findElement(By.xpath("//input[contains(@placeholder,'Search for Products')]"));
     
      searchBox.click();
      searchBox.clear();
      searchBox.sendKeys(text);
      //button[contains(@title,'Search for Products')]
      WebElement searchIcon= driver.findElement(By.xpath("//button[@type='submit']"));
      Thread.sleep(2000);
      searchIcon.click();
      Thread.sleep(2000);
   }

   public static void discountTitles() {

      List<WebElement> offerList = driver.findElements(By.xpath("//div[contains(@class,'UkUFwK')]/span"));

      for (WebElement offer : offerList) {

         String offerTextString = offer.getText().replace("% off", "").trim();

         int offerValue = Integer.parseInt(offerTextString);

         if (offerValue > 17) {
            WebElement displayText = driver.findElement(By.xpath("//div[contains(@class,'KzDlHZ')]"));
            System.out.println("Title: " + displayText);

            System.out.println("Percentage value: " + offerValue);

         }

      }

   }

   public static void countofItems() {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'productRating')]/div")));
      int count = 0;

      List<WebElement> ratings = driver.findElements(By.xpath("//span[contains(@id,'productRating')]/div"));

      for (WebElement rating : ratings) {

         String ratingText = rating.getText();

         double ratingValue = Double.parseDouble(ratingText);

         if (ratingValue <= 4) {
            count++;
         }
      }
         System.out.println("Count of Washing Machine products with less than 4 rating :" + count);
  

   }

   public static void closeLoginPopUP() {
      try {
         WebElement crossButton = driver.findElement(By.xpath("//span[@role='button']"));
         // verifying cross button is visible or not
         if (crossButton.isDisplayed() || crossButton.isEnabled()) {
            System.out.println("Closing logging popup");
            crossButton.click();
         }
      } catch (Exception e) {
         System.out.println(e.getStackTrace());
      }
   }

   public static void top5CoffeeMug() {

      // Variables to store top 5 items' data
      String[] topTitles = new String[5];
      String[] topImageUrls = new String[5];
      int[] topReviews = new int[5];

      // Collect all items on the first page
      List<WebElement> items = driver.findElements(By.xpath("//div[@class='slAVV4']"));

      for (WebElement item : items) {
         try {
            // Get title, image URL, and reviews
            String title = item.findElement(By.xpath("//a[@class='wjcEIp']")).getText();
            String imageUrl = item.findElement((By.xpath("//img[@class='DByuf4']"))).getAttribute("src");
            String reviewsText = item.findElement(By.xpath("//span[@class='Wphh3N']")).getText();

            // Extract number of reviews (first number in the text)
            int numberOfReviews = Integer.parseInt(reviewsText.replace("(", "").replace(")", "").replace(",", ""));
            // Check if this item is among the top 5 by comparing reviews
            for (int i = 0; i < 5; i++) {
               if (topReviews[i] < numberOfReviews) {
                  // Shift lower-ranked items down
                  for (int j = 4; j > i; j--) {
                     topReviews[j] = topReviews[j - 1];
                     topTitles[j] = topTitles[j - 1];
                     topImageUrls[j] = topImageUrls[j - 1];
                  }
                  // Insert the current item in the correct position
                  topReviews[i] = numberOfReviews;
                  topTitles[i] = title;
                  topImageUrls[i] = imageUrl;
                  break;
               }
            }
            // Print the top 5 items
            System.out.println("Top 5 items with the highest number of reviews:");
            for (int i = 0; i < 5; i++) {
               if (topTitles[i] != null) {
                  System.out.println("Title: " + topTitles[i]);
                  System.out.println("Image URL: " + topImageUrls[i]);
                  System.out.println("Number of Reviews: " + topReviews[i]);
                  System.out.println("---------");
               }

            }
         } catch (Exception e) {
            System.out.println("Exception occured while returing top 5 elements");

         }
      }
   }
}
