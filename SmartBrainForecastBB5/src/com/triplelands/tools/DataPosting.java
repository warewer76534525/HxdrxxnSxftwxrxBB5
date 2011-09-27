package com.triplelands.tools;

import java.io.InputStream;

import net.rim.device.api.system.Characters;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.triplelands.utils.AppStatusManager;
import com.triplelands.view.component.AnimatedGIFField;

public class DataPosting extends PopupScreen implements InternetConnectionListener {

	private PostConnectionThread connectionThread;

	public DataPosting(String url, String param) {
		super(new VerticalFieldManager(), Field.FOCUSABLE | Field.FIELD_HCENTER);
		AppStatusManager manager = AppStatusManager.GetInstance();
//		manager.addScreen(this);
		
		add(new AnimatedGIFField((GIFEncodedImage) GIFEncodedImage.getEncodedImageResource("loading.gif")));
		connectionThread = new PostConnectionThread(url, param, this);
		connectionThread.start();
	}

	public boolean keyChar(char key, int status, int time) {
		if (key == Characters.ESCAPE) {
			try {
				connectionThread.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close();
		}
		return super.keyChar(key, status, time);
	}

	public boolean onClose() {
		if (connectionThread.isAlive()) {
			try {
				connectionThread.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.onClose();
	}

	public void onReceiveResponseEvent(InputStream is) {
		System.out.println("Post Response received");
		close();
	}

	public void onErrorOccurEvent(Exception e) {
//		UiApplication.getUiApplication().invokeLater(new Runnable() {
//			public void run() {
//				Dialog.alert("Connection Error. Please check your internet connection.");
//				close();
//			}
//		});
	}

	public void onCancelEvent() {
	}

	public void onStartEvent(long length, String type) {
	}

	public void onNotFound() {
//		UiApplication.getUiApplication().invokeLater(new Runnable() {
//			public void run() {
//				Dialog.alert("Data not found.");
//				close();
//			}
//		});
	}
}