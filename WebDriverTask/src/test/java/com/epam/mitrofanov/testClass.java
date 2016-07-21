package com.epam.mitrofanov;



import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class testClass
{
   //initializing driver for FireFox browser
   private WebDriver driver;
   //initializing wait
   private WebDriverWait wait;

   //message - text body for mail test
   //cities - info about vacancies in every city
   private static String message = "";
   private ArrayList<String> cities = new ArrayList<String>();

   //fill array with cities names
   public static ArrayList<String> fillCities(ArrayList<String> cities)
   {
      cities.add("Минск");
      cities.add("Гродно");
      cities.add("Гомель");
      cities.add("Витебск");
      cities.add("Могилев");
      cities.add("Брест");
      return cities;
   }


    public void openGoogleSelectEpamNavigateToVacancions()
   {
      driver = new FirefoxDriver();
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driver.manage().window().maximize();
      driver.get("https://google.com");
      driver.findElement(By.xpath("//input[@id='lst-ib']")).sendKeys("epam");
      driver.findElement(By.xpath("//input[contains(@value, 'Google')]")).click();
      driver.findElement(By.xpath("//div[@class='g'][1]//h3//a")).click();
      Actions action = new Actions(driver);
      action.moveToElement(driver.findElement(By.xpath("//a[text()='Карьера'][@class='node']")));
      action.perform();
      driver.findElement(By.xpath("//a[@class='node'][text()='Карьера']/..//a[text()='Вакансии']")).click();

      cities = fillCities(cities);

      for(int i = 0; i < cities.size(); i++) {
         message += cities.get(i);
         message += driver.findElement(By.xpath("//div[@class='parbase vacancies-summary section']//a[text()='"+ cities.get(i) +"']/../span")).getText() + "\n";
      }

      driver.findElement(By.xpath("//div[text()='По городам']/..//a[text()='Минск']")).click();
      List<WebElement> elements = driver.findElements(By.xpath("//ul[@class='vacancies']/li/a[contains(text(), 'Senior')]"));
      if(elements.size() != 0)
      {
         elements.get(0).click();
      }
      else
      {
         driver.findElement(By.xpath("//ul[@class='vacancies']/li[1]/a")).click();
      }
      driver.close();
   }


   public void enterGmailAndSentMail()
   {
      driver = new FirefoxDriver();
      wait = new WebDriverWait(this.driver, 10);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driver.manage().window().maximize();
      driver.get("https://gmail.com");
      driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("testmitrofan@gmail.com");
      driver.findElement(By.xpath("//input[@id='next']")).click();
      driver.findElement(By.xpath("//input[@id='Passwd']")).sendKeys("1testmitrofan");
      driver.findElement(By.xpath("//input[@id='signIn']")).click();
      driver.findElement(By.xpath("//div[text()='COMPOSE']")).click();
      driver.findElement(By.xpath("//textarea[@aria-label='To']")).sendKeys("testmitrofan@gmail.com");
      driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(message);
      driver.findElement(By.xpath("//div[text()='Send']")).click();
      driver.findElement(By.xpath("//a[contains(text(),'Inbox')]")).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='zA zE'][1]")));
      Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='zA zE'][1]")).isDisplayed());
   }

   @Test
   public void testAll()
   {
      openGoogleSelectEpamNavigateToVacancions();
      enterGmailAndSentMail();
   }

}
