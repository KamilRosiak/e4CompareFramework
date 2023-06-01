package de.tu_bs.cs.isf.e4cf.core.io.gson.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class NodeDeserializer implements JsonDeserializer<Node> {

	@SuppressWarnings("rawtypes")
	@Override
	public Node deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject jsonTreeObject = json.getAsJsonObject();
		
		// Create a node and set the uuid and type
		NodeType standardizedNodeType = NodeType.fromString(jsonTreeObject.get("standardizedNodeType").getAsString());
		Node node = new NodeImpl(standardizedNodeType, jsonTreeObject.get("nodeType").getAsString());
		node.setUUID(UUID.fromString(jsonTreeObject.get("uuid").getAsString()));
		node.setVariabilityClass(VariabilityClass.valueOf(jsonTreeObject.get("varClass").getAsString()));
		
		// Handle node attributes		
		JsonArray jsonArrayAttributes = jsonTreeObject.get("attributes").getAsJsonArray();
		
		// Iterate over attributes
		for (JsonElement jsonAttributeElement : jsonArrayAttributes) {
			JsonObject jsonAttributeObject = jsonAttributeElement.getAsJsonObject();
			
			// Iterate over attribute values
			JsonArray jsonArrayAttributeValues = jsonAttributeObject.get("attributeValues").getAsJsonArray();
			List<Value> values = new ArrayList<>();
			for (JsonElement jsonAttributeValueElement : jsonArrayAttributeValues) {
				JsonObject jsonAttributeValueObject = jsonAttributeValueElement.getAsJsonObject();
				
				// For now assume a value attribute of type string
				StringValueImpl valueImpl = new StringValueImpl(jsonAttributeValueObject.get("value").getAsString());
				valueImpl.setUUID(UUID.fromString(jsonAttributeValueObject.get("uuid").getAsString()));
				
				values.add((Value<String>) valueImpl);
			}
			
			// Create a new attribute with the key and values
			node.addAttribute(new AttributeImpl(jsonAttributeObject.get("attributeKey").getAsString(), values));
		}
		
		// Handle children
		JsonArray children = jsonTreeObject.get("children").getAsJsonArray();
		
		// Recursively deserialize children
		for (JsonElement jsonElement : children) {
			Node child = deserialize(jsonElement, type, context);
			node.addChild(child);
		}
		
		return node;
	}

}
