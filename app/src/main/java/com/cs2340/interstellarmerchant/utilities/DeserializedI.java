package com.cs2340.interstellarmerchant.utilities;

/**
 * Indicates that a special procedure must occur after deserialization
 */
public interface DeserializedI {
    /**
     * Called after the the object is deserialized. The caller must be aware that the object
     * is of this interface type. It WON'T be called automatically
     */
    void afterDeserialized();
}
