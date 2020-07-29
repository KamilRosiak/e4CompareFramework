package de.tu_bs.cs.isf.e4cf.core.file_structure.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class FileHandlingUtility {
	
	static public String getAbsolutePath(Path p) {
		return p.toAbsolutePath().toString();
	}

	static public boolean isFile(Path p) {
		return Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS);
	}
	
	static public boolean isDirectory(Path p) {
		return Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS);
	}
	
	static public String getFilename(Path p) {
		return p.getFileName().toString();
	}
	
	static public String getFileExtension(Path p) {
		String path = p.getFileName().toString();
		String fileExtension = path.substring(path.lastIndexOf('.') + 1);
		return fileExtension;
	}
	
	static public Path getPath(FileTreeElement element) throws NullPointerException, InvalidPathException {
		return Paths.get(element.getAbsolutePath());		
	}
	
	static public String getFilename(FileTreeElement element) {
		return getPath(element).getFileName().toString();
	}
	
	static public void delete(Path dir) throws IOException {
		if (Files.exists(dir)) {
			FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (e != null) throw e;
					
					// repeat the delete (On windows the delete of sub directories may not be synchronized with program execution)
					boolean repeat = true;
					while (repeat) {
						try {
							Files.delete(dir);
							FileEventLog.getInstance().insertEvent("Directory Deleted on Hard Disk", dir);
							repeat = false;
						} catch (DirectoryNotEmptyException dne) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {
					Files.delete(file);
					FileEventLog.getInstance().insertEvent("File Deleted on Hard Disk", file);
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
					FileEventLog.getInstance().insertEvent("File Visit Failed", file);
					return FileVisitResult.CONTINUE;
				}
			};
			Files.walkFileTree(dir, visitor);	
		}
	}
}
