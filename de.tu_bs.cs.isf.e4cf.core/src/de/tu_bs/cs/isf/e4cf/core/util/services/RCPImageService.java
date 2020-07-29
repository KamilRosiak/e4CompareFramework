package de.tu_bs.cs.isf.e4cf.core.util.services;

import static de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable.DEFAULT_BUNDLE_NAME;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
/**
 * This service returns images from a iamgeDescriptor and caching them in a map. When a component needs an image this service looks in his map
 * if the image exists it will be returned else its creates it an returning them after. 
 * @author {Kamil Rosiak}
 */
@Creatable 
@Singleton
public class RCPImageService {
	private Map<ImageDescriptor, Image> _imageCache; 
	
	public RCPImageService() {
		_imageCache = new HashMap<ImageDescriptor, Image>();
	}
	
	/**
	 * Loads an image and returns its descriptor. If the bundleName is null, 
	 * the default bundle <i>"de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4"</i>
	 * will be used. If no bundle or image was found, null will be returned.
	 * 
	 * @param bundleName the name of the bundle
	 * @param imagePath path relative to the bundle folder
	 * @param imageFileName the image name
	 * @return the image descriptor specified by the arguments
	 */
	public ImageDescriptor getImageDescriptor(String bundleName, String imagePath) {
		if (bundleName == null) {
			bundleName = new String(DEFAULT_BUNDLE_NAME);
		}
		Bundle bundle = Platform.getBundle(bundleName);
		URL url = bundle.getEntry(imagePath);
		ImageDescriptor descritor = ImageDescriptor.createFromURL(url);
		addImage(descritor);
		return descritor;
	}
	
	public String getURL(String bundleName, String imagePath) {
		Bundle bundle = Platform.getBundle(bundleName);
		URL url = bundle.getEntry(imagePath);
		return url.toString();
	}
	
	public void addImage(ImageDescriptor descritor) {
		if(!imageExists(descritor)) {
			_imageCache.put(descritor, descritor.createImage());
		};
	}
	public boolean imageExists(ImageDescriptor descriptor) {
		if(_imageCache.containsKey(descriptor)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns a image from his description it uses {@link getImageDescriptor()}.
	 * @param bundleName the bundle name of the image.
	 * @param imagePath the path within the bundle.
	 * @param imageFileName the name of the image with extension.
	 * @return
	 */
	public Image getImage(String bundleName, String imagePath) {
		ImageDescriptor descriptor = getImageDescriptor(bundleName, imagePath);
		if(imageExists(descriptor)) {
			return _imageCache.get(descriptor);
		} else {
			addImage(descriptor);
		}
			return _imageCache.get(descriptor);
	}
	
	/**
	 * This method resizes an image to given width and height.
	 */
	public Image resizeImage(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose();
		return scaled;
	}

}
