package io.springbatch.springbatchlecture;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomItemWriteListener implements ItemWriteListener {

	@Override
	public void beforeWrite(List items) {
		System.out.println(">> beforeRead");
	}

	@Override
	public void afterWrite(List items) {
		System.out.println(">> afterRead : "+ items);
	}

	@Override
	public void onWriteError(Exception exception, List items) {
		System.out.println(">> onReadError : " + exception.getMessage());
		System.out.println(">> onReadError : " + items);
	}
}