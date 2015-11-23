package org.verapdf;

import org.apache.log4j.Logger;
import org.verapdf.core.FeatureParsingException;
import org.verapdf.features.AbstractICCProfileFeaturesExtractor;
import org.verapdf.features.ICCProfileFeaturesData;
import org.verapdf.features.tools.FeatureTreeNode;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maksim Bezrukov
 */
public class ICCProfileSampleExtractor extends AbstractICCProfileFeaturesExtractor {

	private static final Logger LOGGER = Logger
			.getLogger(ICCProfileSampleExtractor.class);

	@Override
	public List<FeatureTreeNode> getICCProfileFeatures(ICCProfileFeaturesData iccProfileFeaturesData) {
		List<FeatureTreeNode> res = new ArrayList<>();
		try {
			FeatureTreeNode stream = FeatureTreeNode.createRootNode("streamContent");
			stream.setValue(DatatypeConverter.printHexBinary(iccProfileFeaturesData.getStream()));
			res.add(stream);

			byte[] meta = iccProfileFeaturesData.getMetadata();
			FeatureTreeNode metadata = FeatureTreeNode.createRootNode("metadataStreamContent");
			String metaValue = meta == null ? "null" : DatatypeConverter.printHexBinary(meta);
			metadata.setValue(metaValue);
			res.add(metadata);

			String nString = String.valueOf(iccProfileFeaturesData.getN());
			FeatureTreeNode nNode = FeatureTreeNode.createRootNode("nValue");
			nNode.setValue(nString);


		} catch (FeatureParsingException e) {
			LOGGER.error("Some fail in logic", e);
		}
		return res;
	}

	@Override
	public String getID() {
		return "ad7c3157-5865-4e8a-8f47-57f81580b612";
	}

	@Override
	public String getDescription() {
		return "Sample iccprofile extractor.";
	}
}
