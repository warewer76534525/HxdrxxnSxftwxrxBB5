package com.triplelands.utils;

import net.rim.blackberry.api.homescreen.HomeScreen;
import net.rim.blackberry.api.messagelist.ApplicationIcon;
import net.rim.blackberry.api.messagelist.ApplicationIndicator;
import net.rim.blackberry.api.messagelist.ApplicationIndicatorRegistry;
import net.rim.device.api.system.Alert;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.LED;

public class NotificationUtils {
	
	private static void updateIndicator(int value) {
		ApplicationIndicatorRegistry indicatorRegistry = ApplicationIndicatorRegistry.getInstance();
		ApplicationIndicator indicator = indicatorRegistry.getApplicationIndicator();
		if (indicator == null) {
			ApplicationIcon icon = new ApplicationIcon(EncodedImage.getEncodedImageResource("notification.png"));
			indicator = indicatorRegistry.register(icon, true, true);
		}

		indicator.setValue(value);
		if (value > 0) {
			indicator.setVisible(true);
			Alert.startVibrate(1000);
			LED.setConfiguration( 250, 1500, LED.BRIGHTNESS_100 );
            LED.setState( LED.STATE_BLINKING );
		} else {
			indicator.setVisible(false);
		}
	}
	
	public static void clearNotification(){
		try {
			LED.setState(LED.STATE_OFF);
			ApplicationIndicatorRegistry reg = ApplicationIndicatorRegistry.getInstance();
			reg.unregister();
		} catch (Exception e) {
			System.out.println("Gagal unregister");
			System.out.println(e.getMessage());
		} catch (Throwable e) {
			System.out.println("Gagal unregister");
			System.out.println(e.getMessage());
		}
	}
	
	public static void notify(int id, String detail) {	
		updateIndicator(id);
        updateAppIcon("icon_asteriks.png");
	}
	
	public static void updateAppIcon(String iconName){
		Bitmap bmp = Bitmap.getBitmapResource(iconName);
        HomeScreen.updateIcon(bmp);
	}
}
