package pl.tware.cv.ui.component;

import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@StyleSheet("sticker-button.css")
public class StickerButton extends Panel {

	private static final long serialVersionUID = 1L;
	private final String windowCaption;

	public StickerButton(Resource image) {
		this(image, null, null);
	}

	public StickerButton(Resource image, String windowCaption, final Supplier<Component> windowContentComponent) {
		this.windowCaption = windowCaption;
		addStyleName("sticker-button");

		Button button = new Button();
		button.removeStyleName(".v-button");
		button.setIcon(image);

		if (windowContentComponent != null) {
			button.addClickListener(new ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if (closeIfAlreadyOpened()) {
						return;
					}
					Window window = new Window();
					window.setCaption(StickerButton.this.windowCaption);
					window.setResizable(false);
					window.setContent(windowContentComponent.get());
					placeWindow(window);
				}
			});
		}

		setContent(button);

		setWidth(220, Unit.PIXELS);
		setHeight(120, Unit.PIXELS);
	}

	private boolean closeIfAlreadyOpened() {
		Optional<Window> window = Iterables.tryFind(UI.getCurrent().getWindows(), new Predicate<Window>() {

			@Override
			public boolean apply(Window input) {
				return input.getCaption().equals(windowCaption);
			}
		});
		if (window.isPresent()) {
			UI.getCurrent().removeWindow(window.get());
			return true;
		} else {
			return false;
		}
	}

	private void placeWindow(Window window) {
		final int startX = 50;
		final int startY = 200;
		final int addX = 100;
		final int addY = 5;
		Collection<Window> currentWindows = UI.getCurrent().getWindows();
		for (Integer i : ContiguousSet.create(Range.closed(0, 9), DiscreteDomain.integers())) {
			final int currentX = startX + addX * i;
			final int currentY = startY + addY * i;
			Optional<Window> conflictingWindow = Iterables.tryFind(currentWindows, new Predicate<Window>() {

				@Override
				public boolean apply(Window input) {
					return input.getPositionX() == currentX && input.getPositionY() == currentY;
				}
			});
			if (!conflictingWindow.isPresent()) {
				window.setPositionX(currentX);
				window.setPositionY(currentY);
				UI.getCurrent().addWindow(window);
				return;
			}
		}
		Preconditions.checkState(false, "Method doesn't support more than 10 active windows");
	}

}
