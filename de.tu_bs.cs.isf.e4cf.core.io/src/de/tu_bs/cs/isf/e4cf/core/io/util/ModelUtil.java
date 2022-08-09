package de.tu_bs.cs.isf.e4cf.core.io.util;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;



/**
 * Utility class for accessing model contents through the EMF reflective API.
 * 
 */
public class ModelUtil {

	static {
		// Register the proper EMF resource factories
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());
		m.put("arch", new XMIResourceFactoryImpl());
		m.put("ecore", new EcoreResourceFactoryImpl());
	}
/**
	public static List<Feature> getAllFeatures(Class clazz) {
		List<Feature> properties = new ArrayList<Feature>();
		properties.addAll(clazz.getAttributes());
		properties.addAll(clazz.getOperations());

		return properties;
	}

	public static List<NamedElement> getAllElements(Model model, EClass eClass) {
		List<NamedElement> elements = new ArrayList<NamedElement>();
		for (Iterator<EObject> iterator = model.eAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			if (eObject.eClass() == eClass) {
				elements.add((NamedElement) eObject);
			}
		}
		return elements;
	}

	public static String getXmiId(EObject eObject) {
		assert (eObject != null && eObject.eResource() instanceof XMIResource);

		String objectID = ((XMIResource) eObject.eResource()).getID(eObject);

		return objectID;
	}

	public static Model loadModel(String path) {
		ResourceSet resourceSet = new ResourceSetImpl();
		UMLResourcesUtil.init(resourceSet);
		Resource resource = resourceSet.getResource(URI.createFileURI(path), true);
		Model model = (Model) resource.getContents().get(0);

		return model;
	}

	public static void saveModel(Model model, String path) {
		System.out.println(path);
		ResourceSet resourceSet = new ResourceSetImpl();
		UMLResourcesUtil.init(resourceSet);
		Resource resource = resourceSet.createResource(URI.createFileURI(path));
		resource.getContents().add(model);

		// now save the content.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	**/
}
