package org.mouji.stub.java.stubs;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.mouji.common.info.SerializationFormat;
import org.mouji.common.serializer.Serializer;
import org.mouji.stub.java.JsonSerializer;

/**
 * Basic stub class that provides common functionalities between server stub and
 * client stub
 * 
 * @author Salim
 *
 */
public class BasicStub {

	/**
	 * list of supported serializers. Default is included in constructor
	 */
	protected List<Serializer> serializers;

	/**
	 * map of format to serializer instance
	 */
	protected HashMap<SerializationFormat, Serializer> serializerMap;

	public BasicStub(List<Serializer> serializers) {
		boolean hasJson = false;

		this.serializers = serializers;
		// Initializing serializer map
		this.serializerMap = new HashMap<SerializationFormat, Serializer>();
		for (Serializer serializer : serializers) {
			serializerMap.put(serializer.getType(), serializer);
			if (serializer.getType().getName().equals("json")) {
				hasJson = true;
			}
		}

		if (!hasJson) {
			JsonSerializer js = new JsonSerializer();
			this.serializers.add(js);
			serializerMap.put(js.getType(), js);
		}
	}

	/**
	 * return the serializer instance given the SerializationFormat appropriate.
	 * It will return null in absence of a serializer with the given format
	 * 
	 * @param format
	 * @return
	 */
	protected Serializer getSerializer(SerializationFormat format) {
		return serializerMap.get(format);
	}

	/**
	 * return http format string of accepted types
	 * 
	 * @return
	 */
	protected String getAcceptedTypes() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < serializers.size(); i++) {
			sb.append(serializers.get(i).getType().httpFormat());
			if (i != serializers.size() - 1) {
				sb.append(" , ");
			}
		}
		return sb.toString();
	}

	/**
	 * generates a new random id
	 * 
	 * @return
	 */
	public long genRandomId() {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		return rand.nextLong();
	}

}