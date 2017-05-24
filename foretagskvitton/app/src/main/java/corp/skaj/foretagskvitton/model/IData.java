package corp.skaj.foretagskvitton.model;

public interface IData {

    String IMAGE_URI_KEY = "uri_for_image_in_wizard";
    String COLLECTED_STRINGS_KEY = "key_for_collected_strings";

    <T> void writeData(String key, T t);

    <T> T readData(String key, Class<T> classOfT);

    void removeData(String key);

    void clearData();

    boolean initDefaultUser();

    PurchaseList getPurchases(User user);

    User getUser();

    boolean saveUser();
}