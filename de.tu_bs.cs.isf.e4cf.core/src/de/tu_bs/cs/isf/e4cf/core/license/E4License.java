package de.tu_bs.cs.isf.e4cf.core.license;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.ResourcesPlugin;

import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.E4KeyValidation;

/**
 * 
 * @author Schlie
 *
 */
public class E4License {
	private static E4License instance;
	private static boolean isLegit;
	private static boolean licenseFilePresent;
	private static String licenseForm;
	private final long LicenseHashCode = -1188671881855604568L;
	
	private static E4KeyValidation keyValidator;
	
	
	private E4License() {
		licenseForm = "";
		licenseFilePresent = false;
		isLegit = false;
		keyValidator = E4KeyValidation.getInstance();
	}
	
	public static synchronized E4License getInstance () {
	    if (E4License.instance == null) {
	    	E4License.instance = new E4License ();
	    }
	    return E4License.instance;
	  }
	
	public void validateLicense() throws IOException {
		InputStream licenseStream = E4License.class.getResourceAsStream("/src/de/tu_bs/cs/isf/familymining/rcp_e4/core/license/licenseAgreementForm5.lic");
			if (licenseStream != null) {
				licenseFilePresent = true;
			
				BufferedReader br = new BufferedReader(new InputStreamReader(licenseStream));
				
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.isEmpty()) {
						licenseForm += "\n\n";
					} else {
						licenseForm += line;						
					}
				}
				br.close();
				isLegit = verifyHashCodeLegitimacy(licenseForm);
			}	
	}	

	private boolean verifyHashCodeLegitimacy(String string) {
		  long h = 1125899906842597L; // prime
		  int len = string.length();

		  for (int i = 0; i < len; i++) {
		    h = 31*h + string.charAt(i);
		  }
		  return (h == LicenseHashCode) ? true : false;
	}
	
	@SuppressWarnings("static-access")
	public boolean verifySerialKey() {
		String workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		
		FileReader registryReader;
		try {
			registryReader = new FileReader(workspaceLocation+"\\license\\reg.key");
			BufferedReader br = new BufferedReader(registryReader);
			String key = br.readLine();
			
			br.close();
			
			return keyValidator.verifySerialKey(key);
			
		} catch (FileNotFoundException e1) {
			System.out.println("Fatal error: License not found");
			return false;
		} catch (IOException e) {
			System.out.println("Fatal error: License error");
			return false;
		}
	}
	
	public static boolean isLegit() {
		return isLegit;
	}
	
	public static boolean isLicenseFilePresent() {
		return licenseFilePresent;
	}
	
	public static String getLicenseForm() {
		return licenseForm;
	}
}


