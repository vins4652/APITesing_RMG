package com.GenericUtility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	/**
	 * This method will give the Random number from 1-999
	 * 
	 * @return int randomNum
	 * @author Vinaykumar mannur
	 */
	public int getRandomNum() {
		Random random = new Random();
		return random.nextInt(999);
	}

	/**
	 * This method will give the current date and time of the device in use
	 * 
	 * @return String dateAndTime in (Thu Mar 16 15:33:36 IST 2023)
	 */
	public String getSystemDateTime() {
		Date date = new Date();
		return date.toString();
	}

	/**
	 * this method will give the requred format of current date and time G Era
	 * designator (before christ, after christ) y Year (e.g. 12 or 2012). Use either
	 * yy or yyyy. M Month in year. Number of M's determine length of format (e.g.
	 * MM, MMM or MMMMM) d Day in month. Number of d's determine length of format
	 * (e.g. d or dd) h Hour of day, 1-12 (AM / PM) (normally hh) H Hour of day,
	 * 0-23 (normally HH) m Minute in hour, 0-59 (normally mm) s Second in minute,
	 * 0-59 (normally ss) S Millisecond in second, 0-999 (normally SSS) E Day in
	 * week (e.g Monday, Tuesday etc.) D Day in year (1-366) F Day of week in month
	 * (e.g. 1st Thursday of December) w Week in year (1-53) W Week in month (0-5) a
	 * AM / PM marker k Hour in day (1-24, unlike HH's 0-23) K Hour in day, AM / PM
	 * (0-11) z Time Zone ' Escape for text delimiter ' Single quote
	 * 
	 * @param pattern
	 * @return String DateTime in given pattern
	 * @author Vinaykumar Mannur
	 */
	public String formatSystemdate(String pattern) {
		SimpleDateFormat sd = new SimpleDateFormat(pattern);
		Date date = new Date();
		return sd.format(date);

	}

	/**
	 * This method will zoom out the web page one time
	 * 
	 * @throws AWTException
	 */
	public void zoomOutTheWebpage() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_MINUS);
		robot.keyRelease(KeyEvent.VK_MINUS);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * This method will zoom in the web page one time
	 * 
	 * @throws AWTException
	 */
	public void zoomInTheWebpage() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ADD);
		robot.keyRelease(KeyEvent.VK_ADD);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * This message will check for the broken link passed to it and returns the message given by the site
	 * @param link 
	 * @return status of the link 
	 * @author Vinaykumar Mannur
	 */
	public String checkTheLink(String link) {

		// check if the <a href="">
		if (link != null) {
			// check if the <a href="www.fb.com">
			// check for missing protocol(http) in link
			if (link.contains("http")) {
				try {

					URL url = new URL(link);
					URLConnection urlCon = url.openConnection();
					HttpURLConnection httpUrlCon = (HttpURLConnection) urlCon;
					int respCode = httpUrlCon.getResponseCode();
					String respMsg = httpUrlCon.getResponseMessage();
					if (respCode >= 400) {
						return link + " -->" +respCode+respMsg;
					}else {
						return link+" --> "+respCode + respMsg + " --> Link is valid And Checked for connection";
					}
				} catch (Exception e) {
					return link+" --> link is not connected to the server";
				}
			}else {
				return link+" -->Link doesn't have proper protocol";
			}
		}else {
			return link+" -->Given Link is Null";
		}
		
	}

}
