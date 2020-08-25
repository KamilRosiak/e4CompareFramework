package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;

public class MovedResourceResolver implements ProxyResolver {

	private URI[] lookupUris;
	
	/**
	 * Stores the mappings of proxy URIs to their moved locations.
	 * The cache stores fragment-less URIs as we only need the locations of resources; 
	 */
	private Map<URI, URI> uriCache;
	
	public MovedResourceResolver(URI[] lookupUris) {
		this.lookupUris = lookupUris;
		this.uriCache = new HashMap<>();
	}
	
	@Override
	public URI resolve(URI proxyUri) {
		// check the cache for existing entries
		URI targetUri = uriCache.get(proxyUri.trimFragment());
		if (targetUri != null) {
			return targetUri.appendFragment(proxyUri.fragment());
		}
		
		// Consult the lookup directories to find a file with the same name as the proxy's last segment
		for (URI lookupUri : lookupUris) {
			Path lookupPath = Paths.get(lookupUri.toFileString());
			Path targetPath = findInPathDirectory(lookupPath, (filePath) -> {
				if (filePath.endsWith(proxyUri.lastSegment())) {
					// MATCH - we consider the file the actual target of the proxy
					return filePath.toAbsolutePath();
				} else {
					return null;
				}
			});
			
			if (targetPath != null) {
				targetUri = URI.createFileURI(targetPath.toString());
				uriCache.put(proxyUri.trimFragment(), targetUri.trimFragment());
				
				return targetUri.appendFragment(proxyUri.fragment());
			}
		}
		
		return null;
	}

	/**
	 * Searches a path specified by the search function in the hierarchy of a directory.
	 * The first path that is accepted by the search function is returned.
	 * 
	 * @param root
	 * @param searchFunction
	 * @return <tt>path</tt> if the search function accepts the path and null if there is no such path
	 */
	private Path findInPathDirectory(Path root, Function<Path, Path> searchFunction) {
		if (!Files.isDirectory(root)) {
			if (Files.isRegularFile(root)) {
				return searchFunction.apply(root);				
			}
		}
		
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(root)) {
			for (Path child : dirStream) {
				Path path = findInPathDirectory(child, searchFunction);
				if (path != null) {
					return path;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
