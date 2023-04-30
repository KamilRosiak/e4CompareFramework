package de.tu_bs.cs.isf.familymining.ppu_iec.parser.xml.processes;

import static de.tu_bs.cs.isf.familymining.ppu_iec.parser.xml.util.XMLStringTable.MODEL_INSTANCE_EXTENSION;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parser.xml.util.XMLStringTable.NODECALLBACK_EXTENSION_ID;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parser.xml.util.XMLStringTable.PARSER_EXTENSION_ID;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parser.xml.util.XMLStringTable.PARSER_PROCESS_EXTENSION_ID;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import de.tu_bs.cs.isf.e4cf.parser.base.INodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.IParser;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;
import de.tu_bs.cs.isf.e4cf.parser.base.abstracts.AbstractParserProcess;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;

public class IECProjectConverter extends AbstractParserProcess<Configuration> {

	public IECProjectConverter() {
		super(PARSER_PROCESS_EXTENSION_ID);
	}

	/**
	 * Parses the <b>xmlFile</b> into a metamodel instance.
	 * 
	 * @param xmlFile the input file
	 * @return A metamodel instance represented by the input file
	 * @throws IOException when a read on the file fails
	 */
	public Configuration parse(Path xmlFile) throws IOException {
		// prepare callable
		try {
			IParser xmlParser = getParserFactory().createParser(PARSER_EXTENSION_ID, null);
			INodeCallback ppuFillCallback = getParserFactory().createNodeCallable(NODECALLBACK_EXTENSION_ID);
			xmlParser.removeAllNodeCallables();
			xmlParser.addNodeCallable(ppuFillCallback);
			// start parser and store result in for the corresponding scenario map
			String xmlString = getFileContent(xmlFile, StandardCharsets.UTF_8);
			xmlParser.parse(xmlString);
			// store the output path and scenario object
			Configuration configurationModel = (Configuration) ppuFillCallback.getResult();
			return configurationModel;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getModelFilename(Configuration modelInstance) {
		return modelInstance.getIdentifier();
	}

	@Override
	public List<String> getCompatibleFileFormats() {
		return Arrays.asList(ParserType.XML.getExtension());
	}

	@Override
	public String getOutputFileFormat() {
		return MODEL_INSTANCE_EXTENSION;
	}

	@Override
	public ParserType getType() {
		return ParserType.XML;
	}
}
