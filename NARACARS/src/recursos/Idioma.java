package recursos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * Esta clase define un idioma mediante un properties
 * 
 * @author Ander
 */


public class Idioma extends Observable {

	Properties idioma;
	
	/**
	 * Constructor de la clase idioma, carga los properties del idioma
	 * @param idiomaPredeterminado
	 */
	public  Idioma(String idiomaPredeterminado) {
		idioma = new Properties();
		try {
			idioma.load(new FileInputStream("idiomas/"+idiomaPredeterminado));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @see java.util.Hashtable#clear()
	 */
	public void clear() {
		idioma.clear();
		setChanged();
		notifyObservers();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#clone()
	 */
	public Object clone() {
		return idioma.clone();
	}

	/**
	 * @param key
	 * @param remappingFunction
	 * @return
	 * @see java.util.Hashtable#compute(java.lang.Object, java.util.function.BiFunction)
	 */
	public Object compute(Object key, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return idioma.compute(key, remappingFunction);
	}

	/**
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @see java.util.Hashtable#computeIfAbsent(java.lang.Object, java.util.function.Function)
	 */
	public Object computeIfAbsent(Object key, Function<? super Object, ? extends Object> mappingFunction) {
		return idioma.computeIfAbsent(key, mappingFunction);
	}

	/**
	 * @param key
	 * @param remappingFunction
	 * @return
	 * @see java.util.Hashtable#computeIfPresent(java.lang.Object, java.util.function.BiFunction)
	 */
	public Object computeIfPresent(Object key,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return idioma.computeIfPresent(key, remappingFunction);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Hashtable#contains(java.lang.Object)
	 */
	public boolean contains(Object value) {
		return idioma.contains(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return idioma.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Hashtable#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return idioma.containsValue(value);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#elements()
	 */
	public Enumeration<Object> elements() {
		return idioma.elements();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#entrySet()
	 */
	public Set<Entry<Object, Object>> entrySet() {
		return idioma.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Hashtable#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return idioma.equals(o);
	}

	/**
	 * @param action
	 * @see java.util.Hashtable#forEach(java.util.function.BiConsumer)
	 */
	public void forEach(BiConsumer<? super Object, ? super Object> action) {
		idioma.forEach(action);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return idioma.get(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see java.util.Hashtable#getOrDefault(java.lang.Object, java.lang.Object)
	 */
	public Object getOrDefault(Object key, Object defaultValue) {
		return idioma.getOrDefault(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	public String getProperty(String key, String defaultValue) {
		return idioma.getProperty(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	public String getProperty(String key) {
		return idioma.getProperty(key);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#hashCode()
	 */
	public int hashCode() {
		return idioma.hashCode();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#isEmpty()
	 */
	public boolean isEmpty() {
		return idioma.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#keySet()
	 */
	public Set<Object> keySet() {
		return idioma.keySet();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#keys()
	 */
	public Enumeration<Object> keys() {
		return idioma.keys();
	}

	/**
	 * @param out
	 * @see java.util.Properties#list(java.io.PrintStream)
	 */
	public void list(PrintStream out) {
		idioma.list(out);
	}

	/**
	 * @param out
	 * @see java.util.Properties#list(java.io.PrintWriter)
	 */
	public void list(PrintWriter out) {
		idioma.list(out);
	}

	/**
	 * @param inStream
	 * @throws IOException
	 * @see java.util.Properties#load(java.io.InputStream)
	 */
	public void load(InputStream inStream) throws IOException {
		idioma.load(inStream);
		setChanged();
		notifyObservers();
	}

	/**
	 * @param reader
	 * @throws IOException
	 * @see java.util.Properties#load(java.io.Reader)
	 */
	public void load(Reader reader) throws IOException {
		idioma.load(reader);
		setChanged();
		notifyObservers();
	}

	/**
	 * @param in
	 * @throws IOException
	 * @throws InvalidPropertiesFormatException
	 * @see java.util.Properties#loadFromXML(java.io.InputStream)
	 */
	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		idioma.loadFromXML(in);
		setChanged();
		notifyObservers();
	}

	/**
	 * @param key
	 * @param value
	 * @param remappingFunction
	 * @return
	 * @see java.util.Hashtable#merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction)
	 */
	public Object merge(Object key, Object value,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return idioma.merge(key, value, remappingFunction);
	}

	/**
	 * @return
	 * @see java.util.Properties#propertyNames()
	 */
	public Enumeration<?> propertyNames() {
		return idioma.propertyNames();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		Object o = idioma.put(key, value);
		setChanged();
		notifyObservers();
		return o;
	}

	/**
	 * @param t
	 * @see java.util.Hashtable#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends Object, ? extends Object> t) {
		idioma.putAll(t);
		setChanged();
		notifyObservers();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	public Object putIfAbsent(Object key, Object value) {
		Object o = idioma.putIfAbsent(key, value);
		setChanged();
		notifyObservers();
		return o;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#remove(java.lang.Object, java.lang.Object)
	 */
	public boolean remove(Object key, Object value) {
		Boolean b = idioma.remove(key, value);
		setChanged();
		notifyObservers();
		return b;
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
		Object b = idioma.remove(key);
		setChanged();
		notifyObservers();
		return b;
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 * @see java.util.Hashtable#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean replace(Object key, Object oldValue, Object newValue) {
		Boolean b = idioma.replace(key, oldValue, newValue);
		setChanged();
		notifyObservers();
		return b;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#replace(java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object key, Object value) {
		Object b = idioma.replace(key, value);
		setChanged();
		notifyObservers();
		return b;
	}

	/**
	 * @param function
	 * @see java.util.Hashtable#replaceAll(java.util.function.BiFunction)
	 */
	public void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
		idioma.replaceAll(function);
		setChanged();
		notifyObservers();
	}

	/**
	 * @param out
	 * @param comments
	 * @deprecated
	 * @see java.util.Properties#save(java.io.OutputStream, java.lang.String)
	 */
	public void save(OutputStream out, String comments) {
		idioma.save(out, comments);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	public Object setProperty(String key, String value) {
		Object o = idioma.setProperty(key, value);
		setChanged();
		notifyObservers();
		return o;
	}

	/**
	 * @return
	 * @see java.util.Hashtable#size()
	 */
	public int size() {
		return idioma.size();
	}

	/**
	 * @param out
	 * @param comments
	 * @throws IOException
	 * @see java.util.Properties#store(java.io.OutputStream, java.lang.String)
	 */
	public void store(OutputStream out, String comments) throws IOException {
		idioma.store(out, comments);
	}

	/**
	 * @param writer
	 * @param comments
	 * @throws IOException
	 * @see java.util.Properties#store(java.io.Writer, java.lang.String)
	 */
	public void store(Writer writer, String comments) throws IOException {
		idioma.store(writer, comments);
	}

	/**
	 * @param os
	 * @param comment
	 * @param encoding
	 * @throws IOException
	 * @see java.util.Properties#storeToXML(java.io.OutputStream, java.lang.String, java.lang.String)
	 */
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
		idioma.storeToXML(os, comment, encoding);
	}

	/**
	 * @param os
	 * @param comment
	 * @throws IOException
	 * @see java.util.Properties#storeToXML(java.io.OutputStream, java.lang.String)
	 */
	public void storeToXML(OutputStream os, String comment) throws IOException {
		idioma.storeToXML(os, comment);
	}

	/**
	 * @return
	 * @see java.util.Properties#stringPropertyNames()
	 */
	public Set<String> stringPropertyNames() {
		return idioma.stringPropertyNames();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#toString()
	 */
	public String toString() {
		return idioma.toString();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#values()
	 */
	public Collection<Object> values() {
		return idioma.values();
	}
	
	
	
}
